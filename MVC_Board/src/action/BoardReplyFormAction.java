package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardReplyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//답변글 작성을 위한 원본 게시물 정보 가져오기
		//=>BoardModifyFormAction과 거의 동일함
		
		System.out.println("BoardReplyFormAction");
		ActionForward forward = null;
		
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		BoardDetailService boardDetailService = new BoardDetailService();
		BoardBean article = boardDetailService.getArticle(board_num);
		
		request.setAttribute("article", article);
		
		forward = new ActionForward();
		forward.setPath("/board/qna_board_reply.jsp");
		
		
		return forward;
	}

}
