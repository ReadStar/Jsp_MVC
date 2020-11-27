package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.MemberJoinProService;
import vo.ActionForward;
import vo.MemberBean;

public class MemberJoinProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("MemberJoinProAction!");
		
		ActionForward forward = null;
		
		// join_form.jsp 페이지로부터 전달받은 파라미터(회원정보)를
		// MemberBean 객체에 저장
		// => 단, 성별(gender)의 경우 char 타입으로 변환 필요
		MemberBean member = new MemberBean(
				request.getParameter("name"), 
				request.getParameter("id"), 
				request.getParameter("passwd"), 
				Integer.parseInt(request.getParameter("age")), 
				request.getParameter("email"), 
				request.getParameter("gender").charAt(0));
		
		// MemberJoinProService 클래스의 인스턴스 생성 후 
		// registMember() 메서드 호출하여 회원 가입 작업 요청 수행
		// => 파라미터 : MemberBean, 리턴타입 : boolean(isJoinSuccess)
		MemberJoinProService memberJoinProService = new MemberJoinProService();
		boolean isJoinSuccess = memberJoinProService.registMember(member);
		
		// isJoinSuccess 가 false 이면(가입 실패)
		// 자바스크립트를 사용하여 "회원가입 실패!" 출력 후 이전페이지로 이동
		// 아니면(true = 가입 성공) 메인페이지(index.jsp)로 이동
		if(!isJoinSuccess) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원가입 실패!')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			forward = new ActionForward();
			forward.setRedirect(true);
			forward.setPath("./"); // 프로젝트 루트로 경로 설정
		}
		
		return forward;
	}

}
