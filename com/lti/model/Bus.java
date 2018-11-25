package com.lti.model;

/**
 * It consists of Bus Management details for admin purpose
 * @author 10649718
 *
 */
public class Bus {
	private int busno;
	private String bustype;
	private String ac;

	public void setAc(String ac) {
		this.ac = ac;
	}
	public int getBusno() {
		return busno;
	}
	public void setBusno(int busno) {
		this.busno = busno;
	}
	public String getBustype() {
		return bustype;
	}
	public void setBustype(String bustype) {
		this.bustype = bustype;
	}
	public String getAc() {
		return ac;
	}
	public Bus() {
		super();
	}
}
