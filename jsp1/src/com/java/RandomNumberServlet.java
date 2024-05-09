package com.java;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/randomNumber")
public class RandomNumberServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Random Number Button</title></head><body>");
        out.println("<h1>Random Number Button</h1>");
        out.println("<form action=\"/randomNumber\" method=\"post\">");
        out.println("<input type=\"submit\" value=\"Generate Random Number\">");
        out.println("</form>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Random rand = new Random();
        int randomNumber = rand.nextInt(100); // 生成一个0到99的随机数

        out.println("<html><head><title>Random Number</title></head><body>");
        out.println("<h1>随机数</h1>");
        out.println("<p>随机生成的数是: " + randomNumber + "</p>");
        out.println("<a href=\"/randomNumber\">Back</a>");
        out.println("</body></html>");
    }
}
