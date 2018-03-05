package com.dm.srv.impl.outlay;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletOutputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dm.dao.ProjectDao;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.FileDto;
import com.dm.dto.FileExportDto;
import com.dm.dto.ProjectInfoDto;
import com.dm.dto.SubjectLegalDto;
import com.dm.entity.DepartmentInfoEntity;
import com.dm.entity.SubjectDepEntity;
import com.dm.srv.impl.BaseSrvImp;
import com.dm.srv.outlay.ProjectSrv;
import com.dm.tool.Constant;
import com.dm.tool.StringUtil;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：课题Service
 */
@Service
public class ProjectSrvImp extends BaseSrvImp implements ProjectSrv {
	// 申明全局变量 存储课题编号
	List<String> projectNos = new ArrayList<String>();
	// 申明全局变量 存储法人名称
	List<String> fileLegal = new ArrayList<String>();
	@Autowired
	private ProjectDao projectDao;

	// 课题查询
	@Override
	public List<ProjectInfoDto> getSubjectList(ProjectInfoDto projectInfo,int start,int length) {
		//数据转换
		projectInfo = dataChange(projectInfo);
		String type1 = Constant.PROJECT_TYPE;
		// 获取课题一览查询结果
		List<ProjectInfoDto> projectList = projectDao.getSubjectList(projectInfo,type1,start,length);
		//当获取结构不为空的场合
		if(projectList!=null&&projectList.size()!=0) {
			for(int i=0;i<projectList.size();i++) {
				//获取部署信息集合
				 List<DepartmentInfoEntity> departmentList = projectList.get(i).getDepartmentInfo();
				//当部署信息集合不为空的场合
				 if(departmentList!=null&&departmentList.size()!=0) {
					String deptNames="";
					String deptIds="";
					//设置课题部署名称和课题部署Ids
					 for(int j=0;j<departmentList.size();j++) {
						   //当为第一个数据时
							if(j==0) {
								deptNames = departmentList.get(j).getDeptName();
								deptIds = departmentList.get(j).getDeptId();
							} else {
								//当不是第一个数据时
								deptNames = deptNames +","+departmentList.get(j).getDeptName();
								deptIds = deptIds+","+departmentList.get(j).getDeptId();
							}
						}
					 projectList.get(i).setDeptNames(deptNames);
					 projectList.get(i).setDeptIds(deptIds);
				}
			}
		}
		return projectList;
	}

	// 新增课题
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public BaseDto projectAdd(ProjectInfoDto projectForm) {
		// 准备返回对象
		BaseDto baseDto = new BaseDto();
		//获取新增年度
		String fyYear = projectForm.getFyYear();
		//获取新增课题编号
		String projectNo = projectForm.getProjectNo();
		//获取课题新增者
		String creator = projectForm.getCreator();
		//根据课题编号和年度，判断课题编号是否重复
		int count = projectDao.projectCount(fyYear, projectNo);
		//当课题编号存在的场合
		if (count > 0) {
			baseDto.setBizCode(BizCode.BIZ_CODE_DURATION_ERR);
			return baseDto;
		}
		// 新增课题
		projectDao.projectAdd(projectForm);
		// 申明新增部署集合
		List<SubjectDepEntity> subjectDeps = new ArrayList<SubjectDepEntity>();
		//获取新增部署Id集合
		String[] deptIds = projectForm.getDeptIds().split(",");
		//设置新增部署集合
		for (int i = 0; i < deptIds.length; i++) {
			SubjectDepEntity subjectDep = new SubjectDepEntity();
			subjectDep.setProjectNo(projectNo);
			subjectDep.setFyYear(fyYear);
			subjectDep.setDeptId(deptIds[i]);
			subjectDep.setCreator(creator);
			subjectDeps.add(subjectDep);
		}
		//新增部署
		projectDao.subjectdepAdd(subjectDeps);
		// 法人新增
		//获取法人Id集合和比例集合
		String[] legalIds = projectForm.getLegalIds().split(",");
		String[] percentages = projectForm.getPercentages().split(",");
		//申明新增法人集合
		List<SubjectLegalDto> subjectLegals = new ArrayList<SubjectLegalDto>();
		//设置新增法人集合
		for (int i = 0; i < legalIds.length; i++) {
			SubjectLegalDto subjectLegal = new SubjectLegalDto();
			subjectLegal.setProjectNo(projectNo);
			subjectLegal.setFyYear(fyYear);
			subjectLegal.setLegalId(Integer.parseInt(legalIds[i]));
			BigDecimal percentage = new BigDecimal(percentages[i]);
			subjectLegal.setPercentage(percentage);
			subjectLegal.setCreator(creator);
			subjectLegals.add(subjectLegal);
		}
		//新增法人
		projectDao.subjectLegalAdd(subjectLegals);
		// 返回成功Code
		baseDto.setBizCode(BizCode.BIZ_CODE_SUCCESS);
		return baseDto;
	}

