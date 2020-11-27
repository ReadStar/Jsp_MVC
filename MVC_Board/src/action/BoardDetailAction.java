package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

//게시물 목록에서 클릭된 게시물에 대한 상세 내용을 가져오기 위한 클래스
//=>1개 게시물 상세 내용을 가져와서 qna_board_view.jsp 페이지로 전달
public class BoardDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardDetailAction");
		
		ActionForward forward = null;
		
		//파라미터로 전달 받은 게시물 번호(board_num) 가져오기
		int board_num = Integer.parseInt(request.getParameter("board_num"));
//		System.out.println("게시물 번호 : " + board_num);
		
		//BoardDetailService 클래스의 인스턴스 생성 후
		//getArticle() 메서드를 호출하여 게시물 번호에 해당하는 글내용 가져오기
		//=>파라미터 : 글번호(board_num), 리턴타입 : 게시물 1개 정보 BoardBean
		BoardDetailService boardDetailService = new BoardDetailService();
		BoardBean article = boardDetailService.getArticle(board_num);
		
		//글 내용이 저장된 BoardBean 객체를 request 객체에 저장
		request.setAttribute("article", article);
		//board 폴더 내의 qna_board_view.jsp페이지로 포워딩
		//=>request 객체를 유지하고, 서블릿 주소가 유지되어야 하므로
		//	   Dispatcher 방식으로 포워딩
		forward = new ActionForward();
		forward.setPath("/board/qna_board_view.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
