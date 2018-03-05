package com.dm.tool;

import java.math.BigDecimal;
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块：常量定义
 */
public class Constant {
	public static final String SESSION_KEY_LOGIN_INFO = "SESSION_KEY_LOGIN_INFO";

	public static final Object[] OBJ_ARR_NULL = null;

	public static final String SEARCH_TYPE_LIKE = "like";

	public static final String SEARCH_TYPE_EQUAL = "equal";

	public static final String SEARCH_TYPE_LEFT_LIKE = "left_like";

	public static final String SEARCH_TYPE_RIGHT_LIKE = "right_like";

	public static final String STR_PERCENT = "%";

	public static final String STR_EMPTY_BLANK = "";

	//session key
	public static final String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";
	public static final String LOGON_INFO_SESSION_KEY = "LOGON_INFO_SESSION_KEY";
	public static final String SAVE_MONTHLY_HOUR_KEY = "SAVE_MONTHLY_HOUR_KEY";
	public static final String SAVE_PROJECT_LIST_KEY = "SAVE_PROJECT_LIST_KEY";
	public static final String SAVE_DATASRC_ITEM_KEY = "SAVE_DATASRC_ITEM_KEY";
	public static final String SAVE_DEVICE_STATISTICS_KEY = "SAVE_DEVICE_STATISTICS_KEY";
	public static final String SAVE_DEVICE_ANNUAL_STATISTICS_KEY = "SAVE_DEVICE_ANNUAL_STATISTICS_KEY";
	public static final String SAVE_PROJECT_HOUR_MONTH_LIST_KEY = "SAVE_PROJECT_HOUR_MONTH_LIST_KEY";

	//cookie key
	public static final String PROJECT_PAGE_LENGTH = "projectPageLength";
	public static final String DEVICE_PAGE_LENGTH = "devicePageLength";
	public static final String ITEM_PAGE_LENGTH = "itemPageLength";
	public static final String ITEMSRC_PAGE_LENGTH = "itemsrcPageLength";
	public static final String STAFF_PAGE_LENGTH = "staffPageLength";
	public static final String LOG_PAGE_LENGTH = "logPageLength";
	public static final String USE_PAGE_LENGTH = "usePageLength";

	//request key
	public static final String CTRL_START_TIME_REQUEST_KEY = "CTRL_START_TIME_REQUEST_KEY";

	//执行结果
	public static final String EXE_RETURN_OK = "EXE_RETURN_OK";
	public static final String EXE_RETURN_NG = "EXE_RETURN_NG";

	//message
	public static final String MSG_SYS_ERROR = "系统错误发生，原因不明，请联络系统管理员！";
	public static final String MS_ERROR = "画面不存在！";

	//用户级别
	public static final String USERLEVEL_COMPANY = "0";
	public static final String USERLEVEL_DEPART  = "1";
	public static final String USERLEVEL_PERSONAL  = "2";

	// 日期时间格式化参数
	public static final String FORMAT_DATE_0 = "M/d";

	// 文件路径
	public static final String FILE_EXPORT_PATH = "D:/";

	//区分表
	public static final String DOCUMENT_STATUS = "DOCUMENT_STATUS";
	//所属部门ID
	public static final String SUBORDINATE_DEPT_ID = "SUBORDINATE_DEPT_ID";
	//公司名称
	public static final String COMPANY_NAME="NSK";

