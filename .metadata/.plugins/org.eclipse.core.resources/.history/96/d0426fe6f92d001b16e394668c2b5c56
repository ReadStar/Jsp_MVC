package svc;

import java.sql.Connection;

import dao.MemberDAO;
import vo.MemberBean;

import static db.JdbcUtil.*;

public class MemberJoinProService {

	public boolean registMember(MemberBean member) {
		boolean isJoinSuccess = false;
		
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
		
		// MemberDAO 클래스의 insertMember() 메서드를 호출하여 
		// 회원 가입 작업 수행
		// => 파라미터 : MemberBean, 리턴타입 : int(insertCount)
		int insertCount = memberDAO.insertMember(member);
		
		// insertCount 가 0 보다 클 경우 commit, isJoinSuccess 를 true 로 변경
		// 아니면, rollback 수행
		if(insertCount > 0) {
			commit(con);
			isJoinSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isJoinSuccess;
	}

}
