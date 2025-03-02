package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BoardDTO {
	
	int boardNo; // 게시물번호
	
	String id; // 작성자
	
	String depth; // 분류(여행지 or 여행목적)
	
    String title; // 제목
    
    String content; // 내용
    
    MultipartFile thumnailFile; //대표이미지 파일 스트림
    
    String thumnail; //대표이미지
    
    List<MultipartFile> detailImgFiles; // 상세이미지 파일 스트림
    
    List<String> detailImgs; //상세이미지
    
    LocalDateTime regDate; // 등록일
    
    LocalDateTime modDate; // 수정일

}
