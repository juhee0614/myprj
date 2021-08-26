package com.kh.myprj.web.api;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class FindPwReq {

	@NotBlank
	@Email
	private String email;
	@NotBlank
	@Size(max = 13)
	private String tel;
	@NotBlank
	@Size(max = 10)
	private Date birth;
	
	
}
