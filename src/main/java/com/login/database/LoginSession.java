package com.login.database;

import java.math.BigInteger;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.login.dto.Login;
import com.login.dto.Signup;
import com.login.table.Name;
import com.login.table.User;
import com.login.password.hash.HashStorage;
import com.login.files.text.HashTextFile;

/**
 * @author Osman Hussein
 * This is for both login and signup sessions
 * This handles all of the database stuff
 */
public class LoginSession{
	private Session session = null;
	public LoginSession(){
		createSession();
	}
	
	/**
	 * Creates a new session, called by the constructor
	 */
	public void createSession(){
		if(session!=null && session.isOpen()) session.close();
		
		Configuration conf = new Configuration().configure().addAnnotatedClass(User.class).addAnnotatedClass(Name.class); //add the classes which make up the table
		
		/*
		 * check http://docs.jboss.org/hibernate/core/4.0/devguide/en-US/html/ch07.html#d0e3232
		 * for more information on how are service registry built
		 */
		ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build(); 
		SessionFactory sf = conf.buildSessionFactory(sr);
		session = sf.openSession();
		
	}
	
	/**
	 * 
	 * @return the session instance variable
	 */
	public Session getSession(){
		return this.session;
	}
	
	
	/**
	 * 
	 * @param signup
	 *        the signup data transfer object
	 * @return the user id
	 */
	public int addUser(Signup signup){
		Transaction transaction = session.beginTransaction();
		Name name = new Name();
		name.setFirstname(signup.getFirstname());
		name.setSurname(signup.getSurname());
		
		User user = new User();
		user.setUsername(signup.getUsername());
		user.setPassword(signup.getPassword());
		user.setName(name);
		session.save(user);
		transaction.commit();
		return user.getUserid();
	}
	
	/**
	 * 
	 * @param login
	 *        The login data transfer object
	 * @return true if credentials are correct
	 */
	public boolean correctCredentials(Login login){
		Transaction transaction = session.beginTransaction();
		SQLQuery q = session.createSQLQuery("select userid, password from user where username=?");
		q.setParameter(0, login.getUsername());
		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		Map<String, Object> user = (Map<String, Object>)q.uniqueResult();
		int userid = (Integer)user.get("userid");
		String password = (String)user.get("password"); //this is the hashed password
		transaction.commit();
		
		
		HashTextFile htFile = new HashTextFile("salt.txt");
		byte[] salt = htFile.retrieveSalt(userid);
		return HashStorage.checkHashedPassword(login.getPassword(), salt, password.getBytes());
	}
	
	/**
	 * 
	 * @param login
	 *        The login data transfer object
	 * @return the first name of the person logging in
	 */
	public String getFirstname(Login login){
		Transaction transaction = session.beginTransaction();
		SQLQuery q = session.createSQLQuery("select firstname from user where username=?"); //the username is unique but is not the primary key
		q.setParameter(0, login.getUsername());
		String firstname = (String)q.uniqueResult();
		transaction.commit();
		return firstname;
	}
	
	/**
	 * 
	 * @param username
	 *        The username in the text field
	 * @return true if the username exists otherwise false
	 */
	public boolean usernameExists(String username){
		//the username should be unique for every user even though it is not the primary key.
		Transaction transaction = session.beginTransaction();
		SQLQuery q = session.createSQLQuery("select count(*) from user where username=?");
		q.setParameter(0, username);
		BigInteger count = (BigInteger)q.uniqueResult();
		transaction.commit();
		return count.compareTo(new BigInteger("0"))==1; //checks if the count is more than zero
	}
	
	/**
	 * Closes the Hibernate session
	 */
	public void closeSession(){
		if(session!=null && session.isOpen()) session.close();
	}
}
