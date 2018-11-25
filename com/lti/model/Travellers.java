package com.lti.model;

/**
 * it consists of bus seat booked travellers details
 * @author 10649718
 *
 */
public class Travellers {
	private int PNRNo;
	private String name;
	private String gender;
	private long mobile;
	private String email;
	private int age;
	private String status;
	private int copass;
	private double totalfare;

	public int getPNRNo() {
		return PNRNo;
	}
	public void setPNRNo(int pNRNo) {
		PNRNo = pNRNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public long getMobile() {
		return mobile;
	}
	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCopass() {
		return copass;
	}
	public void setCopass(int copass) {
		this.copass = copass;
	}
	public double getTotalfare() {
		return totalfare;
	}
	public void setTotalfare(double totalfare) {
		this.totalfare = totalfare;
	}
}
