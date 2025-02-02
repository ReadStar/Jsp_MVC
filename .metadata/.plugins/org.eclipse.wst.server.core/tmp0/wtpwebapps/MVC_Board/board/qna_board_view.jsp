<%@page import="vo.BoardBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// BoardBean 객체 파라미터 가져오기
	BoardBean article = (BoardBean)request.getAttribute("article");

	// page 파라미터 가져오기
	// 1. BoardDetailAction 에서 request.setAttribute() 메서드로 저장했을 경우
// 	String nowPage = (String)request.getAttribute("page");

	// 2. 서블릿 주소로 전달된 page 값을 파라미터 그대로 사용할 경우
	String nowPage = request.getParameter("page");
%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC 게시판</title>
<style type="text/css">
	#articleForm {
		width: 500px;
		height: 500px;
		border: 1px solid black;
		margin: auto;
	}
	
	h2 {
		text-align: center;
	}
	
	#basicInfoArea {
		height: 40px;
		text-align: center;
	}
	
	#articleContentArea {
		background: orange;
		margin-top: 20px;
		height: 350px;
		text-align: center;
		overflow: auto;
		white-space: pre-line;
	}
	
	#commandList {
		margin: auto;
		width: 500px;
		text-align: center;
	}
</style>
</head>
<body>
	<!-- 게시판 상세내용 보기 -->
	<section id="articleForm">
		<h2>글 상세내용 보기</h2>
		<section id="basicInfoArea">
			제 목 : <%=article.getBoard_subject() %><br>
			첨부파일 : 
			<%
				if(article.getBoard_file() != null) {
					%> <a href="board/file_down.jsp?downFile=<%=article.getBoard_file()%>"><%=article.getBoard_file() %></a> <%
				}
			%>
		</section>
		<section id="articleContentArea">
			<%= article.getBoard_content() %>
		</section>
	</section>
	<section id="commandList">
<%-- 		<a href="BoardReplyForm.bo?board_num=<%=article.getBoard_num()%>&page=<%=nowPage%>">[답변]</a> --%>
<%-- 		<a href="BoardModifyForm.bo?board_num=<%=article.getBoard_num()%>">[수정]</a> --%>
<%-- 		<a href="BoardDeleteForm.bo?board_num=<%=article.getBoard_num()%>&page=<%=nowPage%>">[삭제]</a> --%>
<%-- 		<a href="BoardList.bo?page=<%=nowPage%>">[목록]</a> --%>
		<input type="button" value="답변" onclick="location.href='BoardReplyForm.bo?board_num=<%=article.getBoard_num()%>&page=<%=nowPage%>'">
		<input type="button" value="수정" onclick="location.href='BoardModifyForm.bo?board_num=<%=article.getBoard_num()%>&page=<%=nowPage%>'">
		<input type="button" value="삭제" onclick="location.href='BoardDeleteForm.bo?board_num=<%=article.getBoard_num()%>&page=<%=nowPage%>'">
		<input type="button" value="목록" onclick="location.href='BoardList.bo?page=<%=nowPage%>'">
		&nbsp;&nbsp;
	</section>
</body>
</html>














