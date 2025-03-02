package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.CommentDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Member;

public interface CommentService {

		// 댓글 등록 메소드
		int register(CommentDTO dto);
		
		// 댓글 목록조회 메소드 (아이디로)
		List<CommentDTO> getList(String memberId);
		
		// 댓글 목록조회 메소드 (게시물별)
		List<CommentDTO> getBoardList(int boardNo);
		
		// 댓글 상세 메소드
		CommentDTO read(int reNo);
		
		// 댓글 수정 메소드
		void modify(CommentDTO dto);

		// 댓글 삭제 메소드
		void remove(int reNo);
		
		//엔티티를 DTO로 변환하는 메소드
		default CommentDTO entityToDto(Comment entity) {
			
			CommentDTO dto = CommentDTO.builder()
							.commentNo(entity.getCommentNo())
							.id(entity.getMember().getId())
							.boardNo(entity.getBoard().getBoardNo())
							.content(entity.getContent())
							.regDate(entity.getRegDate())
							.modDate(entity.getModDate())
							.build();

			return dto;
		}

		//DTO를 엔티티로 변환하는 메소드
		default Comment dtoToEntity(CommentDTO dto) {
			Board boardNo = Board.builder().boardNo(dto.getBoardNo()).build();
			Member id = Member.builder().id(dto.getId()).build();

			
			Comment entity = Comment.builder()
								.commentNo(dto.getCommentNo())
								.board(boardNo)
								.member(id)
								.content(dto.getContent())
								.build();
			return entity;
		}
	}

