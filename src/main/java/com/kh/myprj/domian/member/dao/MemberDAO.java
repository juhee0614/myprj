package com.kh.myprj.domian.member.dao;

import java.util.List;

import com.kh.myprj.domian.member.dto.MemberDTO;

public interface MemberDAO {
	/**
	 * 가입
	 * @param memberDTO
	 * @return
	 */
	long insert(MemberDTO memberDTO);
	
	MemberDTO findByIDPw(String id, String pw);
	
	
	/**
	 * 조회 by id
	 * @param id
	 * @return
	 */
	MemberDTO findByID(long id);
	
	/**
	 * 조회 by email
	 * @param email
	 * @return
	 */
	MemberDTO findByEmail(String email);
	
	List<MemberDTO> selectAll();
	
	/**
	 * 수정
	 * @param id
	 * @param memberDTO
	 */
	void update(long id, MemberDTO memberDTO);
	
	/**
	 * 삭제
	 * @param id
	 */
	void delete(long id);

}