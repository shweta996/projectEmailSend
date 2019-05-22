package com.bridgelabz.email;


import java.io.IOException;


import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bridgelabz.jdbcconnection.Connection1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@SuppressWarnings("serial")
@WebServlet("/MailSend")
public class MailSend extends HttpServlet {
	static Mailer mailer = new Mailer();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String Email = request.getParameter("email");
		//String submitType = request.getParameter("submit");
		ServletContext context=getServletContext();
		context.setAttribute("id", Email);
		System.out.println("email for mail  "+Email);
	
			
		//	request.setAttribute("Successmessage", "Registration done please continue with login");
		boolean check = checkEmail(Email);
		if(check == true)
		{
			System.out.println("Email verified");
		}
		else
		{
			System.out.println("not verified");
		}
			request.getRequestDispatcher("ReadyToReset.jsp").forward(request, response);
			System.out.println("reg successfully");
		
	}
	
	public static boolean checkEmail(String email) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn =MyConnectionProvider.getCon();
			ps = conn.prepareStatement("select * from registration  where email_id=?");
			ps.setString(1, email);
			rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				 mailer.send("shweta996kale@gmail.com","shk1996@",email,"link","http://localhost:8080/Projectdemo/reset.jsp");
				rs.close();
				ps.close();
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return false;
	}
	
	

}