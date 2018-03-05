package com.dm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dm.dto.ApprovalDto;
import com.dm.dto.DocFormItemDto;
import com.dm.dto.LevelDeptInfoDto;
import com.dm.dto.UserDeptDto;
import com.dm.entity.DocCodeManageEntity;
import com.dm.entity.DocDetailCustomInfoEntity;
import com.dm.entity.DocDetailInfoEntity;
import com.dm.entity.DocDetailItemSettingEntity;
import com.dm.entity.DocFileEntity;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.DocItemsEntity;
import com.dm.entity.DocViewDeptEntity;
import com.dm.entity.ParmMstEntity;
import com.dm.entity.SysLogEntity;
import com.dm.entity.UserDeptEntity;
import com.dm.entity.UserEntity;

public interface DocEnterDao {

	DocDetailInfoEntity documentInfo(@Param("documentCode") String documentCode);

	List<DocFormItemDto> displayItems(@Param("documentType") String documentType);

	List<DocItemDataSourceCodeEntity> dataSource(@Param("dataSource") List<String> dataSource);

	List<ParmMstEntity> paramMst(@Param("type1")String type1);

	DocDetailCustomInfoEntity customItems(@Param("documentCode") String documentCode,@Param("costomeList")List<String> customeName);

	List<DocViewDeptEntity> getDocViewDept(@Param("documentCode") String documentCode);

	List<ApprovalDto> getApprove(@Param("documentCode") String documentCode);

	void items(@Param("documentCode") String documentCode);

	List<DocItemsEntity> docItems();

	List<DocItemDataSourceCodeEntity> docTypes();

	String lastApproveUser(@Param("documentType") String documentType);

	String departId(@Param("userId") String userId);

	List<String> superUserIds(@Param("userId") String userId);

	List<DocCodeManageEntity> listDocCodes(@Param("documentType") String documentType);

	DocCodeManageEntity queryDocCode(@Param("documentType") String documentType, @Param("documentFy") int documentFy);

	void increaseLastNo(@Param("documentType") String documentType, @Param("documentFy") int documentFy,
			@Param("updater") String userId);

	void createLastNo(@Param("documentType") String documentType, @Param("documentFy") int documentFy,
			@Param("creator") String userId);

	void createLog(@Param("log")SysLogEntity log);

	List<String> queryViewDept(@Param("documentCode") String documentCode);

	void addDocDetailInfo(@Param("doc") DocDetailInfoEntity doc);

	void addDocDetailCustomInfo(@Param("custome") DocDetailCustomInfoEntity custom);

	List<DocFormItemDto> displayItemsForCheck(@Param("documentType") String documentType);

	int checkDocOldCode(@Param("documentOldCode") String documentOldCode, @Param("documentCode") String documentCode);

	List<LevelDeptInfoDto> getLevelDeptInfo(@Param("list")List<UserDeptEntity> list);

	List<LevelDeptInfoDto> getSubDeptInfo(@Param("list")String[] deptIds);

	void addViewDept(@Param("viewDept") DocViewDeptEntity viewDept);

	void deleteViewDept(@Param("documentCode") String documentCode);

	void updateDocDetailCustomInfo(@Param("custome") DocDetailCustomInfoEntity custome);

	void updateDocDetailInfo(@Param("doc") DocDetailInfoEntity doc);

	List<DocFileEntity> files(@Param("documentCode") String documentCode);

	void addFile(@Param("docFile") DocFileEntity docFile);

	void updateFile(@Param("docFile") DocFileEntity docFile);

	void deleteFile(@Param("docFile") DocFileEntity docFile);

	DocDetailItemSettingEntity getInputRequire(@Param("docType")String docType,@Param("docItemCode")String docItemCode);

	String queryFCODE(@Param("documentType") String documentType);

	DocFileEntity mainFile(@Param("documentCode") String documentCode);

	DocFileEntity subFile(@Param("documentCode") String documentCode, @Param("fileNo") String fileNo);

	void addApproveHistory(String documentCode, String comment, String userId);

	void updateDocumentDetailInfo(String documentCode, String comment, String userId);

	 DocDetailCustomInfoEntity getDocCustom(@Param("documentCode")String docCode);

	 DocDetailInfoEntity getDocDetail(@Param("documentCode")String docCode);

	 String getdeptName(@Param("deptId")String deptId);

	 UserEntity getUserEntity(@Param("userId")String userId);

	 List<UserDeptDto> getDeptLeaderId(@Param("deptId")String deptId);

	 String getDeptIdByDocCode(@Param("docCode")String docCode);

	 List<UserEntity> getUserEntities(@Param("deptIds")String[] deptId);

}
