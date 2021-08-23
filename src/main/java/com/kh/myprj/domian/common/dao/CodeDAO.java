package com.kh.myprj.domian.common.dao;

import java.util.List;

import com.kh.myprj.web.form.Code;

public interface CodeDAO {
	List<Code> getCode(String Pcode);
}