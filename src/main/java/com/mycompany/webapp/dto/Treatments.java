package com.mycompany.webapp.dto;

import java.util.Date;

public class Treatments {
	
	private int treatment_id;
	private int treatment_register_id;
	private int treatment_patient_id;
	private String treatment_user_id;
	private String treatment_date;
	private String treatment_smemo;
	private String treatment_omemo;
	private String treatment_amemo;
	private String treatment_pmemo;
	private String treatment_communication;
	private String treatment_state;
	private String treatment_istate;
	private String treatment_type;
	
	private String register_communication;
	private String patient_name;
	private String patient_sex;
	private String patient_ssn;
	
	private String user_name;
	private String register_starttime;
	
	private String[] selectedInspection;
	private String[] selectedInspection2;
	private String[] selectedDrug;
	private String inspectionOption;


	
	
	public String getInspectionOption() {
		return inspectionOption;
	}
	public void setInspectionOption(String inspectionOption) {
		this.inspectionOption = inspectionOption;
	}
	public String[] getSelectedInspection() {
		return selectedInspection;
	}
	public void setSelectedInspection(String[] selectedInspection) {
		this.selectedInspection = selectedInspection;
	}
	public String[] getSelectedDrug() {
		return selectedDrug;
	}
	public void setSelectedDrug(String[] selectedDrug) {
		this.selectedDrug = selectedDrug;
	}

	public String getRegister_starttime() {
		return register_starttime;
	}
	public void setRegister_starttime(String register_starttime) {
		this.register_starttime = register_starttime;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getTreatment_id() {
		return treatment_id;
	}
	public void setTreatment_id(int treatment_id) {
		this.treatment_id = treatment_id;
	}
	public int getTreatment_register_id() {
		return treatment_register_id;
	}
	public void setTreatment_register_id(int treatment_register_id) {
		this.treatment_register_id = treatment_register_id;
	}
	public int getTreatment_patient_id() {
		return treatment_patient_id;
	}
	public void setTreatment_patient_id(int treatment_patient_id) {
		this.treatment_patient_id = treatment_patient_id;
	}
	public String getTreatment_user_id() {
		return treatment_user_id;
	}
	public void setTreatment_user_id(String treatment_user_id) {
		this.treatment_user_id = treatment_user_id;
	}
	public String getTreatment_date() {
		return treatment_date;
	}
	public void setTreatment_date(String treatment_date) {
		this.treatment_date = treatment_date;
	}
	public String getTreatment_smemo() {
		return treatment_smemo;
	}
	public void setTreatment_smemo(String treatment_smemo) {
		this.treatment_smemo = treatment_smemo;
	}
	public String getTreatment_omemo() {
		return treatment_omemo;
	}
	public void setTreatment_omemo(String treatment_omemo) {
		this.treatment_omemo = treatment_omemo;
	}
	public String getTreatment_amemo() {
		return treatment_amemo;
	}
	public void setTreatment_amemo(String treatment_amemo) {
		this.treatment_amemo = treatment_amemo;
	}
	public String getTreatment_pmemo() {
		return treatment_pmemo;
	}
	public void setTreatment_pmemo(String treatment_pmemo) {
		this.treatment_pmemo = treatment_pmemo;
	}
	public String getTreatment_communication() {
		return treatment_communication;
	}
	public void setTreatment_communication(String treatment_communication) {
		this.treatment_communication = treatment_communication;
	}
	public String getTreatment_state() {
		return treatment_state;
	}
	public void setTreatment_state(String treatment_state) {
		this.treatment_state = treatment_state;
	}
	public String getTreatment_istate() {
		return treatment_istate;
	}
	public void setTreatment_istate(String treatment_istate) {
		this.treatment_istate = treatment_istate;
	}
	public String getTreatment_type() {
		return treatment_type;
	}
	public void setTreatment_type(String treatment_type) {
		this.treatment_type = treatment_type;
	}
	public String getRegister_communication() {
		return register_communication;
	}
	public void setRegister_communication(String register_communication) {
		this.register_communication = register_communication;
	}
	public String getPatient_name() {
		return patient_name;
	}
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	public String getPatient_sex() {
		return patient_sex;
	}
	public void setPatient_sex(String patient_sex) {
		this.patient_sex = patient_sex;
	}
	public String getPatient_ssn() {
		return patient_ssn;
	}
	public void setPatient_ssn(String patient_ssn) {
		this.patient_ssn = patient_ssn;
	}
	public String[] getSelectedInspection2() {
		return selectedInspection2;
	}
	public void setSelectedInspection2(String[] selectedInspection2) {
		this.selectedInspection2 = selectedInspection2;
	}
	
	
	
	
}
