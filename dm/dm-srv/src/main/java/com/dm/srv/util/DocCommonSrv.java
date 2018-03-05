package com.dm.srv.util;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：共通Server
 */
public interface DocCommonSrv {
	
	int getFYYear();
	
	/**
	 * 检查审核流程是否完整
	 * @param docType
	 * @param userId
	 * @return
	 */
	boolean CheckApprovalProgress(String docType,String userId);
}
