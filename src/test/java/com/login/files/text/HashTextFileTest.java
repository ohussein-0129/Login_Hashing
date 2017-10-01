package com.login.files.text;

import junit.framework.TestCase;

import java.util.Arrays;

import com.login.password.hash.HashStorage;

public class HashTextFileTest extends TestCase{
	public void testHashTextFile(){
		HashTextFile htf = new HashTextFile("salts.txt");
		assertNotNull(htf.retrieveSalt(55));
		
		/*
		 * Best to put a new mock user (shouldn't already exist)
		 */
		byte[] salt = HashStorage.generateSalt();
		htf.storeSalt(salt, 800);
		byte[] retrieved = htf.retrieveSalt(800);
		assertEquals(true, Arrays.equals(salt, retrieved));
	}
}
