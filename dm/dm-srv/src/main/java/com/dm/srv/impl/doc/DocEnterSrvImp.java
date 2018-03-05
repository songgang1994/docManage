package com.dm.srv.impl.doc;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dm.dao.ComDao;
import com.dm.dao.DocBatchDao;
import com.dm.dao.DocEnterDao;
import com.dm.dto.ApprovalDto;
import com.dm.dto.DocDetailInfoDto;
import com.dm.dto.DocFormItemDto;
import com.dm.dto.FileDto;
import com.dm.dto.LevelDeptInfoDto;
import com.dm.dto.LoginDto;
import com.dm.dto.SysCfgDto;
import com.dm.dto.UserDeptDto;
import com.dm.entity.DepartmentInfoEntity;
import com.dm.entity.DocCodeManageEntity;
import com.dm.entity.DocDetailCustomInfoEntity;
import com.dm.entity.DocDetailInfoEntity;
import com.dm.entity.DocDetailItemSettingEntity;
import com.dm.entity.DocFileEntity;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.DocItemsEntity;
import com.dm.entity.DocViewDeptEntity;
import com.dm.entity.SysLogEntity;
import com.dm.entity.UserDeptEntity;
import com.dm.entity.UserEntity;
import com.dm.srv.doc.DocEnterSrv;
import com.dm.srv.impl.BaseSrvImp;
import com.dm.srv.util.DocCommonSrv;
import com.dm.tool.Constant;
import com.dm.tool.StringUtil;

@Service
public class DocEnterSrvImp extends BaseSrvImp implements DocEnterSrv {

	@Autowired
	private DocEnterDao docDao;
	@Autowired
	private DocCommonSrv docCommonSrv;
	@Autowired
	private ComDao comDao;
	@Autowired
	private DocBatchDao docBatchDao;

	private static final String FILE_TYPE_MAIN = "1";
	private static final String FILE_TYPE_SUB = "2";

