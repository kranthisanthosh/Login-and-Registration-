package com.javadevolper.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class registrationservlet
 */
@WebServlet("/register")
public class registrationservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	@SuppressWarnings("null")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uname = request.getParameter("name");
		String upwd = request.getParameter("pass");
		String uemail = request.getParameter("email");
		String Repwd = request.getParameter("re_pass");
		String umobile = request.getParameter("contact");
		
		RequestDispatcher dispatcher= null;
	   Connection con=null;
	   if(uname==null||uname.equals("")) {
		   request.setAttribute("status", "invalidName");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response); }
			if(uemail==null||uemail.equals("")) {
				   request.setAttribute("status", "invalidUemail");
					dispatcher = request.getRequestDispatcher("registration.jsp");
					dispatcher.forward(request, response);
			}
					if(upwd==null||upwd.equals("")) {
						   request.setAttribute("status", "invalidUpwd");
							dispatcher = request.getRequestDispatcher("registration.jsp");
							dispatcher.forward(request, response);
							
					}
					else if(!upwd.equals(Repwd)) {
						   request.setAttribute("status", "invalidconfirmpassword");
							dispatcher = request.getRequestDispatcher("registration.jsp");
							dispatcher.forward(request, response);
							
					}
							if(umobile==null||umobile.equals("")) {
								   request.setAttribute("status", "invalidMobile");
									dispatcher = request.getRequestDispatcher("registration.jsp");
									dispatcher.forward(request, response);
							}
									else if(umobile.length() > 10) {
										   request.setAttribute("status", "invalidMobile");
											dispatcher = request.getRequestDispatcher("registration.jsp");
											dispatcher.forward(request, response);}
	   
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/joiners?useSSL=false", "root","Sairam@123");
			PreparedStatement pst = con
					.prepareStatement("insert into	users(uname,upwd,Repwd,uemail,umobile) values(?,?,?,?,?)");
			System.out.println("hi");
			pst.setString(1,uname);
			pst.setString(2,upwd);
			pst.setString(3, Repwd);
			pst.setString(4,uemail);
			pst.setString(5, umobile);
			
			int rowCount = pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("registration.jsp");
					if(rowCount > 0) {
						request.setAttribute("status", "success");
					}
					else{
						request.setAttribute("status", "failed");
								
					}
					dispatcher.forward(request,response);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			
		
				try {
					if(con!=null) {
						con.close();
					}
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
					
			
		}
		
		
	}


