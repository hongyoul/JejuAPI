package com.example.demo.service;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MemberDTO;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;

//@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	MemberRepository repository;

	// 시큐리티 나중에
	@Autowired
	PasswordEncoder passwordEncoder;

	// 회원조회
	@Override
	public List<MemberDTO> getList() {
		
		List<Member> entityList = repository.findAll();		
		
		List<MemberDTO> dtoList = entityList.stream()
				.map(entity -> entityToDto(entity))
				.collect(Collectors.toList());

		return dtoList;
	}

	// 회원 등록
	@Override
	public boolean register(MemberDTO dto) {
		
		String id = dto.getId();
		MemberDTO getDto = read(id);
		if (getDto != null) {
			System.out.println("사용중인 아이디입니다.");
			return false;
		}
		Member entity = dtoToEntity(dto);

		// 패스워드 인코더로 패스워드 암호화하기
		String enPw = passwordEncoder.encode(entity.getPassword());
		entity.setPassword(enPw);

		repository.save(entity);
		return true;
	}
        
	// 회원 상세 조회
	@Override
	public MemberDTO read(String id) {
		
		Optional<Member> result = repository.findById(id);
		if (result.isPresent()) {
			Member member = result.get();
			return entityToDto(member);
		} else {
			return null;
		}
	}

	// 회원 수정
	@Override
	public void modify(MemberDTO dto) {
		 
		Optional<Member> result = repository.findById(dto.getId());
		
		if(result.isPresent()) {
			Member entity = result.get();
			
			//비밀번호
			String enPw = passwordEncoder.encode(dto.getPassword());
			entity.setPassword(enPw);
			
			entity.setName(dto.getName());
			
			repository.save(entity);
		}
	}


	// 회원삭제
	@Override
	public void remove(String id) {
		 repository.deleteById(id);
		
	}

}
