package com.dm.dto;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileDto extends BaseDto {
	private String docCode;

	private String mFile;
	private CommonsMultipartFile mainFile;

	private String[] delSubFileNo;
	private String[] updSubFileNo;
	private List<CommonsMultipartFile> subFiles;
	private List<CommonsMultipartFile> updSubFiles;
	private String mode;

	private List<Integer> errorTerm;

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<Integer> getErrorTerm() {
		return errorTerm;
	}

	public void setErrorTerm(List<Integer> errorTerm) {
		this.errorTerm = errorTerm;
	}

	public String getDocCode() {
		return docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public String getmFile() {
		return mFile;
	}

	public void setmFile(String mFile) {
		this.mFile = mFile;
	}

	public CommonsMultipartFile getMainFile() {
		return mainFile;
	}

	public void setMainFile(CommonsMultipartFile mainFile) {
		this.mainFile = mainFile;
	}

	public String[] getDelSubFileNo() {
		return delSubFileNo;
	}

	public void setDelSubFileNo(String[] delSubFileNo) {
		this.delSubFileNo = delSubFileNo;
	}

	public String[] getUpdSubFileNo() {
		return updSubFileNo;
	}

	public void setUpdSubFileNo(String[] updSubFileNo) {
		this.updSubFileNo = updSubFileNo;
	}

	public List<CommonsMultipartFile> getSubFiles() {
		return subFiles;
	}

	public void setSubFiles(List<CommonsMultipartFile> subFiles) {
		this.subFiles = subFiles;
	}

	public List<CommonsMultipartFile> getUpdSubFiles() {
		return updSubFiles;
	}

	public void setUpdSubFiles(List<CommonsMultipartFile> updSubFiles) {
		this.updSubFiles = updSubFiles;
	}

}