	@Override
	public Map<String, Object> docTypes() {

		Map<String, Object> ret = new HashMap<>();

		List<DocItemsEntity> items = docDao.docItems();
		Optional<DocItemsEntity> docTypeItem = items.stream()
				.filter(x -> x.getDocumentItemCode().equals("DOCUMENT_ITEM_F00003")).findFirst();
		Optional<DocItemsEntity> docCodeItem = items.stream()
				.filter(x -> x.getDocumentItemCode().equals("DOCUMENT_ITEM_F00001")).findFirst();

		ret.put("docTypeItem", docTypeItem.get());
		ret.put("docCodeItem", docCodeItem.get());
		ret.put("docTypeItemDS", docDao.docTypes());

		return ret;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, String> fetchCode(String documentType, String userId, String ip) {

		Map<String, String> ret = new HashMap<>();

		// -------------------
		// 1. 检查审核流程是否完整
		// -------------------
		if (!docCommonSrv.CheckApprovalProgress(documentType, userId)) {
			// 不完整
			ret.put("result", "error");
			ret.put("message", "未设置文档的最终审核人。");
			return ret;
		}

		// ----------------------------------------------------------
		// 2. 获取文档编号。因为存在多个用户同时获取编号，因此该步骤需要进行同步处理
		// ----------------------------------------------------------
		synchronized (this) {
			// 1) 调用通用函数|取得【FY年度】
			int documentFy = docCommonSrv.getFYYear();

			// 2) 查询文档编号管理表
			DocCodeManageEntity codeManagerEntity = docDao.queryDocCode(documentType, documentFy);

			// FY年度数据存在时，获取文档编号放入【文档编号】，更新文档编号管理表
			String documentCode = "";
			if (codeManagerEntity != null) {

				docDao.increaseLastNo(documentType, documentFy, userId);

				String fCode = docDao.queryFCODE(documentType);
				documentCode = String.format("%s%02d%05d", fCode, documentFy % 100,
						codeManagerEntity.getLastNo().intValue() + 1);

				ret.put("documentCode", documentCode);

			} else {

				docDao.createLastNo(documentType, documentFy, userId);

				String fCode = docDao.queryFCODE(documentType);
				documentCode = String.format("%s%02d%05d", fCode, documentFy % 100, 0);

				ret.put("documentCode", documentCode);
			}

			// 3）插入系统日志信息
			SysLogEntity log = new SysLogEntity();

			Date now = Date.from(Instant.now());
			log.setOprDatetime(now);
			log.setUserId(userId);
			log.setIp(ip);
			log.setDocumentCode(documentCode);
			log.setOprContent(Constant.OPERATE_TYPE_REGISTER);
			log.setCreator(userId);
			log.setCreateDt(now);
			log.setUpdater(userId);
			log.setUpdateDt(now);

			docDao.createLog(log);

			ret.put("result", "success");
		}

		return ret;
	}

	@Override
	public Map<String, Object> form(String documentType, String documentCode, String mode, LoginDto user) {

		Map<String, Object> ret = new HashMap<>();

		// - 固定项目 BEGIN -------------------- //
		// 查询画面需要显示的项目信息
		List<DocFormItemDto> formItems = docDao.displayItems(documentType);

		ret.put("documentType", documentType);

		// 非 From To 项目 和 From 项目
		List<DocFormItemDto> normalItems = formItems.stream()
				.filter(x -> (x.getIsFromToItem() == null || x.getDocumentItemNo().intValue() == 0)
						&& !x.getDocumentItemCode().equals(Constant.DOCUMENT_ITEM_F00001)
						&& !x.getDocumentItemCode().equals(Constant.DOCUMENT_ITEM_F00003))
				.collect(Collectors.toList());

		// To 项目过滤
		Map<String, List<DocFormItemDto>> extraItems = formItems.stream()
				.filter(x -> x.getIsFromToItem() != null && x.getDocumentItemNo().intValue() >= 1)
				.collect(Collectors.groupingBy(x -> x.getDocumentItemCode()));
		for (int i = 0; i < normalItems.size(); i++) {
			if (i != normalItems.size() - 1) {
				if (normalItems.get(i).getLayoutCol().equals(new BigDecimal("1"))
						&& normalItems.get(i).getLayoutCol().equals(normalItems.get(i + 1).getLayoutCol())) {
					normalItems.get(i).setLeftLayoutCol(true);
				}
			} else if (normalItems.get(i).getLayoutCol().equals(new BigDecimal("1"))) {
				normalItems.get(i).setLeftLayoutCol(true);
			}
		}
		ret.put("formItems", normalItems);
		ret.put("extraItems", extraItems);

		// 将上述查询结果中数据源编号不为空的数据源编号放入【数据源编号】，查询数据源信息
		List<String> ds = formItems.stream()
				.filter(x -> x.getDocumentItemSourceCode() != null && x.getDocumentItemSourceCode().trim().length() > 0)
				.map(x -> x.getDocumentItemSourceCode()).collect(Collectors.toList());
		if (!ds.isEmpty()) {

			List<DocItemDataSourceCodeEntity> dsList = docDao.dataSource(ds);

			Map<String, List<DocItemDataSourceCodeEntity>> groups = dsList.stream()
					.collect(Collectors.groupingBy(DocItemDataSourceCodeEntity::getDocumentItemSourceCode));

			formItems.forEach(x -> {
				if (!StringUtil.isEmpty(x.getDocumentItemSourceCode())) {
					x.setDataSourceList(groups.get(x.getDocumentItemSourceCode()));
				}
			});
		}

		// 设置无数据项项目
		formItems.forEach(x -> {
			if (x.getDocumentItemCode().equals(Constant.DOCUMENT_ITEM_F00008)) {// 文档状态
				x.setParamMstList(docDao.paramMst(Constant.DOC_STATUS));
			}

		});

		// 设置画面显示
		switch (mode) {
		case "0": // 详细模式
			formItems.forEach(x -> x.readonly = true);
			break;
		case "1": // 修改模式
			formItems.forEach(x -> {
				if (Arrays.asList(new String[] { Constant.DOCUMENT_ITEM_F00004, // 担当部门
						Constant.DOCUMENT_ITEM_F00005, // 责任人
						Constant.DOCUMENT_ITEM_F00008 } // 文档状态
				).contains(x.getDocumentItemCode())) {
					x.readonly = true;
				} else {
					x.readonly = false;
				}
			});

			break;
		case "2":// 新增模式
			formItems.forEach(x -> {
				switch (x.getDocumentItemCode()) {
				case Constant.DOCUMENT_ITEM_F00004:// 担当部门
					List<UserDeptEntity> userDept = user.getUserDept();
					String deptName = "", deptId = "";
					for (UserDeptEntity d : userDept) {
						String dn = docDao.getdeptName(d.getDeptId());
						if (deptName.length() > 0) {
							deptName = deptName.concat(",");
						}
						deptName = deptName.concat(dn);
						if (deptId.length() > 0) {
							deptId = deptId.concat(",");
						}
						deptId = deptId.concat(d.getDeptId());
					}
					x.setValue(deptName);
					x.setKey(deptId);
					break;
				case Constant.DOCUMENT_ITEM_F00005:// 责任人
					x.setValue(user.getUserName());
					x.setKey(user.getUserId());
					break;
				case Constant.DOCUMENT_ITEM_F00008:// 文档状态
					x.setValue(String.valueOf(Constant.DOC_STATUS_MAKING));
					x.readonly = true;
					break;
				case Constant.DOCUMENT_ITEM_F00009:// 文档可查看部门
					// 获取sysConfig数据
					WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
					ServletContext servletContext = webApplicationContext.getServletContext();

					SysCfgDto sysCfgInfo = (SysCfgDto) servletContext.getAttribute("SysCfgInfo");
					// 获取技术企划室id
					String techId = sysCfgInfo.getTechnicalPlanRoom();
					// 获取用户所属部门id
					List<String> deptIds = user.getUserDept().stream().map(y -> y.getDeptId())
							.collect(Collectors.toList());
					String deptName1 = "", deptId1 = "";
					// 登录用户为技术企画室
					if (deptIds.contains(techId)) {
						// 获取最高级部门
						List<DepartmentInfoEntity> topDepts = comDao.getDeparts("1", null, 0, Integer.MAX_VALUE);

						for (DepartmentInfoEntity y : topDepts) {
							if (deptName1.length() > 0) {
								deptName1 = deptName1.concat(",");
							}
							deptName1 = deptName1.concat(y.getDeptName());
							if (deptId1.length() > 0) {
								deptId1 = deptId1.concat(",");
							}
							deptId1 = deptId1.concat(y.getDeptId());
						}

					} else {// 其他用户
						List<LevelDeptInfoDto> depts = docDao.getLevelDeptInfo(user.getUserDept());
						for (LevelDeptInfoDto y : depts) {
							if (deptName1.length() > 0) {
								deptName1 = deptName1.concat(",");
							}
							deptName1 = deptName1.concat(y.getDeptName());
							if (deptId1.length() > 0) {
								deptId1 = deptId1.concat(",");
							}
							deptId1 = deptId1.concat(y.getDeptId());
						}
					}
					x.setValue(deptName1);
					x.setKey(deptId1);
					break;
				case Constant.DOCUMENT_ITEM_F00001:// 文档编号
					x.setValue(documentCode);
					x.readonly = true;
					break;
				}
			});
			break;
		case "3":// 审核模式
			formItems.forEach(x -> {
				if ("DOCUMENT_ITEM_F00010".equals(x.getDocumentItemCode())) {
					x.readonly = false;
				} else {
					x.readonly = true;
				}
			});
			break;
		}

		return ret;
	}

	@Override
	public Map<String, Object> detail(String documentCode, String mode, LoginDto user) {

		Map<String, Object> ret = new HashMap<>();

		// 查询文档固定数据项
		DocDetailInfoEntity docDetailInfoEntity = docDao.documentInfo(documentCode);
		ret.put("documentCode", documentCode);

		String documentType = docDetailInfoEntity.getDocumentType();
		ret.putAll(this.form(documentType, documentCode, mode, user));
		ret.putAll(this.docTypes());
		// 填充表单数据
		@SuppressWarnings("unchecked")
		List<DocFormItemDto> formItems = (List<DocFormItemDto>) ret.get("formItems");

		// 查询结果中所有符合以下条件数据项的DB项目字段名放入【自定数据项DB字段名】
		List<String> customeNames = formItems.stream()
				.filter(x -> x.getIsFixItem().equals("0") && x.getIsBlankItem().equals("0")).map(x -> x.getDbItemName())
				.collect(Collectors.toList());
		// 查询文档自定义数据项数据
		DocDetailCustomInfoEntity custome = docDao.customItems(documentCode, customeNames);
		ret.put("docDetailInfo", docDetailInfoEntity);

		formItems.forEach(x -> {
			switch (x.getDocumentItemCode()) {
			case Constant.DOCUMENT_ITEM_F00000: // 空白项目
				break;
			case Constant.DOCUMENT_ITEM_F00001: // 文档编号
				x.setValue(docDetailInfoEntity.getDocumentCode());
				break;
			case Constant.DOCUMENT_ITEM_F00002: // 旧文件编号
				x.setValue(docDetailInfoEntity.getDocumentOldCode());
				break;
			case Constant.DOCUMENT_ITEM_F00003: // 文档类型
				x.setValue(docDetailInfoEntity.getDocumentType());
				break;
			case Constant.DOCUMENT_ITEM_F00004: // 担当部门
				x.setKey(docDetailInfoEntity.getDeptId());
				if (docDetailInfoEntity.getDeptId() != null && !docDetailInfoEntity.getDeptId().isEmpty()) {
					x.setValue(docDao.getdeptName(docDetailInfoEntity.getDeptId()));
				}

				break;
			case Constant.DOCUMENT_ITEM_F00005: // 责任人
				x.setKey(docDetailInfoEntity.getDirector());
				if (docDetailInfoEntity.getDirector() != null && !docDetailInfoEntity.getDirector().isEmpty()) {
					x.setValue(docDao.getUserEntity(docDetailInfoEntity.getDirector()).getUserName());
				}
				break;
			case Constant.DOCUMENT_ITEM_F00006: // 主文件
				break;
			case Constant.DOCUMENT_ITEM_F00007: // 子文件
				break;
			case Constant.DOCUMENT_ITEM_F00008: // 文档状态
				x.setValue(docDetailInfoEntity.getApprovalStatus());
				break;
			case Constant.DOCUMENT_ITEM_F00009: // 文档可查看部门
				List<DocViewDeptEntity> deptIds = docDao.getDocViewDept(documentCode);
				// 获取一级部门
				List<DepartmentInfoEntity> topDepts = comDao.getDeparts("1", null, 0, Integer.MAX_VALUE);
				String deptId = "", deptName = "";
				for (DepartmentInfoEntity y : topDepts) {
					for (DocViewDeptEntity z : deptIds) {
						if (y.getDeptId().equals(z.getDeptId())) {
							if (deptId.length() > 0) {
								deptId = deptId.concat(",");
							}
							deptId = deptId.concat(y.getDeptId());
							if (deptName.length() > 0) {
								deptName = deptName.concat(",");
							}
							deptName = deptName.concat(docDao.getdeptName(y.getDeptId()));

						}
					}
				}
				x.setKey(deptId);
				x.setValue(deptName);
				break;
			case Constant.DOCUMENT_ITEM_F000011:// 审核部门
				x.setKey(docDetailInfoEntity.getApprovalDeptId());
				if (docDetailInfoEntity.getApprovalDeptId() != null
						&& !docDetailInfoEntity.getApprovalDeptId().isEmpty()) {
					x.setValue(docDao.getdeptName(docDetailInfoEntity.getApprovalDeptId()));
				}
				break;
			case Constant.DOCUMENT_ITEM_F000012: // 审核意见
				List<ApprovalDto> approves = docDao.getApprove(documentCode);
				if (approves != null && approves.size() > 0) {
					StringBuilder sb = new StringBuilder("");
					DateFormat dFormat = new SimpleDateFormat("yyyy/MM/dd");
					for (ApprovalDto y : approves) {//
						sb.append(y.getUserName()).append(" ").append(dFormat.format(y.getApproveDate()))
								.append(":\r\n").append(y.getApproveOpinion()).append("\r\n");
					}
					x.setValue(sb.toString());
				} else {
					x.setValue("");
				}
				break;
			default:
				// 自定义项目
				String dbColumn = x.getDbItemName();
				if (dbColumn.startsWith("CUSTOME_ITEM")) {
					try {
						x.setValue(custome.getField(dbColumn));
					} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
							| SecurityException e) {
						e.printStackTrace();
						break;
					}

					if (x.getValue() != null && !x.getValue().isEmpty()) {
						switch (x.getDocumentItemType()) {
						case Constant.ITEM_TYPE_CHECKBOX:
							;
						case Constant.ITEM_TYPE_RADIO:
							;
						case Constant.ITEM_TYPE_SELECT:
							List<String> values = Arrays.asList(x.getValue().split(",")).stream()
									.filter(y -> !StringUtil.isEmpty(y)).map(String::trim).collect(Collectors.toList());
							x.setValues(values);
							break;
						case Constant.ITEM_TYPE_POP_USER:
							x.setKey(x.getValue());
							if (x.getValue() != null && !x.getValue().isEmpty()) {
								x.setValue(docDao.getUserEntity(x.getValue()).getUserName());
							}
							break;
						case Constant.ITEM_TYPE_POP_DEPT:
							x.setKey(x.getValue());
							if (x.getValue() != null && !x.getValue().isEmpty()) {
								x.setValue(docDao.getdeptName(x.getValue()));
							}

							break;
						case Constant.ITEM_TYPE_POP_USER_MULT:
							String[] userIds = x.getValue().split(",");
							String userId = "", userName = "";
							for (String s : userIds) {
								if (userId.length() > 0) {
									userId = userId.concat(",");
								}
								userId = userId.concat(s);
								if (userName.length() > 0) {
									userName = userName.concat(",");
								}
								userName = userName.concat(docDao.getUserEntity(s).getUserName());
							}
							x.setKey(userId);
							x.setValue(userName);
							break;
						case Constant.ITEM_TYPE_POP_DEPT_MULT:
							String[] deptIds1 = x.getValue().split(",");
							String deptId1 = "", deptName1 = "";
							for (String s : deptIds1) {
								if (deptId1.length() > 0) {
									deptId1 = deptId1.concat(",");
								}
								deptId1 = deptId1.concat(s);
								if (deptName1.length() > 0) {
									deptName1 = deptName1.concat(",");
								}
								deptName1 = deptName1.concat(docDao.getdeptName(s));
							}

							x.setKey(deptId1);
							x.setValue(deptName1);
							break;
						}

					}

				}
			}
		});

		@SuppressWarnings("unchecked")
		Map<String, List<DocFormItemDto>> extraItems = (Map<String, List<DocFormItemDto>>) ret.get("extraItems");
		// 查询结果中所有符合以下条件数据项的DB项目字段名放入【自定数据项DB字段名】

		// to项目赋值
		for (String key : extraItems.keySet()) {
			List<DocFormItemDto> extraItem = extraItems.get(key);
			List<String> customeNames1 = extraItem.stream()
					.filter(x -> x.getIsFixItem().equals("0") && x.getIsBlankItem().equals("0"))
					.map(x -> x.getDbItemName()).collect(Collectors.toList());

			// 查询文档自定义数据项数据
			DocDetailCustomInfoEntity custome1 = docDao.customItems(documentCode, customeNames1);
			extraItem.forEach(x -> {
				// 自定义项目
				String dbColumn = x.getDbItemName();
				if (dbColumn.startsWith("CUSTOME_ITEM")) {
					try {
						x.setValue(custome1.getField(dbColumn));
					} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
							| SecurityException e) {
						e.printStackTrace();
					}
				}
			});
		}

