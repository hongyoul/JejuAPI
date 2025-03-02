package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tab_board")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board extends BaseEntity { // BaseEntity 상속 (등록일, 수정일)

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	int boardNo; // 게시물번호

	@ManyToOne
	Member member; // 작성자
	
	@Column(length = 25, nullable = false)
    String title; // 제목
	
	@Column(length = 255, nullable = false)
	 String content; // 내용
	
	@Column(length = 200, nullable = false)
	String thumnail; // 대표이미지(썸네일)
	
	@Column(length = 20, nullable = false)
	String depth; // 분류(여행지 or 여행목적)
}
