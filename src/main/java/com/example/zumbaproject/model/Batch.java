package com.example.zumbaproject.model;

public class Batch {
	//Fields
	private int bid;
	private String batchName;
	private String batchTime;
	private Integer capacity;	
	private String instructorName;
	//Constructors
	public Batch() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Batch(int bid, String batchName, String batchTime, Integer capacity,
			String instructorName) {
		super();
		this.bid = bid;
		this.batchName = batchName;
		this.batchTime = batchTime;
		this.capacity = capacity;		
		this.instructorName = instructorName;
	}
	//Getter and Setters
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public String getBatchTime() {
		return batchTime;
	}
	public void setBatchTime(String batchTime) {
		this.batchTime = batchTime;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}	
	public String getInstructorName() {
		return instructorName;
	}
	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}
	//Override toString() method
	@Override
	public String toString() {
		return "Batch [bid=" + bid + ", batchName=" + batchName + ", batchTime=" + batchTime + ", capacity="
				+ capacity + ", instructorName=" + instructorName + "]";
	}
	
}
