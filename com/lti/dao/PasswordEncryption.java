package com.lti.dao;

import java.util.Scanner;

public class PasswordEncryption {
	static String Password;

	/**
	 * Encrypt the Password 
	 * @param password
	 * @return
	 */
	public static String Encrypt(String password) {
		char ch[]=password.toCharArray();
		String encrypt="";
		for(int i=0;i<password.length()-1;i=i+2)
		{
			char temp=ch[i];
			ch[i]=ch[i+1];
			ch[i+1]=temp;
		}
		for (int i = 0; i < password.length(); i=i+1) {

			encrypt=encrypt+ch[i];
		}

		return "#$!sew^@"+encrypt+"%4!@";

	}

	/**
	 * Decrypt the Password
	 * @param password
	 * @return
	 */
	public static String Decrypt(String password) {
		char ch[]=password.toCharArray();
		String decrypt="";
		for(int i=0;i<password.length()-1;i=i+2)
		{
			char temp=ch[i];
			ch[i]=ch[i+1];
			ch[i+1]=temp;
		}
		for (int i = 0; i < password.length(); i=i+1) {

			decrypt=decrypt+ch[i];
		}

		return "#$!sew^@"+decrypt+"%4!@";

	}

}
