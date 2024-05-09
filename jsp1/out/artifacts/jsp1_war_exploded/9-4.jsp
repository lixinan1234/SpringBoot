<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="java.sql.*" %>
<html>
<head>
    <title>9-4.jsp</title>
</head>
<body>
<form method=get action="">
    <table>
        输入学生学号：<br/>
        <input type=text name=stu_id>
        <br/>
        输入专业名：
        <br/>
        <input type=text name=c_name>
        <br/>
        输入学生成绩:
        <br/>
        <input type=text name=grade>
        <br/>
        <input type=submit value=添加>

    </table>
</form>
<%
    String id = request.getParameter("stu_id");
    String name = request.getParameter("c_name");
    String scoreStr = request.getParameter("grade");
    if(id==null||id.equals("")||name==null||name.equals("")||scoreStr==null||scoreStr.equals(""))
    {
        out.print("请输入数据，并保证数据正确");
        return;
    }
    int stu_id = Integer.parseInt(id);
    int grade = Integer.parseInt(scoreStr);
    Connection conn = null;
    try{
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jqe_school?charcterEncoding=utf-8";
        conn = DriverManager.getConnection(url,"root","rootroot");
        PreparedStatement psmtInsert = conn.prepareStatement("insert into score values(null,?,?,?)");
        psmtInsert.setInt(1,stu_id);
        psmtInsert.setString(2,name);
        psmtInsert.setInt(3,grade);
        int num = psmtInsert.executeUpdate();
        if(num>0){
            out.println("添加成功！");
        }else {
            out.println("添加失败!");
        }
    }catch (ClassNotFoundException e){
        e.printStackTrace();
    }catch (SQLException e){
        e.printStackTrace();
    }
%>
<form method=get action="Selectid">
    <table>
        请输入学生学号查询成绩：
        <br/>
        <input type=text name=grade>
        <br/>
        <input type=submit value=查询>
    </table>
</form>
</body>
</html>