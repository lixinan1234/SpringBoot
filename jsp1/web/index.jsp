<%@ page import="com.java.Resume" %><%--
  Created by IntelliJ IDEA.
  User: 李新安
  Date: 2024/5/8
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
<h1>简历填写界面</h1>
<%
    Resume resume = (Resume) request.getAttribute("resume");
    if (resume==null){
%>
<form action="/receiveServlet" method="post">
    <table>
        <head><h3>简历填写界面</h3></head>
        <tr>
            <td>
                <label>姓名:</label>
            </td>
            <td><input type="text" name="name"></td>
        </tr>

        <tr>
            <td>
                性别：
            </td>
            <td>
                <!-- name的值一样就只能选其中一个 -->
                <input type="radio" name="gen" id="male" value="男">
                <label for="male">男</label>
                <input type="radio" name="gen" id="female" value="女">
                <label for="female">女</label>
            </td>
        </tr>
        <tr>
            <td>
                <label>出生日期:</label>
            </td>
            <td>
                <input type="date" name="birthday" >
            </td>
        </tr>
        <tr>
            <td>
                <label>联系电话：</label>
            </td>
            <td>
                <input type="text" name="phone">
            </td>
        </tr>
        <tr>
            <td>
                <label >电子邮件：</label>
            </td>
            <td>
                <input type="text" name="mail">
            </td>
        </tr>
        <tr>
            <td>
                <label >居住地址：</label>
            </td>
            <td>
                <input type="text" name="address">
            </td>
        </tr>

        <tr>
            <td>教育背景：</td>
            <td>
                <textarea  name="school"  cols="70" rows="6"></textarea>
            </td>
        </tr>
        <tr>
            <td>工作经验：</td>
            <td>
                <textarea  name="work"  cols="70" rows="6"></textarea>
            </td>
        </tr>
        <tr>
            <td>技能特长：</td>
            <td>
                <textarea  name="skill"  cols="70" rows="6"></textarea>
            </td>
        </tr>
        <tr>
            <td><input type="submit" value="提交简历"></td>
        </tr>

    </table>
</form>
<%
}else {
%>
姓名：<%=resume.getName()%><br><br>
性别：<%=resume.getGen()%> <br><br>
出生日期：<%=resume.getBirthday()%><br><br>
联系电话：<%=resume.getPhone()%><br><br>
电子邮件：<%=resume.getMail()%><br><br>
居住地址：<%=resume.getAddress()%><br><br>
教育背景：<%=resume.getSchool()%><br><br>
工作经验：<%=resume.getWork()%><br><br>
技能特长：<%=resume.getSkill()%><br><br>
<h1>简历提交成功!!!</h1>
<button type="button" onclick="window.location.href='index.jsp'">重新提交简历</button>
<%
    }
%>
</body>
</html>