	// 删除课题
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public BaseDto projectdelete(String fyYear, String projectNo) {
		// 准备返回对象
		BaseDto baseDto = new BaseDto();
		//判断工时是否存在
		int count = projectDao.countWorking(fyYear, projectNo);
		//当工时存在的场合,无法删除
		if (count > 1) {
			baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
			return baseDto;
		}
		// 删除课题数据
		projectDao.subjectLegalDelete(fyYear, projectNo);
		projectDao.subjectDepDelete(fyYear, projectNo);
		projectDao.projetctDelete(fyYear, projectNo);
		// 返回成功Code
		baseDto.setBizCode(BizCode.BIZ_CODE_SUCCESS);
		return baseDto;

	}

	// 课题信息获取
	@Override
	public ProjectInfoDto getSubject(String fyYear, String projectNo) {
		ProjectInfoDto projectInfo = new ProjectInfoDto();
		//初始化部门Ids，法人Ids,比例集合
		String deptIds = null,
				  legalIds=null,
				  percentages=null;

		// 获取指定课题信息
		String type1 = Constant.LEGAL;
		projectInfo = projectDao.getSubject(fyYear, projectNo,type1);
		for(int i=0;i<projectInfo.getDepartmentInfo().size();i++) {
			//当i为0的场合，拼接字符串
			if(i==0) {
				deptIds = projectInfo.getDepartmentInfo().get(i).getDeptId();
			}else {
				//当i不为0的场合
				deptIds = deptIds +","+ projectInfo.getDepartmentInfo().get(i).getDeptId();
			}
		}
		for(int i=0;i<projectInfo.getSubjectLegal().size();i++) {
			//当i为0的场合，拼接字符串
			if(i==0) {
				legalIds =  String.valueOf(projectInfo.getSubjectLegal().get(i).getLegalId());
				percentages = String.valueOf(projectInfo.getSubjectLegal().get(i).getPercentage());
			}else {
				//当i不为0的场合
				legalIds = legalIds +","+ String.valueOf(projectInfo.getSubjectLegal().get(i).getLegalId());
				percentages = percentages +","+String.valueOf(projectInfo.getSubjectLegal().get(i).getPercentage());
			}
		}
		//设置部门Ids，法人Ids,比例集合
		projectInfo.setDeptIds(deptIds);
		projectInfo.setLegalIds(legalIds);
		projectInfo.setPercentages(percentages);
		return projectInfo;
	}