		// - 文件 BEGIN ------------------------ //
		List<DocFileEntity> files = docDao.files(documentCode);
		List<DocFileEntity> mainFileList = files.stream().filter(x -> "1".equals(x.getFileType()))
				.collect(Collectors.toList());
		if (!mainFileList.isEmpty()) {
			ret.put("mainFile", mainFileList.get(0));
		}
		List<DocFileEntity> subFilesList = files.stream().filter(x -> "2".equals(x.getFileType()))
				.collect(Collectors.toList());
		ret.put("subFiles", subFilesList);
		// - 文件 END -------------------------- //

		return ret;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save(DocDetailInfoDto doc, DocDetailCustomInfoEntity custome, CommonsMultipartFile mainFile,
			List<CommonsMultipartFile> subFileList, String ip, List<UserDeptEntity> depts, String isCommit) {

		if (isCommit.equals("true")) {// 提交操作
			doc.setApprovalStatus(Integer.toString(Constant.DOC_STATUS_AUDITING));
		}

		// 文档管理表（文档数据项．是否固定项目 = 1的数据项）
		docDao.addDocDetailInfo(doc);

		// 文档自定义数据项表（文档数据项．是否固定项目 = 0的数据项）
		docDao.addDocDetailCustomInfo(custome);

		// 文档可见部门管理表（画面上有此项目时）
		if (!StringUtil.isEmpty(doc.getDocReadDeptId())) {
			// 获取文档可查看部门id
			String[] ids = null;
			if (doc.getDocReadDeptId().indexOf(",") != -1) {// 多个文档可看部门
				ids = doc.getDocReadDeptId().split(",");
			} else {// 文档可看部门唯一
				ids = new String[1];
				ids[0] = doc.getDocReadDeptId();
			}
			// 获取一级部门及其所属部门
			List<LevelDeptInfoDto> subDepts = docDao.getSubDeptInfo(ids);
			// 获取最高级部门
			List<DepartmentInfoEntity> topDepts = comDao.getDeparts("0", null, 0, Integer.MAX_VALUE);
			List<String> deptIds = new ArrayList<>();
			for (LevelDeptInfoDto x : subDepts) {
				deptIds.add(x.getDeptId());
			}
			for (DepartmentInfoEntity x : topDepts) {
				deptIds.add(x.getDeptId());
			}

			// 去处重复的部门id
			HashSet<String> h = new HashSet<String>(deptIds);
			deptIds.clear();
			deptIds.addAll(h);

			deptIds.forEach(x -> {
				DocViewDeptEntity viewDept = new DocViewDeptEntity();
				viewDept.setDocumentCode(doc.getDocumentCode());
				viewDept.setDeptId(x);
				viewDept.setCreator(doc.getCreator());
				docDao.addViewDept(viewDept);
			});
		}

		// 文件
		// 计算差分
		// List<DocFileEntity> origFiles = docDao.files(doc.getDocumentCode());

		List<DocFileEntity> files = new ArrayList<>();
		// 主文件
		if (mainFile != null && !mainFile.isEmpty()) {
			DocFileEntity f = new DocFileEntity();
			f.setDocumentCode(doc.getDocumentCode());
			f.setFileType(FILE_TYPE_MAIN);
			f.setFileName(doc.getDocumentCode());
			f.setFileContent(mainFile.getBytes());
			f.setCreator(doc.getCreator());
			files.add(f);
		}

		// 子文件
		for (CommonsMultipartFile subFile : subFileList) {

			if (subFile == null || subFile.isEmpty())
				continue;
			;

			DocFileEntity f = new DocFileEntity();
			f.setDocumentCode(doc.getDocumentCode());
			f.setFileType(FILE_TYPE_SUB);
			f.setFileName(subFile.getOriginalFilename());
			f.setFileContent(subFile.getBytes());
			f.setCreator(doc.getCreator());
			files.add(f);
		}

		if (!files.isEmpty()) {
			files.stream().forEach(x -> docDao.addFile(x));
		}

		// 插入log
		SysLogEntity log = new SysLogEntity();

		Date now = Date.from(Instant.now());
		log.setOprDatetime(now);
		log.setUserId(doc.getCreator());
		log.setIp(ip);
		log.setDocumentCode(doc.getDocumentCode());
		log.setOprContent(Constant.OPERATE_TYPE_FILE_UPLOAD);
		log.setCreator(doc.getCreator());
		log.setCreateDt(now);
		log.setUpdater(doc.getCreator());
		log.setUpdateDt(now);

		docDao.createLog(log);

		if (isCommit.equals("true")) {// 提交操作
			log.setOprContent(Constant.OPERATE_TYPE_REGISTER);
			docDao.createLog(log);
		}
	}

