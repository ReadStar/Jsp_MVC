package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDeleteProService;
import vo.ActionForward;

public class BoardDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 전달받은 게시물 번호와 패스워드를 사용하여 적합한 사용자인지 판별
//		System.out.println("BoardDeleteProAction!");
		
		ActionForward forward = null;

		int board_num = Integer.parseInt(request.getParameter("board_num"));
		String board_pass = request.getParameter("board_pass");
//		System.out.println("글번호 : " + board_num);
//		System.out.println("패스워드 : " + board_pass);
		
		// BoardDeleteProService 클래스의 removeArticle() 메서드를 호출하여
		// 글 삭제 작업 요청 
		// => 파라미터 : 글번호, 패스워드  리턴타입 : boolean(isArticleWriter)
		BoardDeleteProService boardDeleteProService = new BoardDeleteProService();
		boolean isArticleWriter = 
				boardDeleteProService.isArticleWriter(board_num, board_pass); 
		
		System.out.println("isArticleWriter = " + isArticleWriter);
		
		// 적합한 사용자 여부 판별
		if(!isArticleWriter) { // 패스워드가 틀렸을 경우
			// 자바스크립트를 활용하여 "삭제 권한이 없습니다!" 출력 후
			// 이전페이지로 이동
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('삭제 권한이 없습니다!')");
			out.println("history.back()");
			out.println("</script>");
		} else { // 패스워드가 일치할 경우
			// BoardDeleteProService 클래스의 removeArticle() 메서드 호출하여
			// 글 삭제 작업 요청 
			// => 파라미터 : 글번호   리턴타입 : boolean(isDeleteSuccess) 
			boolean isDeleteSuccess = 
					boardDeleteProService.removeArticle(board_num);
			
			// 삭제 작업 요청 결과 판별
			if(!isDeleteSuccess) { // 삭제 실패 시
				// 자바스크립트를 활용하여 "삭제 실패!" 출력 후
				// 이전페이지로 이동
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('삭제 실패!')");
				out.println("history.back()");
				out.println("</script>");
			} else { // 삭제 성공 시
				// ActionForward 객체를 생성하여 "BoardList.bo" 서블릿 요청
		        // Redirect 방식으로 포워딩
				// => URL 뒤에 파라미터로 페이지번호(page)를 전달
				forward = new ActionForward();
				forward.setPath(
						"BoardList.bo?page=" + request.getParameter("page"));
				forward.setRedirect(true);
			}
		}
		
		
		return forward;
	}

}













