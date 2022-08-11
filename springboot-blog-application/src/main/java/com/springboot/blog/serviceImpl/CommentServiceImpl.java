package com.springboot.blog.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public CommentDto createComment(Long postId, CommentDto commentDto) {

		Comment comment = maptoEntity(commentDto);

		// retrieve post entity by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// set post to comment entity
		comment.setPost(post);

		// save comment entity to database
		Comment newComment = commentRepository.save(comment);

		return mapToDto(newComment);
	}

	@Override
	public List<CommentDto> getCommentsByPostId(Long postId) {
		// retrieve comments from postID
		List<Comment> comments = commentRepository.findByPostId(postId);

		// convert list of comment entity to comment dto entity
		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(Long postId, Long commentId) {

		// retrieve post entity by id

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// retrieve comment by id
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow((() -> new ResourceNotFoundException("Comment", "id", commentId)));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
		}

		return mapToDto(comment);
	}

	@Override
	public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
		// retrieve post entity by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// retrieve comment by id
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow((() -> new ResourceNotFoundException("Comment", "id", commentId)));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
		}

		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());

		Comment updatedComment = commentRepository.save(comment);

		return mapToDto(updatedComment);
	}

	@Override
	public void deleteCommenttById(Long postId, Long commentId) {
		// retrieve post entity by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// retrieve comment by id
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow((() -> new ResourceNotFoundException("Comment", "id", commentId)));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
		}

		commentRepository.delete(comment);

	}

	// convert comment entity to dto
	private CommentDto mapToDto(Comment comment) {
		CommentDto commentDto = mapper.map(comment, CommentDto.class);
		
		
//		CommentDto commentDto = new CommentDto();
//		commentDto.setId(comment.getId());
//		commentDto.setName(comment.getName());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setBody(comment.getBody());

		return commentDto;

	}

	// convert comment dto to entity
	private Comment maptoEntity(CommentDto commentDto) {
		
		Comment comment = mapper.map(commentDto, Comment.class);

//		Comment comment = new Comment();
//		comment.setId(commentDto.getId());
//		comment.setName(commentDto.getName());
//		comment.setEmail(commentDto.getEmail());
//		comment.setBody(commentDto.getBody());

		return comment;

	}

}
