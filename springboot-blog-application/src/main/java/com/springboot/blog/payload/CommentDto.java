package com.springboot.blog.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CommentDto {

	private Long id;

	// name should not be null or empty
	@NotEmpty(message = "Name should not be null or empty")
	private String name;

	@NotEmpty
	@Email(message = "must be a well-formed email address")
	private String email;

	@NotEmpty
	@Size(min = 10, message = "Comment Body should have at least 10 chracters")
	private String body;

}
