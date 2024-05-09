<%--
  Created by IntelliJ IDEA.
  User: 李新安
  Date: 2024/3/27
  Time: 17:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    request.setCharacterEncoding("UTF-8");

%>
<jsp:useBean id="student" scope="page" class="com.java.Student"/>
<jsp:setProperty name="student" property="*"/>
注册成功：
<br>
使用bean中的方法：<br>
<%=student.getUserName()%>
<%=student.getPassword()%>
<%=student.getAge()%>
<br>
使用getProperty:
<br>
<jsp:getProperty name="student" property="userName"/>
<jsp:getProperty name="student" property="password"/>
<jsp:getProperty name="student" property="age"/>
</body>
</html>