	// 课题编辑
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public BaseDto projectUpdate(ProjectInfoDto projectForm) {
		BaseDto baseDto = new BaseDto();
		// 编辑课题
		projectDao.projectUpdate(projectForm);
		//获取编辑年度
		String fyYear = projectForm.getFyYear();
		//获取编辑课题编号
		String projectNo = projectForm.getProjectNo();
		//获取课题编辑者
		String updater = projectForm.getUpdater();
		// 删除部署
		projectDao.subjectDepDelete(fyYear, projectNo);
		// 删除法人
		projectDao.subjectLegalDelete(fyYear, projectNo);
		// 新增部署集合
		List<SubjectDepEntity> subjectDeps = new ArrayList<SubjectDepEntity>();
		//获取修改后部署Id集合
		String[] deptIds = projectForm.getDeptIds().split(",");
		//设置部署信息集合
		for (int i = 0; i < deptIds.length; i++) {
			SubjectDepEntity subjectDep = new SubjectDepEntity();
			subjectDep.setProjectNo(projectNo);
			subjectDep.setFyYear(fyYear);
			subjectDep.setDeptId(deptIds[i]);
			subjectDep.setCreator(updater);
			subjectDeps.add(subjectDep);
		}
		//新增部署
		projectDao.subjectdepAdd(subjectDeps);
		// 法人新增
		//获取修改后法人Id集合
		String[] legalIds = projectForm.getLegalIds().split(",");
		//获取修改后比例集合
		String[] percentages = projectForm.getPercentages().split(",");
		//设置法人信息集合
		List<SubjectLegalDto> subjectLegals = new ArrayList<SubjectLegalDto>();
		for (int i = 0; i < legalIds.length; i++) {
			SubjectLegalDto subjectLegal = new SubjectLegalDto();
			subjectLegal.setProjectNo(projectNo);
			subjectLegal.setFyYear(fyYear);
			subjectLegal.setLegalId(Integer.parseInt(legalIds[i]));
			BigDecimal percentage = new BigDecimal(percentages[i]);
			subjectLegal.setPercentage(percentage);
			subjectLegal.setCreator(updater);
			subjectLegals.add(subjectLegal);
		}
		//新增法人
		projectDao.subjectLegalAdd(subjectLegals);
		// 返回成功Code
		baseDto.setBizCode(BizCode.BIZ_CODE_SUCCESS);
		return baseDto;
	}