	//审核部门ID
	public static final String AUDIT_DEPT_ID = "AUDIT_DEPT_ID";
	//课题批量导入，最小列数
	public static final int  PROJECT_COLUMN_MINE = 8;
	//课题批量导入,最小行数
	public static final int PROJECT_LINE_MINE=2;
	//课题编号最大长度
	public static final int PROJECTNO_LENGTH_MAX=20;
	//课题主题最大长度
	public static final int PROJECTNAME_LENGTH_MAX=100;
	//课题目标最大长度
	public static final int PROJECTGOAL_LENGTH_MAX=500;
	//课题内容最大长度
	public static final int CONTENTS_LENGTH_MAX=500;
	//课题计划人工最大数值
	public static final double PORJECT_PLAN_TIMES = 9999999999.0;
	//课题法人比例总值
	public static final int PROJECT_PERCENTAGE_COUNT=100;
	//法人起始列
	public static final int PROJECT_LEGAL_START = 6;
	//文档填写-请输入
	public static final String DOC_INPUR_PLEASE = "请输入";
	//文档填写-最大长度不能超过
	public static final String DOC_MAXLENGTH_LESS = "最大长度不能超过";
	//文档填写-必须是整数
	public static final String DOC_MUSTIS_INTEGER = "必须是整数";
	//文档填写-必须是金额
	public static final String DOC_MUSTIS_AMOUNT = "必须是金额";
	//文档填写-旧文件编号已录入
	public static final String DOC_INPUT_OLDDOCUMENT = "旧文件编号已录入";
	//文档填写-未设置文档的最终审核人。
	public static final String DOC_NOTSET_FINALEXAMINE = "未设置文档的最终审核人。";
	//文档填写-文档已经被其他用户修改，请重新进入后再进行修改。
	public static final String DOC_REENTER_MODIFY = "文档已经被其他用户修改，请重新进入后再进行修改。";
	//导出文件格式
	public static final String FILE_EXPORT_TYPE=".xls";
	//课题分类
	public static final String PROJECT_TYPE="PROJECT_TYPE";
	//课题法人
	public static final String LEGAL="LEGAL";
	//查询定义默认系统用户编号
	public static final String USER_ID="0000000000";
	//设备预约模式 新增
	public static final int RESERVE_PATTERN_ADD = 1;
	//设备预约模式 修改
	public static final int RESERVE_PATTERN_UPDATE = 2;
	//设备预约模式 删除
	public static final int RESERVE_PATTERN_DELETE = 3;
	//设备预约号 最小值（数据库中的限制）
	public static final BigDecimal RESERVE_NO_MIN = new BigDecimal("1");
	//设备预约号 最大值（数据库中的限制）
	public static final BigDecimal RESERVE_NO_MAX = new BigDecimal("9999999999");
	//设备号的最大长度 （数据库中的限制）
	public static final int DEVICE_NO_MAX_LENGTH = 20;
	//设备预约标题最大长度（数据库中的限制）
	public static final int RESERVE_TITLE_MAX_LENGTH = 200;
	//设备预约使用目的最大长度（数据库中的限制）
	public static final int RESERVE_USE_GOAL_MAX_LENGTH = 500;
	//设备预约颜色正则表达式规则
	public static final String RESERVE_COLOR = "^#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$";

	//数据项修改模式
	public static final int ITEM_UPDATE = 1;
	//数据项isfromto项目 combox状态（选中--未选中）
	public static final String ITEM_CHANGE_N = "ytn";
	//数据项isfromto项目 combox状态（未改变）
	public static final String ITEM_CHANGE_U = "nothing";
	//数据项isfromto项目 combox状态（未选中--选中）
	public static final String ITEM_CHANGE_Y = "nty";
	//公共项，是否指向项目  默认值0
	public static final String ZERO = "0";
	//是否指向项目设置值1
	public static final String ONE = "1";

	//设备名称最大长度（数据库中的限制）
	public static final int RESERVE_DEVICENAME_MAX_LENGTH = 200;
	//设备参数最大长度（数据库限制）
	public static final int RESERVE_DEVICEPARM_MAX_LENGTH = 500;
	//设备说明最大长度（数据库限制）
	public static final int RESERVE_COMMENTINFO_MAX_LENGTH = 500;
	//文档数据项显示名称最大长度（数据库限制）
	public static final int RESERVE_DOCUMENTITEMNAME_MAX_LENGTH = 20;
	//用户名最大长度（数据库限制)
	public static final int RESERVE_USERCODE_MAX_LENGTH = 50;
	//姓名最大长度（数据库限制)
	public static final int RESERVE_USERNAME_MAX_LENGTH = 50;
	//邮箱最大长度（数据库限制)
	public static final int RESERVE_EMAIL_MAX_LENGTH = 50;
	//一览显示数据项
	public static final String LIST_SHOW_DATA_ITEM = "LIST_SHOW_DATA_ITEM";
	//用户自定义查询数据项
	public static final String USER_CUSTOM_QUERY_DATA_ITEM = "USER_CUSTOM_QUERY_DATA_ITEM";
	//课题人工统计月度报表名称
	public static final String PROJECT_HOUR_MONTHLY_REPORT_NAME="课题人工统计月度报表";
	//课题一览报表名称
	public static final String PROJECT_EXPORT_NAME="课题一览报表";
	//实验设备统计报表
	public static final String DEVICE_EXPORT_NAME="实验设备统计报表";
	//部署年度文档统计报表
	public static final String DEPARTANNUAL_EXPORT_NAME="部署年度文档统计报表";
	//所属部署
	public static final String PROJECT_DEPT_TITLE="所属部署";
	//长度0
	public static final int RESERVE_LENGTH_NUM_ZERO = 0;
	//长度0.0
	public static final double RESERVE_LENGTH_NUM_ZEROPOINT = 0.0;
	//权限维护目录树头名称
	public static final String AUTHORITY_MANAGE_TREE_TITLE="权限集";

