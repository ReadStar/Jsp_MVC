<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>로그인</h1>
<form action="LoginPro.me" method="post">
	아이디 : <input type="text" name="id"><br>
	비밀번호: <input type="password" name="password"><br>
	<input type="submit" value="로그인(post)">
</form>
<form action="LoginPro2.me" method="get">
	아이디 : <input type="text" name="id"><br>
	비밀번호: <input type="password" name="password"><br>
	<input type="submit" value="로그인(get)">
	</form>
</body>
</html>