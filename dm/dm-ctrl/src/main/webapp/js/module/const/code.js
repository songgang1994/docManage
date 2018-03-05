/**
 * -------------------------------------------------------
 *
 * @模块描述:消息Code常量定义
 * @作者:邵林军
 * @修改履历:新作成 2017/12/13 -------------------------------------------------------
 */

Namespace.register("Globals.constCode");

Globals.constCode = (function() {
	var _this = null;
	// ========================================================
	// 消息code常量定义区域
	var constCode = {

		// API调用系统CODE
		API_ERR_CODE : {
			// 成功
			RTN_CODE_SUCCESS : 100,
			// 未登录
			RTN_CODE_NOT_LOGIN : 200,
			// 连接超时
			RTN_CODE_CONNECT_TIMEOUT : 201,
			// 未知错误发生
			RTN_CODE_UNKNOWN : 202,
			// 登录超时(session timeout)
			RTN_CODE_SESSION_TIMEOUT : 203,
			// 无权限
			RTN_CODE_NOT_PERMITTED : 204,
			// API接口未找到
			RTN_CODE_API_NOT_FOUND : 205,
			// 返回结果NULL错误
			RTN_CODE_RESULT_NULL : 206,
			// 无效的接口参数
			RTN_CODE_INVALID_PARAMS : 207,
			// 非法请求客户端
			RTN_CODE_ILLEGAL_REQUEST : 208,
			// 服务器端返回的数据格式错误
			RTN_CODE_DATA_PARSER_ERROR : 209
		},

		// API调用业务CODE
		API_BIZ_CODE : {
			// 业务处理成功
			BIZ_CODE_SUCCESS : 100,
			// 接口参数不正
			BIZ_CODE_INVALID_PARAMS : 101,
			// 记录0条
			BIZ_CODE_RECORD_0 : 102,
			// 添加记录成功
			BIZ_CODE_INSERT_SUCCESS : 103,
			// 更新记录成功
			BIZ_CODE_UPDATE_SUCCESS : 104,
			// 删除记录成功
			BIZ_CODE_DELETE_SUCCESS : 105,
			// 登录验证失败错误
			BIZ_CODE_LOGIN_AUTH_FAILED : 106,
			// 操作失败
			BIZ_CODE_HANDLE_FAILED : 107,
			// 期间重复
			BIZ_CODE_DURATION_ERR : 109,
			// 数据源不能被删除
			BIZ_CODE_SRCCANT_DELETE : 110,
			// 条目不能被删除
			BIZ_CODE_ITEMCANT_DELETE : 111,
			// 设备预约新增成功
			BIZ_CODE_RESERVE_ADD_SUCCESS : 112,
			// 设备预约新增失败
			BIZ_CODE_RESERVE_ADD_FAILED : 113,
			// 设备预约修改成功
			BIZ_CODE_RESERVE_UPDATE_SUCCESS : 114,
			// 设备预约修改失败
			BIZ_CODE_RESERVE_UPDATE_FAILED : 115,
			// 设备预约删除成功
			BIZ_CODE_RESERVE_DELETE_SUCCESS : 116,
			// 设备预约删除失败
			BIZ_CODE_RESERVE_DELETE_FAILED : 117,
			// 设备预约操作模式异常
			BIZ_CODE_RESERVE_PATTERN_ERR : 118,
			// 设备正在预约中删除失败
			BIZ_CODE_RESERVEDEVICE_DELETE_FAILED : 119,
			// 设备正在使用中删除失败
			BIZ_CODE_USEDEVICE_DELETE_FAILED : 120,
			// 设备删除成功
			BIZ_CODE_DEVICE_DELETE_SUCCESS : 121,
			// 没有查看文档的权限
			BIZ_CODE_READ_DOC : 122,
			// 修改文档权限
			BIZ_CODE_EDIT_DOC_AUTHORITY : 123,
			// 删除文档权限
			BIZ_CODE_DELETE_DOC_AUTHORITY :  124,
			// 文档数据项已有录入数据，不能删除。
			BIZ_CODE_DOC_DATA_ITEM_ALREADY_EXISTED : 125,
			// 该数据项已在文档录入表单定义表中使用，不能进行删除处理
			BIZ_CODE_DELETE_DOCUMENT_ALREADY : 126,
			// 该数据项已在文档一览检索数据项定义表中使用，不能进行删除处理
			BIZ_CODE_DELETE_DOCUMENT_USE : 127,
			// 删除成功
			BIZ_CODE_DELETE_DOCUMENT_SUCCESS : 128,
			// 删除失败
			BIZ_CODE_DELETE_DOCUMENT_FAILED : 129,
			// 文档({1})已经被其他用户修改，请重新查询后再进行审核处理。
			BIZ_CODE_DOC_UPDATE_BY_OTHER : 130,
			// 邮件发送失败
			BIZ_CODE_SEND_EMAIL_FAILED : 133,
			//审核通过
			BIZ_CODE_APPROVAL_SUCCESS : 131,
			//审核退回
			BIZ_CODE_APPROVAL_FAILED : 132,
		},
	};

	// 动态生成函数
	for ( var i in constCode.API_ERR_CODE) {
		eval('function _' + i + '(){return ' + constCode.API_ERR_CODE[i] + '} ')
	}

	for ( var i in constCode.API_BIZ_CODE) {
		eval('function _' + i + '(){return ' + constCode.API_BIZ_CODE[i] + '} ')
	}

	// ========================================================

	function constructor() {
		_this = this;
		for ( var i in constCode.API_ERR_CODE) {
			eval('this.' + i + ' = _' + i);
		}
		for ( var i in constCode.API_BIZ_CODE) {
			eval('this.' + i + ' = _' + i);
		}
	}

	return constructor;
})();