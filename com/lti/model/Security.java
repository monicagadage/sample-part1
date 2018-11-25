package com.lti.model;

/**
 * It consists of security question details
 * @author 10649718
 *
 */

public class Security {
	private String friend;
	private String movie;
	private String father_year;
	private long Mobile;
	public long getMobile() {
		return Mobile;
	}
	public void setMobile(long mobile) {
		Mobile = mobile;
	}
	public String getFriend() {
		return friend;
	}
	public void setFriend(String friend) {
		this.friend = friend;
	}
	public String getMovie() {
		return movie;
	}
	public void setMovie(String movie) {
		this.movie = movie;
	}
	public String getFather_year() {
		return father_year;
	}
	public void setFather_year(String father_year) {
		this.father_year = father_year;
	}
	public Security() {
		super();
	}

}
