package com.lti.model;

/**
 * It is booked seats details
 * @author 10649718
 *
 */
public class BusSeats {


	public BusSeats() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getPnrno() {
		return pnrno;
	}
	public void setPnrno(int pnrno) {
		this.pnrno = pnrno;
	}
	private int pnrno;
	private int busno;
	private String seatno;
	public int getBusno() {
		return busno;
	}
	public void setBusno(int busno) {
		this.busno = busno;
	}
	public String getSeatno() {
		return seatno;
	}
	public void setSeatno(String seatno) {
		this.seatno = seatno;
	}


}
