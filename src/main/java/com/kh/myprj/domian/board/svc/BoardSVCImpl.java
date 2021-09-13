package com.kh.myprj.domian.board.svc;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.myprj.domian.board.dao.BoardDAO;
import com.kh.myprj.domian.board.dto.BoardDTO;
import com.kh.myprj.domian.board.dto.SearchDTO;
import com.kh.myprj.domian.common.dao.UpLoadFileDAO;
import com.kh.myprj.domian.common.dto.UpLoadFileDTO;
import com.kh.myprj.domian.common.file.FileStore;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BoardSVCImpl implements BoardSVC {

	private final BoardDAO boardDAO;
	private final UpLoadFileDAO upLoadFileDAO;
	private final FileStore fileStore;
	
	//게시글작성
	@Override
	public Long write(BoardDTO boardDTO) {
		Long bnum = boardDAO.write(boardDTO);
	//첨부파일 메타정보 저장
			upLoadFileDAO.addFiles(
					convert(bnum, boardDTO.getBcategory(), boardDTO.getFiles())
			);
		return bnum;
	}
	private List<UpLoadFileDTO> convert(
			Long bnum,String bcategory,List<UpLoadFileDTO> files) {
		for(UpLoadFileDTO ele : files) {
			ele.setRid(String.valueOf(bnum));
			ele.setCode(bcategory);
		}
		return files;
	}

	//답글작성
	@Override
	public Long reply(BoardDTO boardDTO) {
		Long bnum = boardDAO.reply(boardDTO);
	//첨부파일 메타정보 저장
		upLoadFileDAO.addFiles(
				convert(bnum, boardDTO.getBcategory(), boardDTO.getFiles())
		);
	return bnum;
		
	}
	//게시글전체목록
	@Override
	public List<BoardDTO> list() {
		List<BoardDTO> list = boardDAO.list();
		return list;
	}
	@Override
	public List<BoardDTO> list(int startRec, int endRec) {
		List<BoardDTO> list = boardDAO.list(startRec, endRec);
		return list;
	}
	@Override
	public List<BoardDTO> list(String bcategory, int startRec, int endRec) {
		List<BoardDTO> list = boardDAO.list(bcategory, startRec, endRec);
		return list;
	}
	//전체페이지 검색목록
	@Override
	public List<BoardDTO> list(int startRec, int endRec, String searchType, String keyword) {
		List<BoardDTO> list = boardDAO.list(startRec, endRec, searchType, keyword);
		return list;
	}
	//카테고리별 검색목록
	@Override
	public List<BoardDTO> list(SearchDTO searchDTO) {
		List<BoardDTO> list =boardDAO.list(searchDTO);
		return list;
	}
	//게시글상세조회
	@Override
	public BoardDTO itemDetail(Long bnum) {
		//게시글 가져오기
		BoardDTO boardDTO = boardDAO.itemDetail(bnum);
		
		//첨부파일 가져오기
		boardDTO.setFiles(
				upLoadFileDAO.getFiles(
						String.valueOf(boardDTO.getBnum()), boardDTO.getBcategory()));
		
		//조회수증가
		boardDAO.updateBhit(bnum);
		
		return boardDTO;
	}
	//게시글수정
	@Override
	public Long modifyItem(Long bnum, BoardDTO boardDTO) {
		Long modifiedBnum = boardDAO.modifyItem(bnum, boardDTO);
	//첨부파일 메타정보 저장
			upLoadFileDAO.addFiles(
					convert(bnum, boardDTO.getBcategory(), boardDTO.getFiles())
			);
		return modifiedBnum;
	}
	//게시글삭제
	@Override
	public void DelItem(Long bnum) {
		//게시글삭제
		boardDAO.DelItem(bnum);
		//서버파일 시스템에 있는 업로드 파일삭제
		fileStore.deleteFiles(upLoadFileDAO.getStore_Fname(String.valueOf(bnum)));
		//업로드 파일 메타정보 삭제
		upLoadFileDAO.deleteFileByRid(String.valueOf(bnum));
	}
	
	//게시판 전체 레코드 수
	@Override
	public long titalRecordCount() {
		return boardDAO.titalRecordCount();
	}
	//카테고리별 전체 레코드수
	@Override
	public long titalRecordCount(String bcategory) {
		return boardDAO.titalRecordCount(bcategory);
	}
	//카테고리별 검색 총 레코드수
	@Override
	public long totalRecordCount(String bcategory, String searchType, String keyword) {
		return boardDAO.totalRecordCount(bcategory, searchType, keyword);
	}
	//전체게시판 검색 총 레코드 수
	@Override
	public long totalRecordCount(String searchType, String keyword) {
		return boardDAO.totalRecordCount(searchType, keyword);
	}
}
