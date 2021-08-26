package com.kh.myprj.web;

import javax.validation.Valid;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.myprj.domian.member.svc.MemberSVC;
import com.kh.myprj.web.api.FindEmailReq;
import com.kh.myprj.web.api.FindPwReq;
import com.kh.myprj.web.api.JsonResult;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController //@controller + @responsebody 
@RequestMapping("/api/members")
@AllArgsConstructor
public class APIMemberController {
	
	private final MemberSVC memberSVC;
	
	@GetMapping("/email")
	//바디에 응답메세지 직접 써넣음 (ajax 쓸때 써줘야돼)
	//@ResponseBody
	public JsonResult<String> findEmail(@RequestBody 
													FindEmailReq	findEmailReq,
													BindingResult bindingResult) {
		
		log.info("findEmailReq:{}", findEmailReq);
		if(bindingResult.hasErrors()) {
			return null;
		}
		
		String findedEmail = 
		memberSVC.findEmail(findEmailReq.getTel(), findEmailReq.getBirth());
		
		return new JsonResult<String>("00","ok",findedEmail);
		
	}
	@GetMapping("/pw")
	public Object findPw(@RequestBody 
			FindPwReq	findPwReq,
			BindingResult bindingResult) {
		
		log.info("findPwReq:{}", findPwReq);
		if(bindingResult.hasErrors()) {
			return null;
		}
		
		String findedPw = 
				memberSVC.findPw(findPwReq.getEmail(),findPwReq.getTel(), findPwReq.getBirth());
		return new JsonResult<String>("00","ok",findedPw);
	}

}