	@Override
	public boolean checkDocOldCode(String documentOldCode, String documentCode) {
		return 0 == docDao.checkDocOldCode(documentOldCode, documentCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(DocDetailInfoDto doc, DocDetailCustomInfoEntity custome, FileDto fileDto, String ip,
			List<UserDeptEntity> depts, String isCommit) {

		if (isCommit.equals("true")) {// 提交操作
			doc.setApprovalStatus(Integer.toString(Constant.DOC_STATUS_AUDITING));
		}

		// 文档管理表（文档数据项．是否固定项目 = 1的数据项）
		docDao.updateDocDetailInfo(doc);

		// 文档自定义数据项表（文档数据项．是否固定项目 = 0的数据项）
		docDao.updateDocDetailCustomInfo(custome);

		// 文档可见部门管理表（画面上有此项目时）
		if (!StringUtil.isEmpty(doc.getDocReadDeptId())) {

			// 1. 删除文档可见部门管理表
			docDao.deleteViewDept(doc.getDocumentCode());

			// 2. 插入文档可见部门管理表
			// 获取文档可查看部门id
			String[] ids = null;
			if (doc.getDocReadDeptId().indexOf(",") != -1) {// 多个文档可看部门
				ids = doc.getDocReadDeptId().split(",");
			} else {// 文档可看部门唯一
				ids = new String[1];
				ids[0] = doc.getDocReadDeptId();
			}
			// 获取一级部门及其所属部门
			List<LevelDeptInfoDto> subDepts = docDao.getSubDeptInfo(ids);
			// 获取最高级部门
			List<DepartmentInfoEntity> topDepts = comDao.getDeparts("0", null, 0, Integer.MAX_VALUE);
			List<String> deptIds = new ArrayList<>();
			for (LevelDeptInfoDto x : subDepts) {
				deptIds.add(x.getDeptId());
			}
			for (DepartmentInfoEntity x : topDepts) {
				deptIds.add(x.getDeptId());
			}

			// 去处重复的部门id
			HashSet<String> h = new HashSet<String>(deptIds);
			deptIds.clear();
			deptIds.addAll(h);

			deptIds.forEach(x -> {
				DocViewDeptEntity viewDept = new DocViewDeptEntity();
				viewDept.setDocumentCode(doc.getDocumentCode());
				viewDept.setDeptId(x);
				viewDept.setCreator(doc.getCreator());
				docDao.addViewDept(viewDept);
			});
		}

		List<DocFileEntity> files = new ArrayList<>();// 新增文件
		List<DocFileEntity> updfiles = new ArrayList<>();// 更新文件
		List<DocFileEntity> delfiles = new ArrayList<>();// 删除文件

		// log设置
				SysLogEntity log = new SysLogEntity();
				Date now = Date.from(Instant.now());
				log.setOprDatetime(now);
				log.setUserId(doc.getCreator());
				log.setIp(ip);
				log.setDocumentCode(doc.getDocumentCode());
				log.setCreator(doc.getCreator());
				log.setCreateDt(now);
				log.setUpdater(doc.getCreator());
				log.setUpdateDt(now);

		// 主文件处理
		CommonsMultipartFile mainFile = fileDto.getMainFile();
		DocFileEntity mf = docDao.mainFile(doc.getDocumentCode());

		if (mainFile != null && !mainFile.isEmpty()) {// 上传主文件存在
			DocFileEntity f = new DocFileEntity();
			f.setDocumentCode(doc.getDocumentCode());
			f.setFileName(doc.getDocumentCode());
			f.setFileContent(mainFile.getBytes());

			if (mf != null) { // db存在主文件，更新
				f.setFileNo(mf.getFileNo());
				f.setUpdater(doc.getCreator());
				updfiles.add(f);
			} else {// db不存在主文件，新增
				f.setFileType(FILE_TYPE_MAIN);
				f.setCreator(doc.getCreator());
				files.add(f);
			}

		} else {// 上传不存在
			// 非必须输入 的情况下，删除主文件
			DocDetailItemSettingEntity docDetail = docDao.getInputRequire(doc.getDocumentType(), fileDto.getmFile());
			if(docDetail.getInputRequire().toString().equals("2")) {
				DocFileEntity file = new DocFileEntity();
				file.setDocumentCode(doc.getDocumentCode());
				file.setFileType(FILE_TYPE_MAIN);
				file.setFileNo(mf.getFileNo());
				delfiles.add(file);
			}
		}

		// 删除画面已删除文件
		String[] delSubFiles = fileDto.getDelSubFileNo();
		List<String> delSubList = new ArrayList<>();
		if (delSubFiles != null) {
			for (String str : Arrays.asList(delSubFiles)) {
				if (str != null && !str.isEmpty()) {
					delSubList.add(str);
				}
			}
		}
		if (delSubList != null && delSubList.size() > 0) {
			for (String delFileNo : delSubList) {
				DocFileEntity file = new DocFileEntity();
				file.setDocumentCode(doc.getDocumentCode());
				file.setFileType(FILE_TYPE_SUB);
				file.setFileNo(new BigDecimal(delFileNo));
				delfiles.add(file);
			}
		}

		if (!delfiles.isEmpty()) {
			delfiles.stream().forEach(x -> docDao.deleteFile(x));
			// 删除插入log
			log.setOprContent(Constant.OPERATE_TYPE_FILE_DELETE);
			docDao.createLog(log);
		}

		// 更新
		String[] updSubFilesNo = fileDto.getUpdSubFileNo();
		List<String> updSubList = new ArrayList<>();
		if (updSubFilesNo != null) {
			for (String str : Arrays.asList(updSubFilesNo)) {
				if (str != null && !str.isEmpty()) {
					updSubList.add(str);
				}
			}
		}

		List<CommonsMultipartFile> updSubFiles = fileDto.getUpdSubFiles();
		if (updSubFiles != null) {

			for (int i = 0; i < updSubList.size(); i++) {

				DocFileEntity file = new DocFileEntity();
				file.setFileName(updSubFiles.get(i).getOriginalFilename());
				file.setFileContent(updSubFiles.get(i).getBytes());
				file.setUpdater(doc.getCreator());
				file.setDocumentCode(doc.getDocumentCode());
				file.setFileNo(new BigDecimal(updSubList.get(i)));

				updfiles.add(file);
			}

			if (!updfiles.isEmpty()) {
				updfiles.stream().forEach(x -> docDao.updateFile(x));
				// 更新插入log
				log.setOprContent(Constant.OPERATE_TYPE_UPDATE);
				docDao.createLog(log);
			}
		}

		// 新增
		for (CommonsMultipartFile subFile : fileDto.getSubFiles()) {

			if (subFile == null || subFile.isEmpty())
				continue;
			;

			DocFileEntity f = new DocFileEntity();
			f.setDocumentCode(doc.getDocumentCode());
			f.setFileType(FILE_TYPE_SUB);
			f.setFileName(subFile.getOriginalFilename());
			f.setFileContent(subFile.getBytes());
			f.setCreator(doc.getCreator());
			files.add(f);
		}
		if (!files.isEmpty()) {
			files.stream().forEach(x -> docDao.addFile(x));
			// 新增插入log
			log.setOprContent(Constant.OPERATE_TYPE_FILE_UPLOAD);
			docDao.createLog(log);
		}

		if (isCommit.equals("true")) {// 提交操作
			log.setOprContent(Constant.OPERATE_TYPE_REGISTER);
			docDao.createLog(log);
		}
	}

	@Override
	public List<DocFormItemDto> formItems(String documentType) {
		return docDao.displayItems(documentType);
	}

	@Override
	public DocFileEntity file(String documentCode, String fileType, String fileNo, LoginDto user) {
		// 文件下载插入log
		SysLogEntity log = new SysLogEntity();
		Date now = Date.from(Instant.now());
		log.setOprDatetime(now);
		log.setUserId(user.getUserId());
		log.setIp(user.getIp());
		log.setDocumentCode(documentCode);
		log.setCreator(user.getUserId());
		log.setCreateDt(now);
		log.setUpdater(user.getUserId());
		log.setUpdateDt(now);
		log.setOprContent(Constant.OPERATE_TYPE_FILE_DOWNLOAD);

		docDao.createLog(log);

		if (fileType.equals("1")) {
			return docDao.mainFile(documentCode);
		} else {
			return docDao.subFile(documentCode, fileNo);
		}
	}

	@Override
	public DocDetailCustomInfoEntity getDocCustom(String docCode) {

		return docDao.getDocCustom(docCode);
	}

	@Override
	public boolean checkUpdTime(String docCode,Date update) {
		DocDetailInfoEntity doc = docDao.getDocDetail(docCode);
		if (doc != null && doc.getUpdateDt() != null ) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(sf.format(doc.getUpdateDt()).trim().equals(sf.format(update).trim())) {
				return true;
			}else {
				return false;
			}
		}else {
			return true;
		}
	}

	@Override
	public List<UserDeptDto> getDeptLeaderId(String deptId) {
		return docDao.getDeptLeaderId(deptId);
	}

	@Override
	public String getDeptIdByDocCode(String docCode) {
		return docDao.getDeptIdByDocCode(docCode);
	}

	@Override
	public List<UserEntity> getUserEntity(String[] deptId) {

		return docDao.getUserEntities(deptId);
	}

	@Override
	public DocItemDataSourceCodeEntity getDocName(String docCode) {
		return docBatchDao.getDocName(docCode);
	}

}
