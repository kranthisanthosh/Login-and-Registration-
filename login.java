package com.javadevolper.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uemail = request.getParameter("username");
		String upwd = request.getParameter("password");
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher =null;
		
		if(uemail==null||uemail==("")){
			request.setAttribute("status", "invalidEmail");
			dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}if(upwd==null||upwd==("")){
				request.setAttribute("status", "invalidUpwd");
				dispatcher = request.getRequestDispatcher("login.jsp");
				dispatcher.forward(request, response);
			
		}
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/joiners?useSSL=false", "root","Sairam@123");
				PreparedStatement pst = con.prepareStatement("select*from users where uemail=? and upwd=?");
			pst.setString(1, uemail);
			pst.setString(2, upwd);
			   System.out.println("hi");
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				session.setAttribute("name",rs.getString("uname"));
				dispatcher= request.getRequestDispatcher("index.jsp");
		}else { 
			request.setAttribute("status","failed");
			dispatcher = request.getRequestDispatcher("login.jsp");
		
		}
			System.out.println("ji");
			dispatcher.forward(request, response);}
		catch(Exception e) {
		e.printStackTrace();
		}
		
	


	}

}