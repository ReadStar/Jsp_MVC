<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	//session 객체에 저장된 id값을 가져와서 변수에 저장
    	String id = (String)session.getAttribute("id");
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#login{
	text-align: right;
	}
</style>
</head>
<body>
	<section id ="login">
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
	<h1>MVC게시판</h1>
	<h1><a href="BoardWriteForm.bo">글쓰기</a></h1>
	<h1><a href="BoardList.bo">글목록</a></h1>
</body>
</html>