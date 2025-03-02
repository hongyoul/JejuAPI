package com.example.demo.dto;

import java.time.LocalDateTime;

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

public class CommentDTO {
	
	int commentNo; //댓글번호

	String id; // 작성자

    int boardNo; //글번호

    String content; // 댓글내용

    LocalDateTime regDate; //등록일자

    LocalDateTime modDate; //수정일자
}
