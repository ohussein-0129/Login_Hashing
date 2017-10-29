package com.login.files.text;

import java.io.*;

/**
 * @author Osman Hussein
 * unique salts will be stored in a text file alongside the userid
 *
 */

public class HashTextFile {
	private String fileName;
	
	public HashTextFile(String fileName){
		this.fileName = fileName;
	}
	
	
	/**
	 * Stores the salt in the text file
	 * @param salt
	 *        random bytes which will be unique for each user
	 * @param userid
	 *        the user id
	 * @return the line which was written
	 */
	public String storeSalt(byte[] salt, int userid){ 
		File file = new File("C:\\Users\\USER-1\\Desktop\\Java Proj\\login\\src\\main\\resources\\hash storage\\" +getFileName());
		String saltStr = new String(salt);
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(new FileOutputStream(file, true));
			printWriter.println(saltStr +" " +userid);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(printWriter!=null){
				printWriter.flush();
				printWriter.close();
			}
		}
		return saltStr +" " +userid;
	}
	
	/**
	 * gets the salt of the user
	 * @param userid
	 * @return the salt which is an array of bytes
	 */
	public byte[] retrieveSalt(int userid){
		byte[] bHash = null;
		BufferedInputStream in = null;
		File file = new File("C:\\Users\\USER-1\\Desktop\\Java Proj\\login\\src\\main\\resources\\hash storage\\" +getFileName());
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			byte[] bFile = new byte[(int)file.length()];
			in.read(bFile,  0,  (int)file.length());
			String bText = new String(bFile);
			
			int useridIndex = bText.indexOf(" " +userid)+1; //the index of the userid of interest
			int startHash = -1;
			
			for(int i=useridIndex; i>=0; i--){
				if(i==0 || bText.charAt(i)=='\n'){
					startHash = i+1;
					break;
				}
			}
			String hash = bText.substring(startHash, useridIndex-1); //array out of bounds exception thrown here if the startHash remains -1 which shouldn't happen
			bHash = hash.getBytes();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bHash;
	}
	
	
	/**
	 * @return the file name instance variable
	 */
	public String getFileName() {
		return fileName;
	}
}
