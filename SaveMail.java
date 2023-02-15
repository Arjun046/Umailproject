/*
 *** Umail **
 *
 *@author Arjun Suthar
 *
 *Servlet to save a composed mail in drafts.
 */
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;

public class SaveMail extends HttpServlet implements SingleThreadModel {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String userid = (String)session.getAttribute("userid");
//System.out.println("User's id="+userid);
		if(userid == null) {
			response.sendRedirect("/Umail/");
		} else {
			String to = (String)request.getParameter("to");
			String cc = (String)request.getParameter("cc");
			String bcc = (String)request.getParameter("bcc");
			String subject = (String)request.getParameter("subject");
			String content = (String)request.getParameter("content");
			if(to.equals("") && cc.equals("") && bcc.equals("") && subject.equals("") && subject.equals("")){
				
			} else{
				Database.saveMessageAs("drafts", userid, to, cc, bcc, subject, content);
			}
			response.sendRedirect("/Umail/Home");
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}