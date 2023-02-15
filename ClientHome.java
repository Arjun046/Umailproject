/*
 * ** Umail **
 *
 *@author Arjun Suthar
 *
 *Servlet to display Client's Home Screen
 */
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.*;
import java.io.*;

public class ClientHome extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try{
System.out.println("\nIn Home's Starting");		
			HttpSession session = request.getSession(true);
			String userid = (String)session.getAttribute("userid");
			System.out.println("User's id="+userid);
			ServletContext context = getServletContext();
			if(userid == null) {
				response.sendRedirect("/Umail/");
			} else {
				if(session.getAttribute("tag")==null) {
					session.setAttribute("tag","inbox");
				}
				String tag = (String)session.getAttribute("tag");
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println(""
				+"	<!doctype html>"
				+"	<html lang='en'>"
				+"	<head>"
				+"		<title>uMail</title>"
				+"		<meta charset='utf-8' />"
				+"		<style> li[value='"+tag+"'] {"
				+"			color				: black;"
				+"			font-weight			: bold;"
				+"			}"	
				+"		</style>"
				+"		<link rel='StyleSheet' type='text/css' href='css/clientHome.css' />"
				+"		<script src='javascript/clientHome.js'> </script>"
				+"	</head>"
				+"	<body>"
				+"		<div id='mainDiv'>"
				+"			<header id='mainHeader'>"
				+"				<div id='titleDiv'>"
				+"					<h2 id='title'> Umail </h2>"
				+"				</div>"
				+"				<div id='logoutDiv'>"
				+"					<input type='button' id='logoutBtn' name='logout' value='Logout' onclick='logMeOut()' />"
				+"				</div>"
				+"			</header>"
				+"			<div id='centerDiv'>"
				+"				<nav id='options'>"
				+"					<input type='button' id='compose' name='compose' value='Compose' onclick='showComposeDiv()' />"
				+"					<input type='hidden' id='selectedOption' value='inbox' />"
				+"					<ul>"
				+"						<li name='option' value='inbox' onClick='optionClicked(\"inbox\")'>Inbox</li>"
				+"						<li name='option' value='sentmail' onClick='optionClicked(\"sentmail\")'>Sent Mail</li>"
				+"						<li name='option' value='drafts' onClick='optionClicked(\"drafts\")'>Drafts</li>"
				+"						<li name='option' value='outbox' onClick='optionClicked(\"outbox\")'>Outbox</li>"
				+"						<li name='option' value='trash' onClick='optionClicked(\"trash\")'>Trash</li>"
				+"					</ul>"
				+"				</nav>"
				+"				<section id='mainSection'>"
				+"					<div id='mailsDiv'>");
System.out.println("\n***Initiating LoadMails***");			
					RequestDispatcher reqDis = context.getRequestDispatcher("/LoadMails");
					reqDis.include(request, response);
System.out.println("\n***#Completed Loading#***");			
					out.println(""
+"				</div>"
+"					<div id='mailDetailDiv'>"
+"						<header id='toolbar'>"
+"							<input type='button' id='back' name='back' value='Back' onclick='hideMailDetail()'/>"
+"							&nbsp;&nbsp;&nbsp;&nbsp;"
+"							<input type='button' id='reply' name='reply' value='Reply' onclick='replyToMail()' />"
+"							<input type='button' id='forward' name='forward' value='Forward' onclick='forwardMail()' />"
+"						</header>"
+"						<font size='1.1em'><b>From &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; :</b> &nbsp;<span id='from'></span> </br>"
+"						<b>To &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; :</b> &nbsp;<span id='to'></span> </br>"
+"						<b>Subject &nbsp; :</b> &nbsp;<span id='subject'></span> </br> </br>"
+"						</font>"
+"						<textarea name='messageTA' id='msgContent'>"
+"						</textarea>"
+"					</div>"
+"				</section>"
+"			</div>"
+"		</div>"
+"		<div id='composeMailRootDiv' onClick='outerClicked(window.event)'>"
+"			<div id='composeMailCenterDiv' onmouseover='darkenBackground()' onmouseout='blurBackground()' >"
+"				<form method='post' id='composeForm' >"
+"					<font color='cyan' size='1.1em'>To: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='email' name='to' id='composeTo' maxlength='30' placeholder='A User ID' required onfocus='darkenBackground()' /> </br>"
+"					Cc: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='email' name='cc' id='composeCc' placeholder='User-ID seperated with a Whitespace' onfocus='darkenBackground()' multiple /> </br>"
+"					Bcc: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='email' name='bcc' id='composeBcc' placeholder='User-ID seperated with a Whitespace' onfocus='darkenBackground()' multiple /> </br>"
+"					Subject: <input type='text' name='subject' id='composeSubject' placeholder='Mandatory To Fill' required onfocus='darkenBackground()' /> </br> </br>"
+"					</font>"
+"					<textarea name='content' id='composeContent' placeholder='Content' onfocus='darkenBackground()'></textarea> </br>"
+"					<input type='submit' id='sendBtn' name='sendBtn' value='Send' formaction='SendMail' />"
+"					<input type='submit' id='saveBtn' name='saveBtn' value='Save' formaction='SaveMail' />"
+"					<input type='button' value='Discard' onClick='closeComposeMail()' />"
+"				</form>"
+"			</div>"
+"		</div>"
+"	</body>"
+"</html>");

			out.close();					
		}					
	}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Exceptionnnnnnnn ");
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}