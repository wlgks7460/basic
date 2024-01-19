package com.encore.basic.servletjsp;

import com.encore.basic.domain.Hello;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello-servlet-rest-get")
public class HelloServletRestGet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Hello hello = new Hello();
        hello.setName("son");
        hello.setEmail("hm77@naver.com");
        hello.setPassword("21234");

        ObjectMapper mapper = new ObjectMapper();
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        String s = mapper.writeValueAsString(hello);
        PrintWriter out = resp.getWriter();
        out.print(s);
//        버퍼를 통해 조립이 이루어지므로, 버퍼를 비우는 과정
        out.flush();
    }




}
