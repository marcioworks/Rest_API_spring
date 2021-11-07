package com.marciobr.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ResourceNotFoundException extends RuntimeException{


	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(String exception) {
		super(exception);
	}
	
	

}
