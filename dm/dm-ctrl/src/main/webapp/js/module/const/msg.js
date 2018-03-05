/**
 * -------------------------------------------------------
 * @模块描述:消息常量定义
 * @作者:邵林军
 * @修改履历:新作成 2017/12/14
 * -------------------------------------------------------
 */

Namespace.register("Globals.constMsg");

Globals.constMsg = (function(){
	var _this = null;
	//========================================================
	//消息msg常量定义区域
	var constMsg = {

		//API调用系统Msg
		API_ERR_Msg:{
			//成功
			RTN_CODE_SUCCESS : "成功",
			//未登录
			RTN_CODE_NOT_LOGIN : "您尚未登录，请先登录",
			//连接超时
			RTN_CODE_CONNECT_TIMEOUT : "服务器响应超时",
			//未知错误发生
			RTN_CODE_UNKNOWN : "服务器端未知错误发生，请联系管理员",
			//登录超时(session timeout)
			RTN_CODE_SESSION_TIMEOUT : "会话超时，请重新登录",
			//无权限
			RTN_CODE_NOT_PERMITTED : "您没有权限访问数据",
			//API接口未找到
			RTN_CODE_API_NOT_FOUND : "API接口不存在",
			//返回结果NULL错误
			RTN_CODE_RESULT_NULL : "返回结果NULL",
			//无效的接口参数
			RTN_CODE_INVALID_PARAMS : "无效参数",
			//非法请求客户端
			RTN_CODE_ILLEGAL_REQUEST : "非法请求",
			//服务器端返回的数据格式错误
			RTN_CODE_DATA_PARSER_ERROR : "服务器端返回数据格式错误",
		},

		//API调用业务Msg
		API_BIZ_Msg: {
			//是否要删除
			BIZ_CODE_SHUR_DELETE:"是否要删除",
			//确认删除
			CONFIRM_DELETE:"是否要删除",
			//确认修改
			CONFIRM_UPD:"确认修改",
			//是否放弃
			CONFIRM_BACK:"是否放弃操作?",
			//业务处理成功
			BIZ_CODE_SUCCESS : "成功",
			//接口参数不正
			BIZ_CODE_INVALID_PARAMS : "接口参数不正确",
			//改数据项已在文档录入表单定义表中使用，不能进行删除处理！
			BIZ_CODE_DELETE_FAILED :"该数据项已在文档录入表单定义表中使用，不能进行删除处理！",
			//记录0条
			BIZ_CODE_RECORD_0 : "没有记录",
			//添加记录成功HTML_MSG_LENGTH_ITEM()
			BIZ_CODE_INSERT_SUCCESS : "新增成功",
			//更新记录成功
			BIZ_CODE_UPDATE_SUCCESS : "更新成功",
			//更新失败
			BIZ_CODE_UPDATE_ERROR : "更新失败",
			//新增失败
			BIZ_CODE_INSERT_FAILED : "新增失败",
			//删除记录成功
			BIZ_CODE_DELETE_SUCCESS : "删除成功",
			//登录验证失败错误
			BIZ_CODE_LOGIN_AUTH_FAILED : "登录验证失败",
			//操作失败
			BIZ_CODE_HANDLE_FAILED : "操作失败",
			//预约期间重复，请重新填写
			BIZ_CODE_DURATION_ERR : "预约期间重复，请重新填写",
			//数据源不能被删除
			BIZ_CODE_SRCCANT_DELETE : "数据源已被使用，不能删除",
			//条目不能被删除
			BIZ_CODE_ITEMCANT_DELETE : "本条目已被使用，不能删除",
			//设备预约新增成功
			BIZ_CODE_RESERVE_ADD_SUCCESS : "设备预约新增成功",
			//设备预约新增失败
			BIZ_CODE_RESERVE_ADD_FAILED : "设备预约新增失败",
			//设备预约修改成功
			BIZ_CODE_RESERVE_UPDATE_SUCCESS : "设备预约修改成功",
			//设备预约修改失败
			BIZ_CODE_RESERVE_UPDATE_FAILED : "设备预约修改失败",
			//设备预约删除成功
			BIZ_CODE_RESERVE_DELETE_SUCCESS : "设备预约删除成功",
			//设备预约删除失败
			BIZ_CODE_RESERVE_DELETE_FAILED : "设备预约删除失败",
			//设备预约操作模式异常
			BIZ_CODE_RESERVE_PATTERN_ERR : "设备预约操作模式异常",
			//行不能被删除
			BIZ_CODE_TRCANT_DELETE : "无法删除",
			//设备预约中删除失败
			BIZ_CODE_RESERVEDEVICE_DELETE_FAILED :"设备预约中，删除失败！",
			//设备使用中删除失败
			BIZ_CODE_USEDEVICE_DELETE_FAILED:"设备使用中，删除失败！",
			//删除成功
			BIZ_CODE_DEVICE_DELETE_SUCCESS:"删除成功！",
			//该数据项已在文档录入表单定义表中使用，不能进行删除处理
			BIZ_CODE_DELETE_DOCUMENT_ALREADY : "该数据项已在文档录入表单定义表中使用，不能进行删除处理",
			//该数据项已在文档一览检索数据项定义表中使用，不能进行删除处理
			BIZ_CODE_DELETE_DOCUMENT_USE : "该数据项已在文档一览检索数据项定义表中使用，不能进行删除处理",
			//删除成功
			BIZ_CODE_DELETE_DOCUMENT_SUCCESS : "删除成功",
			//删除失败
			BIZ_CODE_DELETE_DOCUMENT_FAILED : "删除失败",
			//做成中
			BIZ_CODE_SUCCESSING_DOCUMENT : "做成中",
			//审核中
			BIZ_CODE_EXAMINEING_DOCUMENT : "审核中",
			//已完成
			BIZ_CODE_SUCCESS_DOCUMENT : "已完成",
			//选中文件
			SELECT_FILE : "请选择文件",
			//文件格式不正确
			FILE_TYLE_ERROR : "文件格式不正确，请选择excel文件！",
			//是否导出课题数据
			FILE_EXPORT : "是否导出课题数据？",
			//文件导出成功
			FILE_EXPORT_SUCCESS : "文件导出成功！",
			//文件导出失败
			FILE_EXPORT_ERROR : "文件导出失败！",
			//课题新增失败
			PROJECTNO_REPEAT : "新增失败,课题编号已存在!",
			//文件导入成功
			FILE_IMPORT_SUCCESS:"批量导入成功!",
			//批量导入失败
			FILE_IMPORT_ERROR:"批量导入失败!",
			//错误行
			File_ROW_ERROR:"该文档内容有误，修改后重新导入！错误行号为:",
			// 文档管理 - 未设置文档的最终审核人。
			DOC_APPROVE_STAFF_NOT_SET : "未设置文档的最终审核人。",
			//权限未选中
			AUTHORITY_UNSELECTED:"请选择角色权限",
			//权限保存成功
			AUTHORITY_SUCCESS:"权限设定保存成功",
			//权限设定保存失败
			AUTHORITY_ERROR:"权限设定保存失败",
			//公司名称不可修改
			DEPART_NODE_COMPANY:"公司信息不可修改!",
			//部门已被删除
			DEPART_UNEXIST:"该部门不存在!",
			//设备删除失败（设备处于使用中）
			DELETE_FAILED:"该设备处于使用中，无法删除",
			//公司不可删除
			COMPANY_UNDELETE:"不可删除公司",
			//实验设备编号已存在
			DEVICE_EXIST:"设备编号已存在",
			//为选择删除对象
			DELETE_UNSELECT:"请选择角色",
			//角色不能删除
			ROLE_UNDELETE:"该角色已被使用，不能删除",
			//请选择部门
			MONTHLY_HOUR_CHOOSE_DEPT:'请选择部门',
			//最终审核人必须选择
			DOC_LAST_APPROVAL_MUST_SELECT:'最终审核人必须选择',
			//请选择部门
			MONTHLY_HOUR_CHOOSE_FYYEAR:'请选择FY年月',
			//请选择文档类型
			MONTHLY_HOUR_CHOOSE_DOCUMENTTYPE:'请选择文档类型',
			//部门无法删除
			DEPARTMANAGE_UNABLE_DELETE:"已存在员工，无法删除。",
			//导出实验设备数据
			DEVICE_FILE_EXPORT:"是否导出实验设备统计数据？",
			//导出部署年度文档统计数据
			DEVICEANNUAL_FILE_EXPORT:"是否导出部署年度文档统计数据？",
		},

		//前端Msg(显示在右上角Msg里的内容)
		HTML_BIZ_Msg: {
			//请选择人员
			HTML_MSG_CHOOSE_STAFF:"请选择人员！",
			//请选择法人
			HTML_MSG_CHOOSE_LEGAL:"请选择法人！",
			//请选择部门
			HTML_MSG_CHOOSE_DEPT:"请选择部门！",
			//请选择课题
			HTML_MSG_CHOOSE_PROJECT:"请选择课题！",
			//系统错误
			HTML_MSG_ERROR_SYSTEM:"系统错误",
			//工时填写失败
			HTML_MSG_FILLIN_FAIL:"工时填写失败",
			//导出成功
			HTML_MSG_EXPORT_SUCCESS:"导出成功",
			//系统错误
			HTML_MSG_EXPORT_ERROR:"登陆超时，请重新登陆!",
			//法人比例总和必须为100%
			HTML_MSG_PROPORTION_LEGAL:"法人比例总和必须为100%",
			//请选择所属部门
			HTML_MSG_CHOOSE_USERDEPART:"请选择所属部门",
			//法人比例
			HTML_MSG_LEGAL_PROPORTION:"法人比例必须输入",
			//添加部门
			HTML_MSG_DEPART_ESXIT:"已存在，请勿重复添加!",
			//文档录入表单定义修改成功
			HTML_MSG_DOC_ENTER_FORM_EDIT_SUCCESS:'文档录入表单定义修改成功。',
			//文档数据项已有录入数据，不能删除
			HTML_MSG_DOC_CANNOT_DELETE:'文档数据项已有录入数据，不能删除。',
			//查询项目未设置
			HTML_MSG_SEARCH_ITEM_DONOT_STTING:'查询项目未设置。',
			//文档查询默认条件设置保存成功
			HTML_MSG_DOC_SEARCH_DEFAULT_SETTING_SUCCESS:'文档查询默认条件设置保存成功。',
			//没有修改文档的权限
			HTML_MSG_NO_AUTHORITY_EDIT_DOC:'没有修改文档的权限。',
			//没有删除文档的权限。
			HTML_MSG_NO_AUTHORITY_DELETE_DOC:'没有删除文档的权限。',
			//没有查看文档的权限
			HTML_MSG_NO_AUTHORITY_READ_DOC:'没有查看文档的权限。',
			//文档已成功删除
			HTML_MSG_DOC_DELETE_SUCCESS:'文档已成功删除。',
			//请先录入文档类型后，再进行文档录入表单定定义。
			HTML_MSG_DOC_ENTER_DEFINE:'请先录入文档类型后，再进行文档录入表单定定义。',
			//文档审核处理成功。
			HTML_MSG_DOC_EXAMINE_SUCCESS:'文档审核处理成功。',
			//审核成功。
			HTML_MSG_DOC_TOEXAMINE_SUCCESS:'文档审核处理成功。',
			//文档数据保存成功。
			HTML_MSG_DOC_DATA_VATIONSUCCESS:'文档数据保存成功。',
			//邮件发送失败
			HTML_MSG_SEND_EMAIL_FAILED:'邮件发送失败。',
		},

		//前端表单验证Msg
		HTML_FRM_Msg: {
			//请选择设备
			HTML_FRM_CHOOSE_DEVICE:"请选择设备",
			//请选择年月
			HTML_FRM_CHOOSE_YEAR_MONTH:"请选择年月",
			//请输入标题
			HTML_FRM_INPUT_TITLE:"请输入标题",
			//标题最长输入{0}个字符
			HTML_FRM_TITLE_MAX:"标题最长输入{0}个字符",
			//请输入使用目的
			HTML_FRM_INPUT_USE_GOAL:"请输入使用目的",
			//使用目的最长输入{0}个字符
			HTML_FRM_USE_GOAL_MAX:"使用目的最长输入{0}个字符",
			//请输入开始时间
			HTML_FRM_INPUT_START_TIEM:"请输入开始时间",
			//请输入结束时间
			HTML_FRM_INPUT_END_TIME:"请输入结束时间",
			//期间结束时间必须大于期间开始时间
			HTML_FRM_PERIOD_START_GREATER_END:"期间结束时间必须大于期间开始时间",
			//期间结束时间必须大于系统时间
			HTML_FRM_PERIOD_END_GREATER_SYS:"期间结束时间必须大于系统时间",
			//类别必须选择
			HTML_FRM_SELECT_LEGAL:"类别必须选择",
			//数据源名称必须填写
			HTML_MSG_MUSTINPUT_SOURCENAME:"数据源名称必须填写",
			//条目名称必须填写
			HTML_MSG_MUSTINPUT_TMNAME:"条目名称必须填写",
			//条目主文档编号必须填写
			HTML_MSG_MUSTINPUT_TMNO:"条目主文档编号必须填写",
			//数据项类型必须选择
			HTML_MSG_MUSTCHOOSE_ITEMNAME:"数据项类型必须选择",
			//数据源必须选择
			HTML_MSG_MUSTCHOOSE_SOURCENAME:"数据源必须选择",
			//数据项显示名称不得为空
			HTML_MSG_NOTNULL_ITEMNAME:"数据项显示名称不得为空",
			//数字格式不得为空
			HTML_MSG_NOTNULL_NUMTYPR:"数字格式不得为空",
			//请不要输入#,.以外字符
			HTML_MSG_MUSTINPUT_NUMTYPR:"请不要输入#,.以外字符",
			//请不要输入#,.以外字符
			HTML_MSG_MUSTINPUT_TRUENUMTYPR:"请输入正确的数字格式",
			//用户id必须输入
			HTML_MSG_MUSTCHOOSE_USERID:"用户id必须输入",
			//用户账号必须输入
			HTML_MSG_MUSTCHOOSE_USERNAME:"用户账号必须输入",
			//用户姓名必须输入
			HTML_MSG_MUSTCHOOSE_REALNAME:"用户姓名必须输入",
			//用户邮箱必须输入
			HTML_MSG_MUSTCHOOSE_EMAIL:"用户邮箱必须输入",
			//请输入有效的邮箱地址
			HTML_MSG_EFFECT_EMAIL:"请输入有效的邮箱地址",
			//角色类型必须选择
			HTML_MSG_MUSTCHOOSE_USERTYPE:"角色类型必须选择",
			//请选择数据。
			HTML_MSG_CHOOSE_ITEM:"请选择数据。",
			//请选择用户。
			HTML_MSG_CHOOSE_USER:"请选择用户。",
			//所选数据
			HTML_MSG_CHOOSE_CITEM:"所选数据",
			//用户
			HTML_MSG_NAME_USER:"用户",
			//数据项名称最长输入20个字符
			HTML_MSG_LENGTH_ITEMNAME:"数据项名称最长输入20个字符",
			//类别必须选择
			HTML_MSG_MUSTCHOOSE_CATEGORY:"类别必须选择",
			//内容不得为空
			HTML_MSG_NOTNULL_CONTENT:"内容不得为空",
			//请输入设备编号
			HTML_MSG_CHOOSE_DEVICENUM:"请输入设备编号",
			//设备编号最长输入20个字符
			HTML_MSG_LENGTH_DEVICENUM:"设备编号最长输入20个字符",
			//请输入设备名称
			HTML_MSG_CHOOSE_DEVICENAME:"请输入设备名称",
			//设备名称最长输入1000个字符
			HTML_MSG_LENGTH_DEVICENAME:"设备名称最长输入1000个字符",
			//请输入设备位置
			HTML_MSG_CHOOSE_DEVICEPOSITION:"请输入设备位置",
			//设备位置最长输入10个字符
			HTML_MSG_LENGTH_DEVICEPOSITION:"设备位置最长输入10个字符",
			//实验设备必须选择
			HTML_MSG_MUSTCHOOSE_DEVICE:"实验设备必须选择",
			//设备类型必须选择
			HTML_MSG_MUSTCHOOSE_DEVICETYPE:"设备类型必须选择",
			//请填写选择人
			HTML_MSG_CHOOSE_CHOOSEMAN:"请填写选择人",
			//开始时间必须选择
			HTML_MSG_MUSTCHOOSE_STARTTIME:"开始时间必须选择",
			//结束时间必须选择
			HTML_MSG_MUSTCHOOSE_ENDTIME:"结束时间必须选择",
			//开始时间必须小于系统时间
			HTML_MSG_MUSTLESS_STARTTIME:"开始时间必须小于系统时间",
			//结束时间必须小于系统时间
			HTML_MSG_MUSTLESS_ENDTIME:"结束时间必须小于系统时间",
			//使用期间重复，请重新填写
			HTML_MSG_GAGINCHOOSE_USEREPEAT:"使用期间重复，请重新填写",
			//设备备注必须输入
			HTML_MSG_MUSTCHOOSE_DEVICEREMARKS:"设备备注必须输入",
			//开始时间必须早于结束时间
			HTML_MSG_MUSTAGO_TIME:"开始时间必须早于结束时间",
			//结束时间不得早于当前时间
			HTML_MSG_NOTAGO_TIME:"结束时间不得早于当前时间",
			//使用期间重复，请重新填写
			HTML_MSG_NOTGAGIN_USE:"使用期间重复，请重新填写",
			//设备编号必须输入
			HTML_MSG_MUSTINPUT_DEVICENO:"设备编号必须输入",
			//设备名称必须输入
			HTML_MSG_MUSTINPUT_DEVICENAME:"设备名称必须输入",
			//设备状态必须选择
			HTML_MSG_MUSTINPUT_DEVICESTATE:"设备状态必须选择",
			//设备参数必须输入
			HTML_MSG_MUSTINPUT_DEVICEPMATER:"设备参数必须输入",
			//设备位置必须选择
			HTML_MSG_MUSTINPUT_DEVICEPOSITION:"设备位置必须选择",
			//设备说明必须输入
			HTML_MSG_MUSTINPUT_DEVICETYPE:"设备说明必须输入",
			//工时必须填写
			HTML_MSG_MUSTINPUT_WORKINGTIME:"工时必须填写",
			//请输入数字
			HTML_MSG_MUSTINPUT_NUMBER:"请输入数字",
			//工时最长为16个字符
			HTML_MSG_MAXLENGTH_WORKINGTIME:"请输入正确的格式",
			//请选择年度
			HTML_MSG_CHOOSE_YEARS:"请选择年度",
			//课题编号必须输入
			HTML_MSG_MUSTINPUT_PROJECTNUM:"课题编号必须输入",
			//主题必须填写
			HTML_MSG_MUSTINPUT_THEME:"主题必须填写",
			//目标必须填写
			HTML_MSG_MUSTINPUT_TARGET:"目标必须填写",
			//请选择部门
			HTML_MSG_CHOOSE_DEPARTMENT:"请选择部门",
			//计划人工必须填写
			HTML_MSG_MUSTINPUT_PLANMANUAL:"计划人工必须填写",
			//计划人工必须为数字
			HTML_MSG_MUSTNUM_PLANMANUAL:"计划人工必须为数字",
			//请选择课题类别
			HTML_MSG_CHOOSE_PROJECTTYPE:"请选择课题类别",
			//内容必须填写
			HTML_MSG_MUSTINPUT_CONTENT:"内容必须填写",
			//请选择法人
			HTML_MSG_CHOOSE_LEGAL:"请选择法人",
			//责任人不能与指派责任人相同，请输入责任人以外的人员。
			HTML_MSG_INPUT_NEWLIABLE:"责任人不能与指派责任人相同，请输入责任人以外的人员。",
			//请输入指派责任人。
			HTML_MSG_MUSTINPUT_LIABLE:"请输入指派责任人。",
			//请输入整数
			HTML_MSG_INPUT_INTEGER:"请输入整数",
			//请输入金额
			HTML_MSG_INPUT_AMOUNT:"请输入金额",
			//请输入小数
			HTML_MSG_INPUT_DECIMAL:"请输入小数",
			//from项目不得大于to项目
			HTML_MSG_LESS_FROMTO:"from项目不得大于to项目",
			//旧文件编号已录入。
			HTML_MSG_INPUT_OLDNUMBER:"旧文件编号已录入。",
			//部门人数必须输入
			HTML_MSG_MUSTINPUT_DEPARTMRNTNUM:"部门人数必须输入",
			//角色名称必须输入
			HTML_MSG_MUSTINPUT_ROLENAME:"角色名称必须输入",
			//输入字符不能超过50
			HTML_MSG_INPUTEXCEED_FIRETY:"输入字符不能超过50",
			//输入字符不能超过200
			HTML_MSG_INPUTEXCEED_THB:"输入字符不能超过200",
			//部门Id必须填写
			HTML_MSG_MUSTINPUT_DEPTID:"部门Id必须填写",
			//请选择部门负责人
			HTML_MSG_CHOOSE_DEPTPRESON:"请选择部门负责人",
			//部门名必须填写
			HTML_MSG_MUSTINPUT_DEPTNAME:"部门名必须填写",
			//是否删除部门
			HTML_MSG_DEPARTMANANGE_DELETE:"是否删除此部门以及其下属部门？",
			//输入字符不能超过100
			HTML_MSG_INPUTEXCEED_HUNDRED:"输入字符不能超过100",
		},

		//前端Txt(显示在页面上的提示)
		HTML_BIZ_Txt: {
			//是否删除此条设备预约？
			HTML_TXT_IS_DELETE_DEVICE_RESERVE:"是否删除此条设备预约？",
			//是否放弃新增设备预约
			HTML_TXT_IS_GIVEUP_ADD_DEVICE_RESERVE:"是否放弃新增设备预约？",
			//是否放弃更新设备预约
			HTML_TXT_IS_GIVEUP_UPDATE_DEVICE_RESERVE:"是否放弃更新设备预约？",
			//是否要删除
			HTML_TXT_IS_GIVEUP_UPDATE_DEVICE_CURRENTLINE:"是否要删除当前行",
			//是否导出工时数据
			HTML_TXT_IS_GIVEUP_EXPORT_HOUR_DATA:"是否导出工时数据?",
			//[已删除]
			HTML_TXT_IS_GIVEUP_DELETE:"[已删除]",
			//请输入责任人。
			HTML_TXT_IS_INPUT_RESPONSE:"请输入责任人。",
			//责任人变更成功。
			HTML_TXT_IS_RESPONSCHANGE_SUCCESS:"责任人变更成功。",
			//下载成功
			HTML_TXT_IS_DOWNLOAD_SUCCESS:"下载成功",
		}
	};

	//动态生成函数
	for(var i in constMsg.API_ERR_Msg){
	   eval('function _'+i+'(){return \''+constMsg.API_ERR_Msg[i]+' \' } ')
	}

	for(var i in constMsg.API_BIZ_Msg){
	   eval('function _'+i+'(){return \''+constMsg.API_BIZ_Msg[i]+' \' } ')
	}

	for(var i in constMsg.HTML_BIZ_Msg){
	   eval('function _'+i+'(){return \''+constMsg.HTML_BIZ_Msg[i]+' \' } ')
	}

	for(var i in constMsg.HTML_FRM_Msg){
	   eval('function _'+i+'(){return \''+constMsg.HTML_FRM_Msg[i]+' \' } ')
	}

	for(var i in constMsg.HTML_BIZ_Txt){
	   eval('function _'+i+'(){return \''+constMsg.HTML_BIZ_Txt[i]+' \' } ')
	}

	//========================================================

	function constructor(){
		_this = this;
		for(var i in constMsg.API_ERR_Msg){
			eval('this.'+i+' = _'+i);
		}
		for(var i in constMsg.API_BIZ_Msg){
			eval('this.'+i+' = _'+i);
		}
		for(var i in constMsg.HTML_BIZ_Msg){
			eval('this.'+i+' = _'+i);
		}
		for(var i in constMsg.HTML_FRM_Msg){
			eval('this.'+i+' = _'+i);
		}
		for(var i in constMsg.HTML_BIZ_Txt){
			eval('this.'+i+' = _'+i);
		}
	}

	return constructor;
})();