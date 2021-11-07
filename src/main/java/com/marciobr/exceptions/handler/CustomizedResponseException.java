package com.marciobr.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.marciobr.exceptions.ExceptionResponse;
import com.marciobr.exceptions.InvalidJwtAuthenticationException;
import com.marciobr.exceptions.ResourceNotFoundException;

@ControllerAdvice
@RestController
public class CustomizedResponseException extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handlerAllException(Exception ex, WebRequest request){
		ExceptionResponse exception = new ExceptionResponse(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> ResourceNotFoundException(Exception ex, WebRequest request){
		ExceptionResponse exception = new ExceptionResponse(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidJwtAuthenticationException.class)
	public final ResponseEntity<ExceptionResponse> InvalidJwtAuthenticationException(Exception ex, WebRequest request){
		ExceptionResponse exception = new ExceptionResponse(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
	}
}
