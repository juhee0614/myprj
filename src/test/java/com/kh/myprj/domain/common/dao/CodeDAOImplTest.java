package com.kh.myprj.domain.common.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kh.myprj.domian.common.dao.CodeDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class CodeDAOImplTest {

	@Autowired
	private CodeDAO codeDAO;
	
	@Test
	@DisplayName("์ฝ๋์กฐํ")
	void code() {
		log.info("A01:{}",codeDAO.getCode("A01"));
	}
}