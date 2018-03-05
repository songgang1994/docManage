package com.dm.dto;

public class BizCode {

	/*
	 * 成功
	 */
	public static final int BIZ_CODE_SUCCESS = 100;

	/*
	 * 接口参数不正
	 */
	public static final int BIZ_CODE_INVALID_PARAMS = 101;
	/*
	 * 记录0条
	 */
	public static final int BIZ_CODE_RECORD_0 = 102;
	/*
	 * 添加记录成功
	 */
	public static final int BIZ_CODE_INSERT_SUCCESS = 103;
	/*
	 * 更新记录成功
	 */
	public static final int BIZ_CODE_UPDATE_SUCCESS = 104;
	/*
	 * 删除记录成功
	 */
	public static final int BIZ_CODE_DELETE_SUCCESS = 105;
	/*
	 * 登录验证失败错误
	 */
	public static final int BIZ_CODE_LOGIN_AUTH_FAILED = 106;
	/*
	 * 操作失败
	 */
	public static final int BIZ_CODE_HANDLE_FAILED = 107;
	/*
	 * 操作模式错误
	 */
	public static final int BIZ_CODE_PATTERN_ERR = 108;
	/*
	 * 期间重复
	 */
	public static final int BIZ_CODE_DURATION_ERR = 109;
	/*
	 * 数据源不允许删除
	 */
	public static final int BIZ_CODE_SRCCANT_DELETE = 110;
	/*
	 * 条目不允许删除
	 */
	public static final int BIZ_CODE_ITEMCANT_DELETE = 111;
	/*
	 * 设备预约新增成功
	 */
	public static final int BIZ_CODE_RESERVE_ADD_SUCCESS = 112;
	/*
	 * 设备预约新增失败
	 */
	public static final int BIZ_CODE_RESERVE_ADD_FAILED = 113;
	/*
	 * 设备预约修改成功
	 */
	public static final int BIZ_CODE_RESERVE_UPDATE_SUCCESS = 114;
	/*
	 * 设备预约修改失败
	 */
	public static final int BIZ_CODE_RESERVE_UPDATE_FAILED = 115;
	/*
	 * 设备预约删除成功
	 */
	public static final int BIZ_CODE_RESERVE_DELETE_SUCCESS = 116;
	/*
	 * 设备预约删除失败
	 */
	public static final int BIZ_CODE_RESERVE_DELETE_FAILED = 117;
	/*
	 * 设备预约操作模式异常
	 */
	public static final int BIZ_CODE_RESERVE_PATTERN_ERR = 118;
	/*
	 * 设备预约中删除失败
	 */
	public static final int BIZ_CODE_RESERVEDEVICE_DELETE_FAILED = 119;
	/*
	 * 设备使用中删除失败
	 */
	public static final int BIZ_CODE_USEDEVICE_DELETE_FAILED = 120;
	/*
	 * 设备删除成功
	 */
	public static final int BIZ_CODE_DEVICE_DELETE_SUCCESS = 121;
	/*
	 * 没有查看文档的权限
	 */
	public static final int BIZ_CODE_READ_DOC = 122;
	/*
	 * 修改文档权限
	 */
	public static final int BIZ_CODE_EDIT_DOC_AUTHORITY = 123;
	/*
	 * 删除文档权限
	 */
	public static final int BIZ_CODE_DELETE_DOC_AUTHORITY = 124;
	/*
	 * 文档数据项已有录入数据，不能删除。
	 */
	public static final int BIZ_CODE_DOC_DATA_ITEM_ALREADY_EXISTED = 125;
	/*
	 * 该数据项已在文档录入表单定义表中使用，不能进行删除处理
	 */
	public static final int BIZ_CODE_DELETE_DOCUMENT_ALREADY = 126;
	/*
	 * 该数据项已在文档一览检索数据项定义表中使用，不能进行删除处理
	 */
	public static final int BIZ_CODE_DELETE_DOCUMENT_USE = 127;
	/*
	 * 删除成功
	 */
	public static final int BIZ_CODE_DELETE_DOCUMENT_SUCCESS = 128;
	/*
	 * 删除失败
	 */
	public static final int BIZ_CODE_DELETE_DOCUMENT_FAILED = 129;
	/*
	 * 文档({1})已经被其他用户修改，请重新查询后再进行审核处理。
	 */
	public static final int BIZ_CODE_DOC_UPDATE_BY_OTHER = 130;
	/*
	 * 审核通过
	 */
	public static final int APPROVAL_SUCCESS = 131;
	/*
	 * 审核退回
	 */
	public static final int APPROVAL_FAILED = 132;
	/*
	 *发送邮件失败
	 */
	public static final int SEND_EMAIL_FAILED = 133;

}
