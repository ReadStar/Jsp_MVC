

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class loginServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("loginservlet-doGet");
		
		//id password 파라미터 값 가져와서 출력
		String id=request.getParameter("id");
		String pass=request.getParameter("pass");
		System.out.println(id);
		System.out.println(pass);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("loginservlet-doPost");
		//post방식에서의 한글 처리
		request.setCharacterEncoding("UTF-8");
		String id=request.getParameter("id");
		String pass=request.getParameter("pass");
		System.out.println(id);
		System.out.println(pass);
	}

}
