package com.kh.myprj.domian.board.svc;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.myprj.domian.board.dao.BoardDAO;
import com.kh.myprj.domian.board.dto.BoardDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BoardSVCImpl implements BoardSVC {

	private final BoardDAO boardDAO;
	//게시글작성
	@Override
	public Long write(BoardDTO boardDTO) {
		Long bnum = boardDAO.write(boardDTO);
		return bnum;
	}
	//답글작성
	@Override
	public Long reply(BoardDTO boardDTO) {
		Long bnum = boardDAO.reply(boardDTO);
		return bnum;
	}
	//게시글전체목록
	@Override
	public List<BoardDTO> list() {
		List<BoardDTO> list = boardDAO.list();
		return list;
	}
	//게시글상세조회
	@Override
	public BoardDTO itemDetail(Long bnum) {
		BoardDTO boardDTO = boardDAO.itemDetail(bnum);
		return boardDTO;
	}
	//게시글수정
	@Override
	public Long modifyItem(Long bnum, BoardDTO boardDTO) {
		Long modifiedBnum = boardDAO.modifyItem(bnum, boardDTO);
		return modifiedBnum;
	}
	//게시글삭제
	@Override
	public void DelItem(Long bnum) {
		boardDAO.DelItem(bnum);
	}

}
