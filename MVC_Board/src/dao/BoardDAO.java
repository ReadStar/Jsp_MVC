package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.BoardBean;

//import db.JdbcUtil;
import static db.JdbcUtil.*;


public class BoardDAO {
	/*
	 * ========================================================================
	 * 싱글톤 디자인 패턴을 활용한 BoardDAO 인스턴스 작업
	 * 1. 외부에서 인스턴스 생성이 불가능하도록 생성자 호출을 막기 위해
	 *    private 접근제한자를 적용하여 생성자 정의
	 * 2. 직접 DAO 클래스의 인스턴스를 생성하여 멤버변수(instance)로 저장 
	 *    => 접근제한자 private 로 설정하여 외부에서 접근 불가능하도록 지정
	 *    => 생성자를 리턴하는 static 메서드 getInstance() 에서
	 *       멤버변수에 접근할 수 있도록 static 멤버변수로 선언
	 * 3. 생성된 인스턴스를 외부로 리턴하기 위해 Getter 메서드(getInstance) 정의
	 *    => 파라미터 : 없음, 리턴타입 : BoardDAO
	 *    => 외부에서 인스턴스 생성 없이도 호출 가능하도록 static 메서드로 정의    
	 */
	private BoardDAO() {}
	
	private static BoardDAO instance = new BoardDAO();

	public static BoardDAO getInstance() {
		return instance;
	}
	// ========================================================================

	Connection con; // Connection 객체를 전달받아 저장할 멤버변수

	// 외부(Service 클래스)로부터 Connection 객체를 전달받아
	// 멤버변수에 저장하는 setConnection() 메서드 정의
	public void setConnection(Connection con) {
		this.con = con;
	}

	// 글 등록 작업
	public int insertArticle(BoardBean boardBean) {
		// Service 클래스로부터 BoardBean 객체를 전달받아
		// DB의 board 테이블에 INSERT 작업 수행하고 결과(int타입)를 리턴
		System.out.println("BoardDAO - insertArticle()");
		int insertCount = 0; // INSERT 작업 수행 결과를 저장할 변수
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int num = 1; // 새 글 번호를 저장할 변수
		
		try {
			// 현재 게시물 번호(board_num) 중 가장 큰 번호를 조회하여
			// 해당 번호 + 1 값을 새 글 번호(num)으로 저장
			String sql = "SELECT MAX(board_num) FROM board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			// 조회된 결과가 있을 경우 조회된 번호 + 1 값을 num 에 저장
			// => 조회 결과가 없을 경우 새 글 번호는 1번이므로 기본값 그대로 사용
			if(rs.next()) {
				num = rs.getInt(1) + 1; // 새 글 번호 만들기
			} 
			
			// 전달받은 BoardBean 객체 내의 데이터를 사용하여 INSERT 작업 수행
			// => 컬럼 중 board_date 항목(작성일)은 now() 함수 사용
			sql = "INSERT INTO board VALUES (?,?,?,?,?,?,?,?,?,?,now())";
			pstmt = con.prepareStatement(sql);
			// BoardBean 객체로부터 데이터를 꺼내서 쿼리문 ? 대체
			pstmt.setInt(1, num); // 글번호
			pstmt.setString(2, boardBean.getBoard_name());
			pstmt.setString(3, boardBean.getBoard_pass());
			pstmt.setString(4, boardBean.getBoard_subject());
			pstmt.setString(5, boardBean.getBoard_content());
			pstmt.setString(6, boardBean.getBoard_file());
			pstmt.setInt(7, num); // 참조글 번호(새 글이므로 자신이 참조글이 됨)
			pstmt.setInt(8, boardBean.getBoard_re_lev());
			pstmt.setInt(9, boardBean.getBoard_re_seq());
			pstmt.setInt(10, boardBean.getBoard_readcount());
			
			// INSERT 구문 실행 결과값을 int형 변수 insertCount 에 저장
			insertCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("insertArticle() 오류! - " + e.getMessage());
			e.printStackTrace();
		} finally {
			// 자원 반환
			// 주의! DAO 클래스 내에서 Connection 객체 반환 금지!
			close(rs);
			close(pstmt);
		}
		
		return insertCount;
	}

	// 전체 게시물 수 조회
	public int selectListCount() {
		int listCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// SELECT 구문을 사용하여 전체 게시물 수 조회
			// => count() 함수 사용, 조회 대상 컬럼 1개 지정하거나 * 사용
			String sql = "SELECT COUNT(board_num) FROM board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			// 조회 결과가 있을 경우(= 게시물이 하나라도 존재하는 경우)
			// => 게시물 수를 listCount 에 저장
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("selectListCount() 오류! - " + e.getMessage());
			e.printStackTrace();
		} finally {
			// 자원 반환
			// 주의! DAO 클래스 내에서 Connection 객체 반환 금지!
			close(rs);
			close(pstmt);
		}
		
