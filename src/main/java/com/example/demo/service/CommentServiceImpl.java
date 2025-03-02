package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CommentDTO;
import com.example.demo.entity.Comment;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	CommentRepository repository;
	
	@Autowired
	BoardRepository boardRepository;

	// 댓글 등록
	@Override
	public int register(CommentDTO dto) {
		
		Comment entity = dtoToEntity(dto);
		
		repository.save(entity);
		int cmNo = entity.getCommentNo();
		
		return cmNo;
	}

	
	// 댓글 목록 조회
	@Override
	public List<CommentDTO> getList(String memberId) {
		
		// 1. 회원 ID로 댓글 조회
	    List<Comment> comment = repository.findByMember_Id(memberId);
	    
	    return comment.stream()
	            .map(this::entityToDto)
	            .collect(Collectors.toList());
	}

	// 댓글 목록조회 (게시물별)
	@Override
	public List<CommentDTO> getBoardList(int boardNo) {
		// 1. 게시물 번호로 댓글 조회
	    List<Comment> comment = repository.findByBoard_boardNo(boardNo);
	    return comment.stream()
	            .map(this::entityToDto)
	            .collect(Collectors.toList());
	}


	// 댓글 상세 조회
	@Override
	public CommentDTO read(int reNo) {
		Optional<Comment> result = repository.findById(reNo);
		 if(result.isPresent()) {
			 Comment comment =  result.get();
	        	return entityToDto(comment);
	        } else {
	        	return null;
	        }
	}

	// 댓글 수정
	@Override
	public void modify(CommentDTO dto) {
		Optional<Comment> result = repository.findById(dto.getCommentNo());
		
		if(result.isPresent()) {
			Comment entity = result.get();
			
			//내용
			entity.setContent(dto.getContent());
			repository.save(entity);
		}
		
	}

	@Override
	public void remove(int reNo) {
		repository.deleteById(reNo);
		
	}

}
