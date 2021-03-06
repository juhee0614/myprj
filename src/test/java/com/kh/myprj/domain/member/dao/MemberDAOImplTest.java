package com.kh.myprj.domain.member.dao;

import java.sql.Date;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kh.myprj.domian.member.dao.MemberDAO;
import com.kh.myprj.domian.member.dto.MemberDTO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class MemberDAOImplTest {

	@Autowired
	private MemberDAO mdao;
	
	@Test
	@DisplayName("가입")
	@Disabled
	void insert() {
		MemberDTO mdto = new MemberDTO();
		
//		pstmt.setString(1,memberDTO.getEmail());
//		pstmt.setString(2, memberDTO.getPw());
//		pstmt.setString(3, memberDTO.getTel());
//		pstmt.setString(4, memberDTO.getNickname());
//		pstmt.setString(5, memberDTO.getGender());
//		pstmt.setString(6, memberDTO.getRegion());
//		pstmt.setDate(7, memberDTO.getBirth());
//		pstmt.setString(8,memberDTO.getLetter());
		mdto.setEmail("test3@test.com");
		mdto.setPw("1234");
		mdto.setTel("010-1111-1111");
		mdto.setNickname("테스터");
		mdto.setGender("남");
		mdto.setRegion("부산");
		mdto.setBirth(Date.valueOf("2021-08-20"));
		mdto.setLetter("0");
		
		log.info("member-id:{}",mdao.insert(mdto));

	}
	
	@Test
	@DisplayName("회원조회 by id")
	void findById() {
		
		log.info("findById:{}",mdao.findByID(1));
	}
	@Test
	@DisplayName("회원조회 by email")
	void findByEmail() {
		
		log.info("findByEmail:{}",mdao.findByEmail("user@test.com"));
	}
	
	@Test
	@DisplayName("이메일찾기")
	void findId() {
		MemberDTO mdto = mdao.findByEmail("test@test.com");
		String findedEmail =  mdao.findEmail(mdto.getTel(), mdto.getBirth());
		Assertions.assertThat(findedEmail).isEqualTo(mdto.getEmail());
	}
	
	@Test
	@DisplayName("비밀번호찾기")
	void findPw() {
		MemberDTO mdto = mdao.findByEmail("test@test.com");
		String findedPw =  mdao.findPw(mdto.getEmail(),mdto.getTel(), mdto.getBirth());
		Assertions.assertThat(findedPw).isEqualTo(mdto.getPw());
		
	}
	
	
	
}
