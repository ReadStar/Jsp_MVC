package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exception.LoginException;
import vo.MemberBean;

import static db.JdbcUtil.*;

public class MemberDAO {
	// ------------------- 싱글톤 패턴 -------------------
	private static MemberDAO instance = new MemberDAO();
	
	private MemberDAO() {}

	public static MemberDAO getInstance() {
		return instance;
	}
	// ---------------------------------------------------
	
	Connection con = null;

	public void setConnection(Connection con) {
		this.con = con;
	}

	// 회원 추가
	public int insertMember(MemberBean member) {
		int insertCount = 0;
		
		PreparedStatement pstmt = null;
		
		try {
			// 전달받은 회원가입정보(MemberBean)를 사용하여
			// member 테이블에 레코드 추가
			String sql = "INSERT INTO member VALUES (?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getId());
			pstmt.setString(3, member.getPasswd());
			pstmt.setInt(4, member.getAge());
			pstmt.setString(5, member.getEmail());
			pstmt.setString(6, member.getGender() + ""); // char -> String 변환 필요
			
			insertCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("insertMember() 오류! - " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return insertCount;
	}

	// 로그인
	// => LoginException 발생 시 외부로 위임하기(throws)
	public boolean selectLoginMember(String id, String passwd) 
					throws LoginException {
		boolean isMember = false;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 아이디에 해당하는 패스워드 검색
			// 1. 조회 성공 시 존재하는 아이디이므로 패스워드 판별 가능
			//    => 조회 결과에서 패스워드를 다시 판별
			//    1-2. 아이디 존재 시 패스워드 일치 여부 판별
			//         => 패스워드 일치할 경우 "로그인 성공" 출력
			//         => 패스워드 일치하지 않을 경우 "패스워드 틀림" 출력
			// 2. 조회 실패 시 존재하지 않는 아이디이므로 "아이디 없음" 출력
			String sql = "SELECT passwd FROM member WHERE id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // 조회 성공(아이디에 해당하는 패스워드 있을 경우)
				
				// 조회된 패스워드 일치 여부 판별
				if(passwd.equals(rs.getString("passwd"))) { // 패스워드 일치 시
					isMember = true;
//					System.out.println("로그인 성공!");
				} else { // 패스워드가 일치하지 않을 경우
//					System.out.println("패스워드 틀림!");
					// 강제로 LoginException 예외 발생시키기
					// => 발생한 예외를 외부로 던지기(위임)
					throw new LoginException("패스워드 틀림");
				}
				
			} else { // 조회 실패(아이디에 해당하는 패스워드 없을 경우)
//				System.out.println("아이디 없음!");
				// 강제로 LoginException 예외 발생시키기
				// => 발생한 예외를 외부로 던지기(위임)
				throw new LoginException("아이디 없음");
			}
			
		} catch (SQLException e) {
			System.out.println("selectLoginMember 오류! - " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return isMember;
		
	}
	
	
	
}














