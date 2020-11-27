package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardModifyProService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardModifyProAction");
		
		ActionForward forward = null;
		
		//게시물 수정에 필요한 글번호(board_num) 가져오기
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		//BoardModifyProService 클래스 인스턴스 생성 후
		//isArticleWriter() 메서드를 호출하여 적합한 사용자인지 판별
		//=>적합한 사용자 : 패스워드가 DB에 저장된 패스워드와 일치하는 경우
		//=>파라미터 : 글번호(board_num), 패스워드(board_pass)
		//	   리턴타입 : boolean(isRightUser)
		BoardModifyProService boardModifyProService = new BoardModifyProService();
		boolean isRightUser = boardModifyProService.isArticleWriter(board_num, request.getParameter("board_pass"));
		//임시확인용 isRightUser 출력
		System.out.println("isRightUser");
		
		//적합한 사용자 판별에 따른 처리
		//패스워드가 틀릴 경우 (=적합한 사용자가 아닐 경우)
		//=>자바스크립트를 사용하여 "수정 권한이 없습니다" 출력 후 이전 페이지 이동
		if(!isRightUser) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('수정 권한이 없습니다')");
			out.println("history.back()");
			out.println("</script>");
		}else {
			//패스워드가 일치할 경우(=적합한 사용자일 경우)
			//BoardBean 객체 생성하여 전달받은 글 수정 내용을 저장
			//=>글번호, 작성자, 제목, 내용
			BoardBean article = new BoardBean();
			article.setBoard_num(board_num);
			article.setBoard_name(request.getParameter("board_name"));
			article.setBoard_subject(request.getParameter("board_subject"));
			article.setBoard_content(request.getParameter("board_content"));
			
			//BoardModifyProService 클래스의 modifyArticle() 메서드를 호출하여
			//글 수정 작업 요청
			//=>파라미터 : BoardBean, 리턴타입 : int(updateCount)
			boolean isModifySuccess = 
					boardModifyProService.modifyArticle(article);
			
			//수정 결과에 따른 처리
			//수정 실패(updateCount가 0)일 경우 자바스크립트를 사용하여
			//"글 수정 실패" 출력 후 이전페이지 이동
			//=>아니면 ActionForward 객체 생성 후 BoardDetail.bo로 포워딩
			//	   새 요청이 발생하므로 Redirect방식으로 이동
			if(!isModifySuccess) {
				response.setContentType("text/html : charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('글 수정 실패')");
				out.println("history.back()");
				out.println("</script>");
			}else {
				forward = new ActionForward();
				forward.setPath("BoardDetail.bo?board_num=" + board_num + "&page=" + request.getParameter("page"));
				forward.setRedirect(true);
			}
			
			
		}
		
		return forward;
	}

}
