package com.kh.myprj.domian.member.dao;

import org.springframework.stereotype.Repository;

import com.kh.myprj.domian.member.dto.MemberDTO;

import lombok.extern.slf4j.Slf4j;


public interface MemberDAO {

	//회원가입
	long insert(MemberDTO memberDTO);
	
	//회원조회 by id
	MemberDTO findById(long id);
	
	//회원조회 by email
	MemberDTO findByEmail(String email);
}
