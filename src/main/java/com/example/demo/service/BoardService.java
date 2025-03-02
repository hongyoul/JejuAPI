package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.BoardDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Member;
import com.example.demo.repository.BoardDetailRepository;

public interface BoardService {
	
		// 게시물 등록 메소드
		int register(BoardDTO dto);
		
		// 게시물 목록조회 메소드
		List<BoardDTO> getList(String keyword);

		// 게시물 상세조회 메소드
		BoardDTO read(int boardNo);

		// 게시물 수정 메소드
		void modify(BoardDTO dto);

		// 게시물 삭제 메소드
		void remove(int boardNo);
		
		 // 엔티티 -> DTO 변환
		default BoardDTO entityToDto(Board entity) {
	    	BoardDTO dto = BoardDTO.builder()
	    									.boardNo(entity.getBoardNo())
						    		        .id(entity.getMember().getId()) // ✅ getMember().getId()로 변경
						    		        .depth(entity.getDepth())
						    		        .title(entity.getTitle())
						    		        .content(entity.getContent())
						    		        .thumnail(entity.getThumnail()) // ✅ getThumbnail() 대신 getThumnail() 사용
						    		        .regDate(entity.getRegDate()) // ✅ DTO에 필요하므로 변환
						    		        .modDate(entity.getModDate()) // ✅ DTO에 필요하므로 변환
						    		        .build();
	        
	        return dto;
	    }
		
		//DTO를 엔티티로 변환하는 메소드
		default Board dtoToEntity(BoardDTO dto) {
			
			Member id = Member.builder().id(dto.getId()).build();

			Board entity = Board.builder()
											.boardNo(dto.getBoardNo())
									        .member(id) 
									        .depth(dto.getDepth())
									        .title(dto.getTitle())
									        .content(dto.getContent())
									        .thumnail(dto.getThumnail()) 
									        // ✅ regDate, modDate는 JPA가 자동 관리	
									        .build();
			return entity ;
			
		}

}
