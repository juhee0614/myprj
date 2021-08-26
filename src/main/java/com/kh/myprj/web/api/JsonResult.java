package com.kh.myprj.web.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor//모든 멤버를 매개변수로 가지는 생성자
@NoArgsConstructor //디폴트생성자
//<> 다이아몬드 연산자 : 어떤타입이 들어오든 디버깅할때 해당타입으로 바뀜
public class JsonResult<T> {

	private String rtcd;
	private String rtmsg;
	private T data;
}
