package com.login.password.hash;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;;
public class HashStorage {
	
	/**
	 *  Source: https://www.owasp.org/ which is a good site for those interested in security
	 */
	public static byte[] hashPassword(final char[] password, final byte[] salt, final int iterations, final int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException{
    	SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
    	PBEKeySpec pbe = new  PBEKeySpec(password, salt, iterations, keyLength);
    	SecretKey sk = skf.generateSecret(pbe);
    	byte[] b = sk.getEncoded();
    	Arrays.fill(password, '0');
    	return b;
    }
	
	/**
	 * 
	 * @param password has to be a character array so that it can be cleared once processed
	 * @param salt
	 * @param original hash
	 * @return
	 */
	public static boolean checkHashedPassword(final char[] password, final byte[] salt, final byte[] original){
    	byte[] hashed = null;
    	try{
    		hashed = hashPassword(password, salt, 600, 256);
    		Arrays.fill(password, '0');
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	for(int i=0; i<original.length; i++){
    		byte ib = hashed[i];
    		if(original[i]==63 && (ib==-99 || ib==-112 || ib==-113 || ib==-115)){
    			//keep looping
    			//the database seem to convert these characters to 63 (all question marks). All other byte codes are fine
    			//not much sacrifice as the unique salt will still do its job
    		}
    		else if(original[i]!=hashed[i]){
    			return false;
    		}
    	}
    	return true;
    }
	
	/*
	 * Each user will get a unique salt
	 * It will make it more difficult to get all passwords from the database if broken in
	 */
	public static byte[] generateSalt(){
    	SecureRandom random = new SecureRandom();
    	byte[] b = new byte[32];
        random.nextBytes(b);
        
        /*removing all whitespace characters which will help when retrieving hash salts without much sacrifice
         * refer to the retrieveHash(String fileName, int userid) function 
         */
        byte[] bWhiteSpace = new String(b).replaceAll("\\s", "").getBytes();
        return bWhiteSpace;
    }
}

