package com.kh.myprj.domian.board.svc;

import java.util.List;

import com.kh.myprj.domian.board.dto.BoardDTO;

public interface BoardSVC {

	/**
	 * 원글작성
	 * @param boardDTO
	 * @return
	 */
	Long write (BoardDTO boardDTO);
	
	/**
	 * 답글작성
	 * @param boardDTO
	 * @return
	 */
	Long reply(BoardDTO boardDTO);
	
	/**
	 * 게시글 목록
	 * @return
	 */
	List<BoardDTO> list();
	List<BoardDTO> list(int startRec,int endRec);
	
	/**
	 * 게시글 상세조회
	 * @param bnum
	 * @return
	 */
	BoardDTO itemDetail(Long bnum);
	
	/**
	 * 게시글 수정
	 * @param bnum
	 * @param boardDTO
	 * @return
	 */
	Long modifyItem(Long bnum,BoardDTO boardDTO);
	
	/**
	 * 게시글 삭제
	 * @param bnum
	 */
	void DelItem(Long bnum);
	
	/**
	 * 총 게시글 레코드수
	 * @return
	 */
	long titalRecordCount();
	
	
}
