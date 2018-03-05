package com.dm.ctrl.doc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dm.ctrl.BaseCtrl;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.DocDetailInfoDto;
import com.dm.dto.DocFormItemDto;
import com.dm.dto.ErrCode;
import com.dm.dto.FileDto;
import com.dm.dto.LoginDto;
import com.dm.dto.ResultDto;
import com.dm.dto.SysCfgDto;
import com.dm.dto.UserDeptDto;
import com.dm.entity.DocDetailCustomInfoEntity;
import com.dm.entity.DocFileEntity;
import com.dm.srv.doc.DocBatchCheckSrv;
import com.dm.srv.doc.DocEnterSrv;
import com.dm.srv.util.DocCommonSrv;
import com.dm.tool.Constant;
import com.dm.tool.StringUtil;

@Controller
@RequestMapping("/doc")
public class DocEnterCtrl extends BaseCtrl {

	@Autowired
	private DocEnterSrv docSrv;
	@Autowired
	private DocBatchCheckSrv docBatchSrv;
	@Autowired
	private DocCommonSrv docCommonSrv;

	@RequestMapping("/enter")
	public ModelAndView enter(@RequestParam(value = "mode", required = false, defaultValue = "-1") String mode,
			@RequestParam(value = "documentType", required = false) String documentType,
			@RequestParam(value = "documentCode", required = false) String documentCode,
			@RequestParam(value = "locationType", required = false, defaultValue = "0") String locationType) {

		ModelAndView mv = new ModelAndView();
		Map<String, Object> map = null;
		LoginDto user = (LoginDto) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
		try {
			switch (mode) {
			case "-1": // 初始画面

				map = docSrv.docTypes();
				mv.addAllObjects(map);

				break;
			case Constant.MODE_DETAIL: // 详细模式

				map = docSrv.detail(documentCode, mode, user);
				mv.addAllObjects(map);

				break;
			case Constant.MODE_MODIFY: // 修改模式

				map = docSrv.docTypes();
				mv.addAllObjects(map);

				mv.addAllObjects(docSrv.detail(documentCode, mode, user));

				break;

			case Constant.MODE_ADD_NEW: // 新增模式

				map = docSrv.docTypes();
				mv.addAllObjects(map);

				mv.addObject("documentType", documentType);
				mv.addObject("documentCode", documentCode);

				// ----------- 画面ITEM显示 --------------- //

				mv.addAllObjects(docSrv.form(documentType, documentCode, Constant.MODE_ADD_NEW, user));

				break;
			case Constant.MODE_APPROVE: // 审核模式

				map = docSrv.docTypes();
				mv.addAllObjects(map);

				mv.addAllObjects(docSrv.detail(documentCode, mode, user));

				break;
			}

			mv.setViewName("doc/DocEnter");
			mv.addObject("locationType", locationType);
			mv.addObject("mode", mode);
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}

		return mv;
	}