	// 课题批量导入
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public FileDto projectUpload(List<Row> fileList, String fyyear, String creator) {
		//申明返回对象
		FileDto baseDto = new FileDto();
		//获取第1行列数
		int column = fileList.get(0).getLastCellNum();
		//当列数少于8的场合
		if (column < Constant.PROJECT_COLUMN_MINE) {
			baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
			return baseDto;
		}
		// 获取第2行
		Row row = fileList.get(1);
		// 法人最后位所在列
		int legalColumn = column - 1;
		// 比较法人是否存在
		boolean legalCheck = legalCheck(row, legalColumn);
		//当法人不存在的场合
		if (!legalCheck) {
			baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
			return baseDto;
		}
		// 正确行数据
		List<ProjectInfoDto> projectList = new ArrayList<ProjectInfoDto>();
		List<SubjectDepEntity> subjectDepList = new ArrayList<SubjectDepEntity>();
		List<SubjectLegalDto> subjectLegalList = new ArrayList<SubjectLegalDto>();
		// 判读数据是否存在
		if (fileList.size() < Constant.PROJECT_LINE_MINE) {
			baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
			return baseDto;
		}
		// 错误行号
		List<Integer> errorTerm = new ArrayList<Integer>();
		// 获取课题编号
		projectNos = projectDao.getProjectNo(fyyear);
		// 获取课题分类
		String type1 = Constant.PROJECT_TYPE;
		List<String> projectTypes = projectDao.getProjectType(type1);
		// 获取法人信息
		String type_1 = Constant.LEGAL;
		List<SubjectLegalDto> subjectLegal = projectDao.getSubjectLegal(fileLegal,type_1);
		// 获取部署Id集合
		List<String> departIds = projectDao.getDepatId();
		for (int i = 2; i < fileList.size(); i++) {
			ProjectInfoDto projectInfo = new ProjectInfoDto();
			projectInfo.setFyYear(fyyear);
			Row data = fileList.get(i);
			// 获取excel课题编号
			Cell projectNoRow = data.getCell(0);
			Object projectNo = null;
			//当数据不为空的场合
			if(projectNoRow!=null) {
			 projectNo = getJavaValue(projectNoRow);
			} else {
				//记录错误行
				errorTerm.add(i+1);
				continue;
			}
			// 课题编号长度
			int projectNoLength = Constant.PROJECTNO_LENGTH_MAX;
			// check课题编号
			String projectno;
			//判断课题编号是否符合要求
			if (projectNoCheck(projectNo, projectNoLength)) {
				//转换课题编号类型
				projectno = String.valueOf(projectNo);
				//设置课题编号
				projectInfo.setProjectNo(projectno);
			} else {
				//记录错误行
				errorTerm.add(i+1);
				continue;
			}
			// 获取分类
			Cell projectTypeRow = data.getCell(1);
			Object projectType=null;
			//当数据不为空的场合
			if(projectTypeRow!=null) {
				 projectType = getJavaValue(projectTypeRow);
			} else {
				//记录错误行
				errorTerm.add(i+1);
				continue;
			}
			// check课题分类
			if (projectTypeCheck(projectType, projectTypes)) {
				int projecttype = Integer.parseInt(projectType.toString());
				projectInfo.setProjectType(projecttype);
			} else {
				//记录错误行
				errorTerm.add(i+1);
				continue;
			}
			// 获取主题
			Cell projectNameRow = data.getCell(2);
			Object projectName=null;
			if(projectNameRow!=null) {
				 projectName = getJavaValue(projectNameRow);
			} else {
				//记录错误行
				errorTerm.add(i+1);
				continue;
			}
			//主题最大长度
			int projectNameLength = Constant.PROJECTNAME_LENGTH_MAX;
			// check主题
			if (projectCheck(projectName, projectNameLength)) {
				//转换课题主题类型
				String projectname = String.valueOf(projectName);
				//设置课题主题
				projectInfo.setProjectName(projectname);
			} else {
				//记录错误行
				errorTerm.add(i+1);
				continue;
			}
			// 获取内容
			Cell contentsRow = data.getCell(3);
			Object contents=null;
			//当数据不为空的场合
			if(contentsRow!=null) {
				 contents = getJavaValue(contentsRow);
			} else {
				//记录错误行
				errorTerm.add(i+1);
				continue;
			}
			String content = String.valueOf(contents);
			// check内容
			if (!StringUtil.isEmpty(content)&&content.length()<Constant.CONTENTS_LENGTH_MAX) {
				//设置课题内容
				projectInfo.setContents(content);
			} else {
				//记录错误行
				errorTerm.add(i+1);
				continue;
			}
			// 获取目标
			Cell projectGoalRow = data.getCell(4);
			Object projectGoal=null;
			//当数据不为空的场合
			if(projectGoalRow!=null) {
				projectGoal = getJavaValue(projectGoalRow);
			} else {
				//记录错误行
				errorTerm.add(i+1);
				continue;
			}
			int projectGoalLength = Constant.PROJECTGOAL_LENGTH_MAX;
			// check目标
			if (projectCheck(projectGoal, projectGoalLength)) {
				//转换课题目标类型
				String projectgoal = String.valueOf(projectGoal);
				//设置课题目标
				projectInfo.setProjectGoal(projectgoal);
			} else {
				//记录错误行
				errorTerm.add(i+1);
				continue;
			}
			// 获取计划人工数据类型
			Cell planTimesRow = data.getCell(5);
			int planTimesType;
			//当数据不为空的场合
			if(planTimesRow!=null) {
				planTimesType = data.getCell(5).getCellType();
			} else {
				//记录错误行
				errorTerm.add(i+1);
				continue;
			}
			//check计划人工数据类型
			if (planTimesType == 0) {
				//获取计划人工
				double planTimes = data.getCell(5).getNumericCellValue();
				//check计划人工值
				if (planTimes < Constant.PORJECT_PLAN_TIMES) {
					//转换计划人工数据类型
					BigDecimal planTime = new BigDecimal(planTimes);
					//设置计划人工
					projectInfo.setPlanTimes(planTime);
				} else {
					//记录错误行
					errorTerm.add(i+1);
					continue;
				}
			} else {
				//记录错误行
				errorTerm.add(i+1);
				continue;
			}
			// 声明法人集合
			List<SubjectLegalDto> subjectlegals = new ArrayList<SubjectLegalDto>();
			//声明比例值
			int percentageCount = 0;
			//申明比例check默认值
			boolean percentageBln = true;
			for (int j = Constant.PROJECT_LEGAL_START; j < legalColumn; j++) {
				// 声明法人对象
				SubjectLegalDto sujectlegal = new SubjectLegalDto();
				//获取法人比例数据类型
				Cell percentageRow = data.getCell(j);
				int percentageType;
				//当数据不为空的场合
				if(percentageRow!=null) {
					 percentageType = data.getCell(j).getCellType();
				} else {
					//记录错误行
					errorTerm.add(i+1);
					continue;
				}
				//当法人比例数据类型为数字或为空的场合
				if (percentageType == 0) {
					//获取法人比例
					double percentage = data.getCell(j).getNumericCellValue();
					//获取法人比例count值
					percentageCount = (int) (percentageCount + percentage);
					//check法人比例count值
					if (percentage <= Constant.PROJECT_PERCENTAGE_COUNT) {
						//当法人比例不为空的场合
						if (percentage != 0.0) {
							//设置法人ID
							sujectlegal.setLegalId(Integer.parseInt(subjectLegal.get(j - Constant.PROJECT_LEGAL_START).getValue()));
							//转换法人比例类型
							BigDecimal prtg = new BigDecimal(percentage);
							//设置法人比例
							sujectlegal.setPercentage(prtg);
							//设置年度
							sujectlegal.setFyYear(fyyear);
							//设置课题编号
							sujectlegal.setProjectNo(projectno);
							//设置创建人
							sujectlegal.setCreator(creator);
							//获取法人对象
							subjectlegals.add(sujectlegal);
						}
					} else {
						percentageBln = false;
						break;
					}
				} else {
					percentageBln = false;
					break;
				}
			}
			//根据check结果判断法人
			if (!percentageBln || percentageCount != Constant.PROJECT_PERCENTAGE_COUNT) {
				//记录错误行
				errorTerm.add(i+1);
				continue;
			}
			// 获取部署
			Cell departmentInfoRow = data.getCell(legalColumn);
			Object departmentInfo=null;
			//当数据不为空的场合
			if(departmentInfoRow!=null) {
				 departmentInfo = getJavaValue(departmentInfoRow);
			} else {
				//记录错误行
				errorTerm.add(i+1);
				continue;
			}
			String department = String.valueOf(departmentInfo);
			//申明部署对象集合
			List<SubjectDepEntity> subjectDeps = new ArrayList<SubjectDepEntity>();
			// check部署信息
			if (department != null && department.length() > 0) {
				//设置部署check默认值
				boolean SubjectDepBln = true;
				//获取部署Id集合
				String[] departList = department.split(",");
				for (int j = 0; j < departList.length; j++) {
					SubjectDepEntity subjectDep = new SubjectDepEntity();
					//check部署Id是否存在
					if (departIds.contains(departList[j])) {
						//设置课题编号
						subjectDep.setProjectNo(projectno);
						//设置课题年度
						subjectDep.setFyYear(fyyear);
						//设置部署Id
						subjectDep.setDeptId(departList[j]);
						//设置创建人
						subjectDep.setCreator(creator);
					} else {
						SubjectDepBln = false;
						break;
					}
					//判断check结果
					if (!SubjectDepBln) {
						//记录错误行
						errorTerm.add(i+1);
						continue;
					}
					//设置部署对象
					subjectDeps.add(subjectDep);
				}
			} else {
				//记录错误行
				errorTerm.add(i+1);
				continue;
			}
			//课题始终创建人
			projectInfo.setCreator(creator);
			//设置法人集合
			subjectLegalList.addAll(subjectlegals);
			//设置部署集合
			subjectDepList.addAll(subjectDeps);
			//设置课题集合
			projectList.add(projectInfo);
		}
		//当课题集合不为空的场合
		if ((projectList != null && projectList.size() != 0)&&(errorTerm==null||errorTerm.size()==0)) {
			//新增课题
			projectDao.projectAddList(projectList);
			//新增法人
			projectDao.subjectLegalAdd(subjectLegalList);
			//新增部署
			projectDao.subjectdepAdd(subjectDepList);
		}
		//获取错误行
		baseDto.setErrorTerm(errorTerm);
		baseDto.setBizCode(BizCode.BIZ_CODE_SUCCESS);
		return baseDto;
	}

