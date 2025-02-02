package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.ActionForward;

public class BoardListAction {
	//글목록을 조회한 후 ActionForward 객체를 통해 
	//qna_board_list.jsp페이지로 포워딩
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//글목록 가져오기
		
		//ActionForward 객체를 생성하여 포워딩 정보 설정 후 리턴
		ActionForward forward = new ActionForward();
		//포워딩 경로 : board 폴더 내의 qna_board_list.jsp
		forward.setPath("/board/qna_board_list.jsp");
		//포워딩 방식 : 기존 서블릿 주소를 변경하지 않으므로 Dispathcer 방식
		forward.setRedirect(false);
		//ActionForward 객체 리턴
		return forward;
	}
}
