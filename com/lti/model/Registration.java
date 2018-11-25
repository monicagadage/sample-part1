package com.lti.model;

import com.lti.dao.PasswordEncryption;
/**
 * It is for registration purpose
 * @author 10649718
 *
 */
public class Registration {

	private String Name;
	private String Gender;
	private long Mobile;
	private String Email;
	private int Age;
	private long AdminMobile;
	private String LoginPassword;

	/**
	 * 
	 * @return
	 */
	public String getLoginPassword() {
		return PasswordEncryption.Decrypt(LoginPassword);
	}
	public void setLoginPassword(String loginPassword) {
		LoginPassword = loginPassword;
	}
	public long getAdminMobile() {
		return AdminMobile;
	}
	public void setAdminMobile(long adminMobile) {
		AdminMobile = adminMobile;
	}


	public void setMobile(long mobile) {
		Mobile = mobile;
	}

	private String Password;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	public long getMobile() {
		return Mobile;
	}

	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public int getAge() {
		return Age;
	}
	public void setAge(int age) {
		Age = age;
	}
	public String getPassword() {

		return PasswordEncryption.Encrypt(Password);
	}

	public void setPassword(String password) {
		Password=password;
	}
	public Registration() {

	}


}
