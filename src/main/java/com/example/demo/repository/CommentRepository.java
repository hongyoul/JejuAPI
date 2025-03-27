package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Comment;

import jakarta.transaction.Transactional;

@Transactional
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	// 사용자별 댓글 목록 조회
	List<Comment> findByMember_Id(String memberId);
	
	// 게시물별 댓글 목록 조회
	List<Comment> findByBoard_boardNo(int boardNo);
	
	// 게시물별 댓글 리스트 삭제
    @Query(value = "delete FROM tab_board_img WHERE board_board_no = :boardNo", nativeQuery = true)
    @Modifying
    void deleteByPdNo(@Param("boardNo") int boardNo);
}
