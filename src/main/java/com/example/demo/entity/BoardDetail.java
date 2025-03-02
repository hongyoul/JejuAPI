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
import lombok.ToString;

// 게시물 상세이미지
@Entity
@Table(name = "tab_board_img")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int badINo; //번호
	
	@ManyToOne
	Board board;
	
	@Column(length = 200)
	String detailImg; //상세이미지
}
