package com.mycompany.webapp.dto;


public class Schedules {	
	private int schedule_id;
	private String schedule_user_id;
	private String schedule_content;
	private String schedule_state;
	private String schedule_regdate;
	
	public int getSchedule_id() {
		return schedule_id;
	}
	
	public void setSchedule_id(int schedule_id) {
		this.schedule_id = schedule_id;
	}
	
	public String getSchedule_user_id() {
		return schedule_user_id;
	}
	
	public void setSchedule_user_id(String schedule_user_id) {
		this.schedule_user_id = schedule_user_id;
	}
	
	public String getSchedule_content() {
		return schedule_content;
	}
	
	public void setSchedule_content(String schedule_content) {
		this.schedule_content = schedule_content;
	}
	
	public String getSchedule_state() {
		return schedule_state;
	}
	
	public void setSchedule_state(String schedule_state) {
		this.schedule_state = schedule_state;
	}
	
	public String getSchedule_regdate() {
		return schedule_regdate;
	}
	
	public void setSchedule_regdate(String schedule_regdate) {
		this.schedule_regdate = schedule_regdate;
	}
	
}
