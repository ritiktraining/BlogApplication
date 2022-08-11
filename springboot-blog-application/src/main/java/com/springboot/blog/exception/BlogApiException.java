package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogApiException extends RuntimeException {

	private HttpStatus httpStatus;
	private String message;

	public BlogApiException(HttpStatus httpStatus, String message) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