	// check课题分类
	public boolean projectTypeCheck(Object data, List<String> projectTypes) {
		String projecttype = String.valueOf(data);
		//check课题分类是否存在
		if (projecttype.length() > 0) {
			if (projectTypes.contains(projecttype)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	// check课题编号
	public boolean projectNoCheck(Object data, int length) {
		//check课题格式
		if (projectCheck(data, length)) {
			//转换课题编号类型
			String projectno = String.valueOf(data);
			//check课题编号是否存在
			if (!projectNos.contains(projectno)) {
				projectNos.add(projectno);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	// check数据是否为空或长度
	public boolean projectCheck(Object data, int length) {
		//转换数据获取类型
		String data1 = String.valueOf(data);
		//check数据格式
		if (data1 == null || data1.length() <= 0 || data1.length() > length) {
			return false;
		} else {
			return true;
		}
	}

	// 法人验证
	public boolean legalCheck(Row row, int legalColumn) {
		//获取法人名称集合
		String type1 = Constant.LEGAL;
		List<String> legalList = projectDao.getLegalList(type1);
		//清空法人名称集合
		fileLegal = new ArrayList<String>();
		//
		for (int i = Constant.PROJECT_LEGAL_START; i < legalColumn; i++) {
			//获取法人名称
			String legalName = row.getCell(i).getStringCellValue().trim();
			fileLegal.add(legalName);
		}
		//check法人是否存在
		if (legalList.containsAll(fileLegal)) {
			return true;
		} else {
			return false;
		}
	}

	// 根据不同数据类型获取数据
	public static Object getJavaValue(Cell cell) {
		Object o = null;
		int cellType = cell.getCellType();
		switch (cellType) {
		// 当为数字场合
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				o = String.valueOf(cell.getDateCellValue());
			} else {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String temp = cell.getStringCellValue();
				// 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
				if (temp.indexOf(".") > -1) {
					o = String.valueOf(new Double(temp)).trim();
				} else {
					o = temp.trim();
				}
			}
			break;
		// 当为字符串的场合
		case Cell.CELL_TYPE_STRING:
			o = cell.getStringCellValue().trim();
			break;
		// 当为公式的场合
		case Cell.CELL_TYPE_FORMULA:
			o = "";
			break;
		// 当为空的场合
		case Cell.CELL_TYPE_BLANK:
			o = "";
			break;
		// 当为布尔值的场合
		case Cell.CELL_TYPE_BOOLEAN:
			o = "";
			break;
		// 其它类型的场合
		default:
			o = "";
		}
		return o;
	}

	//数据格式转换
	public ProjectInfoDto dataChange(ProjectInfoDto projectInfo) {
		// 申明查询变量，默认值设置为“”
		String fyYear = "";
		String projectName = "";
		String[] deptIdList = null;
		// 当查询条件不为空时
		if (projectInfo.getFyYear() != null && !projectInfo.getFyYear().isEmpty()) {
			fyYear = projectInfo.getFyYear();
		}
		if (projectInfo.getDeptId() != null && !projectInfo.getDeptId().isEmpty()) {
			deptIdList = projectInfo.getDeptId().split(",");
		}
		if (projectInfo.getProjectName() != null && !projectInfo.getProjectName().isEmpty()) {
			projectName = projectInfo.getProjectName();
		}
		// 模糊查询
		projectInfo.setFyYear(fyYear);
		projectInfo.setDeptIdList(deptIdList);
		projectInfo.setProjectName(projectName);
		return projectInfo;
	}
	// 课题导出
	@Override
	public FileExportDto projectExport(String filePath, ServletOutputStream out, ProjectInfoDto projectInfo) {
		FileExportDto baseDto = new FileExportDto();
		try {
			projectInfo = dataChange(projectInfo);
			String type1 = Constant.PROJECT_TYPE;
			String type2 = Constant.LEGAL;
			// 获取课题一览查询结果
			List<ProjectInfoDto> list = projectDao.getSubjectListAll(projectInfo,type1,type2);
			//当获取结构不为空的场合
			if(list!=null&&list.size()!=0) {
				for(int i=0;i<list.size();i++) {
					//获取部署信息集合
					 List<DepartmentInfoEntity> departmentList = list.get(i).getDepartmentInfo();
					//当部署信息集合不为空的场合
					 if(departmentList!=null&&departmentList.size()!=0) {
						String deptIds="";
						//设置课题部署名称和课题部署Ids
						 for(int j=0;j<departmentList.size();j++) {
							   //当为第一个数据时
								if(j==0) {
									deptIds = departmentList.get(j).getDeptId();
								} else {
									//当不是第一个数据时
									deptIds = deptIds+","+departmentList.get(j).getDeptId();
								}
							}
						 list.get(i).setDeptIds(deptIds);
					}
				}
			}
			//获取样本文件
			FileInputStream is = new FileInputStream(filePath);
			//获取excel
			Workbook workbook = null;
			try {
				workbook = new HSSFWorkbook(is);
			} catch (Exception ex) {
			    // 解决read error异常
			    is = new FileInputStream(filePath);
			    workbook = new XSSFWorkbook(is);
			}
			// 获取第一个sheet
			Sheet  hssfSheet = workbook.getSheetAt(0);
			//获取法人名称集合
			List<String> legalList = projectDao.getLegalList(type2);
		//设置对应法人
			//获取第3行
			Row hssfRow0 = hssfSheet.getRow(0);
			//获取第4行
			Row hssfRow1 = hssfSheet.getRow(1);
			//设置默认值
			Cell hssfCell = null;
			Cell cellLegal = null;
			//获取第3行和第4行对应excel样本式样
			CellStyle columnlegal = hssfSheet.getRow(0).getCell(6).getCellStyle();
			CellStyle columnlegal1 = hssfSheet.getRow(0).getCell(7).getCellStyle();
			CellStyle columnNum = hssfSheet.getRow(1).getCell(6).getCellStyle();
			CellStyle columnNum1 = hssfSheet.getRow(1).getCell(7).getCellStyle();
			//循环遍历法人
			for (int i = 0; i < legalList.size(); i++) {
				hssfCell = hssfRow1.createCell(6+i);
				//当i不为0的场合
				if(i!=0) {
					cellLegal = hssfRow0.createCell(6+i);
				}
				if(i==legalList.size()-1) {
				//当i为最大值的场合
					hssfCell.setCellStyle(columnNum1);
					//当i不为0的场合
					if(i!=0) {
						cellLegal.setCellStyle(columnlegal1);
					}
				}else {
				//当i不为最大值的场合
					hssfCell.setCellStyle(columnNum);
					//当i不为0的场合
					if(i!=0) {
						cellLegal.setCellStyle(columnlegal);
					}
				}
				//设置法人名称
				hssfCell.setCellValue(legalList.get(i));
			}
			//总列数
			int countColumn = 6+legalList.size()+1;
			//对应法人单元格合并
			CellRangeAddress cra=new CellRangeAddress(0, 0, 6, countColumn-2);
			//在sheet里增加合并单元格
			hssfSheet.addMergedRegion(cra);
			//获取合并单元格
			cellLegal = hssfSheet.getRow(0).getCell(6);
			//设置单元格样式
			cellLegal.setCellStyle(columnlegal);
			cellLegal=hssfRow0.createCell(6+legalList.size());
			cellLegal.setCellStyle(columnNum1);
			hssfCell = hssfRow1.createCell(6+legalList.size());
			hssfCell.setCellStyle(columnNum1);
			hssfCell.setCellValue(Constant.PROJECT_DEPT_TITLE);
			Row hssfRow=null;
			// 写入实体数据
			if (list != null && !list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					hssfRow = hssfSheet.createRow(i + 2);
					//获取数据
					ProjectInfoDto projectInfoDto = list.get(i);
					//课题编号
					String projectNo = "";
					hssfCell = hssfRow.createCell(0);
					if (projectInfoDto.getProjectNo() != null) {
						projectNo = projectInfoDto.getProjectNo();
					}
					hssfCell.setCellValue(projectNo);
					//分类
					String dispName = "";
					hssfCell = hssfRow.createCell(1);
					if (projectInfoDto.getProjectNo() != null) {
						dispName = projectInfoDto.getDispName();
					}
					hssfCell.setCellValue(dispName);
					//主题
					String projectName = "";
					hssfCell = hssfRow.createCell(2);
					if (projectInfoDto.getProjectName() != null) {
						projectName = projectInfoDto.getProjectName();
					}
					hssfCell.setCellValue(projectName);
					//内容
					String contents = "";
					hssfCell = hssfRow.createCell(3);
					if (projectInfoDto.getContents() != null) {
						contents = projectInfoDto.getContents();
					}
					hssfCell.setCellValue(contents);
					//目标
					String projectGoal = "";
					hssfCell = hssfRow.createCell(4);
					if (projectInfoDto.getProjectGoal() != null) {
						projectGoal = projectInfoDto.getProjectGoal();
					}
					hssfCell.setCellValue(projectGoal);
					//计划人工
					BigDecimal planTimes = new BigDecimal(0);
					hssfCell = hssfRow.createCell(5);
					if (!projectInfoDto.getPlanTimes().equals(BigDecimal.ZERO)) {
						planTimes = projectInfoDto.getPlanTimes();
					}
					hssfCell.setCellValue(planTimes.doubleValue());
					//对应法人
					List<SubjectLegalDto> legals =projectInfoDto.getSubjectLegal();
					for(int j=0;j<legalList.size();j++) {
						hssfCell = hssfRow.createCell(6+j);
						BigDecimal percentage = new BigDecimal(0);
						//判断对应法人是否为空
						if (legals!= null&&!legals.isEmpty()) {
							//循环遍历课题对应法人集合
							for(int m=0;m<legals.size();m++) {
								//判断是否含有此法人
								if(legalList.get(j).trim().equals(legals.get(m).getDispName().trim())) {
									//获取法人比例
									percentage = legals.get(m).getPercentage();
									break;
								}
							}
						}
						hssfCell.setCellValue(percentage.doubleValue());
					}
					String deptIds = "";
					hssfCell = hssfRow.createCell(6+legalList.size());
					if (projectInfoDto.getDeptIds()!= null) {
						deptIds = projectInfoDto.getDeptIds();
					}
					hssfCell.setCellValue(deptIds);
				}
			}

			// 第七步，将文件输出到客户端浏览器
			try {
				//文件导出路径
				String fileName= Constant.PROJECT_EXPORT_NAME.concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMDDHHmmSS")));
				 String file_Path =Constant.FILE_EXPORT_PATH + fileName+Constant.FILE_EXPORT_TYPE;
				FileOutputStream fileOutputStream = new FileOutputStream(file_Path);// 指定路径与名字和格式
				workbook.write(fileOutputStream);// 将数据写出去
				fileOutputStream.close();// 关闭输出流
				baseDto.setFileName(fileName);
			} catch (Exception e) {
				baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
				return baseDto;
			}
			baseDto.setBizCode(BizCode.BIZ_CODE_SUCCESS);
			return baseDto;
		} catch (Exception e) {
			baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
			return baseDto;
		}
	}

	@Override
	public int getSubjectListCount(ProjectInfoDto projectInfo) {
		// 申明查询变量，默认值设置为“”
		String fyYear = "";
		String projectName = "";
		String[] deptIdList = null;
		// 当查询条件不为空时
		if (projectInfo.getFyYear() != null && !projectInfo.getFyYear().isEmpty()) {
			fyYear = projectInfo.getFyYear();
		}
		if (projectInfo.getDeptId() != null &&!projectInfo.getDeptId().isEmpty()) {
			deptIdList = projectInfo.getDeptId().split(",");
		}
		if (projectInfo.getProjectName() != null && !projectInfo.getProjectName().isEmpty()) {
			projectName = projectInfo.getProjectName();
		}
		// 模糊查询
		projectInfo.setFyYear(fyYear  );
		projectInfo.setDeptIdList(deptIdList);
		projectInfo.setProjectName(projectName);
		String type1 = Constant.PROJECT_TYPE;
		// 获取课题一览查询结果
		int count = projectDao.getSubjectListCount(projectInfo,type1);
		return count;
	}
}
