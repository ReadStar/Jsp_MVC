package svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.BoardDAO;

public class BoardDeleteProService {
	
	public boolean isArticleWriter(int board_num, String board_pass) {
		// 글 삭제를 위한 패스워드 확인 작업 요청 수행
		boolean isArticleWriter = false;
		
		// 1(공통). Connection 객체 가져오기
		Connection con = getConnection();
		
		// 2(공통). BoardDAO 객체 가져오기
		BoardDAO boardDAO = BoardDAO.getInstance();
		
		// 3(공통). BoardDAO 객체에 Connection 객체 전달
		boardDAO.setConnection(con);
		
		// 4. BoardDAO 클래스의 isArticleBoardWriter() 메서드를 호출하여
		//    적합한 사용자인지 판별
		//    => 파라미터 : 글번호, 패스워드, 리턴타입 : boolean(isArticleWriter)
		isArticleWriter = boardDAO.isArticleBoardWriter(board_num, board_pass);
		
		// 5(공통). Connection 객체 반환하기
		close(con);
		
		// 6. 결과 리턴
		return isArticleWriter;
	}
	
	public boolean removeArticle(int board_num) {
		// 글 수정 작업 요청 수행
		boolean isDeleteSuccess = false;
		
		// 1(공통). Connection 객체 가져오기
		Connection con = getConnection();
		
		// 2(공통). BoardDAO 객체 가져오기
		BoardDAO boardDAO = BoardDAO.getInstance();
		
		// 3(공통). BoardDAO 객체에 Connection 객체 전달
		boardDAO.setConnection(con);
		
		// 4. BoardDAO 클래스의 deleteArticle() 메서드를 호출하여 글 삭제
		//    => 파라미터 : 글번호(board_num), 리턴타입 : int(deleteCount)
		int deleteCount = boardDAO.deleteArticle(board_num);
		
		// 5. 글 삭제 결과에 대한 판별 작업 수행
		// => deleteCount 가 0보다 크면 commit 수행, isDeleteSuccess 를 true 변경
		// => 아니면 rollback 수행
		if(deleteCount > 0) {
			commit(con);
			isDeleteSuccess = true;
		} else {
			rollback(con);
		}
		
		// 6(공통). Connection 객체 반환하기
		close(con);
		
		// 7. 결과 리턴
		return isDeleteSuccess;
	}


}
