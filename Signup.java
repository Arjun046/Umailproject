/*
 *** uMail **
 *
 *@author Arjun Suthar
 *
 *Servlet to check client's userid & password and forward the request to client's homePage
 */
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.*;
import java.io.*;

public class Signup extends HttpServlet {
	
	Database db = new Database();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String userid = (String)request.getParameter("userid");
		userid+="@umail.com";
		String pwd = (String)request.getParameter("password");
System.out.println("Userid=" +userid+ " Password=" +pwd);
		if(Database.hasUser(userid)) {
			showInvalidSignupPage("User ID Not Available", response);
		} else {
			String usersname = (String)request.getParameter("usersname");
			String mobNo = (String)request.getParameter("mobileNo");
			boolean success = Database.addUser(usersname.replaceAll("\'","\\\\'"), userid.replaceAll("\'","\\\\'"), pwd.replaceAll("\'","\\\\'"), mobNo);
			
			if(success) {
				HttpSession session = request.getSession(true);
				session.setAttribute("userid",userid);
				response.sendRedirect("/Umail/Home");
			} else {
				showInvalidSignupPage("An Error Occured, Try Again !", response);
			}
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
	
	public void showInvalidSignupPage(String msg, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(""
		+"<!doctype html>"
		+"<html lang='en'>"
		+"	<head>"
		+"		<meta charset='utf-8' />"
		+"		<title> uMail </title>"
		+"		<link rel='Stylesheet' type='text/css' href='css/invalidSignupPage.css' />"
		+"	</head>"
		+"	<body>"
		+"		<h2 id='title'> Umail </h2>"
		+"		<center>"
		+"		<div id='centerDiv'>"
		+"			<img src='images/signupProfile.png' /> <br />"
		+"			<p id='msg'> " +msg+ " </p>"
		+"			<form method='get' action='SignUp'>"
		+"				<table rule='none' cellpadding='2' cellspacing='5'>"
		+"					<tr>"
		+"						<td>"
		+"							<input type='text' name='usersname' class='fields' placeholder='              Full Name'"
		+"								size='20' maxlength='30' required />"
		+"						</td>"
		+"					</tr>"
		+"					<tr>"
		+"						<td> "
		+"							<input type='text' name='userid' class='fields' placeholder='                User ID'"
		+"								size='20' maxlength='30' required />"
		+"						</td>"
		+"					</tr>"
		+"					<tr>"
		+"						<td> "
		+"							<input type='text' name='password' class='fields' placeholder='          New Password'"
		+"								size='20' maxlength='30' required />"
		+"						</td>"
		+"					</tr>"
		+"					<tr>"
		+"						<td> "
		+"							<input type='text' name='confirmPassword' class='fields' placeholder='       Confirm Password'"
		+"								size='20' maxlength='30' required />"
		+"						</td>"
		+"					</tr>"
		+"					<tr>"
		+"						<td> "
		+"							<input type='number' name='mobileNo' class='fields' placeholder='          Mobile Number'"
		+"								size='20' maxlength='10' pattern='[0-9]' min='1000000000'/>"
		+"						</td>"
		+"					</tr>"
		+"					<tr>"
		+"						<td align='center'>"
		+"							<input type='submit' name='signup' id='signupBtn' value='Sign Up' />"
		+"						</td>"
		+"					</tr>"
		+"				</table>"
		+"			</form>"
		+"		</div>"
		+"		</center>"
		+"	</body>"
		+"</html>");
	}
}