	@RequestMapping("/save")
	@ResponseBody
	public ResultDto<BaseDto> save(
			@RequestParam(value = "mainFile", required = false, defaultValue = ValueConstants.DEFAULT_NONE) CommonsMultipartFile mainFile,
			@RequestParam(value = "subFiles", required = false, defaultValue = ValueConstants.DEFAULT_NONE) CommonsMultipartFile[] subFiles,
			@RequestParam(value = "isCommit", required = false, defaultValue = "false") String isCommit) {
		ResultDto<BaseDto> resultDto = new ResultDto<BaseDto>();
		BaseDto baseDto = new BaseDto();
		resultDto.setData(baseDto);
		try {
			String documentCode = request.getParameter("documentCode");
			String documentType = request.getParameter("documentType");
			LoginDto user = (LoginDto) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);

			if (StringUtil.isEmpty(documentCode) || StringUtil.isEmpty(documentType)) {
				baseDto.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
				baseDto.setBizExeMsgs(Arrays.asList(Constant.LOGIN_NULL_FILE_NUMBER));
				return resultDto;
			}

			DocDetailInfoDto doc = new DocDetailInfoDto();
			DocDetailCustomInfoEntity custome = new DocDetailCustomInfoEntity();

			doc.setDocumentCode(documentCode);
			doc.setDocumentType(documentType);
			custome.setDocumentCode(documentCode);
			custome.setCreator(user.getUserId());
			// 填充表单

			readForm(doc, custome, mainFile, subFiles);
			// 后台验证
			List<String> errorMsgs = validForm(doc, custome, user, false);
			if (!errorMsgs.isEmpty()) {
				baseDto.setBizExeMsgs(errorMsgs);
				return resultDto;
			}

			doc.setCreator(user.getUserId());

			if (mainFile == null || mainFile.isEmpty()) {
				mainFile = null;
			}
			List<CommonsMultipartFile> subFileList = new ArrayList<>();
			if (subFiles != null) {
				for (CommonsMultipartFile file : Arrays.asList(subFiles)) {
					if (file != null && !file.isEmpty()) {
						subFileList.add(file);
					}
				}
			}

			if (doc.getApprovalDeptId() == null || doc.getApprovalDeptId().isEmpty()) {
				// 设置文档审核部门
				List<String> deptIds = user.getUserDept().stream().map(x -> x.getDeptId()).collect(Collectors.toList());
				String approveId = "";
				List<String> approveIds = new ArrayList<>();

				for (String s : deptIds) {

					// 获取该部门所有负责人
					List<UserDeptDto> leaders = docSrv.getDeptLeaderId(s).stream()
							.filter(x -> x.getLeaderFlg().equals("1")).collect(Collectors.toList());
					// 获取该部门所有审核人id
					List<String> leaderIds = leaders.stream().map(x -> x.getUserId()).collect(Collectors.toList());
					if (leaderIds.contains(user.getUserId())) {
						approveIds.add(leaders.get(0).getParentDeptId());
					} else {
						approveIds.add(leaders.get(0).getDeptId());
					}

				}
				// 去处重复的审核部门id
				HashSet<String> h = new HashSet<String>(approveIds);
				approveIds.clear();
				approveIds.addAll(h);

				// 字符串拼接
				for (String a : approveIds) {
					if (approveId.length() > 0) {
						approveId = approveId.concat(",");
					}
					approveId = approveId.concat(a);
				}
				doc.setApprovalDeptId(approveId);
			}

			docSrv.save(doc, custome, mainFile, subFileList, getIpAddr(request), user.getUserDept(), isCommit);

			return resultDto;

		} catch (Exception e) {
			e.printStackTrace();
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	@RequestMapping("/update")
	@ResponseBody
	public ResultDto<BaseDto> update(
			@RequestParam(value = "mainFile", required = false, defaultValue = ValueConstants.DEFAULT_NONE) CommonsMultipartFile mainFile,
			@RequestParam(value = "subFiles", required = false, defaultValue = ValueConstants.DEFAULT_NONE) CommonsMultipartFile[] subFiles,
			@RequestParam(value = "updSubFiles", required = false, defaultValue = ValueConstants.DEFAULT_NONE) CommonsMultipartFile[] updSubFiles,
			@RequestParam(value = "isCommit", required = false, defaultValue = "false") String isCommit) {
		ResultDto<BaseDto> resultDto = new ResultDto<BaseDto>();
		BaseDto baseDto = new BaseDto();
		resultDto.setData(baseDto);
		try {

			String documentCode = request.getParameter("documentCode");
			String documentType = request.getParameter("documentType");
			String updateDt = request.getParameter("updateTime");
			LoginDto user = (LoginDto) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			if (updateDt != null && !updateDt.isEmpty()) {
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				user.setUpdateDt(sf.parse(updateDt));
			}
			if (StringUtil.isEmpty(documentCode) || StringUtil.isEmpty(documentType)) {
				baseDto.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
				baseDto.setBizExeMsgs(Arrays.asList(Constant.LOGIN_NULL_FILE_NUMBER));
				return resultDto;
			}

			DocDetailInfoDto doc = new DocDetailInfoDto();
			DocDetailCustomInfoEntity custome = new DocDetailCustomInfoEntity();

			doc.setDocumentCode(documentCode);
			doc.setDocumentType(documentType);
			custome.setDocumentCode(documentCode);
			custome.setUpdater(user.getUserId());

			// 填充表单

			readForm(doc, custome, mainFile, subFiles);
			// 后台验证
			List<String> errorMsgs = validForm(doc, custome, user, true);
			if (!errorMsgs.isEmpty()) {
				baseDto.setBizExeMsgs(errorMsgs);
				return resultDto;
			}

			doc.setUpdater(user.getUserId());

			FileDto fileDto = new FileDto();
			// 主文件
			if (mainFile == null || mainFile.isEmpty()) {
				mainFile = null;
			}
			String mFile = request.getParameter("mFile");
			fileDto.setmFile(mFile);
			fileDto.setMainFile(mainFile);

			// 已删除子文件
			String delSubFilesNo = request.getParameter("delSubFilesNo");
			if (delSubFilesNo != null && !delSubFilesNo.isEmpty()) {
				String[] delSubArray = delSubFilesNo.substring(0, delSubFilesNo.length() - 1).split(",");
				fileDto.setDelSubFileNo(delSubArray);
			}

			// 已更新子文件
			String updSubFilesNo = request.getParameter("updSubFilesNo");
			if (updSubFilesNo != null && !updSubFilesNo.isEmpty()) {
				String[] updSubArray = updSubFilesNo.substring(0, updSubFilesNo.length() - 1).split(",");
				fileDto.setUpdSubFileNo(updSubArray);
			}

			// 新增子文件
			List<CommonsMultipartFile> subFileList = new ArrayList<>();
			if (subFiles != null) {
				for (CommonsMultipartFile file : Arrays.asList(subFiles)) {
					if (file != null && !file.isEmpty()) {
						subFileList.add(file);
					}
				}
			}

			List<CommonsMultipartFile> subFileList1 = new ArrayList<>();
			if (updSubFiles != null) {
				for (CommonsMultipartFile file : Arrays.asList(updSubFiles)) {
					if (file != null && !file.isEmpty()) {
						subFileList1.add(file);
					}
				}
			}

			// 主文件
			fileDto.setMainFile(mainFile);

			// 子文件
			fileDto.setDocCode(documentCode);
			fileDto.setSubFiles(subFileList);
			fileDto.setUpdSubFiles(subFileList1);
			if (isCommit.equals("true")) {// 提交操作
				doc.setApprovalStatus(Integer.toString(Constant.DOC_STATUS_AUDITING));

			}

			if (doc.getApprovalDeptId() == null || doc.getApprovalDeptId().isEmpty()) {
				// 设置文档审核部门
				List<String> deptIds = user.getUserDept().stream().map(x -> x.getDeptId()).collect(Collectors.toList());
				String approveId = "";
				List<String> approveIds = new ArrayList<>();
				for (String s : deptIds) {
					// 获取该部门所有人员
					List<UserDeptDto> depts = docSrv.getDeptLeaderId(s);
					// 获取该部门所有负责人
					List<UserDeptDto> leaders = depts.stream().filter(x -> x.getLeaderFlg().equals("1"))
							.collect(Collectors.toList());
					for (UserDeptDto m : leaders) {

						if (m.getUserId().equals(user.getUserId())) {// 登录用户为部门负责人时
							approveIds.add(m.getParentDeptId());
							break;
						} else {
							approveIds.add(m.getDeptId());
							break;
						}
					}

				}
				// 去处重复的审核部门id
				HashSet<String> h = new HashSet<String>(approveIds);
				approveIds.clear();
				approveIds.addAll(h);

				// 字符串拼接
				for (String a : approveIds) {
					if (approveId.length() > 0) {
						approveId = approveId.concat(",");
					}
					approveId = approveId.concat(a);
				}
				doc.setApprovalDeptId(approveId);
			}

			docSrv.update(doc, custome, fileDto, getIpAddr(request), user.getUserDept(), isCommit);

			resultDto.setData(baseDto);

			return resultDto;

		} catch (Exception e) {
			e.printStackTrace();
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	@RequestMapping("/commit")
	@ResponseBody
	public ResultDto<BaseDto> commit(
			@RequestParam(value = "mainFile", required = false, defaultValue = ValueConstants.DEFAULT_NONE) CommonsMultipartFile mainFile,
			@RequestParam(value = "subFiles", required = false, defaultValue = ValueConstants.DEFAULT_NONE) CommonsMultipartFile[] subFiles,
			@RequestParam(value = "updSubFiles", required = false, defaultValue = ValueConstants.DEFAULT_NONE) CommonsMultipartFile[] updSubFiles) {
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		try {
			String mode = request.getParameter("mode");
			// 确定是否已经save
			String documentCode = request.getParameter("documentCode");
			String isCommit = "true";
			DocDetailCustomInfoEntity docCus = docSrv.getDocCustom(documentCode);
			if (docCus != null) {
				mode = Constant.MODE_MODIFY;
			}
			switch (mode) {
			case Constant.MODE_MODIFY:
				resultDto = this.update(mainFile, subFiles, updSubFiles, isCommit);
				break;
			case Constant.MODE_ADD_NEW:
				resultDto = this.save(mainFile, subFiles, isCommit);
				break;
			}

			return resultDto;
		} catch (Exception e) {
			e.printStackTrace();
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	@RequestMapping("/approve")
	@ResponseBody
	public ResultDto<BaseDto> approve(@RequestParam("documentCode") String[] documentCode,
			@RequestParam(value = "APPROVAL_COMMENT", defaultValue = "") String comment) {

		ResultDto<BaseDto> resultDto = new ResultDto<BaseDto>();
		BaseDto baseDto = new BaseDto();

		try {
			LoginDto user = (LoginDto) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			String userId = user.getUserId();
			baseDto = docBatchSrv.passOrRet(documentCode, null, Constant.APPROVAL_STATUS_SUCCESS, comment, userId);
			resultDto.setData(baseDto);
			// 开启发送邮件的线程
			if (baseDto.getBizCode() != BizCode.BIZ_CODE_DOC_UPDATE_BY_OTHER) {
				List<String> str = baseDto.getBizExeMsgs();
				int f = baseDto.getBizCode();
				Callable<Void> callable = new Callable<Void>() {
					public Void call() throws Exception {
						sendEmails(str, f);
						return null;
					}
				};
				FutureTask<Void> future = new FutureTask<Void>(callable);
				new Thread(future).start();
			}
		} catch (Exception e) {
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
		return resultDto;
	}

	@RequestMapping("/decline")
	@ResponseBody
	public ResultDto<BaseDto> decline(@RequestParam("documentCode") String[] documentCode,
			@RequestParam(value = "APPROVAL_COMMENT", defaultValue = "") String comment) {

		ResultDto<BaseDto> resultDto = new ResultDto<BaseDto>();
		BaseDto baseDto = new BaseDto();

		try {
			LoginDto user = (LoginDto) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			String userId = user.getUserId();
			baseDto = docBatchSrv.passOrRet(documentCode, null, Constant.APPROVAL_STATUS_FAILED, comment, userId);
			resultDto.setData(baseDto);
			// 开启发送邮件的线程
			if (baseDto.getBizCode() != BizCode.BIZ_CODE_DOC_UPDATE_BY_OTHER) {
				List<String> str = baseDto.getBizExeMsgs();
				int f = baseDto.getBizCode();
				Callable<Void> callable = new Callable<Void>() {
					public Void call() throws Exception {
						sendEmails(str, f);
						return null;
					}
				};
				FutureTask<Void> future = new FutureTask<Void>(callable);
				new Thread(future).start();
			}
		} catch (Exception e) {
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}

		return resultDto;
	}

	@RequestMapping("/fetchcode")
	@ResponseBody
	public ResultDto<BaseDto> fetchCode(@RequestParam(value = "documentType") String documentType) {
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		try {
			LoginDto user = (LoginDto) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			String userId = user.getUserId();
			Map<String, String> code = docSrv.fetchCode(documentType, userId, getIpAddr(request));
			BaseDto baseDto = new BaseDto();

			resultDto.setData(baseDto);
			if (code.get("result").equals("error")) {
				baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
				return resultDto;
			} else {
				String documentCode = code.get("documentCode");
				baseDto.setBizExeMsgs(Arrays.asList(new String[] { documentCode }));
				return resultDto;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	@RequestMapping("/download/{documentCode}/{fileType}")
	public void download(@PathVariable("documentCode") String documentCode, @PathVariable("fileType") String fileType,
			@RequestParam(value = "fileNo", required = false) String fileNo) {

		try {

			// 验证
			if (!Constant.FILE_TYPE_PARENT.equals(fileType) && !Constant.FILE_TYPE_CHILD.equals(fileType)) {
				return;
			}
			LoginDto user = (LoginDto) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			user.setIp(getIpAddr(request));
			DocFileEntity file = docSrv.file(documentCode, fileType, fileNo, user);

			if (file == null)
				return;

			response.setContentType("application/force-download");
			response.addHeader("Content-Disposition", "attachment;fileName=" + file.getFileName());// 设置文件名
			response.getOutputStream().write(file.getFileContent());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/check")
	@ResponseBody
	public boolean checkDocOldCode(String docCode, String docOldCode) {
		return docSrv.checkDocOldCode(docCode, docOldCode);
	}

	private void readForm(DocDetailInfoDto doc, DocDetailCustomInfoEntity custome, CommonsMultipartFile mainFile,
			CommonsMultipartFile[] subFile) {
		Map<?, ?> map = request.getParameterMap();
		for (Object k : map.keySet()) {

			if (!(k instanceof String))
				continue;

			String key = (String) k;

			String[] vs = (String[]) map.get(key);
			if (vs == null || vs.length == 0)
				continue;

			String value = vs[0] == null ? null : vs[0].trim();

			switch (key) {

			case "DOCUMENT_OLD_CODE":
				// 旧文档编号
				doc.setDocumentOldCode(value);
				break;

			case "DEPT_ID":
				// 担当部门
				doc.setDeptId(value);
				break;

			case "DIRECTOR":
				// 责任人
				doc.setDirector(value);
				break;

			case "APPROVAL_STATUS":
				// 文档状态
				doc.setApprovalStatus(value);
				break;

			case "APPROVAL_COMMENT":
				// 审核意见
				doc.setApprovalComment(value);
				break;

			case "APPROVAL_DEPT_ID":
				// 审核部门
				doc.setApprovalDeptId(value);
				break;

			case "DOCUMENT_VIEW_DEPT":
				// 文档可查看部门
				doc.setDocReadDeptId(value);

			default:
				// 自定义数据项
				String pattern = "^CUSTOME_ITEM([1-9]|([1-5][0-9]))$";
				if (Pattern.compile(pattern).matcher(key).find()) {
					try {
						custome.setField(key, value);
					} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
							| SecurityException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private List<String> validForm(DocDetailInfoDto doc, DocDetailCustomInfoEntity custome, LoginDto user,
			boolean updateFlag) {

		List<String> errorMsgs = new ArrayList<>();

		List<DocFormItemDto> checks = docSrv.formItems(doc.getDocumentType());
		checks.forEach(x -> {

			String itemName = x.getDocumentItemName();
			String value = null;

			String dbItemName = x.getDbItemName();
			if (dbItemName != null) {
				switch (dbItemName) {
				case "DOCUMENT_TYPE":
					value = doc.getDocumentType();
					break;
				case "DOCUMENT_CODE":
					value = doc.getDocumentCode();
					break;
				case "DOCUMENT_OLD_CODE":
					// 旧文档编号
					value = doc.getDocumentOldCode();
					break;

				case "DEPT_ID":
					// 担当部门
					value = doc.getDeptId();
					break;

				case "DIRECTOR":
					// 责任人
					value = doc.getDirector();
					break;

				case "APPROVAL_STATUS":
					// 文档状态
					value = doc.getApprovalStatus();
					break;

				case "APPROVAL_COMMENT":
					// 审核意见
					value = doc.getApprovalComment();
					break;

				case "APPROVAL_DEPT_ID":
					// 审核部门
					value = doc.getApprovalDeptId();
					break;

				default:
					// 自定义数据项
					String pattern = "^CUSTOME_ITEM([1-9]|([1-5][0-9]))$";
					if (Pattern.compile(pattern).matcher(dbItemName).find()) {
						try {
							value = custome.getField(dbItemName);
						} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
								| SecurityException e) {
							e.printStackTrace();
						}
					}
				}

			} else {
				if (x.getDocumentItemCode().equals(Constant.DOCUMENT_ITEM_F00009)) {
					value = doc.getDocReadDeptId();
				} else if (x.getDocumentItemType().equals(Constant.ITEM_TYPE_FILE_SINGLE)) {

				} else if (x.getDocumentItemType().equals(Constant.ITEM_TYPE_FILE_MULT)) {

				}
			}

			// 必填
			if (x.getInputRequire() != null && x.getInputRequire().intValue() == 1 && StringUtil.isEmpty(value)) {
				if (!x.getDocumentItemType().equals(Constant.ITEM_TYPE_FILE_SINGLE)
						&& !x.getDocumentItemType().equals(Constant.ITEM_TYPE_FILE_MULT)) {
					// TODO:文件必填验证
					errorMsgs.add(Constant.DOC_INPUR_PLEASE + itemName);
				}

			}
			// 最大长度
			if (x.getMaxLength() != null) {
				int maxLength = x.getMaxLength().intValue();
				if (!StringUtil.isEmpty(value) && maxLength < value.length()) {
					errorMsgs.add(itemName + Constant.DOC_MAXLENGTH_LESS + maxLength);
				}
			}
			// 整数
			if (Constant.ITEM_TYPE_INT.equals(x.getDocumentItemType())) {
				try {
					if (value != null && !value.isEmpty()) {
						Integer.valueOf(value, 10);
					}
				} catch (NumberFormatException ex) {
					errorMsgs.add(itemName + Constant.DOC_MUSTIS_INTEGER);
				}
			}
			// 金额
			if (Constant.ITEM_TYPE_MONEY.equals(x.getDocumentItemType())) {
				if (value != null && !value.isEmpty()) {
					if (!Pattern.compile("(^[1-9]([0-9]+)?(\\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\\.[0-9]([0-9])?$)")
							.matcher(value).find()) {
						errorMsgs.add(itemName + Constant.DOC_MUSTIS_AMOUNT);
					}
				}

			}
			// 旧文档编号
			if (!updateFlag) {
				if (Constant.DOCUMENT_ITEM_F00002.equals(x.getDocumentItemCode())) {

					if (doc.getDocumentOldCode() != null && !doc.getDocumentOldCode().isEmpty()
							&& !docSrv.checkDocOldCode(doc.getDocumentOldCode(), doc.getDocumentCode())) {
						errorMsgs.add(Constant.DOC_INPUT_OLDDOCUMENT);
					}
				}
			}

		});

		// 检查审核节点是否完整
		if (!docCommonSrv.CheckApprovalProgress(doc.getDocumentType(), user.getUserId())) {
			// 不完整
			errorMsgs.add(Constant.DOC_NOTSET_FINALEXAMINE);
		}

		// 判断是否有其他人同时修改同一个文档
		if (updateFlag) {
			if (user.getUpdateDt() != null) {
				if (!docSrv.checkUpdTime(doc.getDocumentCode(), user.getUpdateDt())) {
					errorMsgs.add(Constant.DOC_REENTER_MODIFY);
				}
			}
		}

		return errorMsgs;
	}

	/**
	 * 获取当前网络ip
	 *
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length() = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	private void sendEmails(List<String> str, Integer flag) throws UnsupportedEncodingException, MessagingException {

		// 获取sysconfig数据
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();

		SysCfgDto sysCfgInfo = (SysCfgDto) servletContext.getAttribute("SysCfgInfo");

		// 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
		// 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
		String myEmailSMTPHost = sysCfgInfo.getEmailSMTPHost();

		// 设置发送者信息
		String myEmailAccount = sysCfgInfo.getEmailAccount();
		String myEmailPassword = sysCfgInfo.getEmailPassword();

		// 获取首页地址
		String url = sysCfgInfo.getIndex();
		// 设置接受者信息
		for (String s : str) {
			String[] emailInfo = s.split(",");
			if (flag == Constant.APPROVAL_SUCCESS) {// 给下一级所有的审批人发送审批通知邮件：
				String title = "【NRDC管理系统】 【" + emailInfo[0] + "】 审批处理提醒 " + emailInfo[1];
				String content = emailInfo[2] + " 女士/先生 您好<br/> 您有一件新的【NRDC管理系统-" + emailInfo[0] + "】文档需要处理(流水号："
						+ emailInfo[1] + ")，请点击以下链接处理：<br/>" + url + "?docCode=" + emailInfo[1] + "&mode=" + 3
						+ "<br/><br/>注：本邮件有系统自动发送，请勿向此邮箱发送邮件。";
				sendEmail(emailInfo[3], content, myEmailSMTPHost, myEmailAccount, myEmailPassword, title);
			} else if (flag == Constant.APPROVAL_FAILED) {
				String title = "【NRDC管理系统】 【" + emailInfo[0] + "】 退回提醒 " + emailInfo[1];
				String content = emailInfo[2] + " 女士/先生 您好<br/> 您的【NRDC管理系统-" + emailInfo[0] + "】文档(流水号：" + emailInfo[1]
						+ ")被退回，请点击以下链接处理：<br/>" + url + "?docCode=" + emailInfo[1] + "&mode=" + 1 + "<br/><br/>";
				sendEmail(emailInfo[3], content, myEmailSMTPHost, myEmailAccount, myEmailPassword, title);
			}
		}

	}

	private void sendEmail(String receiveMailAccount, String content, String myEmailSMTPHost, String myEmailAccount,
			String myEmailPassword, String title) throws UnsupportedEncodingException, MessagingException {

		// 1. 创建参数配置, 用于连接邮件服务器的参数配置
		Properties props = new Properties(); // 参数配置
		props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
		props.setProperty("mail.smtp.host", myEmailSMTPHost); // 发件人的邮箱的 SMTP 服务器地址
		props.setProperty("mail.smtp.auth", "true"); // 需要请求认证
		props.put("mail.smtp.auth", "true");

		// 2. 根据配置创建会话对象, 用于和邮件服务器交互
		Session session = Session.getInstance(props);
		session.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log

		// 3. 创建一封邮件
		MimeMessage message;

		message = createMimeMessage(session, myEmailAccount, receiveMailAccount, content, title);

		// 4. 根据 Session 获取邮件传输对象
		Transport transport = session.getTransport();

		transport.connect(myEmailAccount, myEmailPassword);

		// 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人,
		// 密送人
		transport.sendMessage(message, message.getAllRecipients());

		// 7. 关闭连接
		transport.close();
	}

	public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, String content,
			String title) throws UnsupportedEncodingException, MessagingException {

		// 1. 创建一封邮件
		MimeMessage message = new MimeMessage(session);

		// 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
		message.setFrom(new InternetAddress(sendMail, sendMail, "UTF-8"));

		// 3. To: 收件人（可以增加多个收件人、抄送、密送）
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, receiveMail, "UTF-8"));

		// 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
		message.setSubject(title, "UTF-8");

		// 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
		// String secretKey= UUID.randomUUID().toString(); //密钥
		// String path = "http://localhost:14602/#/ResetPwd/"; // 跳转地址
		// String num = id + "/" + type; // 所需参数
		// String truepath = path + num; // 地址，参数的整合
		//
		// String key = id + type;
		// String digitalSignature = EncryptionStr(key, MD5); // 数字签名
		// String pathh = truepath + "/" + digitalSignature;
		message.setContent(content, "text/html;charset=UTF-8");

		// 6. 设置发件时间
		message.setSentDate(new Date());

		// 7. 保存设置
		message.saveChanges();

		return message;
	}

}
