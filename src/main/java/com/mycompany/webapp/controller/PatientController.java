package com.mycompany.webapp.controller;

import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Patients;
import com.mycompany.webapp.service.PatientsService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/patient")
public class PatientController {
	private static final Logger logger = LoggerFactory.getLogger(PatientController.class);
	
	@Autowired
	private PatientsService patientsService;
	
	//환자 목록
	@GetMapping("")
	public void list(HttpServletRequest request, HttpServletResponse response,
					@RequestParam(defaultValue = "") String keyword) {
		List<Patients> patientList = patientsService.getPatients(keyword);
		
		for (int i = 0; i < patientList.size(); i++) {
			patientList.get(i).setPatient_tel1(patientList.get(i).getPatient_tel().split("-")[0]);
			patientList.get(i).setPatient_tel2(patientList.get(i).getPatient_tel().split("-")[1]);
			patientList.get(i).setPatient_tel3(patientList.get(i).getPatient_tel().split("-")[2]);
			patientList.get(i).setPatient_ssn1(patientList.get(i).getPatient_ssn().split("-")[0]);
			patientList.get(i).setPatient_ssn2(patientList.get(i).getPatient_ssn().split("-")[1]);
		}
		
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jObj = new JSONObject();
		jObj.put("patientList", patientList);
		try {
			Writer writer = response.getWriter();
			writer.write(jObj.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//환자 정보 수정
	@PutMapping("")
	public Patients update(HttpServletRequest request, HttpServletResponse response, @RequestBody Patients patient) {
		patient.setPatient_ssn(patient.getPatient_ssn1() + "-" + patient.getPatient_ssn2());
		patient.setPatient_tel(patient.getPatient_tel1() + "-" + patient.getPatient_tel2() + "-" + patient.getPatient_tel3());
		
		patientsService.updatePatient(patient);
		
		return patient;
	}
	
	//환자 등록
	@PostMapping("") 
	public Map<String, String> create(HttpServletRequest request, HttpServletResponse response, @RequestBody Patients patient) {
		Map<String, String> map = new HashMap<String, String>();
		
		if (patientsService.getUnique(patient.getPatient_ssn2()) == 0) {				
			int count = patientsService.getCount() + 1;
			patient.setPatient_id(count);
			patient.setPatient_ssn(patient.getPatient_ssn1() + "-" + patient.getPatient_ssn2());
			patient.setPatient_tel(patient.getPatient_tel1() + "-" + patient.getPatient_tel2() + "-" + patient.getPatient_tel3());
			
			patientsService.createPatient(patient);
			map.put("result", "success");
		} else {
			map.put("result", "notUnique");	
		}
		return map;
	}
}