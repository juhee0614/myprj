package com.kh.myprj.web.form.member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ChangePwForm {

	@NotBlank
	@Size(min =4, max=10)
	private String prepw;
	@NotBlank
	@Size(min =4, max=10)
	private String postpw;
	@NotBlank
	private String postpwChk;
}
