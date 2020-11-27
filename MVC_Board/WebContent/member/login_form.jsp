<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
</style>

<%
	// session 객체에 저장된 id 값이 존재할 경우
	// "잘못된 접근입니다." 출력 후 메인페이지로 이동
	if(session.getAttribute("id") != null) {
		%>
		<script>
			alert('잘못된 접근입니다.');
			location.href="./";
		</script>
		<%
	}

%>    

</head>
<body>
	<section id="loginForm">
		<h2>로그인</h2>
		<form action="MemberLoginPro.me" method="post"  name="loginform">
			<table>
				<tr>
					<td class="td_left"><label for="id">아이디</label></td>
					<td class="td_right"><input type="text" name="id" id="id" required="required"/></td>
				</tr>
				<tr>
					<td class="td_left"><label for="passwd">비밀번호</label></td>
					<td class="td_right"><input name="passwd" type="password" id="passwd" required="required"/></td>
				</tr>
			</table>
			<section id="commandCell">
					<input type="submit" value="로그인">&nbsp; &nbsp;
					<input type="reset" value="다시쓰기"/>&nbsp; &nbsp;
					<input type="button" value="회원가입" onclick="location.href='MemberJoinForm.me'"/>
			</section>
			</form>
	</section>
</body>
</html>