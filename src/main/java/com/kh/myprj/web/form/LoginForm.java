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
	@Size(min=4, max=20)
	private String email;
	
	@NotBlank
	@Size(min=4,max =10)
	private String pw;
	
}