		return listCount;
	}

	// 게시물 목록 조회
	public ArrayList<BoardBean> selectArticleList(int page, int limit) {
		// 지정된 갯수만큼의 게시물 조회 후 ArrayList 객체에 저장한 뒤 리턴
		ArrayList<BoardBean> articleList = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 조회를 시작할 레코드(행) 번호 계산
		int startRow = (page - 1) * limit;
		
		try {
			// 게시물 조회
			// 참조글번호(board_re_ref) 번호를 기준으로 내림차순 정렬,
			// 순서번호(board_re_seq) 번호를 기준으로 오름차순 정렬
			// 조회 시작 게시물 번호(startRow)부터 limit 갯수만큼 조회
			String sql = "SELECT * FROM board "
					+ "ORDER BY board_re_ref DESC,board_re_seq ASC "
					+ "LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			// ArrayList 객체 생성(while문 위에서 생성 필수!)
			articleList = new ArrayList<BoardBean>();
			
			// 읽어올 게시물이 존재할 경우 다음 작업 반복
			// => BoardBean 객체를 생성하여 레코드 데이터 모두 저장 후
			//    BoardBean 객체를 다시 ArrayList 객체에 추가
			// => 단, 패스워드(board_pass) 는 제외
			while(rs.next()) {
				// 1개 게시물 정보를 저장할 BoardBean 객체 생성 및 데이터 저장
				BoardBean article = new BoardBean();
				article.setBoard_num(rs.getInt("board_num"));
				article.setBoard_name(rs.getString("board_name"));
				article.setBoard_subject(rs.getString("board_subject"));
				article.setBoard_content(rs.getString("board_content"));
				article.setBoard_file(rs.getString("board_file"));
				article.setBoard_re_ref(rs.getInt("board_re_ref"));
				article.setBoard_re_lev(rs.getInt("board_re_lev"));
				article.setBoard_re_seq(rs.getInt("board_re_seq"));
				article.setBoard_readcount(rs.getInt("board_readcount"));
				article.setBoard_date(rs.getDate("board_date"));
				
				// 레코드 저장 확인용 코드
//				System.out.println("제목 : " + article.getBoard_subject());
				
				// 1개 게시물을 전체 게시물 저장 객체(ArrayList)에 추가
				articleList.add(article);
			}
			
		} catch (SQLException e) {
			System.out.println("selectArticleList() 오류! - " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return articleList;
	}

	// 게시물 상세 내용 조회
	public BoardBean selectArticle(int board_num) {
		// 글번호(board_num)에 해당하는 레코드를 SELECT
		// 조회 결과가 있을 경우 BoardBean 객체에 저장한 뒤 리턴
		BoardBean article = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM board WHERE board_num=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();
			
			// 게시물이 존재할 경우 BoardBean 객체를 생성하여 게시물 내용 저장
			if(rs.next()) {
				article = new BoardBean();
				article.setBoard_num(rs.getInt("board_num"));
				article.setBoard_name(rs.getString("board_name"));
				article.setBoard_subject(rs.getString("board_subject"));
				article.setBoard_content(rs.getString("board_content"));
				article.setBoard_file(rs.getString("board_file"));
				article.setBoard_re_ref(rs.getInt("board_re_ref"));
				article.setBoard_re_lev(rs.getInt("board_re_lev"));
				article.setBoard_re_seq(rs.getInt("board_re_seq"));
				article.setBoard_readcount(rs.getInt("board_readcount"));
				article.setBoard_date(rs.getDate("board_date"));
				
				// 임시 확인용 상세 내용 출력
//				System.out.println("글 제목 : " + article.getBoard_subject());
			}
				
			
		} catch (SQLException e) {
			System.out.println("selectArticle() 오류! - " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		
		return article;
	}

	// 조회수 증가
	public int updateReadcount(int board_num) {
		// 글번호(board_num)에 해당하는 게시물의 조회수(readcount)를 1 증가
		int updateCount = 0;
		
		PreparedStatement pstmt = null;
		
		try {
			String sql = "UPDATE board SET board_readcount=board_readcount+1 "
					+ "WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			updateCount = pstmt.executeUpdate();
			
			// 임시 확인용
//			System.out.println("조회수 증가 결과 : " + updateCount);
		} catch (SQLException e) {
			System.out.println("updateReadcount() 오류! - " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}

	// 글 작성자 본인 확인
	public boolean isArticleBoardWriter(int board_num, String board_pass) {
		boolean isArticleWriter = false;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// board_num 에 해당하는 레코드의 board_pass 를 가져와서
			// 파라미터로 전달받은 board_pass 와 비교하여 일치 여부 판별
			// => 만약, 패스워드 일치하는 경우 isArticleWriter 를 true 로 변경
			String sql = "SELECT board_pass FROM board WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(board_pass.equals(rs.getString("board_pass"))) {
					isArticleWriter = true;
				}
			}
			
		} catch (SQLException e) {
			System.out.println("isArticleBoardWriter() 오류! - " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return isArticleWriter;
	}

	// 글 수정
	public int updateArticle(BoardBean article) {
		// BoardBean 객체에 저장된 수정 내용(작성자, 제목, 내용)을 사용하여
		// 글번호(board_num)에 해당하는 레코드를 수정 후 결과 리턴
		int updateCount = 0;
		
		PreparedStatement pstmt = null;
		
		try {
			String sql = "UPDATE board "
					+ "SET board_name=?,board_subject=?,board_content=? "
					+ "WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, article.getBoard_name());
			pstmt.setString(2, article.getBoard_subject());
			pstmt.setString(3, article.getBoard_content());
			pstmt.setInt(4, article.getBoard_num());
			updateCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("updateArticle() 오류! - " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}

	// 답글 등록
	public int insertReplyArticle(BoardBean article) {
		int insertCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 현재 게시물 목록의 최대 글번호(board_num)를 조회하여
			// 조회된 번호 + 1 값을 새 글 번호(num)에 저장
			int num = 1; // 답글의 새 글 번호
			
			String sql = "SELECT MAX(board_num) FROM board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // 등록된 게시물이 하나라도 존재할 경우
				num = rs.getInt(1) + 1; // 새 글 번호 = 현재 가장 큰 번호 + 1
			}
			
//			System.out.println("답글 번호 : " + num);
			
			/*
			 * 답변글 작성 시 글목록에서 표시되는 정렬 방법
			 * SELECT * FROM board ORDER BY board_re_ref DESC,board_re_seq ASC
			 * => board_re_ref 기준 내림차순, board_re_seq 기준 오름차순 정렬
			 * 
			 *----------------------------------------------------------------
			 *  원본번호   새번호  참조글번호      순서번호       들여쓰기   | 
			 *| board_num | num | board_re_ref | board_re_seq | board_re_lev |
			 *----------------------------------------------------------------
			 *|   없음       3         3               0              0  
			 *|   없음       2         2               0              0 
			 *|   없음       1         1               0              0
			 *----------------------------------------------------------------
			 *>>> 3번 게시물에 대한 답글 작성 시(답글 번호를 4번이라고 가정) <<<
			 *    => 원본글 3번의 참조글번호를 답글의 참조글번호로 설정
			 *    => 원본글 참조글번호와 같고, 순서번호가 더 큰 게시물이 없으므로
			 *       답글의 순서번호는 원본글의 순서번호 + 1 로 설정(1로 설정)
			 *    => 들여쓰기 레벨도 원본글 들여쓰기레벨 + 1 로 설정(1로 설정)
			 *----------------------------------------------------------------
			 *  원본번호   새번호  참조글번호      순서번호       들여쓰기   | 
			 *| board_num | num | board_re_ref | board_re_seq | board_re_lev |
			 *----------------------------------------------------------------
			 *|   없음       3         3               0              0  
			 *|     3        4         3               1              1
			 *|   없음       2         2               0              0 
			 *|   없음       1         1               0              0
			 *---------------------------------------------------------------- 
			 * 
			 *>>> 3번 게시물에 대한 답글 작성 시(답글 번호를 5번이라고 가정) <<<
			 *    => 원본글 3번의 참조글번호를 답글의 참조글번호로 설정
			 *    => 원본글 참조글번호와 같고, 순서번호가 더 큰 게시물이 4번 글이므로
			 *       4번 글의 순서번호를 + 1 시킴(1 -> 2로 변경됨)
			 *       새 답글의 순서번호는 원본글의 순서번호 + 1 로 설정(1로 설정)
			 *    => 들여쓰기 레벨도 원본글 들여쓰기레벨 + 1 로 설정(1로 설정)
			 *----------------------------------------------------------------
			 *  원본번호   새번호  참조글번호      순서번호       들여쓰기   | 
			 *| board_num | num | board_re_ref | board_re_seq | board_re_lev |
			 *----------------------------------------------------------------
			 *|   없음       3         3               0              0  
			 *      3        5         3               1              1  
			 *|     3        4         3               2              1
			 *|   없음       2         2               0              0 
			 *|   없음       1         1               0              0
			 *---------------------------------------------------------------- 
			 *
			 *>>> 3번 게시물에 대한 답글 작성 시(답글 번호를 6번이라고 가정) <<<
			 *    => 원본글 3번의 참조글번호를 답글의 참조글번호로 설정
			 *    => 원본글 참조글번호와 같고, 순서번호가 더 큰 게시물이 4,5번 글이므로
			 *       4번, 5번 글의 순서번호를 + 1 시킴(1 -> 2, 2 -> 3 으로 변경됨)
			 *       새 답글의 순서번호는 원본글의 순서번호 + 1 로 설정(1로 설정)
			 *    => 들여쓰기 레벨도 원본글 들여쓰기레벨 + 1 로 설정(1로 설정)
			 *----------------------------------------------------------------
			 *  원본번호   새번호  참조글번호      순서번호       들여쓰기   | 
			 *| board_num | num | board_re_ref | board_re_seq | board_re_lev |
			 *----------------------------------------------------------------
			 *|   없음       3         3               0              0  
			 *      3        6         3               1              1
			 *      3        5         3               2              1  
			 *|     3        4         3               3              1
			 *|   없음       2         2               0              0 
			 *|   없음       1         1               0              0
			 *---------------------------------------------------------------- 
			 *>>> 3번에 대한 답글인 5번에 대한 답글을 작성할 경우(7번이라고 가정)
			 *=> 원본글 5번의 참조글번호(3)를 자신의 참조글번호로 사용
			 *=> 원본글 5번과 동일한 참조글번호 중 순서번호가 큰 4번글 순서번호 + 1
			 *   (3 -> 4)
			 *=> 새 답글의 순서번호는 원본글 5번 순서번호(2) + 1 로 설정
			 *=> 새 답글의 들여쓰기레벨은 원본들 5번 들여쓰기레벨(1) + 1 로 설정
			 *----------------------------------------------------------------
			 *  원본번호   새번호  참조글번호      순서번호       들여쓰기   | 
			 *| board_num | num | board_re_ref | board_re_seq | board_re_lev |
			 *----------------------------------------------------------------
			 *|   없음    |  3  |            3 |            0 |            0 |
			 *|     3     |  6  |            3 |            1 |            1 |
			 *|     3     |  5  |            3 |            2 |            1 |
			 *      5        7               3              3              2
			 *|   없음    |  4  |            3 |            4 |            1 |
			 *|   없음    |  2  |            2 |            0 |            0 |
			 *|   없음    |  1  |            1 |            0 |            0 |
			 *----------------------------------------------------------------
			 */

			int board_re_ref = article.getBoard_re_ref(); // 기존글 참조번호
			int board_re_lev = article.getBoard_re_lev(); // 기존글 들여쓰기 값
			int board_re_seq = article.getBoard_re_seq(); // 기존글 순서번호
			
			// 기존 답글들에 대한 순서 번호 증가
			// => board_re_ref 가 전달받은 값과 같고
			//    board_re_seq 가 전달받은 값보다 큰 게시물들의 
			//    board_re_seq 번호를 1씩 증가시키기
			sql = "UPDATE board SET board_re_seq=board_re_seq+1 "
					+ "WHERE board_re_ref=? AND board_re_seq>?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_re_ref);
			pstmt.setInt(2, board_re_seq);
			pstmt.executeUpdate();
			
			// 새 답글에 대한 순서 번호와 들여쓰기 레벨 설정
			// => 순서번호와 들여쓰기레벨은 기존 글 순서번호, 들여쓰기레벨 + 1
			board_re_lev += 1;
			board_re_seq += 1;
			
			// 답변글에 대한 INSERT 작업 수행
			sql = "INSERT INTO board VALUES(?,?,?,?,?,?,?,?,?,?,now())";
			System.out.println(article.getBoard_subject());
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, article.getBoard_name());
			pstmt.setString(3, article.getBoard_pass());
			pstmt.setString(4, article.getBoard_subject());
			pstmt.setString(5, article.getBoard_content());
			pstmt.setString(6, ""); // 파일업로드 생략
			pstmt.setInt(7, board_re_ref);
			pstmt.setInt(8, board_re_lev);
			pstmt.setInt(9, board_re_seq);
			pstmt.setInt(10, 0);
			insertCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("insertReplyArticle() 오류! - " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return insertCount;
	}
	//글 삭제
	public int deleteArticle(int board_num) {
		int deleteCount = 0;
		PreparedStatement pstmt = null;
		
		try {
		//글번호(board_num)dㅔ 해당하는 레코드 삭제
		String sql = "DELETE FROM board WHERE board_num=?";
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, board_num);
		deleteCount = pstmt.executeUpdate();
		}catch (SQLException e) {
			System.out.println("deleteArticle() 오류! - " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return deleteCount;
	}
	
	
	
}











