/*
 * ** uMail **
 *
 *@author Arjun Suthar
 *
 *Servlet to delete the mails and move to trash category
 */
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.*;
import java.io.*;

public class DeleteMails extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
System.out.println("\nStarting mails' Deletion--------------");
		HttpSession session = request.getSession(true);
		String userid = (String)session.getAttribute("userid");
		if(userid == null) {
			response.sendRedirect("/Umail/Home");
		} else {
			String tag = (String)session.getAttribute("tag");
			try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Umail", "root", "root");
            PreparedStatement pstmt1 = conn.prepareStatement("UPDATE " +userid.substring(0,userid.length()-10)+ " SET tag=? WHERE msg_id=?");){
//            PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO trash values(?,?)");){
            	String msg[] = request.getParameterValues("check");
            	if(msg.length>0){
	            	for(int i=0; i<msg.length; i++) {
	            		pstmt1.setString(1, "trash "+tag);
	            		pstmt1.setInt(2, Integer.parseInt(msg[i]));
	            		pstmt1.executeUpdate();
//	            		pstmt2.setInt(1, Integer.parseInt(msg[i]));
//	            		pstmt2.setString(2,tag);
//	            		pstmt2.executeUpdate();
System.out.println("------Deleting Mail "+msg[i]+" from " +tag+ "--------");           		
	            	}
            	}
System.out.println("--------------Mails Deleted");
				response.sendRedirect("/Umail/Home");
            } catch(SQLException sqlExc) {
            	sqlExc.printStackTrace();
            	response.sendRedirect("/Umail/Home");
            }
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}