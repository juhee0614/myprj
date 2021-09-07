package com.kh.myprj.domian.board.dto;

import java.sql.Timestamp;
import java.util.List;

import com.kh.myprj.domian.common.dto.UpLoadFileDTO;

import lombok.Data;

@Data
public class BoardDTO {
		private Long bnum;
		private String bcategory;
		private String btitle;
		private Long bid;
		private String bemail;
		private String bnickname;
		private Long bhit;
		private String bcontent;
		private Long pbnum;
		private Long bgroup;
		private Long bstep;
		private Long bindent;
		private String status;
		private Timestamp bcdate;
		private Timestamp budate;
		
		private List<UpLoadFileDTO> files;
}
