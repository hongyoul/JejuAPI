package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.BoardDTO;
import com.example.demo.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	 @Autowired
	 BoardService service;
	
	 
	 // 게시물 등록
	 @PostMapping("/register")
	  	public ResponseEntity<Integer> register(@ModelAttribute BoardDTO dto) {
	  		int newNo = service.register(dto);
	  		return new ResponseEntity<>(newNo, HttpStatus.CREATED); //201성공코드와 새로운 글번호를 반환한다
	  	}
	 
	 // 게시물 목록
	 @GetMapping("/list")
		public ResponseEntity<List<BoardDTO>> getList(
				@ModelAttribute String keyword
		) {
			List<BoardDTO> list = service.getList(keyword);
			return new ResponseEntity<>(list, HttpStatus.OK); //200성공코드와 게시물목록을 반환한다
		}
	 
	 //게시물 상세
	  @GetMapping("/read")
	  	public ResponseEntity<BoardDTO> read(@RequestParam(name = "no") int boardNo) {
		  BoardDTO dto = service.read(boardNo);
	  		return new ResponseEntity<>(dto, HttpStatus.OK); //200성공코드와 게시물정보를 반환한다
	  	}
	  	
	  //게시물 수정
	  @PutMapping("/modify")
	  	public ResponseEntity modify(BoardDTO dto) {
	  		 service.modify(dto);
	  		 return new ResponseEntity(HttpStatus.NO_CONTENT); //204성공코드를 반환한다
	  	}
	  	
	  //게시물 삭제
	  @DeleteMapping("/remove")
	  	public ResponseEntity remove(@RequestParam(name = "no") int boardNo) {
	  		service.remove(boardNo);
	  		return new ResponseEntity(HttpStatus.NO_CONTENT); //204성공코드를 반환한다
	  	}
}
