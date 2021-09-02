package com.kh.myprj.domain.board.dao;



import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.kh.myprj.domian.board.dao.BoardDAOImpl;
import com.kh.myprj.domian.board.dto.BoardDTO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class BoardDAOImplTest {

	@Autowired
	private BoardDAOImpl boardDAOImpl;
	
	@Test
	@DisplayName("게시글생성")
void write() {
		
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBcategory("A0502");
		boardDTO.setBtitle("제목-2");
		boardDTO.setBid(1L);
		boardDTO.setBemail("wwwss7@naver.com");
		boardDTO.setBnickname("test");
		boardDTO.setBcontent("원글생성 테스트중...");
		
		Long bnum = boardDAOImpl.write(boardDTO);
		log.info("원글생성 bnum:{}",bnum);
	}
	@Test
	@DisplayName("게시글수정")
	@Disabled
	void modifyItem() {
		Long bnum =3L;
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBcategory("A0501");
		boardDTO.setBtitle("제목수정");
		boardDTO.setBcontent("내용수정");
		boardDAOImpl.modifyItem(bnum, boardDTO);
		
		BoardDTO modifiedBoardDTO = boardDAOImpl.itemDetail(bnum);
		
		Assertions.assertThat(boardDTO.getBtitle()).isEqualTo(modifiedBoardDTO.getBtitle());
		Assertions.assertThat(boardDTO.getBcategory()).isEqualTo(modifiedBoardDTO.getBcategory());
		Assertions.assertThat(boardDTO.getBcontent()).isEqualTo(modifiedBoardDTO.getBcontent());
		
	}
	
	@Test
	@DisplayName("게시글 수정 잘못된 bnum")
	@Disabled
	void modifyItemThrow() {
		Long bnum =5L;
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBcategory("A0501");
		boardDTO.setBtitle("제목수정2");
		boardDTO.setBcontent("내용수정2");
		
		org.junit.jupiter.api.Assertions.assertThrows(
				InvalidDataAccessApiUsageException.class,
				()->boardDAOImpl.modifyItem(bnum, boardDTO)
				);
	}

}
