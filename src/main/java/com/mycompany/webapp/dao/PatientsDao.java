package com.mycompany.webapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.Patients;

@Mapper
public interface PatientsDao {

	List<Patients> selectAllPatients();

	// 서영
	public List<Patients> selectPatients(String keyword);
	public void updatePatient(Patients patient);
	public void insertPatient(Patients patient);
	public int getCount();
	public int selectUnique(String patient_ssn2);
}
