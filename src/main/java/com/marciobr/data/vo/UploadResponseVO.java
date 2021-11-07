package com.marciobr.data.vo;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

public class UploadResponseVO extends RepresentationModel<PersonVO> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String fileName;
	private String fileDownloadUri;
	private String fileType;
	private long size;

	public String getFileName() {
		return fileName;
	}

	public UploadResponseVO(String fileName, String fileDownloadUri, String fileType, long size) {
		this.fileName = fileName;
		this.fileDownloadUri = fileDownloadUri;
		this.fileType = fileType;
		this.size = size;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(fileDownloadUri, fileName, fileType, size);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UploadResponseVO other = (UploadResponseVO) obj;
		return Objects.equals(fileDownloadUri, other.fileDownloadUri) && Objects.equals(fileName, other.fileName)
				&& Objects.equals(fileType, other.fileType) && size == other.size;
	}

}
