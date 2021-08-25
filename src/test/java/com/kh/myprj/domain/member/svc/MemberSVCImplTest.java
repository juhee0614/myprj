package com.kh.myprj.domain.member.svc;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kh.myprj.domian.member.dto.MemberDTO;
import com.kh.myprj.domian.member.svc.MemberSVC;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MemberSVCImplTest {
	
	@Autowired
	private MemberSVC mSVC;
	
	@Test
	@DisplayName("이메일중복체크")
	void isExistEmail() {
	boolean result =	mSVC.isExistEmail("test@test.com");
	Assertions.assertThat(result).isEqualTo(true);
	boolean result2 =	mSVC.isExistEmail("1@test.com");
	Assertions.assertThat(result2).isEqualTo(false);
	}
	
	@Test
	@DisplayName("로그인체크")
	void isLogin() {
		MemberDTO mdto =  mSVC.isLogin("test@test.com", "1234");
		Assertions.assertThat(mdto.getEmail()).isEqualTo("test@test.com");
		
//		MemberDTO mdto2 =  mSVC.isLogin("1@test.com", "1234");
//		Assertions.assertThat(mdto2).isEqualTo(null);
	}
		
		@Test
		@DisplayName("이메일로 회원정보 가져오기")
		void findByEmail() {
			MemberDTO mdto = mSVC.findByEmail("test@test.com");
			log.info("mdto:{}", mdto);
		}
	}
	

