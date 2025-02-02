<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC_Board</title>
 
<style type="text/css">
	h1 {
		text-align: center;	
	}
	
	table {
		border: 1px solid black;
		width : 400px;
		margin: auto;
		text-align: left;
	}
	
</style>
<script type="text/javascript">
	// submit 전 최종 상태(아이디, 패스워드 규칙 일치 여부) 저장할 전역변수 설정
	var checkIdResult = false, checkPasswdResult = false; 

	// -------------------------------------------------------------------------------
	// 회원 ID 에 대한 정규표현식(4 ~ 12자리 영문, 숫자 조합) 체크를 위해
	// ID 폼을 전달받아 입력받은 ID 에 대한 유효성 검사 함수
	function checkId(idForm) { // 파라미터 this 로 전달된 ID 입력폼을 매개변수에 저장
		var id = idForm.value; // ID 입력폼의 입력값을 가져와서 변수에 저장
		
		// ID 입력 항목의 체크 결과 메세지 출력에 필요한 <div> 태그의 element 가져오기
		var element = document.getElementById('checkIdResult');
		
		// ID 유효성 검사를 위한 정규표현식 설정
		// => 첫글자 영문자로 시작, 두번째부터 영문자, 숫자 조합 3 ~ 11자리 반복 
		// => 영문자(대문자 또는 소문) : [A-Za-z]
		// => 숫자 : [0-9]
		// => 3 ~ 11자리 반복 : {3,11}
		// => 플래그 /x/g : x 문자열의 전체에 대해서 정규표현식을 사용하여 검사 
		var regex = /^[A-Za-z][A-Za-z0-9]{3,11}$/g;
		
		// 자바스크립트에서 정규표현식 판별을 위해서는 다음과 같은 문자열 사용
		// => 정규표현식문자열.exec(검사할데이터) => 판별 결과가 true 또는 false
		if(regex.exec(id)) { // 정규표현식과 일치할 경우
			element.innerHTML = "사용 가능";
			checkIdResult = true; // 전역변수 true 로 변경
		} else { // 일치하지 않을 경우
			element.innerHTML = "사용 불가";
			checkIdResult = false; // 전역변수 false 로 변경(필수!)
		}
		
	}
	
	// 회원 패스워드에 대한 정규표현식(4 ~ 16자리 영문, 숫자, 특수문자 조합) 체크를 위해
	// 패스워드 폼을 전달받아 입력받은 패스워드에 대한 유효성 검사
	function checkPasswd(passwdForm) { // 파라미터 this 로 전달된 ID 입력폼을 매개변수에 저장
		var passwd = passwdForm.value; // ID 입력폼의 입력값을 가져와서 변수에 저장
		
		// 패스워드 입력 항목의 체크 결과 메세지 출력에 필요한 <div> 태그의 element 가져오기
		var element = document.getElementById('checkPasswdResult');
		
		var lengthRegex = /^[A-Za-z0-9!@#$%]{4,16}$/;
		// upperCaseRegex(대문자), lowerCaseRegex(소문자), digitRegex(숫자), 
		// specRegex(특수문자 !@#$%) 정규표현식 작성
		var upperCaseRegex = /[A-Z]/;
		var lowerCaseRegex = /[a-z]/;
		var digitRegex = /[0-9]/;
		var specRegex = /[!@#$%]/;
		
		// 길이 체크(lengthRegex)를 통과할 경우에만 각 항목에 대한 체크 실시
		if(lengthRegex.exec(passwd)) {
			// 대문자, 소문자, 숫자, 특수문자 체크하여 카운팅(복잡도 체크)
			var count = 0;
			// 각 항목별 체크 후 true 일 경우 count 1씩 증가시킴
			if(upperCaseRegex.exec(passwd)) count++;
			if(lowerCaseRegex.exec(passwd)) count++;
			if(digitRegex.exec(passwd)) count++;
			if(specRegex.exec(passwd)) count++;
			
// 			element.innerHTML = "사용 가능 " + count;

			// 점수(count) 에 따른 안전도 출력
			if(count == 4) {
				element.innerHTML = "사용 가능(안전)";
				checkPasswdResult = true; // 전역변수 true 로 변경
			} else if(count == 3) {
				element.innerHTML = "사용 가능(보통)";
				checkPasswdResult = true; // 전역변수 true 로 변경
			} else if(count == 2) {
				element.innerHTML = "사용 가능(위험)";
				checkPasswdResult = true; // 전역변수 true 로 변경
			} else {
				element.innerHTML = "사용 불가(두 가지 이상 조합)";
				checkPasswdResult = false; // 전역변수 false 로 변경
			}

		} else {
			element.innerHTML = "사용 불가";
			checkPasswdResult = false; // 전역변수 false 로 변경
		}
		
	}
	
	// 아이디, 패스워드 정규표현식 체크 후 정상이면 true 리턴(submit), 아니면 false 리턴
	function check() {
		if(checkIdResult && checkPasswdResult) {
			return true;
		} else {
			alert('아이디 또는 패스워드 규칙 확인 필수!');
			return false;
		}
	}
	
</script>
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
	<h1>회원가입</h1>
	<form action="MemberJoinPro.me" method="post" name="joinForm" onsubmit="return check()">
		<table>
			<tr>
				<td>이름</td>
				<td><input type="text" name="name"  required="required" size="15"></td>
			</tr>
			<tr>
				<td>아이디</td>
				<td>
					<!-- 아이디 한 글자 입력할 때마다 정규표현식 체크 -->
					<input type="text" name="id" required="required" size="15" 
							placeholder="4-12자리 영문,숫자 조합" onkeyup="checkId(this)">
					<div id="checkIdResult"><!-- 자바스크립트에서 메세지 출력 공간 --></div>
				</td>
			</tr>
			<tr>
				<td>패스워드</td>
				<td>
					<input type="password" name="passwd" required="required" size="15"
							placeholder="4-16자리 영문,숫자,특수문자 조합" onkeyup="checkPasswd(this)">
					<div id="checkPasswdResult"><!-- 자바스크립트에서 메세지 출력 공간 --></div>
				</td>
			</tr>
			<tr>
				<td>나이</td>
				<td><input type="text" name="age" required="required" size="15"></td>
			</tr>
			<tr>
				<td>E-Mail</td>
				<td><input type="text" name="email" required="required" size="15"></td>
			</tr>
			<tr>
				<td>성별</td>
				<td>
					<input type="radio" name="gender" value="남" checked="checked" size="15">남&nbsp;&nbsp;
					<input type="radio" name="gender" value="여" size="15">여
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="회원가입">
					<input type="button" value="돌아가기" onclick="history.back()">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>


















