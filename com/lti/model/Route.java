package com.lti.model;

/**
 * it consists of Route management details 
 * @author 10649718
 *
 */
public class Route {

	private int busno;
	private String source;
	private String destination;
	private String depttime;
	private String arrtime;
	private double fare;
	private String day;
	public Route() {
		super();
	}
	public int getBusno() {
		return busno;
	}
	public void setBusno(int busno) {
		this.busno = busno;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getDepttime() {
		return depttime;
	}
	public void setDepttime(String depttime) {
		this.depttime = depttime;
	}
	public String getArrtime() {
		return arrtime;
	}
	public void setArrtime(String arrtime) {
		this.arrtime = arrtime;
	}
	public double getFare() {
		return fare;
	}
	public void setFare(double fare) {
		this.fare = fare;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
}
