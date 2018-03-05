package com.dm.srv.impl.doc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dm.dao.PersonResponsibleEditDao;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.PersonResDto;
import com.dm.entity.DepartmentInfoEntity;
import com.dm.entity.DocFileEntity;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.ParmMstEntity;
import com.dm.srv.doc.PersonResponsibleEditSrv;
import com.dm.srv.impl.BaseSrvImp;

@Service
public class PersonResponsibleEditSrvImpl extends BaseSrvImp implements PersonResponsibleEditSrv {

	@Autowired
	private PersonResponsibleEditDao pREDao;

	@Override
	public List<DepartmentInfoEntity> getDeptByUserId(String userId) {

		return pREDao.getDeptByUserId(userId);
	}

	@Override
	public List<DocItemDataSourceCodeEntity> getDocSource() {

		return pREDao.getDocSource();
	}


	@Override
	public List<ParmMstEntity> getDocStatus() {
		// TODO Auto-generated method stub
		return pREDao.getDocStatus();
	}


	@Override
	public List<PersonResDto> query(String userId, String personId, String docType, String docStatus) {

		// 获取查询结果
		List<PersonResDto> list = pREDao.query(userId, personId, docType, docStatus);

		// 存在查询信息，获取子文件信息
		if(list !=null && list.size() > 0) {
			List<DocFileEntity> childFilelist = pREDao.getChildFileByParentFile(list);

			// 对子文件处理 ，放入对应审核文档中
			for (int i = 0; i < list.size(); i++) {
				List<DocFileEntity> tChildFile = new ArrayList<>();
				for (int j = 0; j < childFilelist.size(); j++) {
					DocFileEntity docFile = childFilelist.get(j);
					if (docFile.getDocumentCode().equals(list.get(i).getDocumentCode())) {
						tChildFile.add(docFile);
					}
				}
				// 设置子文件
				if (tChildFile.size() > 0) {
					// TODO: 子文件 设置为java常量
					list.get(i).setChildFileName("子文件...");
				} else {
					list.get(i).setChildFileName("");
				}
				list.get(i).setDocChildList(tChildFile);
			}

		}

		return list;
	}


	@Override
	public BaseDto resEditSubmit(String[] docCode, String appointPersonId,String userId) {
		BaseDto baseDto = new BaseDto();

		int count = pREDao.resEditSubmit(docCode, appointPersonId,userId,new Date());
		if(count>0) {
			baseDto.setBizCode(BizCode.BIZ_CODE_SUCCESS);
		}else {
			baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
		}
		return baseDto;
	}





}
