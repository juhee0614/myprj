package com.kh.myprj.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.myprj.domian.board.svc.BoardSVC;
import com.kh.myprj.domian.common.dao.UpLoadFileDAO;
import com.kh.myprj.domian.common.file.FileStore;
import com.kh.myprj.web.api.JsonResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bbs")
public class APIBoardController {

	private final BoardSVC boardSVC;
	private final UpLoadFileDAO upLoadFileDAO;
	private final FileStore fileStore;
	
	//게시글 삭제
	@DeleteMapping("/{cate}/{bnum}")
	public JsonResult<String> delItem(@PathVariable String cate,
																		@PathVariable Long bnum) {

		boardSVC.DelItem(cate,bnum);
		return new JsonResult<String>("00", "ok", String.valueOf(bnum));
	}
	
	//첨부파일 삭제 by fid
	@DeleteMapping("/attach/{cate}/{sfname}")
	public JsonResult<String> delFile(@PathVariable String cate,
																		@PathVariable String sfname){
		
		if( fileStore.deleteFile(cate,sfname)) {
		upLoadFileDAO.deleteFileBySfname(sfname);
		}else {
			return new JsonResult<String>("0","nok","파일삭제실패!");
		}
		return new JsonResult<String>("00","ok","파일삭제성공~!");
	}
}
