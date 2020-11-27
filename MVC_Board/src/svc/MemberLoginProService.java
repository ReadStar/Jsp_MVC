package svc;

import java.sql.Connection;

import dao.MemberDAO;
import exception.LoginException;

import static db.JdbcUtil.*;

public class MemberLoginProService {

	public boolean isLoginMember(String id, String passwd) throws LoginException {
		boolean isMember = false;
		
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
		
		// MemberDAO 클래스의 selectLoginMember() 메서드를 호출하여
		// 로그인 작업 수행
		// => 파라미터 : 아이디, 패스워드   리턴타입 : boolean(isMember)
		// => 로그인 실패 시 LoginException 예외가 위임되므로
		//    외부(MemberLoginProAction)로 다시 위임(throws)
		isMember = memberDAO.selectLoginMember(id, passwd);
		
		close(con);
		
		return isMember;
	}

}
