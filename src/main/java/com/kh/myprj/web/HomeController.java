package com.kh.myprj.web;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kh.myprj.web.form.LoginForm;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

	//초기화면
	@GetMapping
	public String home() {
		return "home";
	}
	
	//로그인화면출력
	@GetMapping("/login")
	public String loginForm(@ModelAttribute LoginForm loginForm) {
		return "loginForm";
	}
	//로그인처리
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute LoginForm loginForm,
			//ModelAttribute >> loginForm의 model 자동생성해주고 사용자가 입력한 값을 model에 저장해서 로그인실패하면 다시 불러오려고
			//bindingResult는 오류생성할 객체 바로 뒤에 생성해줘야함!					
											BindingResult bindingResult) {
		
		log.info("LoginForm:{}",loginForm);
		log.info("BindingResult:{}",bindingResult);
		
		//오류를 가지고있으면 로그인 폼으로 다시 출력
		if(bindingResult.hasErrors()) {
			return "loginForm";
		}
		return "home";
	}
	
	//로그아웃
	@GetMapping("/logout")
	public String logout() {
		//세션제거
		return "home";
	}
}
