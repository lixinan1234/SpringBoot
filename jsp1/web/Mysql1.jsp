<%@page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@page import="java.sql.*" %>

<html>
<body>

<%
    Connection con;
    Statement sql;
    ResultSet rs;
    try {
        Class.forName("com.mysql.jdbc.Driver");
    }catch (Exception e){
        e.printStackTrace();
    }

    try {
        String url = "jdbc:mysql://localhost:3306/jsp";
        String user = "root";
        String password = "rootroot";

        con = DriverManager.getConnection(url,user,password);

        sql = con.createStatement();
        rs = sql.executeQuery("select * from jsp_student");

        out.print("<table border=1>");
        out.print("<tr>");
        out.print("<th width=100>"+"id");
        out.print("<th width=100>"+"name");
        out.print("<th width=100>"+"sex");
        out.print("<th width=100>"+"birth");
        out.print("<th width=100>"+"department");
        out.print("<th width=100>"+"address");
        out.print("<th width=100>"+"height");
        out.print("<th width=100>"+"nianji");

        out.print("</tr>");
        while(rs.next()){
            out.print("<tr>");
            out.print("<td>"+rs.getInt("id")+"</td>");
            out.print("<td>"+rs.getString("name")+"</td>");
            out.print("<td>"+rs.getInt("sex")+"</td>");
            out.print("<td>"+rs.getInt("birth")+"</td>");
            out.print("<td>"+rs.getString("birth")+"</td>");
            out.print("<td>"+rs.getInt("department")+"</td>");
            out.print("<td>"+rs.getString("address")+"</td>");
            out.print("<td>"+rs.getInt("height")+"</td>");
            out.print("<td>"+rs.getString("nianji")+"</td>");
            out.print("</tr>");
        }
        out.print("</table>");
        con.close();
    }
    catch (SQLException e){
        out.print(e);
    }
%>
</body>

</html>