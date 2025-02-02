package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.BoardListAction;
import action.BoardWriteProAction;
import vo.ActionForward;

@WebServlet("*.bo")
public class BoardFrontController extends HttpServlet{
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		System.out.println("BoardFrontController");
		//Post방식을 위한 한글 처리
		request.setCharacterEncoding("UTF-8");
		//요청된 서블릿 주소를 추출
		String command = request.getServletPath();
		System.out.println("command : "+command);
		
		if(command.equals("/BoardWriteForm.bo")) {
//			System.out.println("BoardWriteForm.bo 로 이동");
			//http://localhost:8080/MVC_Board/board/board_write_form.jsp
			//위의 URL로 포워딩 할 때 해당 주소를 숨기기 위해
			//요청 서블릿 주소를 그대로 유지하려면 Dispatcher 방식으로 포워딩
			//request 객체의 getRequestDispatcher() 메서드를 호출하여
			//RequestDispatcher객체를 리턴 받기
			//=>포워딩할 주소를 파라미터로 전달
			//		(현재 위치의 board폴더 내의 "board_write_form.jsp"
			String path="/board/qna_board_write.jsp";//포워딩 할 주소
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		} else if(command.equals("/BoardList.bo")) {
			//BoardListAction 클래스 인스턴스 생성
			BoardListAction action = new BoardListAction();
			//Action 클래스의 execute() 메서드를 호출하여 글목록 가져오기 수행
			ActionForward forward = action.execute(request, response);
			//ActionFroward 객체의 isRedirect()가 false이면
			//Dispatcher 방식으로 포워딩-
			if(!forward.isRedirect()) {
				//request 객체의 getRequestDispatcher() 메서드를 호출하여
				//RequestDispatcher 객체 가져오기(파라미터 :포워딩 경로)
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
//			String path="/board/qna_board_list.jsp";
//			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
//			dispatcher.forward(request, response);
		} else if(command.equals("/BoardWritePro.bo")) {
			//BoardWriteProAction 클래스 인스턴스 생성
			BoardWriteProAction action = new BoardWriteProAction();
			//Action 클래스의 excute() 메서드를 호출하여 글쓰기 작업 수행
			ActionForward forward = action.excute(request, response);
			//ActionForward 객체의 isRedirect()가 true 이면 Redirect방식 포워딩
			if(forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			}
		}
	}
	
	//서블릿 요청이 Get 방식 일 경우 자동으로 호출되는 메서드
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	//서블릿 요청이 POST 방식일 경우 자동으로 호출되는 메서드
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
}
