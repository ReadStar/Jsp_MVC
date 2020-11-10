<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//DBCP 사용을 위한 테스트
Connection con = null;

try{
//JNDI 연결을 위한 설정(대부분의 API는 javax.naming 패키지에 위치함)
//context.xml 파일 내의 <Context> 태그 항목 가져오기
Context initCtx = new InitialContext();

//context.xml 파일 내의 <context> 태그 내에서 <Reswource> 태그 항목 가져오기
//Context 객체의 lookup()메서드를 호출하여 "java:comp/env" 문자열 전달
Context envCtx = (Context)initCtx.lookup("java:comp/env");

//context.xml 파일 내의 <Resource> 태그 내에서 JNDI 설정을 위한 이름(name) 가져오기
//=>리턴타입이 Object 타입이므로 DataSource 타입으로 다운캐스팅 필요
DataSource ds = (DataSource)envCtx.lookup("jdbc/MySQL");

//DataSource 객체의 getConnection()메서드를 호출하여 connection객체 가져오기
con = ds.getConnection();
//=>ds.getConnection(username, password)형식으로도 사용할 수 있다
out.println("<h1>DBCP 연결 성공</h1>");
}catch(Exception e){
	out.println("<h1>DBCP 연결 실패</h1>");
	e.printStackTrace();
}
%>