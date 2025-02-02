

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.me")
public class TestFrontController extends HttpServlet {
	//서블릿 클래스의 doGet(), doPost() 메서드는 각 요청에 따라 자동으로 호출됨
	//=>이 때, 동일한 동작을 처리하더라도 호출되는 메서드가 다르면
	//		작성해야하는 코드가 중복될 수 있다
	//		따라서, 두 요청 방식에 대한 공통 작업을 수행할 별도의 메서드를 정의하고
	//		doGet(), doPost() 메서드에서 해당 메서드를 호출하여 중복 제거 가능
	
	
	//GET 방식 요청을 처리하기 위해 자동으로 호출되는 메서드
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	//POST 방식 요청을 처리하기 위해 자동으로 호출되는 메서드
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	//GET 방식과 POST 방식 요청을 모두 처리하기 위한 doProcess()메서드 정의
	//=>doGet()메서드와 doPost()메서드에서 doProcess()메서드를 호출하고
	//		메서드 파라미터로 request,  response객체를 전달해야함
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//1.getRequestURI
		//서블릿으로 요청된 URL(xxxx.me) 주소를 판별하기 위한 작업
		//=>전체 URL중에서 프로토콜 ://주소:포트번호 부분을 제외한 나머지 추출
		String requestURI = request.getRequestURI();
		System.out.println(requestURI);
		//2.request 객체의 getContextPath() 메서드를 호출하여
		//	  전체 URL중에서 프로젝트 경로 추출
		String contextPath = request.getContextPath();
		System.out.println(contextPath);
		//3. URL 중에서 서블릿 주소 부분만 추출하기 위해
		//	   RequestURI 와 ContextPath를 사용하여 문자열 조작 수행
		//		=>실제 주소 매핑에 사용할 서블릿 주소 추출과정
		//		=>RequestURI 추출 결과에서 ContextPath 문자열 길이를
		//			시작 인덱스로 사용하여 문자열 추출(substring())
//		String command = requestURI.substring(contextPath.length());
//		System.out.println(command);
		//위의 1~3번 과정을 하나의 메서드로 제공 : getServletPath()
		String command = request.getServletPath();
		System.out.println(command);
		
//		String id=request.getParameter("id");
//		String pass=request.getParameter("password");
//		System.out.println(id);
//		System.out.println(pass);
//		if(command.contentEquals("/LoginForm.me")) {
//			           
//		}else if(command.equals("/LoginPro.me")) {
//			
//		}
		//Dispatcher 방식과 Redirect 방식 차이
		if(command.equals("/DispatcherTest.me")) {
			//Request 객체의 getRequestDispathcer() 메서드를 호출하여
			//RequestDispatcher 객체 리턴받기
			//=>파라미터 : 포워딩 할 주소 문자열로
			String path="dispatcher_result.jsp";//포워딩 할 주소
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			//RequestDispatcher 객체의 forward() 메서드를 호출항u 포워딩
			//=>파라미터 : request 객체와 response 객체
			dispatcher.forward(request, response);
		}else if(command.contentEquals("/RedirectTest.me")) {
			//response 객체이ㅡ sendRedirect()메서드를 호출하여
			//지정된 주소로 포워딩 => 파라미터 : 포워딩 할 주소
			String path="redirect_result.jsp"; //포워딩 할 주소
			response.sendRedirect(path);
		}
	}
}
