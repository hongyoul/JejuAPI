package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.BoardDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.BoardDetail;
import com.example.demo.entity.Member;
import com.example.demo.repository.BoardDetailRepository;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.CommentRepository;

import jakarta.transaction.Transactional;
import com.example.demo.util.FileUtil;
import com.example.demo.util.S3FileUtil;

@Service 
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardRepository repository;
	
	@Autowired
	BoardDetailRepository detailRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	S3FileUtil fileUtil;

	// 게시물 등록
	@Override
	public int register(BoardDTO dto) {
		
				// 게시물 저장
				Board entity = dtoToEntity(dto);

				// 게시물 썸네일 저장
				MultipartFile thumnailFile = dto.getThumnailFile();
				if(thumnailFile != null){
					String filename = fileUtil.fileUpload(thumnailFile);
					entity.setThumnail(filename);
				}

				repository.save(entity);
				int BoardNo = entity.getBoardNo();

				// 게시물 상세 이미지 저장
				List<MultipartFile> detailImgFiles = dto.getDetailImgFiles();
				// fileUtil을 사용하여 폴더에 파일을 저장한 후 파일명 반환
				// 반환받은 파일의 이름을 상세테이블에 저장
				for( MultipartFile file : detailImgFiles ){
					String filename = fileUtil.fileUpload(file);
					BoardDetail detail = BoardDetail.builder()
														.board(entity)
														.detailImg(filename)
														.build();
					detailRepository.save(detail);
				}
				
			return BoardNo;
	}
				

	// 게시글 목록 조회 
	@Override
	public List<BoardDTO> getList(String keyword) {
		List<Board> entityList;

		// 검색 키워드가 있는 경우 필터링하여 조회
	    if (keyword != null && !keyword.isEmpty()) {
	        entityList = repository.findByTitleContaining(keyword);
	    } else {
	        entityList = repository.findAll();
	    }

	    return entityList.stream()
	                     .map(this::entityToDto) // `BoardService`에 정의된 변환 메서드 사용
	                     .collect(Collectors.toList());
	}


	// 게시글 상세 조회
	@Override
	public BoardDTO read(int boardNo) {
		Optional<Board> result = repository.findById(boardNo); // 특정 상품 정보 가져오기

		if (result.isPresent()) {
			Board board = result.get();
			BoardDTO dto = entityToDto(board);

			List<String> list = detailRepository.selectDetailImgByBoardNo(dto.getBoardNo());
			dto.setDetailImgs(list);

			return dto; // DTO 반환
		} else {
			return null;
		}
	}

	// 게시글 수정
	@Override
	public void modify(BoardDTO dto) {
		// 전달받은 DTO에서 게시물 번호 꺼내고, 해당 상품 조회
		Optional<Board> result = repository.findById(dto.getBoardNo());
			if (result.isPresent()) { // 해당 상품이 존재하는지 확인
				Board entity = result.get();
					
				// 작성자
				entity.setTitle(dto.getTitle());
					
				//대분류
				entity.setDepth(dto.getDepth());
					
				//설명
				entity.setContent(dto.getContent());
					
				//썸네일
				MultipartFile thumnailFile = dto.getThumnailFile();
				
					if(thumnailFile!=null){
						String filename = fileUtil.fileUpload(thumnailFile);
						entity.setThumnail(filename);
					}

				// 상품 상세 이미지
				List<MultipartFile> detailImgFiles = dto.getDetailImgFiles();
					if(detailImgFiles!=null){
					// 기존 이미지 삭제
					detailRepository.deleteByBoardNo(entity.getBoardNo());
					// 새로운 이미지 저장
					for( MultipartFile file : detailImgFiles ){
						String filename = fileUtil.fileUpload(file);
						BoardDetail detail = BoardDetail.builder()
																.board(entity)
																.detailImg(filename)
																.build();
					detailRepository.save(detail);
						}
					}
					
				// 다시 저장
				repository.save(entity);
		  }
	}

	//게시글 삭제
	@Override
	public void remove(int boardNo) {
		
		// 게시물 삭제
		repository.deleteById(boardNo);
		
		// 게시물 상세 이미지 삭제
		detailRepository.deleteByBoardNo(boardNo);
		
		// 댓글 삭제
		commentRepository.deleteByPdNo(boardNo);
		
	}

}