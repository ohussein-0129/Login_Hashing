package com.login.process;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.login.database.LoginSession;
import com.login.dto.Login;

@WebServlet(urlPatterns="/process-login")
public class LoginProcess extends HttpServlet{
	private static final long serialVersionUID = 2910861526570419719L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LoginSession loginSession = new LoginSession();
        String username = req.getParameter("username");
        if(!loginSession.usernameExists(username)){
        	//if the username doesn't exist
        	HttpSession session = req.getSession();
			session.setAttribute("server-error", true);
			session.setAttribute("error-type", "username");
			resp.sendRedirect("/login/fields/login-user.jsp"); //redirect back to the login page
			return;
        }
		Login login = new Login();
		login.setUsername(username); 
		login.setPassword(req.getParameter("password").toCharArray());
		boolean correct = loginSession.correctCredentials(login);
		Arrays.fill(login.getPassword(), '0'); //clear the password
		if(correct){
			//if password is correct
			HttpSession session = req.getSession();
			session.setAttribute("firstname", loginSession.getFirstname(login));
			resp.sendRedirect("/login/result/loginresult.jsp"); 
		}
		else{
			//if password is incorrect
			HttpSession session = req.getSession();
			session.setAttribute("server-error", true);
			session.setAttribute("error-type", "password");
			resp.sendRedirect("/login/fields/login-user.jsp");
		}
		loginSession.closeSession();
	}
	
}
