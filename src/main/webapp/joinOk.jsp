<%@page import="com.javalec.ex.MemberDao"%>
<%@page import="java.sql.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	request.setCharacterEncoding("UTF-8");
%>

<jsp:useBean id="dto" class="com.javalec.ex.MemberDto"></jsp:useBean>
<jsp:setProperty property="*" name="dto"></jsp:setProperty>

<%
	dto.setrDate(new Timestamp(System.currentTimeMillis()));
	MemberDao dao=MemberDao.getInstance();
	
	//같은 아이디가 존재하는지 확인
	if(dao.confirmId(dto.getId()) == MemberDao.MEMBER_EXISTENT){
%>
	<script type="text/javascript">
		alert("이미 존재하는 아이디입니다.")
		history.back();
	</script>

<%
	//아이디 중복아님
	}else{
		int ri=dao.insertMember(dto);
		if(ri == MemberDao.MEMBER_JOIN_SUCESS){
			session.setAttribute("id", dto.getId());
	
%>
	<script type="text/javascript">
		alert("회원가입을 축하합니다.");
		document.location.href="login.jsp";
	</script>

<%
		}else{
			//아이디가 중복은 아니지만 테이블에 저장 실패
%>
	<script type="text/javascript">
		alert("회원가입에 실패했습니다.");
		document.location.href="login.jsp";
	</script>

<%
		}
	}
%>
</body>
</html>
