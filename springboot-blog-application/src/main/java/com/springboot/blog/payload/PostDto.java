package com.springboot.blog.payload;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PostDto {

	private Long id;

	// title should not be empty or null
	// title should have at least 2 Chracters
	@NotEmpty
	@Size(min = 2, message = "Post title should have at least 2 chracters")
	private String title;

	// post not be null and have at least 10 chracters
	@NotEmpty
	@Size(min = 2, message = "Post Description should have at least 10 chracters")
	private String description;

	@NotEmpty
	private String content;
	private Set<CommentDto> comments;
}
