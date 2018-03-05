package com.dm.srv.doc;

import java.util.List;
import java.util.Map;

import com.dm.dto.BaseDto;
import com.dm.dto.DocSearchDto;
import com.dm.dto.LoginDto;
import com.dm.dto.TableDto;
import com.dm.entity.DocFileEntity;


public interface DocSearchSrv {

	Map<String, Object> getQueryDataItemByUserId(String UserId);

	TableDto getTableData(List<DocSearchDto> listItems, List<DocSearchDto> usreCustomItems,String userId) throws Exception;

	BaseDto handleDocCode(TableDto table,String line);


	BaseDto handleFile(TableDto table,String line,String fileNo);

	BaseDto handleEdit(TableDto table,String line,String userId);

	BaseDto handleDelete(TableDto table,String line,String userId);

	DocFileEntity handleExport(TableDto table,List<DocSearchDto> listItems,LoginDto user)throws Exception;
}
