package com.dm.dto;

import java.util.Date;

/**
 * 文档数据源 Dto
 * @author 张丽
 *
 */

public class DocItemDataSourceCodeDto extends BaseDto {


	/*
	 * 数据源编号
	 */
	private String documentItemSourceCode;
	/*
	 * 数据源名称
	 */
    private String documentItemSourceName;
     /*
      * 数据源序号
      */
     private Integer documentItemSourceNo;
     /*
      * 数据源条目值
      */
     private String itemValue;
     /*
      * 数据源条目名称
      */
     private String itemName;
     /*
      * 数据源条目排序号
      */
     private Integer itemSortNo;
     /*
      * 数据源附加值
      */
     private String itemOtherValue;
     /*
      * 创建人
      */
     private String creator;
     /*
      * 创建时间
      */
     private Date createDt;
     /*
      * 更新人
      */
     private String updater;
     /*
      * 更新时间
      */
     private Date updateDt;
     /*
      * 条目详细内容
      */
     private String detail;
     /*
      * 主文档编号详细内容
      */
     private String otherDetail;
     /*
      * 条目值集合
      */
     private String valDetail;

	public String getDocumentItemSourceCode() {
		return documentItemSourceCode;
	}
	public void setDocumentItemSourceCode(String documentItemSourceCode) {
		this.documentItemSourceCode = documentItemSourceCode;
	}
	public String getDocumentItemSourceName() {
		return documentItemSourceName;
	}
	public void setDocumentItemSourceName(String documentItemSourceName) {
		this.documentItemSourceName = documentItemSourceName;
	}
	public Integer getDocumentItemSourceNo() {
		return documentItemSourceNo;
	}
	public void setDocumentItemSourceNo(Integer documentItemSourceNo) {
		this.documentItemSourceNo = documentItemSourceNo;
	}
	public String getItemValue() {
		return itemValue;
	}
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Integer getItemSortNo() {
		return itemSortNo;
	}
	public void setItemSortNo(Integer itemSortNo) {
		this.itemSortNo = itemSortNo;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreateDt() {
		return createDt;
	}
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	public Date getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getItemOtherValue() {
		return itemOtherValue;
	}
	public void setItemOtherValue(String itemOtherValue) {
		this.itemOtherValue = itemOtherValue;
	}
	public String getOtherDetail() {
		return otherDetail;
	}
	public void setOtherDetail(String otherDetail) {
		this.otherDetail = otherDetail;
	}
	public String getValDetail() {
		return valDetail;
	}
	public void setValDetail(String valDetail) {
		this.valDetail = valDetail;
	}
}
