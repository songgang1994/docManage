package com.dm.srv.doc;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dm.dto.DocDetailInfoDto;
import com.dm.dto.DocFormItemDto;
import com.dm.dto.FileDto;
import com.dm.dto.LoginDto;
import com.dm.dto.UserDeptDto;
import com.dm.entity.DocDetailCustomInfoEntity;
import com.dm.entity.DocFileEntity;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.UserDeptEntity;
import com.dm.entity.UserEntity;


public interface DocEnterSrv {

	Map<String, Object> docTypes();

	Map<String, String> fetchCode(String documentType, String userId,String ip);

	List<DocFormItemDto> formItems(String documentType);

	boolean checkUpdTime(String docCode,Date update);

	Map<String, Object> form(String documentType, String documentCode, String mode,LoginDto user);

	Map<String, Object> detail(String documentCode, String mode,LoginDto user);

	List<UserDeptDto> getDeptLeaderId(String deptId);

	void save(DocDetailInfoDto doc, DocDetailCustomInfoEntity custome, CommonsMultipartFile mainFile, List<CommonsMultipartFile> subFileList,String ip,List<UserDeptEntity> depts,String isCommit);

	DocDetailCustomInfoEntity getDocCustom(String docCode);

	boolean checkDocOldCode(String documentOldCode, String documentCode);

	void update(DocDetailInfoDto doc, DocDetailCustomInfoEntity custome,FileDto fileDto,String ip,List<UserDeptEntity> depts,String isCommit);

	DocFileEntity file(String documentCode, String fileType, String fileNo,LoginDto user);

	String getDeptIdByDocCode(String docCode);

	List<UserEntity> getUserEntity(String[] deptId);

	DocItemDataSourceCodeEntity getDocName(String docCode);

}
