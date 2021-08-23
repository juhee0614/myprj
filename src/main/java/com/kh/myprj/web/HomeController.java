package com.kh.myprj.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kh.myprj.web.form.LoginForm;
import com.kh.myprj.web.form.LoginMember;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

	//초기화면
	@GetMapping("/")
	public String home() {
		return "home";
	}
		
	//로그인 양식
	@GetMapping("/login")
	public String loginForm(@ModelAttribute LoginForm loginForm) {
		return "loginForm";
	}
	
	//로그인 처리
	@PostMapping("/login")
	public String login(
			@Valid @ModelAttribute LoginForm loginForm, 
			BindingResult bindingResult,
			Model model,
			//세션만들때 들어가야함
			HttpServletRequest request) {
		
		log.info("LoginForm:{}",loginForm);
		
		LoginMember loginMember = null;		
		//계정확인
		if("user@test.com".equals(loginForm.getEmail()) && "user1234".equals(loginForm.getPw())) {
			loginMember = new LoginMember("user","회원","user");
		}else if("admin@test.com".equals(loginForm.getEmail()) && "admin1234".equals(loginForm.getPw())){
			loginMember = new LoginMember("admin","관리자","admin");
		}else {
			//글로벌 오류 추가
			bindingResult.reject("loginChk", "아이디 또는 비밀번호가 잘못되었습니다");						
		}
		//글로벌오류 체크
		if(bindingResult.hasErrors()) {
			log.info("BindingResult:{}",bindingResult);
			return "loginForm";
		}
		
		//세션생성
		//true>>세션이 있으면 그걸가져오고 없으면 세션을 생성한다.
		HttpSession session =request.getSession(true);
		session.setAttribute("loginMember", loginMember );
		
		return "redirect:/";
	}
	
	//로그아웃
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		//세션제거
		//false >>세션이 존재하면 가져오고 없으면 세션을 생성하지 않는다.
		HttpSession session =  request.getSession(false);
		
		//세션에서 클라이언트 정보 얻어내기
		//LoginMember loginMember =(LoginMember)session.getAttribute("loginMember");
		
		//세션값이 있으면 ~
		if(session != null) {
			//세션값 삭제
			session.invalidate();
		}
		return "home";
	}
}