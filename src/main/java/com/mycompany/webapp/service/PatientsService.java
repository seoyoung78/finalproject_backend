package com.mycompany.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.PatientsDao;
import com.mycompany.webapp.dto.Patients;

@Service
public class PatientsService {
	@Autowired 
	private PatientsDao patientsDao;

	public List<Patients> getPatients(String keyword) {
		List<Patients> patientsList = patientsDao.selectPatients(keyword);
		return patientsList;
	}

	public void updatePatient(Patients patient) {
		patientsDao.updatePatient(patient);
	}

	public void createPatient(Patients patient) {
		patientsDao.insertPatient(patient);
	}

	public int getCount() {
		int count = patientsDao.getCount();
		return count;
	}

	public int getUnique(String patient_ssn2) {
		return patientsDao.selectUnique(patient_ssn2);
	}

}
