/*
 * ** Umail **
 *
 *@author Arjun Suthar
 *
 *Servlet to restore deleted mails from trash
 */
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.*;
import java.io.*;

public class RestoreMails extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
System.out.println("\nStarting mails' Restoration--------------");
		HttpSession session = request.getSession(true);
		String userid = (String)session.getAttribute("userid");
		if(userid == null) {
			response.sendRedirect("/Umail/Home");
		} else {
			String tag = (String)session.getAttribute("tag");
			try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/umail", "root", "root");
			PreparedStatement pstmt1 = conn.prepareStatement("SELECT tag FROM " +userid.substring(0,userid.length()-10)+ " WHERE msg_id=?");
            PreparedStatement pstmt2 = conn.prepareStatement("UPDATE " +userid.substring(0,userid.length()-10)+ " SET tag=? WHERE msg_id=?");){
//            PreparedStatement pstmt3 = conn.prepareStatement("DELETE FROM trash WHERE msg_id=?");){
            	String msg[] = request.getParameterValues("check");
            	if(msg.length>0){
            		ResultSet rst = null;
	            	for(int i=0; i<msg.length; i++) {
	            		pstmt1.setInt(1, Integer.parseInt(msg[i]));
	            		rst = pstmt1.executeQuery();
	            		String restoreTo="";
	            		if(rst.next()){
	            			restoreTo = ((rst.getString(1)).split(" "))[1];
	            		} else {
	            			break;
	            		}
	            		pstmt2.setString(1, restoreTo);
	            		pstmt2.setInt(2, Integer.parseInt(msg[i]));
	            		pstmt2.executeUpdate();
//	            		pstmt3.setInt(1, Integer.parseInt(msg[i]));
//	            		pstmt3.executeUpdate();
System.out.println("------Restoring Mail "+msg[i]+"--------");           		
	            	}
            	}
System.out.println("--------------Mails Restore");
				response.sendRedirect("/Umail/Home");
            } catch(SQLException sqlExc) {
            	sqlExc.printStackTrace();
            	response.sendRedirect("/UMail/Home");
            }
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}