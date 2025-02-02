package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardReplyProService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardReplyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		 * 답변글 작성 요청하기
		 * -전달받은 파라미터(답글 작성에 필요한 모든 파라미터) 가져오기
		 * 	  => BoardBean객체에 저장
		 * -BoardReplyProService 클래스의 replyArticle() 메서드를 호출하여 답변글 작성 요청
		 * 	 =>파라미터 : BoardBean, 리턴타입 : boolean(isPeplySuccess)
		 * 
		 */
		System.out.println("BoardReplyProACtion");
		ActionForward forward = null;
		
		BoardBean article = new BoardBean();
		
		article.setBoard_num(Integer.parseInt(request.getParameter("board_num")));
		article.setBoard_name(request.getParameter("board_name"));
		article.setBoard_pass(request.getParameter("board_pass"));
		article.setBoard_subject(request.getParameter("board_subject"));
		article.setBoard_content(request.getParameter("board_content"));
		article.setBoard_re_ref(Integer.parseInt(request.getParameter("board_re_ref")));
		article.setBoard_re_lev(Integer.parseInt(request.getParameter("board_re_lev")));
		article.setBoard_re_seq(Integer.parseInt(request.getParameter("board_re_seq")));
		
		
		BoardReplyProService boardReplyProService = new BoardReplyProService();
		boolean isReplySuccess = boardReplyProService.replyArticle(article);
		
		//답변글 작성 요청 결과에 따른 판별 작업 수행
		//작성 실패 시 (isReplySuccess가 false일 경우)
		//=>자바스크립트를 사용하여 :답글 등록 실패 출력 후 이전페이지로 이동
		//작성 성공 시 ActionForward 객체를 생성하여
		//BoardList.bo 서블릿 주소를 요청하여 포워딩
		//=>서블릿 주소 뒤에 파라미터로 페이지번호(page)를 전달
		//=>Redirect 방식으로 포워딩
		
		if(!isReplySuccess)	{
			//글쓰기 작업 실패 시 자바스크립트를 통해 실패 메세지 출력 후 
			//이전 페이지로 이동
			//=>자바 코드를 사용하여 응답페이지 설정
			//1.response 객체의 setContenType() 메서드를 호출하여
			//문서타입 및 인코딩 방식 설정
			response.setContentType("text/html; charset=UTF-8");
			//2.response 객체의 getWrite() 메서드를 호출하여
			//	 출력스트림 객체를 리턴받아 PrinWriter타입으로 저장
			PrintWriter out = response.getWriter();
			//3.PrintWriter 객체의 println()메서드를 호출하여
			//	 응답페이지에서 수행할 작업을 기술
			//	  => 모든 작업(자바스크립트, 태그 등)은 문자열로 지정
			out.println("<script>"); //자바스크립트 시작 태그
			out.println("alert('답글 등록 실패')"); //다이얼로그 메세지 출력
			out.println("history.back()");// 이전 페이지로 이동
			out.println("</script>"); //자바스크립트 끝 태그
		}else {
			//1. ActionForward객체 생성
			forward = new ActionForward();
			//2. 포워딩 경로(URL) 지정
			// 		경로 앞에 /기호 붙이지 말 것
			forward.setPath("BoardList.bo?page="+request.getParameter("page"));
			//3. 포워딩 방식 (Redirect 방식)지정
			forward.setRedirect(true);
		}
		//4. ActionForward 객체 리턴 => BoardFrontController 클래스로 전달
		return forward;
	}

}
