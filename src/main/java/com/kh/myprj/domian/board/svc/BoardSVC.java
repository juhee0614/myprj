package com.kh.myprj.domian.board.svc;

import java.util.List;

import com.kh.myprj.domian.board.dto.BoardDTO;
import com.kh.myprj.domian.board.dto.SearchDTO;

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
	 * 게시글 전체 검색 목록
	 * @param startRec
	 * @param endRec
	 * @param searchType
	 * @param keyword
	 * @return
	 */
	List<BoardDTO> list(int startRec,int endRec,String searchType,String keyword);
	/**
	 * 카테고리별 게시글 목록
	 * @param category
	 * @param startRec
	 * @param endRec
	 * @return
	 */
	List<BoardDTO> list(String bcategory,int startRec,int endRec);
	/**
	 * 게시글 카테고리별 검색결과 목록
	 * @param searchDTO
	 * @return
	 */
	List<BoardDTO> list(SearchDTO searchDTO);
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
	void DelItem(String cate,Long bnum);
	
	/**
	 * 총 게시글 레코드수
	 * @return
	 */
	long titalRecordCount();
	/**
	 * 게시판 카테고리별 레코드 수
	 * @param category
	 * @return
	 */
	long titalRecordCount(String bcategory);
	/**
	 * 게시판 전체 검색 레코드 총수
	 * @param searchType
	 * @param keyword
	 * @return
	 */
	long totalRecordCount(String searchType,String keyword);
	/**
	 * 게시판 카테고리별 검색 레코드 총수
	 * @param bcategory
	 * @param searchType
	 * @param keyword
	 * @return
	 */
	long totalRecordCount(String bcategory,String searchType,String keyword);
	
}
