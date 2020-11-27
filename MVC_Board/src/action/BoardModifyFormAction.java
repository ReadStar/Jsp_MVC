package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//특정 게시물 수정을 위해 기존 게시물 정보를 가져오는 Action클래스
		System.out.println("BoardModifyFormAction");
		
		ActionForward forward = null;
		//원본 게시글 정보 요청에 필요한 글번호(board_num) 파라미터 가져오기
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		//원본 게시물 정보 요청을 위해 새로운 Service 클래스를 정의하지 않고
		//기존의 BoardDetailService 클래스의 getArticle() 메서드를 호출하여
		//원본 게시물 정보 요청을 수행
		//=>파라미터 : 글번호(board_num), 리턴타입 : BoardBean(article)
		BoardDetailService boardDetailService = new BoardDetailService();
		BoardBean article = boardDetailService.getArticle(board_num);
		
		//request 객체에 원본 게시물 정보(BoardBean) 저장
		request.setAttribute("article", article);
		//ActionForward 객체를 생성하여 qna_board_modify.jsp 페이지로 포워딩
		//=>Dispatcher 방식
		forward = new ActionForward();
		forward.setPath("/board/qna_board_modify.jsp");
		
		return forward;
	}

}
