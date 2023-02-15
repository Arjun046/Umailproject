/*
 *Bodacious Assignment ** uMail **
 *
 *@author Parth Khandelwal
 *
 *Servlet to save a composed mail in drafts.
 */
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;

public class SendMail extends HttpServlet implements SingleThreadModel {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String userid = (String)session.getAttribute("userid");
		if(userid == null) {
			response.sendRedirect("/uMail/");
		} else {
			String to = (String)request.getParameter("to");
			String cc = (String)request.getParameter("cc");
			String bcc = (String)request.getParameter("bcc");
			String subject = (String)request.getParameter("subject");
			String content = (String)request.getParameter("content");
			boolean isValid = true;
			if(to.equals("") && cc.equals("") && bcc.equals("") && subject.equals("") && subject.equals("")){
				
			} else {
				if(isValid) {	//checking email-id of TO field.
System.out.println("$$$$$$$$$$$$$To Field value = " +to);				
					isValid = Database.hasUser(to);
				}
				if(isValid && cc.length()>10) {	//checking email-id of CC field.
					StringTokenizer stroker = new StringTokenizer(cc);
					while(isValid && stroker.hasMoreTokens()) {
						String token = stroker.nextToken();
						isValid = Database.hasUser(token);
					}
				}
				if(isValid && bcc.length()>10) {	//checking email-id of CC field.
					StringTokenizer stroker = new StringTokenizer(bcc);
					while(isValid && stroker.hasMoreTokens()) {
						String token = stroker.nextToken();
						isValid = Database.hasUser(token);
					}
				}
				if(isValid) {
					Database.saveMessageAs("sentmail", userid, to, cc, bcc, subject, content);
					Database.sendMessage(userid, to, cc, bcc, subject, content);
				} else{
					Database.saveMessageAs("outbox", userid, to, cc, bcc, subject, content);
				}
			}
			response.sendRedirect("/uMail/Home");
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}