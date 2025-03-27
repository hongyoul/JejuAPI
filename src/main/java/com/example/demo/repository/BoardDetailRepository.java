package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.BoardDetail;

import jakarta.transaction.Transactional;

@Transactional
public interface BoardDetailRepository extends JpaRepository<BoardDetail, Integer> {

	@Query(value = "SELECT detail_img FROM tab_board_img WHERE board_board_no = :boardNo", nativeQuery = true)
    List<String> selectDetailImgByBoardNo(@Param("boardNo") int boardNo);

    @Query(value = "delete FROM tab_board_img WHERE board_board_no = :boardNo", nativeQuery = true)
    @Modifying
    void deleteByBoardNo(@Param("boardNo") int boardNo);
    
}
