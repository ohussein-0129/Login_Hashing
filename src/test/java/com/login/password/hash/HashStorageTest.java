package com.login.password.hash;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import junit.framework.TestCase;

public class HashStorageTest extends TestCase{
	public void testHashStorage(){
		char[] correctPassword = "correct".toCharArray();
		char[] wrongPassword = "wrong".toCharArray();
		byte[] salt = HashStorage.generateSalt();
		
		try {
			byte[] original = HashStorage.hashPassword(correctPassword, salt, 600, 256);
			assertEquals(true, HashStorage.checkHashedPassword(correctPassword, salt, original));
			assertEquals(false, HashStorage.checkHashedPassword(wrongPassword, salt, original));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}
	
}
