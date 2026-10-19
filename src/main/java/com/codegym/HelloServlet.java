package com.codegym;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Servlet mau: nhan ten tu form tren index.jsp, chuyen du lieu sang hello.jsp de hien thi.
 * URL: /jsp-servlet-demo/hello
 */
@WebServlet(name = "HelloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        if (name == null || name.isBlank()) {
            name = "CodeGym";
        }

        request.setAttribute("name", name.trim());
        request.setAttribute("serverTime", LocalDateTime.now().format(TIME_FORMAT));
        request.setAttribute("serverInfo", getServletContext().getServerInfo());

        request.getRequestDispatcher("/WEB-INF/views/hello.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