	//登录用户不存在
	public static final String LOGIN_MESSAGE_NO_USER="用户不存在";
	//登录用户名密码有误
	public static final String LOGIN_MESSAGE_PWD_FAIL="用户名或密码输入有误";
	//用户权限不足
	public static final String LOGIN_MESSAGE_ROLE_FAIL="角色信息不足，登录失败";
	//文档编号或文档类型为空
	public static final String LOGIN_NULL_FILE_NUMBER="文档编号或文档类型为空";

	// 匹配模式
	public static final String MATCHING_TYPE_EQUAL = "1";
	public static final String MATCHING_TYPE_LIKE = "2";
	public static final String MATCHING_TYPE_GREATER_OR_EQUAL = "3";
	public static final String MATCHING_TYPE_LESS_OR_EQUAL = "4";
	public static final String MATCHING_TYPE_IN = "5";

	public static final String KEEP_DOC_SEARCH_LIST_DATA = "KEEP_DOC_SEARCH_LIST_DATA";

	// 文档状态
	public static final String DOC_STATUS = "DOCUMENT_STATUS";
	public static final int DOC_STATUS_MAKING = 1;
	public static final int DOC_STATUS_AUDITING = 2;
	public static final int DOC_STATUS_COMPLETED = 3;

	//文档数据项
	public static final String DOCUMENT_ITEM_F00000 = "DOCUMENT_ITEM_F00000";
	public static final String DOCUMENT_ITEM_F00001 = "DOCUMENT_ITEM_F00001";
	public static final String DOCUMENT_ITEM_F00002 = "DOCUMENT_ITEM_F00002";
	public static final String DOCUMENT_ITEM_F00003 = "DOCUMENT_ITEM_F00003";
	public static final String DOCUMENT_ITEM_F00004 = "DOCUMENT_ITEM_F00004";
	public static final String DOCUMENT_ITEM_F00005 = "DOCUMENT_ITEM_F00005";
	public static final String DOCUMENT_ITEM_F00006 = "DOCUMENT_ITEM_F00006";
	public static final String DOCUMENT_ITEM_F00007 = "DOCUMENT_ITEM_F00007";
	public static final String DOCUMENT_ITEM_F00008 = "DOCUMENT_ITEM_F00008";
	public static final String DOCUMENT_ITEM_F00009 = "DOCUMENT_ITEM_F00009";
	public static final String DOCUMENT_ITEM_F000010 = "DOCUMENT_ITEM_F00010";
	public static final String DOCUMENT_ITEM_F000011 = "DOCUMENT_ITEM_F00011";
	public static final String DOCUMENT_ITEM_F000012 = "DOCUMENT_ITEM_F00012";

	//数据项类型
	public static final String ITEM_TYPE_TEXT = "1";
	public static final String ITEM_TYPE_AREA = "2";
	public static final String ITEM_TYPE_INT = "3";
	public static final String ITEM_TYPE_MONEY = "4";
	public static final String ITEM_TYPE_FLOAT = "5";
	public static final String ITEM_TYPE_DATE = "6";
	public static final String ITEM_TYPE_SELECT = "7";
	public static final String ITEM_TYPE_CHECKBOX = "8";
	public static final String ITEM_TYPE_RADIO = "9";
	public static final String ITEM_TYPE_FILE_SINGLE = "10";
	public static final String ITEM_TYPE_FILE_MULT = "11";
	public static final String ITEM_TYPE_POP_USER = "12";
	public static final String ITEM_TYPE_POP_DEPT = "13";
	public static final String ITEM_TYPE_POP_USER_MULT = "14";
	public static final String ITEM_TYPE_POP_DEPT_MULT = "15";

	//操作内容
	public static final int OPERATE_TYPE_REGISTER = 1;
	public static final int OPERATE_TYPE_FILE_UPLOAD = 2;
	public static final int OPERATE_TYPE_FILE_DOWNLOAD = 4;
	public static final int OPERATE_TYPE_FILE_DELETE = 5;
	public static final int OPERATE_TYPE_UPDATE = 6;

	//文件类型
	public static final String FILE_TYPE_PARENT = "1";
	public static final String FILE_TYPE_CHILD = "2";

	// 审核通过/退回
	public static final String APPROVAL_STATUS_SUCCESS = "1";
	public static final String APPROVAL_STATUS_FAILED = "2";

	//文档填写模式
	public static final String MODE_DETAIL = "0";
	public static final String MODE_MODIFY = "1";
	public static final String MODE_ADD_NEW = "2";
	public static final String MODE_APPROVE = "3";
	//审核
	public static final int APPROVAL_SUCCESS = 131;
	public static final int APPROVAL_FAILED = 132;

}
