package com.kh.myprj.domian.member.svc;

import com.kh.myprj.domian.member.dto.MemberDTO;

public interface MemberSVC {
	/**
	 * 가입
	 * @param memberDTO
	 * @return
	 */
	void join(MemberDTO memberDTO);
	/**
	 * 수정
	 * @param id
	 * @param memberDTO
	 */
	void update(long id, MemberDTO memberDTO);
}