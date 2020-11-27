<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
	// session 객체에 저장된 id 값 가져와서 변수에 저장
	String id = (String)session.getAttribute("id");
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC 게시판</title>
<style type="text/css">
#registForm{
width: 500px;
height: 610px;
border: 1px solid red;
margin: auto;
}

h2{
	text-align: center;
}

table{
	margin: auto;
	width: 450px;
}

.td_left{
	width: 150px;
	background: orange;
}

.td_right{
	width: 300px;
	background: skyblue;
}

#commandCell{
	text-align: center;
}

	#login {
		text-align: right;
	}
</style>

	<section id="login">
		<!-- 
			세션에서 가져온 id 값이 존재하지 않을 경우 
			로그인(MemberLoginForm.me), 회원가입(MemberJoinForm.me) 링크 
		-->
		<%
		if(id == null) { 
		%>
			<a href="MemberLoginForm.me">로그인</a> | <a href="MemberJoinForm.me">회원가입</a>
		<%} else { %>
		<!-- 세션에서 가져온 id 값이 존재할 경우 아이디 표시 및 로그아웃(MemberLogout.me) 링크 -->
			<%=id %>님 | <a href="MemberLogout.me">로그아웃</a>
		<%} %>
	</section>
</head>
<body>
	<!-- 게시판 등록-->
	<section id="writeForm">
		<h2>게시판글 등록</h2>
		<form action="BoardWritePro.bo" method="post"
			enctype="multipart/form-data" name="boardform">
			<table>
				<tr>
					<td class="td_left"><label for="board_name">글쓴이</label></td>
					<td class="td_right"><input type="text" name="board_name" id="board_name" required="required"/></td>
				</tr>
				<tr>
					<td class="td_left"><label for="board_pass">비밀번호</label></td>
					<td class="td_right"><input name="board_pass" type="password" id="board_pass" required="required"/></td>
				</tr>
				<tr>
					<td class="td_left"><label for="board_subject">제목</label></td>
					<td class="td_right"><input name="board_subject" type="text" id="board_subject" required="required"/></td>
				</tr>
				<tr>
					<td class="td_left"><label for="board_content">내용</label></td>
					<td><textarea id="board_content" name="board_content" cols="40" rows="15" required="required"></textarea></td>
				</tr>
				<tr>
					<td class="td_left"><label for="board_file">파일 첨부</label></td>
					<td class="td_right"><input name="board_file" type="file" id="board_file" required="required"/></td>
				</tr>
			</table>
			<section id="commandCell">
					<input type="submit" value="등록">&nbsp; &nbsp;
					<input type="reset" value="다시쓰기"/>
			</section>
			</form>
	</section>
	<!-- 게시판 등록 -->
</body>
</html>