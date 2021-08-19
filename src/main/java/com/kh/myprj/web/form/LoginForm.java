package com.kh.myprj.web.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LoginForm {

	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	@Size(min=4,max =8)
	private String pw;
	
}
