package com.kh.myprj.domian.member.dto;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data

public class MemberDTO {

	private long id;
	private String email;
	private String pw;
	private String tel;
	private String nickname;
	private String gender;
	private String region;
	private Date birth;
	private List<String> hobby;
	private String letter;
	private Long fid;
	private LocalDateTime cdate;
	private LocalDateTime udate;
}
