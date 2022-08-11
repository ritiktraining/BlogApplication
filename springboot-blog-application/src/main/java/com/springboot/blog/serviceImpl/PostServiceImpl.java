package com.springboot.blog.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;

	private ModelMapper mapper;

	public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
		super();
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	@Override
	public PostDto createPost(PostDto postDto) {

		// convert DTO to entity
		Post post = maptoEntity(postDto);
//		Post post = new Post();
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());

		Post newPost = postRepository.save(post);

		// convert post entity to DTO
		PostDto postResponse = mapToDto(newPost);
//		PostDto postResponse = new PostDto();
//		postResponse.setId(newPost.getId());
//		postResponse.setTitle(newPost.getTitle());
//		postResponse.setDescription(newPost.getDescription());
//		postResponse.setContent(newPost.getContent());

		return postResponse;
	}

	@Override
	public PostResponse getAllPosts(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {

//		List<PostDto> list = new ArrayList<PostDto>();

		// create pageable instance

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

		Page<Post> posts = postRepository.findAll(pageable);

		// get content from page object
		List<Post> listOfPosts = posts.getContent();

		List<PostDto> content = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());

		return postResponse;
	}

	@Override
	public PostDto getPostById(Long id) {

		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		return mapToDto(post);

	}

	@Override
	public PostDto upDatePost(PostDto postDto, Long id) {

		// get post by id from the database
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());

		Post updatedPost = postRepository.save(post);

		return mapToDto(updatedPost);
	}

	@Override
	public void deletePostById(Long id) {

		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		postRepository.delete(post);

	}

	// converted post entity to dto
	private PostDto mapToDto(Post post) {

		PostDto postDto = mapper.map(post, PostDto.class);

//		PostDto postDto = new PostDto();
//
//		postDto.setId(post.getId());
//		postDto.setTitle(post.getTitle());
//		postDto.setDescription(post.getDescription());
//		postDto.setContent(post.getContent());

		return postDto;

	}

	// convert post Dto to entity
	private Post maptoEntity(PostDto postDto) {

		Post post = mapper.map(postDto, Post.class);

//		Post post = new Post();
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());

		return post;
	}

}
