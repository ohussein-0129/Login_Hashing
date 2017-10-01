package com.login.process;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.login.database.LoginSession;
import com.login.dto.Signup;
import com.login.password.hash.HashStorage;
import com.login.files.text.HashTextFile;

@WebServlet(urlPatterns="/process-signup")
public class SignupProcess extends HttpServlet{
	private Signup signup = null;
	private byte[] salt = null;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LoginSession loginSession = new LoginSession();
		if(!loginSession.usernameExists(req.getParameter("username"))){
			addDetails(req);
			req.setAttribute("user_firstname", signup.getFirstname());
			int userid = loginSession.addUser(signup);
			saveSalt(userid);
		}
		else{
			//if username already exists
			HttpSession session = req.getSession();
			session.setAttribute("server-error", true);
			resp.sendRedirect("/login/fields/signup.jsp"); //redirect back to the signup page
		}
		loginSession.closeSession();
	}
	
	public void addDetails(HttpServletRequest req){
		signup = new Signup();
		signup.setUsername(req.getParameter("username"));
		//signup.setPassword(req.getParameter("password"));
		signup.setPassword(hashPassword(req.getParameter("password")));
		signup.setFirstname(req.getParameter("firstname"));
		signup.setSurname(req.getParameter("surname"));
	}
	
	public String hashPassword(String password){
		byte[] salt = HashStorage.generateSalt();
		setSalt(salt);
		byte[] hashed = null;
		try {
			hashed = HashStorage.hashPassword(password.toCharArray(), salt, 600, 256);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return new String(hashed);
	}
	
	//saves salt in text file along with the user id
	public void saveSalt(int userid){
		HashTextFile htf = new HashTextFile("somalia.txt");
		htf.storeSalt(getSalt(), userid);
		
	}

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}
	
}
