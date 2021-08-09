package com.mycompany.webapp.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Registers {
    // Original DB Data
	private int register_id;
	private int register_patient_id;
	private String register_user_id;
	private String register_regdate;
	private String register_date;
	private String register_starttime;
	private String register_memo;
	private String register_communication;
	private String register_state;
	
	// Add Data
	private String patient_name;
	private String patient_ssn;
	private String patient_sex;
	private String patient_tel;
	
	private String user_name;
	
	
	public int getRegister_id() {
		return register_id;
	}
	public void setRegister_id(int register_id) {
		this.register_id = register_id;
	}
	public int getRegister_patient_id() {
		return register_patient_id;
	}
	public void setRegister_patient_id(int register_patient_id) {
		this.register_patient_id = register_patient_id;
	}
	public String getRegister_user_id() {
		return register_user_id;
	}
	public void setRegister_user_id(String register_user_id) {
		this.register_user_id = register_user_id;
	}
	public String getRegister_regdate() {
		return register_regdate;
	}
	public void setRegister_regdate(String register_regdate) {
		this.register_regdate = register_regdate;
	}
	public String getRegister_date() {
		return register_date;
	}
	public void setRegister_date(String register_date) {
		this.register_date = register_date;
	}
	public String getRegister_starttime() {
		return register_starttime;
	}
	public void setRegister_starttime(String register_starttime) {
		this.register_starttime = register_starttime;
	}
	public String getRegister_memo() {
		return register_memo;
	}
	public void setRegister_memo(String register_memo) {
		this.register_memo = register_memo;
	}
	public String getRegister_communication() {
		return register_communication;
	}
	public void setRegister_communication(String register_communication) {
		this.register_communication = register_communication;
	}
	public String getRegister_state() {
		return register_state;
	}
	public void setRegister_state(String register_state) {
		this.register_state = register_state;
	}
	
	//Add Data
	public String getPatient_name() {
		return patient_name;
	}
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	public String getPatient_ssn() {
		return patient_ssn;
	}
	public void setPatient_ssn(String patient_ssn) {
		this.patient_ssn = patient_ssn;
	}
	public String getPatient_sex() {
		return patient_sex;
	}
	public void setPatient_sex(String patient_sex) {
		this.patient_sex = patient_sex;
	}
	public String getPatient_tel() {
		return patient_tel;
	}
	public void setPatient_tel(String patient_tel) {
		this.patient_tel = patient_tel;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
}
