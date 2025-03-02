package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.MemberDTO;
import com.example.demo.entity.Member;

@Service
public interface MemberService {

		//회원 목록조회
		List<MemberDTO> getList(); 
		
		//회원 등록
		boolean register(MemberDTO dto);
		
		//회원 단건 조회
		MemberDTO read(String id);
		
		//회원 수정 메소드
		void modify(MemberDTO dto);
		
		//회원 삭제 메소드
		void remove(String id);
		
		//엔티티를 DTO로 변환하는 메소드
		default MemberDTO entityToDto(Member entity) {
			MemberDTO dto = MemberDTO.builder()
										.id(entity.getId())
										.password(entity.getPassword())
										.name(entity.getName())
										.role(entity.getRole())
										.build();
			return dto;
		}
		
		//DTO를 엔티티로 변환하는 메소드 
		default Member dtoToEntity(MemberDTO dto) {
			Member entity = Member.builder()
										.id(dto.getId())
										.password(dto.getPassword())
										.name(dto.getName())
										.role(dto.getRole())
										.build();
			return entity;
		}
}
