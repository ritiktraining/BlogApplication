package com.springboot.blog.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

	private List<PostDto> content;
	private Integer pageNo;
	private Integer pageSize;
	private Long totalElements;
	private Integer totalPages;
	private Boolean last;
}
