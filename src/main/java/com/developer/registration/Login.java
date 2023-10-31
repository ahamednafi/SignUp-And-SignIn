package com.developer.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private static final String QUERY = "SELECT * FROM users WHERE email=? AND password=?";
	private PreparedStatement prepareStatement;
	private ResultSet res;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				String email = req.getParameter("username");
				String password = req.getParameter("password");
				String url = "jdbc:mysql://localhost:3306/company?useSSL=false";
				String user = "root";
				String pass = "root";
				HttpSession session = req.getSession();
				RequestDispatcher dispatcher=null;
				
				if(email==null || email.equals(""))
				{
					req.setAttribute("status", "invalidEmail");
					dispatcher = req.getRequestDispatcher("login.jsp");
					dispatcher.forward(req, resp);
				}
				if(password==null || password.equals(""))
				{
					req.setAttribute("status", "invalidPassword");
					dispatcher = req.getRequestDispatcher("login.jsp");
					dispatcher.forward(req, resp);
				}
				
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					connection = DriverManager.getConnection(url,user,pass);
					prepareStatement = connection.prepareStatement(QUERY);
					prepareStatement.setString(1, email);
					prepareStatement.setString(2, password);
					res = prepareStatement.executeQuery();
					if(res.next())
					{
						session.setAttribute("name", res.getString("username"));
						dispatcher = req.getRequestDispatcher("index.jsp");
					}
					else 
					{
						req.setAttribute("status","failed");
						dispatcher = req.getRequestDispatcher("login.jsp");
					}
					dispatcher.forward(req, resp);
					
				} catch (ClassNotFoundException | SQLException e) { 
					e.printStackTrace();
				}
	}

}
