package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.LoginException;
import svc.MemberLoginProService;
import vo.ActionForward;

public class MemberLoginProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("MemberLoginProAction!");
		
		ActionForward forward = null;
		
		// 로그인 처리를 위해 입력받은 아이디, 패스워드 가져오기
		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");
//		System.out.println(id + ", " + passwd);
		
		// MemberLoginProService 클래스 인스턴스 생성 후
		// isLoginMember() 메서드를 호출하여 로그인 요청 수행하여
		// 로그인 성공 여부를 boolean 타입으로 리턴
		// => 파라미터 : 아이디, 패스워드   리턴타입 : boolean(isMember)
		MemberLoginProService memberLoginProService = new MemberLoginProService();
		
		try {
			boolean isMember = memberLoginProService.isLoginMember(id, passwd);
			
			// 로그인 성공 여부 판별(성공일 경우만 판별)
			if(isMember) {
				// 로그인 성공 시 아이디값을 세션 객체에 추가
				// 1. request 객체로부터 HttpSession 객체를 가져오기
				HttpSession session = request.getSession();
				// 2. HttpSession 객체의 setAttribute() 메서드 호출하여 아이디 추가
				session.setAttribute("id", id);
				
				// ActionForward 객체를 생성하여 Redirect 방식으로 메인페이지 이동
				forward = new ActionForward();
				forward.setRedirect(true);
				forward.setPath("./"); // 프로젝트 루트로 경로 설정
			}
		} catch (LoginException e) { // 로그인 실패 시
//			e.printStackTrace();
			// LoginException 예외가 발생하여 외부로 위임되고
			// 현재 위치에서 catch 문을 통해 예외를 전달받게 됨
			// => 전달받은 예외 객체의 메세지를 자바스크립트로 출력하면
			//    로그인 실패 원인에 대한 결과 메세지 출력 구분 가능
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('" + e.getMessage() + "')"); // 실패 메세지 출력
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}















