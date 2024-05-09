<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="java.sql.*" %>
<html>
<head>
    <title>Select.jsp</title>
</head>
<body>
<table>
    学号：230311367 专业：软件工程 成绩：80
</table>
<% String id = request.getParameter("selectid");
    Connection conn = null;
    try{
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jqe_school?characterEncoding=utf-8";
        conn = DriverManager.getConnection(url,"root","rootroot");
        PreparedStatement psmtQuery = conn.prepareStatement("select * from score where stu_id='''+id+'''");
        ResultSet rs = psmtQuery.executeQuery();
        while (rs.next()){
            int stuid = rs.getInt("stu_id");
            String name = rs.getString("c_name");
            int score = rs.getInt("grade");
            out.println("学号：" +id);
            out.println("专业：" +name);
            out.println("成绩：" +score);
        }
    }catch (ClassNotFoundException e){
        e.printStackTrace();
    }catch (SQLException e){
        e.printStackTrace();
    }
%>
</body>
</html>