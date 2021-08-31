package com.kh.myprj.domain.common.email;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataSource;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;

import com.kh.myprj.domian.common.email.MailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MailServiceTest {
	
	@Autowired
	private MailService ms;
	
	@Test
	@DisplayName("이메일전송")
	@Disabled
	void sendMail() {
		
		ms.sendMail("wwwss7@naver.com", "안녕!", "이건테스트메일");
	}
	
	@Test
	@DisplayName("메일전송 with 첨부")
	void sendMailWithAttech() {
    FileSystemResource res = new FileSystemResource(new File("d:/apple.png"));
		
		List<File> files = new ArrayList<>();
		files.add(res.getFile());
		ms.sendMailWithAttatch("wwwss7@naver.com", "첨부메일", "이건테스트메일",files);
		
	}
	
	@Test
	@DisplayName("메일전송 with 인라인")
	void sendMailWithInline() {
		FileSystemResource res = new FileSystemResource(new File("d:/apple.png"));
		List<File> files = new ArrayList<>();
		files.add(res.getFile());
		
		ms.sendMailWithInline("wwwss7@naver.com", "인라인메일!", "이건테스트메일",files);
		
	}
}
