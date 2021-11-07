package com.marciobr.exceptions;

import java.io.Serializable;
import java.util.Date;


public class ExceptionResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Date timestamps;
	private String message;
	private String details;
	
	public ExceptionResponse(Date timestamps, String message, String details) {
		this.timestamps = timestamps;
		this.message = message;
		this.details = details;
	}

	public Date getTimestamps() {
		return timestamps;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}
	
	
	
	

}
