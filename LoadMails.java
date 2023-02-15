/*
 ** Umail **
 *
 *@author Arjun Suthar
 *
 *Servlet to create a div's content consisting Client's Mails and Toolbar for a category
 */
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.*;
import java.io.*;

public class LoadMails extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
			try{
System.out.println("\nLoading mails............");
		HttpSession session = request.getSession(true);
		String userid = (String)session.getAttribute("userid");
		if(userid == null) {
			response.sendRedirect("/Umail/");
		} else {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Umail", "root", "root");
            Statement stmt = conn.createStatement();){
            String tag = request.getParameter("option");
            
			if(tag==null){
				tag = (String)session.getAttribute("tag");
			}
			session.setAttribute("tag",tag);
System.out.println(">>>>>>Fetching from "+tag+" <<<<<<<<<<<");			
			ResultSet rst=null;
			switch(tag){
				case "inbox":
				default:
System.out.println("Case is Inbox");
System.out.println("SELECT * FROM " +userid.substring(0,userid.length()-10)+ " WHERE to_userid like '%" +userid+ "%' and tag='" +tag+ "' order by msg_timestamp desc");
					rst = stmt.executeQuery("SELECT * FROM " +userid.substring(0,userid.length()-10)+ " WHERE to_userid like '%" +userid+ "%' and tag='inbox' order by msg_timestamp desc");
					break;
				case "sentmail":
				case "drafts":
				case "outbox":
					rst = stmt.executeQuery("SELECT * FROM " +userid.substring(0,userid.length()-10)+ " WHERE from_userid='" +userid+ "' and tag='" +tag+ "' order by msg_timestamp desc");
					break;
				case "trash":					
					rst = stmt.executeQuery("SELECT * FROM " +userid.substring(0,userid.length()-10)+ " WHERE tag like '" +tag+ "%' and (from_userid like '%" +userid+ "%' or to_userid like '%" +userid+ "%') order by msg_timestamp desc");
System.out.println("SELECT * FROM " +userid.substring(0,userid.length()-10)+ " WHERE tag like '" +tag+ "%' and (from_userid like '%" +userid+ "%' or to_userid like '%" +userid+ "%') order by msg_timestamp desc");
					break;
			}
            
            
            if(rst==null || !(rst.next())){
            	out.println("<center><h1> All Caught Up here ! </h1></center>");	
            } else {
				out.println(""
			+"			<header id='toolbar'>"
			+"				<input type='checkbox' id='selectAll' name='selectall' onClick='selectAllMails()'/>"
			+"				&nbsp;&nbsp;&nbsp;&nbsp;");
				if(tag.equalsIgnoreCase("trash")){
					out.println(""
			+"				<input type='submit' id='delete' name='delete' value='Delete' form='mailForm' formaction='DeleteMailsPermanently' />"
			+"				<input type='submit' id='restore' name='restore' value='Restore' form='mailForm' formaction='RestoreMails' />");
				} else {
					out.println(""
			+"				<input type='submit' id='delete' name='delete' value='Delete' form='mailForm' formaction='DeleteMails' />");
				}
				out.println(""
			+"			</header>"
			+"			<form name='mailForm' id='mailForm' method='post'>"
			+"				<table cellspacing='0px'>");
				int count=0;
				do{
					out.println(""
			+"					<tr>"
			+"						<td><input type='checkbox' width='3%' name='check' id='check' value='"+rst.getInt("msg_id")+"' onChange = 'checkStateToDisplayToolbarButton()';/></td>");
					out.println(""
			+"						<td width='15%' name='msg"+count+"' onClick='showMailDetail(\"msg"+count+"\")'>" +rst.getString("from_userid")+ "</td>");
//					if(!tag.equals("trash")){
//						out.println(""
//			+"						<td width='10%' name='msg"+count+"' >" +rst.getString("to_userid")+ "</td>");
//					} else {
						out.println(""
			+"						<td width='10%' name='msg"+count+"' onClick='showMailDetail(\"msg"+count+"\")'>" +rst.getString("to_userid")+ "</td>");
//					}
					out.println(""
			+"						<td name='msg"+count+"'  onClick='showMailDetail(\"msg"+count+"\")'>" +rst.getString("subject")+ "</td>"
			+"						<td width='16%' name='msg"+count+"'  onClick='showMailDetail(\"msg"+count+"\")'>" +rst.getString("msg_timestamp")+ "</td>"
			+"						<input type='hidden' value='"+rst.getString("content")+"' name='msg"+count+"' />"
			+"					</tr>");
					count++;
				} while(rst.next());
					out.println(""
			+"				</table>"
			+"			</form>");
				if(request.getParameter("option")!=null) {
					out.close();
				}
			}
			System.out.println("-----------------Done Loading");
            } catch(SQLException sqlExc) {
            	sqlExc.printStackTrace();
            }
		}}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}