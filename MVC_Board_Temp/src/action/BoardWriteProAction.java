package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.ActionForward;

public class BoardWriteProAction {
	
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("BoardWriteProAction");
		//글씀->
		//이동할 페이지 정보 설정하여 리턴
		//BoardList.bo 경로 설정
		//포워딩 방식을 Redirect방식으로 이동하도록 설정
		ActionForward forward = new ActionForward();
		forward.setPath("BoardList.bo"); //포워딩 경로 지정
		forward.setRedirect(true); //Redirect 방식 지정
		//ActionForward 객체 리턴
		return forward;
	}
}
