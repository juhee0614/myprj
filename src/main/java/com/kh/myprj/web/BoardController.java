package com.kh.myprj.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.myprj.domian.board.dto.BoardDTO;
import com.kh.myprj.domian.board.svc.BoardSVC;
import com.kh.myprj.domian.common.dao.CodeDAO;
import com.kh.myprj.web.api.JsonResult;
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
	
	@ModelAttribute("bcategory")
	public List<Code> bcategory(){
		List<Code> list = codeDAO.getCode("A05");
				log.info("code-bcategory:{}",list);
				return list;
	}
	
	
	//게시글 작성 양식출력
	@GetMapping("/")
	public String writeForm(Model model,
													HttpServletRequest request) {
		
		WriteForm writeForm = new WriteForm();
		
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("loginMember") != null) {
		LoginMember loginMember =
							(LoginMember)session.getAttribute("loginMember");
		writeForm.setBid(loginMember.getId());
		writeForm.setBnickname(loginMember.getNickname());
		writeForm.setBemail(loginMember.getEmail());
		}
		model.addAttribute("writeForm",writeForm);
		return "bbs/writeForm";
	}
	
	//게시글 처리
	@PostMapping("/")
	public String write(@Valid @ModelAttribute WriteForm writeForm,
												BindingResult bindingResult,
												RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			return "bbs/writeForm";
		}
		BoardDTO boardDTO = new BoardDTO();
		BeanUtils.copyProperties(writeForm, boardDTO);
		Long bnum = boardSVC.write(boardDTO);
		
		//주소값받은걸 다시 return값에 파라미터로 넣어야하면 redirectAttributes 써줘야함!
		redirectAttributes.addAttribute("bnum",bnum);
		
		return "redirect:/bbs/{bnum}";
	}
	//답글 작성 양식출력
	@GetMapping("/reply")
	public String replyForm(@ModelAttribute ReplyForm replyForm) {
		
		return "bbs/replyForm";
	}
	
	//답글 처리
	@PostMapping("/reply")
	public String reply(@Valid @ModelAttribute ReplyForm replyForm,
			BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "bbs/replyForm";
		}
		
		
		return "redirect:/bbs/list";
	}
	
	//게시글상세조회
	@GetMapping("/{bnum}")
	public String detailItem(@PathVariable Long bnum,
													Model model) {
		
		 model.addAttribute("detailItem", boardSVC.itemDetail(bnum));
		
		return "bbs/detailItem";
	}
	
	//게시글 목록출력
	@GetMapping("/list")
	public String list(Model model) {
		
		List<BoardDTO>list = boardSVC.list();
		model.addAttribute("list",list);
		
		return "bbs/list";
	}
	
	//게시글 수정양식
	@GetMapping("/{bnum}/edit")
	public String editForm(@PathVariable Long bnum,
													Model model) {
		
		model.addAttribute("item", boardSVC.itemDetail(bnum));
		
		return "bbs/editForm";
	}
	
	//게시글 수정
	@PatchMapping("/{bnum}/edit")
	public String edit(@PathVariable Long bnum,
										@Valid @ModelAttribute EditForm editForm,
										BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
		return "bbs/editForm";
		}
		
		return "redirect:/bbs/{bnum}";
		}
	
	//게시글삭제
	@DeleteMapping("/{bnum}")
	@ResponseBody
	public JsonResult<String> delItem(@PathVariable Long bnum) {
		
		boardSVC.DelItem(bnum);
		
		return new JsonResult<String>("00","ok",String.valueOf(bnum));
	}
}








