package com.developer.registration;

import java.io.IOException;
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


@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private static final String QUERY = "INSERT INTO users(username,password,email,number) VALUES(?,?,?,?)";
	private PreparedStatement prepareStatement;
	          
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String username = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("pass");
			String rePassword = request.getParameter("re_pass");
			String number = request.getParameter("contact");
			String url = "jdbc:mysql://localhost:3306/company";
			String user = "root";
			String pass = "root";
			RequestDispatcher dispatcher = null;
			
			if(username==null || username.equals(""))
			{
				request.setAttribute("status", "invalidName");
				dispatcher = request.getRequestDispatcher("registration.jsp");
				dispatcher.forward(request, response);
			}
			if(email==null || email.equals(""))
			{
				request.setAttribute("status", "invalidEmail");
				dispatcher = request.getRequestDispatcher("registration.jsp");
				dispatcher.forward(request, response);
			}
			if(password==null || password.equals(""))
			{
				request.setAttribute("status", "invalidPassword");
				dispatcher = request.getRequestDispatcher("registration.jsp");
				dispatcher.forward(request, response);
			}
			else if(!(password.equals(rePassword)))
			{
				request.setAttribute("status", "invalidConfirmPassword");
				dispatcher = request.getRequestDispatcher("registration.jsp");
				dispatcher.forward(request, response); 
			}
			
			if(number==null || number.equals(""))
			{
				request.setAttribute("status", "invalidNumber");
				dispatcher = request.getRequestDispatcher("registration.jsp");
				dispatcher.forward(request, response);
			}
			else if(number.length()>10 || number.length()<10)
			{
				request.setAttribute("status", "invalidNumberLength");
				dispatcher = request.getRequestDispatcher("registration.jsp");
				dispatcher.forward(request, response);
			}
			
			
		
			if(dispatcher==null)
			{
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					connection = DriverManager.getConnection(url,user,pass);
					prepareStatement = connection.prepareStatement(QUERY);
					prepareStatement.setString(1, username);
					prepareStatement.setString(2, password);
					prepareStatement.setString(3, email);
					prepareStatement.setString(4, number);
					int rowCount = prepareStatement.executeUpdate();
					if(rowCount>0)
					{
						
						request.setAttribute("status", "success");
					}
					else
					{
						request.setAttribute("status", "failed");
					}
					dispatcher = request.getRequestDispatcher("registration.jsp"); 
					dispatcher.forward(request, response);
		
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
			
			
			
//			finally {
//				
//				try {
//					if(prepareStatement!=null)
//					{
//						prepareStatement.close();
//					}
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//				
//				try {
//					if(connection!=null)
//					{
//						connection.close();
//					}
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
	}

}
