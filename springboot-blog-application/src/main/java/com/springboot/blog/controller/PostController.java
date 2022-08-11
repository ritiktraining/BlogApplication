package com.springboot.blog.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.util.AppConstants;

@RestController
@RequestMapping("api/posts")
public class PostController {

	private PostService postService;

	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}

	// create blog post rest api
//	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {

		return new ResponseEntity<PostDto>(postService.createPost(postDto), HttpStatus.CREATED);
	}

	// get all posts rest api
	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
	}

	// get post by id
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(postService.getPostById(id));

	}

	// update post
//	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> upDatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") Long id) {

		PostDto upDatePost = postService.upDatePost(postDto, id);
		return new ResponseEntity<PostDto>(upDatePost, HttpStatus.OK);
	}

	// delete post by id
//	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<PostDto> deletePostById(@PathVariable(name = "id") Long id) {
		postService.deletePostById(id);

		return new ResponseEntity("Post Entity Deleted Successfully", HttpStatus.OK);
	}
}
