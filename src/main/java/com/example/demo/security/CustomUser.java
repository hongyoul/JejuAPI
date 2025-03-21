package com.example.demo.security;

import java.util.Arrays;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;


// 사용자 정보를 담고 있는 인증 객체

public class CustomUser extends User {

  public CustomUser(com.example.demo.dto.MemberDTO dto) {
	super(dto.getId(), dto.getPassword(), Arrays.asList(new SimpleGrantedAuthority(dto.getRole())));    
  }
  
}