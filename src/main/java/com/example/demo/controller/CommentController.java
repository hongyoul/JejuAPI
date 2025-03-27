package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.CommentDTO;
import com.example.demo.service.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	CommentService service;
	
	//댓글 등록
		@PostMapping("/register")
		public ResponseEntity<Integer> register(@RequestBody CommentDTO dto, Authentication authentication){
			// 로그인한 사용자의 ID 가져오기
		    String memberId = authentication.getName(); // 인증 객체에서 사용자 ID 추출
		    
		    // DTO에 로그인한 사용자 ID 설정
		    dto.setId(memberId);
			
		    // 댓글 등록
			int newNo = service.register(dto);
			
			return new ResponseEntity<>(newNo, HttpStatus.CREATED);
		}
		
		//회원별 댓글 목록
		@GetMapping("/myList")
		public ResponseEntity<List<CommentDTO>> getList(Authentication authentication) {
			// 로그인한 사용자의 ID 가져오기
		    String memberId = authentication.getName(); // 인증 객체에서 사용자 ID 추출
		
		    // 서비스 호출하여 해당 사용자의 댓글 목록 조회
		    List<CommentDTO> list = service.getList(memberId);
		
		    // HTTP 상태 코드 200과 함께 목록 반환
		    return new ResponseEntity<>(list, HttpStatus.OK);
		}
		
		// 게시물별 댓글 목록
		@GetMapping("/boardList")
		public ResponseEntity<List<CommentDTO>> getBoardList(@RequestParam(name = "boardNo") int boardNo) {

		    // 서비스 호출하여 상품별 리뷰 목록 조회
		    List<CommentDTO> list = service.getBoardList(boardNo);

		    // HTTP 상태 코드 200과 함께 목록 반환
		    return new ResponseEntity<>(list, HttpStatus.OK);
		}
		
		//댓글 상세
		@GetMapping("/read")
		public ResponseEntity<CommentDTO> read(@RequestParam(name = "no") int reNo) {

	        // 리뷰 항목 조회
			CommentDTO dto = service.read(reNo);

	        // 성공 응답
	        return new ResponseEntity<>(dto, HttpStatus.OK);
	    }
		
		//댓글 수정
		@PutMapping("/modify")
		public ResponseEntity<Void> modify(@RequestBody CommentDTO dto, Authentication authentication){
			// 로그인한 사용자의 ID 가져오기
	        String memberId = authentication.getName();
	        
	        CommentDTO existingDto = service.read(dto.getCommentNo());
	        
	     // 소유자 검증
	        if (existingDto == null || !existingDto.getId().equals(memberId)) {
	            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 Forbidden
	        }
	        
	     // 수정 수행
		    service.modify(dto);
		    return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
	 
		}
		
		//댓글 삭제
		@DeleteMapping("/remove")
		 public ResponseEntity<Void> remove(@RequestParam(name = "no") int reNo, Authentication authentication) {
			 // 로그인한 사용자의 ID 가져오기
	        String memberId = authentication.getName();

	        // 기존 장바구니 항목 조회
	        CommentDTO existingDto = service.read(reNo);

	        // 소유자 검증
	        if (existingDto == null || !existingDto.getId().equals(memberId)) {
	            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 Forbidden
	        }

	        // 삭제 수행
	        service.remove(reNo);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
		}

}
