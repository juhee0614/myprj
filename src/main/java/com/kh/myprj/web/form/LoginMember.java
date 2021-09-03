package com.kh.myprj.web.form;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginMember {
	private Long id;
	private String email;
	private String nickname;
	private String role;
}