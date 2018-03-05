package com.dm.dto;

/**
 * 服务器端返回结果状态CODE定义 Created by guwei on 2017/07/24.
 */

public class ErrCode {
	/*
	 * 成功
	 */
	public static final int RTN_CODE_SUCCESS = 100;
	/*
	 * 未登录
	 */
	public static final int RTN_CODE_NOT_LOGIN = 200;
	/*
	 * 连接超时
	 */
	public static final int RTN_CODE_CONNECT_TIMEOUT = 201;
	/*
	 * 未知错误发生
	 */
	public static final int RTN_CODE_UNKNOWN = 202;
	/*
	 * 登录超时(session timeout)
	 */
	public static final int RTN_CODE_SESSION_TIMEOUT = 203;
	/*
	 * 无权限
	 */
	public static final int RTN_CODE_NOT_PERMITTED = 204;
	/*
	 * API接口未找到
	 */
	public static final int RTN_CODE_API_NOT_FOUND = 205;
	/*
	 * 返回结果NULL错误
	 */
	public static final int RTN_CODE_RESULT_NULL = 206;
	/*
	 * 无效的接口参数
	 */
	public static final int RTN_CODE_INVALID_PARAMS = 207;
	/*
	 * 非法请求客户端
	 */
	public static final int RTN_CODE_ILLEGAL_REQUEST = 208;

}
