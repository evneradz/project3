
import java.io.*;
import java.util.*;
 
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import java.sql.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;



public class login extends HttpServlet {

private static final String LOGIN_QUERY = "select * from logintable where username=? and password=?";
private static final String HOME_PAGE = "start";
private static final String LOGIN_PAGE = "test.jsp";
private Connection getConnection() throws Exception {
   Connection conn = null;
   try {
     String url = "jdbc:mysql://localhost/login";       
     Class.forName("com.mysql.jdbc.Driver");
     conn = DriverManager.getConnection(url,"root","%ev6705");

   } catch (SQLException sqle) {
      System.out.println("SQLException: Unable to open connection to db: "+sqle.getMessage());
      throw sqle;
   } catch(Exception e) {
      System.out.println("Exception: Unable to open connection to db: "+e.getMessage());
      throw e;
   }
   return conn;
 }

private boolean authenticateLogin(String strUserName, String strPassword) throws Exception {
  boolean isValid = false;
  Connection conn = null;
  try {
    conn = getConnection();
    PreparedStatement prepStmt = conn.prepareStatement(LOGIN_QUERY);
    prepStmt.setString(1, strUserName);
    prepStmt.setString(2, strPassword);
    ResultSet rs = prepStmt.executeQuery();
    if(rs.next()) {
      System.out.println("User login is valid in DB");
      isValid = true;  
    }
 } catch(Exception e) {
   System.out.println("validateLogon: Error while validating password: "+e.getMessage());
   throw e;
 } finally {
    conn.close();
 }
 return isValid;
}

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
private boolean register(String strUserName, String strPassword) throws Exception {
  boolean isValid = true;
  Connection conn = null;
try{
conn = getConnection();
PreparedStatement pst = conn.prepareStatement("insert into logintable values (?,?)");
pst.setString(1, strUserName);
pst.setString(2, strPassword);
int rs =  pst.executeUpdate();

 } catch(Exception e) {
   System.out.println("validateLogon: Error while validating password: "+e.getMessage());
   throw e;
 } finally {
    conn.close();
 }
 return isValid;



}



protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  String strUserName = request.getParameter("userName");
  String strPassword = request.getParameter("password");
  String button = request.getParameter("but");
 String msg = request.getParameter("errorMsg");

  String strErrMsg = null;
  HttpSession session = request.getSession();
  boolean isValidLogon = false;
 boolean isValidreg = false;

if(button.equals("Login")){
  try {
    isValidLogon = authenticateLogin(strUserName, strPassword);
    if(isValidLogon) {
       session.setAttribute("userName", strUserName);
    } else {
       strErrMsg = "User name or Password is invalid. Please try again.";
    }
  } catch(Exception e) {
    strErrMsg = "Unable to validate user / password in database";
  }

  if(isValidLogon) {
    response.sendRedirect(HOME_PAGE);
  } else {
    session.setAttribute("errorMsg", strErrMsg);
    response.sendRedirect(LOGIN_PAGE);
  }
}
else if(button.equals("Register")){

try {
    isValidreg = register(strUserName, strPassword);
    if(isValidreg) {
       session.setAttribute("userName", strUserName);
	   
    } else {
       strErrMsg = "User name or Password is invalid. Please try again.";
    }
  } catch(Exception e) {
    strErrMsg = "Unable to validate user / password in database";
  }
  if(isValidreg) {
    response.sendRedirect(LOGIN_PAGE);
  } else {
    session.setAttribute("errorMsg", strErrMsg);
  response.sendRedirect(LOGIN_PAGE);
  }



}

}


}



 
