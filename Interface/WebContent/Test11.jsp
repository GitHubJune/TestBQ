<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String nameStr = request.getParameter("name");//用request得到
    request.setAttribute("nameAttr", nameStr);
%> 
<!DOCTYPE html>
<html>
<head>   
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">   
    <title>Hello</title>   
</head>
<body>
  Hi,<c:out value="${nameAttr}"/>
    Hi,<%=nameStr%>
    Hi,${param.name}
</body>
</html>