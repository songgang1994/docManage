package com.dm.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.dm.dto.BaseDto;

/**
 * 文档文件 Entity
 * @author 张丽
 *
 */

public class DocFileEntity extends BaseDto {

	/*
	 * 文档编号
	 */
	private String documentCode;
	/*
	 * 文件序号
	 */
	private BigDecimal fileNo;
	/*
	 * 文件区分
	 */
	private String fileType;
	/*
	 * 文件名称
	 */
	private String fileName;
	/*
	 * 文件内容
	 */
	private byte[] fileContent;
	/*
	 * 文件创建时间
	 */
	private Date createDt;
	/*
	 * 文件创建人
	 */
	private String creator;
	/*
	 * 文件更新时间
	 */
	private Date updateDt;
	/*
	 * 文件更新人
	 */
	private String updater;

	public String getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	public BigDecimal getFileNo() {
		return fileNo;
	}
	public void setFileNo(BigDecimal fileNo) {
		this.fileNo = fileNo;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public byte[] getFileContent() {
		return fileContent;
	}
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}
	public Date getCreateDt() {
		return createDt;
	}
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}




}
