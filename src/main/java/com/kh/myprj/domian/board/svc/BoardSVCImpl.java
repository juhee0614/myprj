package com.kh.myprj.domian.board.svc;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.myprj.domian.board.dao.BoardDAO;
import com.kh.myprj.domian.board.dto.BoardDTO;
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

}
