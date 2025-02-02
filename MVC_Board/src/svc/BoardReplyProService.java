package svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardBean;

public class BoardReplyProService {

	public boolean replyArticle(BoardBean article) {
		boolean isReplySuccess = false;
		
		// 1(공통). Connection 객체 가져오기
		Connection con = getConnection();
		// 2(공통). BoardDAO 객체 가져오기
		BoardDAO boardDAO = BoardDAO.getInstance();
		// 3(공통). BoardDAO 객체에 Connection 객체 전달
		boardDAO.setConnection(con);
		// 4. BoardDAO 클래스의 insertReplyArticle() 메서드를 호출하여
		//    적합한 사용자인지 판별
		//    => 파라미터 : BoardBean, 리턴타입 : int(insertCount)
		int insertCount = boardDAO.insertReplyArticle(article);
		// 5. 답글 등록 결과에 대한 판별 작업 수행
		//=>insertCount 가 0보다 크면 commit 수행, isReplySuccess를 true 변경
		//=>아니면 rollback 수행
		if(insertCount > 0) {
			commit(con);
			isReplySuccess = true;
		}else {
			rollback(con);
		}
		//6.(공통) Connection 객체 반환하기
		close(con);
		// 7. 결과 리턴
		return isReplySuccess;
	}

}
