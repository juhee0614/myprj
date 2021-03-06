package com.kh.myprj.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.myprj.domian.board.dto.BoardDTO;
import com.kh.myprj.domian.board.dto.SearchDTO;
import com.kh.myprj.domian.board.svc.BoardSVC;
import com.kh.myprj.domian.common.dao.CodeDAO;
import com.kh.myprj.domian.common.dto.MetaOfUploadFile;
import com.kh.myprj.domian.common.dto.UpLoadFileDTO;
import com.kh.myprj.domian.common.file.FileStore;
import com.kh.myprj.domian.common.paging.FindCriteria;
import com.kh.myprj.web.form.Code;
import com.kh.myprj.web.form.LoginMember;
import com.kh.myprj.web.form.bbs.EditForm;
import com.kh.myprj.web.form.bbs.ReplyForm;
import com.kh.myprj.web.form.bbs.WriteForm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/bbs")
public class BoardController {

	private final BoardSVC boardSVC;
	private final CodeDAO codeDAO;
	private final FileStore fileStore;
	@Autowired
	@Qualifier("fc10")
	private FindCriteria fc;
	
	@ModelAttribute("category")
	public List<Code> hobby(){
		List<Code> list = codeDAO.getCode("A05");
		log.info("code-category:{}",list);
		return list;
	}
	
	//?????? ?????? ??????
	@GetMapping("")
	public String writeForm(
			//@ModelAttribute WriteForm wrtieForm
			@RequestParam String cate,
			Model model,
			HttpServletRequest request
			) {
		
		WriteForm writeForm = new WriteForm();
		
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("loginMember") != null) {
			LoginMember loginMember = 
					(LoginMember)session.getAttribute("loginMember");
			
			writeForm.setBid(loginMember.getId());
			writeForm.setBemail(loginMember.getEmail());
			writeForm.setBnickname(loginMember.getNickname());
			writeForm.setBcategory(cate);
		}
		
		model.addAttribute("writeForm",writeForm);
		return "bbs/writeForm";
	}
	
	//?????? ?????? ??????
	@PostMapping("")
	public String write(
			@RequestParam String cate,
			@Valid @ModelAttribute WriteForm writeForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
	
		if(bindingResult.hasErrors()) {
			return "bbs/writeForm";
		}
		log.info("writeForm:{}",writeForm);
		BoardDTO boardDTO = new BoardDTO();
		BeanUtils.copyProperties(writeForm, boardDTO);
		
		//???????????? ?????????????????? ????????? ???????????? ??????
		List<MetaOfUploadFile> storedFiles = fileStore.storeFiles(cate, writeForm.getFiles());
		//UploadFileDTO ??????
		boardDTO.setFiles(convert(storedFiles));
		
		Long bnum = boardSVC.write(boardDTO);
		
		redirectAttributes.addAttribute("bnum", bnum);
		return "redirect:/bbs/{bnum}";
	}
	
	private UpLoadFileDTO convert(MetaOfUploadFile attatchFile) {
		UpLoadFileDTO uploadFileDTO = new UpLoadFileDTO();
		BeanUtils.copyProperties(attatchFile, uploadFileDTO);
		return uploadFileDTO;
	}
	
	private List<UpLoadFileDTO> convert(List<MetaOfUploadFile> uploadFiles) {
		List<UpLoadFileDTO> list = new ArrayList<>();
	
		for(MetaOfUploadFile file : uploadFiles) {
			UpLoadFileDTO uploadFIleDTO = convert(file);
			list.add( uploadFIleDTO );
		}		
		return list;
	}
	
	//?????? ?????? ??????
	@GetMapping("/reply/{bnum}")
	public String replyForm(
			@PathVariable Long bnum,
			Model model,
			HttpServletRequest request) {		
		
		ReplyForm replyForm = new ReplyForm();
		
		//???????????? ?????? id,email,nickname????????????
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("loginMember") != null) {
			LoginMember loginMember = 
					(LoginMember)session.getAttribute("loginMember");
			
			replyForm.setBid(loginMember.getId());
			replyForm.setBemail(loginMember.getEmail());
			replyForm.setBnickname(loginMember.getNickname());
		}
		
		//???????????? ?????????, ????????????, ?????? ????????????
		BoardDTO pBoardDTO = boardSVC.itemDetail(bnum);
		replyForm.setPbnum(pBoardDTO.getBnum());
		replyForm.setBcategory(pBoardDTO.getBcategory());
		replyForm.setBtitle("?????? : " + pBoardDTO.getBtitle());
		
		model.addAttribute("replyForm", replyForm);
		
		return "bbs/replyForm";
	}
	
	//?????? ?????? ??????
	@PostMapping("/reply/{bnum}")
	public String reply(
			@PathVariable("bnum") Long pbnum,  //?????????
			@Valid @ModelAttribute ReplyForm replyForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
	
		if(bindingResult.hasErrors()) {
			return "bbs/replyForm";
		}
		
		BoardDTO boardDTO = new BoardDTO();
		BeanUtils.copyProperties(replyForm, boardDTO);
		
		//???????????? bnum, bgroup, bstep, bindent
		BoardDTO pboardDTO = boardSVC.itemDetail(pbnum);
		boardDTO.setPbnum(pboardDTO.getBnum());
		boardDTO.setBgroup(pboardDTO.getBgroup());
		boardDTO.setBstep(pboardDTO.getBstep());
		boardDTO.setBindent(pboardDTO.getBindent());
		
		//???????????? ?????????????????? ????????? ???????????? ??????
		List<MetaOfUploadFile> storedFiles = fileStore.storeFiles(replyForm.getBcategory(), replyForm.getFiles());
		//UploadFileDTO ??????
		boardDTO.setFiles(convert(storedFiles));
		
		Long rbnum = boardSVC.reply(boardDTO);		
		
		redirectAttributes.addAttribute("bnum", rbnum);
		return "redirect:/bbs/{bnum}";
	}	
	
	//????????? ??????
	@GetMapping("/{bnum}")
	public String detailItem(
			@PathVariable Long bnum,
			Model model) {
		
		model.addAttribute("detailItem", boardSVC.itemDetail(bnum));
		
		return "bbs/detailItem";
	}
	
	//????????? ?????? ??????
	@GetMapping({"/all",
							 "/all/{reqPage}",
							 "/all/{reqPage}/{searchType}/{keyword}"})
	public String all(
			@PathVariable(required = false) Integer reqPage,
			@PathVariable(required = false) String searchType,
			@PathVariable(required = false) String keyword,			
			Model model
			) {
		List<BoardDTO> list = null;
		
		//?????????????????? ????????? 1????????????
		if(reqPage == null) reqPage = 1;
		//???????????? ????????? ???????????????
		fc.getRc().setReqPage(reqPage);	
		
		
		//????????? ??????
		if((searchType == null || searchType.equals(""))
				&& (keyword == null || keyword.equals(""))) {
			//????????? ??????????????????
			fc.setTotalRec(boardSVC.titalRecordCount());
			
			list = boardSVC.list(
					fc.getRc().getStartRec(),
					fc.getRc().getEndRec());		
		}else {
			//????????? ??????????????????
			fc.setTotalRec(boardSVC.totalRecordCount(searchType,keyword));
			
			list = boardSVC.list(
					fc.getRc().getStartRec(),
					fc.getRc().getEndRec(),						
					searchType,keyword
					);						
		}
		
		fc.setSearchType(searchType);
		fc.setKeyword(keyword);
				
		model.addAttribute("list", list);
		model.addAttribute("fc", fc);
		
		return "bbs/all";
	}	
	
	
