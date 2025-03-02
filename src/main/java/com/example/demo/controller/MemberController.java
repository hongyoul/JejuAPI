package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MemberDTO;
import com.example.demo.service.MemberService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class MemberController {
	
	@Autowired
	MemberService service;
	
	// 회원가입
	@PostMapping("/register") //dto: 파라미터, redirectAttributes: 모델 객체
	public ResponseEntity<Boolean> registerPost(@RequestBody MemberDTO dto) {
		boolean result = service.register(dto);
		return new ResponseEntity<>(result, HttpStatus.CREATED); //201성공코드와 처리결과 반환
	}

	// 목록 화면을 반환하는 메소드
	@GetMapping("/member/list")
	public ResponseEntity<List<MemberDTO>> getList() {
		List<MemberDTO> list = service.getList();
		return new ResponseEntity<>(list, HttpStatus.OK); //200성공코드와 회원목록 반환
	}

    // 회원 상세 조회
    @GetMapping("/member/{id}")
    public ResponseEntity<MemberDTO> read(@PathVariable(name = "id") String id,  Authentication authentication) {
	
        // JWT 토큰에서 로그인한 사용자 ID 가져오기
        String loggedInUserId = authentication.getName();

        // 요청한 ID와 JWT에서 추출한 ID가 일치하지 않으면 접근 불가
        if (!loggedInUserId.equals(id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 Forbidden
        }

        // 서비스에서 회원 정보 조회
        MemberDTO dto = service.read(id);

        // 회원 정보가 없으면 404 반환
        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }

        // 성공적으로 회원 정보 반환
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
    //회원 정보 수정
    @PutMapping("/member/modify")
    public ResponseEntity<Void> modify(@RequestBody MemberDTO dto, Authentication authentication) {
    	// 로그인한 사용자의 ID 가져오기
        String memberId = authentication.getName();
        
        //기존 사용자 항목 조회
        MemberDTO existingDto = service.read(dto.getId());
        
        //검증
        if (existingDto == null || !existingDto.getId().equals(memberId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 Forbidden
        }
        
        // 수정 수행
        service.modify(dto);
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
    }
    
    //회원 삭제
    @DeleteMapping("/member/remove")
    public ResponseEntity<Void> remove(@RequestParam(name = "id") String id, Authentication authentication){
    	// 로그인한 사용자의 ID 가져오기
        String memberId = authentication.getName();

        //기존 사용자 항목 조회
        MemberDTO existingDto = service.read(id);
        
        //검증
        if (existingDto == null || !existingDto.getId().equals(memberId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 Forbidden
        }
        
        //삭제 수행
        service.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
    }
    
}
