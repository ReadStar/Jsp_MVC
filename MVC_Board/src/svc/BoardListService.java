package svc;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import dao.BoardDAO;
import vo.BoardBean;

public class BoardListService {
	public int getListCount() throws Exception {
		//전체 게시물 수를 조회하여 리턴하는 작업을 요청하는 getListCount() 메서드
		System.out.println("BoardListService - getListCount");
		int listCount = 0;
		
		//1(공통) Connection객체 가져오기
		Connection con = getConnection();
		//2(공통) BoardDAO 객체 가져오기
		BoardDAO boardDAO = BoardDAO.getInstance();
		//3(공통) BoardDAO 객체에 Connection 전달
		boardDAO.setConnection(con);
		//4 BoardDAO객체의 selectListCount()메서드 호출하여 전체 게시물 수 가져오기
		listCount = boardDAO.selectListCount();
		//5(공통) Connection 객체 반환하기
		close(con);
		//6 전체 게시물 수 리턴
		return listCount;
	}
	public ArrayList<BoardBean> getArticleList(int page, int limit) throws Exception{
		ArrayList<BoardBean> articleList = null;
		System.out.println("BoardListService - getArticleList");
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		//BoardDAO 객체의 selectArticleList()메서드를 호출하여
		//게시물 목록 조회 결과는 ArrayList 객체로 리턴받기
		//=>파라미터 : page, limit
		articleList = boardDAO.selectArticleList(page,limit);
		close(con);
		return articleList;
	}
}
