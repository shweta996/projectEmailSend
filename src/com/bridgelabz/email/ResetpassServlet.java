package com.bridgelabz.email;


import java.io.IOException;


import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bouncycastle.asn1.ocsp.Request;






@WebServlet("/ResetpassServlet")
public class ResetpassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    Connection con=null;
    PreparedStatement stmt=null;
	ResultSet result=null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	PrintWriter pw=response.getWriter();
		
		String password=request.getParameter("password1");
		String reset_password=request.getParameter("password2");
		ServletContext context=getServletContext();
		String id=(String) context.getAttribute("id");
		System.out.println("id"+id);
		
		try 
		{
			con = MyConnectionProvider.getCon();
			stmt=con.prepareStatement("update registration set password= ? , retype_password= ? where email_id= ?");
			stmt.setString(1, password);
			stmt.setString(2, reset_password);
			stmt.setString(3, id);
	
			if(password.equals(reset_password))
			{
				int result =stmt.executeUpdate();
				if(result!=0)
				{
					pw.println("<html><body align='center'><font color='orange'>Password changed Successfully..!!!</font></body></html>");
					request.getRequestDispatcher("login.jsp").include(request, response);
				}
			}
			else 
			{
				pw.println("<html><body align='center'><font color='red'>Password and Confirm Password does not match,Try Again!!!</font></body</html>");
				request.getRequestDispatcher("reset.jsp").include(request, response);
			}
							
			
		}
	catch (Exception e) 
		{
		pw.println(e);
		pw.close();
	   System.out.println(e);
			
		}	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
