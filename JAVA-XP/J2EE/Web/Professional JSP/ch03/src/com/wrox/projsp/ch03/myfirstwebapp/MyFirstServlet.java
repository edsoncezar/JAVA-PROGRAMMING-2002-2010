package com.wrox.projsp.ch03.myfirstwebapp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletException;

public class MyFirstServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, 
                    HttpServletResponse response) throws ServletException, 
                    IOException {
    response.setContentType("text/plain");
log("doGet was just called");
    PrintWriter out = response.getWriter();
    out.println("This is my first Servlet");

  } 
}
