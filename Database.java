/*
 *** Umail **
 *
 *@author Arjun Suthar
 *
 *Class to create the database and interact with it.
 */
 
 import java.sql.*;
 import java.util.*;
 
 public class Database {
 	
 	public Database() {
 		try {
 			Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/?", "root", "root");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS Umail");
            stmt.close();
            conn.close();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/umail", "root", "root");
            stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (usersname VARCHAR(30), userid VARCHAR(30), password VARCHAR(30), mobileNo varchar(11), PRIMARY KEY (userid))");
// 			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS trash (msg_id int, tag varchar(20), PRIMARY KEY(msg_id))");
 		} catch (ClassNotFoundException | SQLException sqlExc) {
 			sqlExc.printStackTrace();
 		}
 	}
 	
 	// Method for authenticating userid and password of user while logging in
 	synchronized public static boolean hasUser(String userid, String pwd) {
 		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/umail", "root", "root");
 			 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE userid=? AND password=?");
 			 ) {
 			pstmt.setString(1, userid);
 			pstmt.setString(2, pwd);
 			ResultSet rst = pstmt.executeQuery();
 			if(rst.next()){
 				return true;
 			} else {
 				return false;
 			}
 		} catch (SQLException sqlExc) {
 			sqlExc.printStackTrace();
 		}
 		return false;
 	}
 	
 	//Method to check whether a user with given userid exists or not
 	synchronized public static boolean hasUser(String userid) {
 		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/umail", "root", "root");
 			 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE userid=?");
 			 ) {
 			pstmt.setString(1, userid);
 			ResultSet rst = pstmt.executeQuery();
 			if(rst.next()){
 				return true;
 			} else {
 				return false;
 			}
 		} catch (SQLException sqlExc) {
 			sqlExc.printStackTrace();
 		}
 		return false;
 	}
 	
 	//Method to add a new user in the database
 	synchronized public static boolean addUser(String usersname, String userid, String pwd, String mobileNo) {
 			try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/umail", "root", "root");
 			 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users values(?,?,?,?)");
 			 Statement stmt = conn.createStatement();
 			 ) {
 			pstmt.setString(1, usersname);
 			pstmt.setString(2, userid);
 			pstmt.setString(3, pwd);
 			pstmt.setString(4, mobileNo);
 			pstmt.executeUpdate();
 			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " +userid.substring(0,userid.length()-10)+ " (msg_id int AUTO_INCREMENT, from_name VARCHAR(30), from_userid VARCHAR(30), to_userid VARCHAR(30),"
            					+ "tag VARCHAR(20), subject VARCHAR(500), content VARCHAR(3000), msg_timestamp timestamp, PRIMARY KEY(msg_id))");
 			return true;
 		} catch (SQLException sqlExc) {
 			sqlExc.printStackTrace();
 			return false;
 		}
 	}
 	
 	//Method to add a message in the database tagged with the attribute passed as tag.
 	synchronized public static void saveMessageAs(String tag, String from, String to, String cc, String bcc, String subject, String content) {
 		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Umail", "root", "root");
 			 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO " +from.substring(0,from.length()-10)+ " (from_userid, to_userid, tag, subject, content, msg_timestamp)"
 			 												+" values(?,?,?,?,?,?)");
 			 ) {
 			 	to += "," +cc+ "," +bcc;
 				pstmt.setString(1, from);
 				pstmt.setString(2, to);
 				pstmt.setString(3, tag);
 				pstmt.setString(4, subject);
 				pstmt.setString(5, content);
 				pstmt.setTimestamp(6, new Timestamp(new java.util.Date().getTime()));
 				pstmt.executeUpdate();
System.out.println("Saved Mail As....");
 		} catch (SQLException sqlExc) {
 			sqlExc.printStackTrace();
 		}
 	}
 	
 	//Method to send message.
 	synchronized public static void sendMessage(String from, String to, String cc, String bcc, String subject, String content) {
 		String forwardedTo = to + "," + cc;
 		sendTo(from, to, forwardedTo, subject, content);
 		StringTokenizer stroker=null;
 		if(cc.length()>0) {
 			stroker = new StringTokenizer(cc);
 			while(stroker.hasMoreTokens()) {
 				String token = stroker.nextToken();
 				sendTo(from, token, forwardedTo, subject, content);
 			}
 		}
 		if(bcc.length()>0) {
 			stroker = new StringTokenizer(bcc);
 			while(stroker.hasMoreTokens()) {
 				String token = stroker.nextToken();
 				sendTo(from, token, token, subject, content);
 			}
 		}
System.out.println("Sent Mail....");
 	}
 	
 	synchronized public static void sendTo(String from, String to, String forwardedTo, String subject, String content) {
 		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Umail", "root", "root");
 			 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO " +to.substring(0,to.length()-10)+ " (from_userid, to_userid, tag, subject, content, msg_timestamp)"
 			 												+" values(?,?,?,?,?,?)");
 			 ) {
 				pstmt.setString(1, from);
 				pstmt.setString(2, forwardedTo);
 				pstmt.setString(3, "inbox");
 				pstmt.setString(4, subject);
 				pstmt.setString(5, content);
 				pstmt.setTimestamp(6, new Timestamp(new java.util.Date().getTime()));
 				pstmt.executeUpdate();
System.out.println("Sent Mail....");
 		} catch (SQLException sqlExc) {
 			sqlExc.printStackTrace();
 		}
 	}
 }