package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto);

	PostResponse getAllPosts(Integer pageNo, Integer pageSize,String sortBy,String sortDir);

	PostDto getPostById(Long id);

	PostDto upDatePost(PostDto postDto, Long id);

	void deletePostById(Long id);

}
