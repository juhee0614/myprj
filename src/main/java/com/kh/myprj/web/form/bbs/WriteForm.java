package com.kh.myprj.web.form.bbs;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class WriteForm {
	@NotBlank
	private String bcategory;
	@NotBlank
	private String btitle;
	@NotNull
	private Long bid;
	@NotBlank
	private String bemail;
	@NotBlank
	private String bnickname;
	@NotBlank
	private String bcontent;
}