//????????? ??????????????? ??????
	@GetMapping({"/list",
							 "/list/{reqPage}",
							 "/list/{reqPage}/{searchType}/{keyword}"})
	public String list(
			@RequestParam(required = false) String cate,
			@PathVariable(required = false) Integer reqPage,
			@PathVariable(required = false) String searchType,
			@PathVariable(required = false) String keyword,			
			Model model
			) {
		List<BoardDTO> list = null;
		
		//?????????????????? ????????? 1????????????
		if(reqPage == null) reqPage = 1;
		//???????????? ????????? ???????????????
		fc.getRc().setReqPage(reqPage);	
		
		
		//????????? ??????
		if((searchType == null || searchType.equals(""))
				&& (keyword == null || keyword.equals(""))) {
			//????????? ??????????????????
			fc.setTotalRec(boardSVC.titalRecordCount(cate));
			
			list = boardSVC.list(
					cate,
					fc.getRc().getStartRec(),
					fc.getRc().getEndRec());		
		}else {
			//????????? ??????????????????
			fc.setTotalRec(boardSVC.totalRecordCount(cate,searchType,keyword));
			
			list = boardSVC.list(
					new SearchDTO(
							cate, 
							fc.getRc().getStartRec(), fc.getRc().getEndRec(), 
							searchType, keyword)
			);						
		}
		
		fc.setSearchType(searchType);
		fc.setKeyword(keyword);
				
		model.addAttribute("list", list);
		model.addAttribute("fc", fc);
		model.addAttribute("cate",cate);
		
		return "bbs/list";
	}	
	
	//????????? ?????? ??????
	@GetMapping("/{bnum}/edit")
	public String editForm(
			@PathVariable Long bnum,
			Model model) {
			
		model.addAttribute("editForm", boardSVC.itemDetail(bnum)) ;
		return "bbs/editForm";
	}
	
	//????????? ?????? ??????
	@PatchMapping("/{bnum}/edit")
	public String edit(
			@PathVariable Long bnum,
			@Valid @ModelAttribute EditForm editForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
		
		if(bindingResult.hasErrors()) {
			log.info("???????????????????????????:{}",bindingResult);
			return "bbs/editForm";
		}
		
		BoardDTO boardDTO = new BoardDTO();
		
		//???????????? ?????????????????? ????????? ???????????? ??????
		List<MetaOfUploadFile> storedFiles = fileStore.storeFiles(editForm.getBcategory(), editForm.getFiles());
		//UploadFileDTO ??????
		boardDTO.setFiles(convert(storedFiles));		
		BeanUtils.copyProperties(editForm, boardDTO);
		
		Long modifyedBnum = boardSVC.modifyItem(bnum, boardDTO);
		redirectAttributes.addAttribute("bnum", modifyedBnum);
		
		return "redirect:/bbs/{bnum}";
	}

}








