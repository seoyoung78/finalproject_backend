package com.mycompany.webapp.controller;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.DrugsInjections;
import com.mycompany.webapp.dto.DrugsInjectionsLists;
import com.mycompany.webapp.dto.InspectionLists;
import com.mycompany.webapp.dto.Inspections;
import com.mycompany.webapp.dto.Treatments;
import com.mycompany.webapp.dto.Users;
import com.mycompany.webapp.service.TreatmentsService;
import com.mycompany.webapp.twilio.SendMessage;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/treatment")
public class TreatmentController {
	private static final Logger logger = LoggerFactory.getLogger(TreatmentController.class);

	@Autowired
	private TreatmentsService treatmentsService;
	
	private SendMessage msg = new SendMessage();

	/* 진료대기환자 리스트 */
	@GetMapping("/treatmentlist")
	public void list(HttpServletRequest request, HttpServletResponse response,String date, 
			@RequestParam(defaultValue = "") String state, @RequestParam String globalUid) {

		// 해당 날짜의 접수 내역 불러오기
		List<Treatments> treatmentlist = treatmentsService.getAllTreatment(date, state, globalUid);
		for (int i = 0; i < treatmentlist.size(); i++) {
			treatmentlist.get(i).setPatient_ssn(treatmentlist.get(i).getPatient_ssn().split("-")[0]);
		}

		response.setContentType("application/json;charset=UTF-8");

		JSONObject jObj = new JSONObject();
		jObj.put("treatmentlist", treatmentlist);
		try {
			Writer writer = response.getWriter();
			writer.write(jObj.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/* 약/주사 키워드 검색 */
	@GetMapping("/keyword")
	public void searchDrug(@RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = "") String condition, HttpServletRequest request,
			HttpServletResponse response) {
		// logger.info("qtqtqt"+keyword);
		List<DrugsInjectionsLists> druglist = treatmentsService.getDrug(keyword, condition);
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jObj = new JSONObject();
		jObj.put("druglist", druglist);
		try {
			Writer writer = response.getWriter();
			writer.write(jObj.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* 진단 검사별 검사 리스트 */
//	@GetMapping("/categoryValue")
//	public void categoryInspectionList(@RequestParam String categoryValue,
//			HttpServletRequest request, HttpServletResponse response) {
//		List<InspectionLists> inspectionList = treatmentsService.getInspection(categoryValue);
//		response.setContentType("application/json;charset=UTF-8");
//		JSONObject jObj = new JSONObject();
//		jObj.put("inspectionList", inspectionList);
//		try {
//			Writer writer = response.getWriter();
//			writer.write(jObj.toString());
//			writer.flush();
//			writer.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	@GetMapping("/categoryValue")
	public void categoryInspectionList(HttpServletRequest request, HttpServletResponse response) {
		List<InspectionLists> inspectionList = treatmentsService.getInspection();
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jObj = new JSONObject();
		jObj.put("inspectionList", inspectionList);
		try {
			Writer writer = response.getWriter();
			writer.write(jObj.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* 환자 번호 별 진료 기록리스트 */
	@GetMapping("/historyList")
	public void read(String treatment_patient_id, HttpServletRequest request,
			HttpServletResponse response) {
		
		int treatmentPatientId = Integer.parseInt(treatment_patient_id);
		List<Treatments> historylist = treatmentsService.getHistoryList(treatmentPatientId);
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jObj = new JSONObject();
		jObj.put("historylist", historylist);
		try {
			Writer writer = response.getWriter();
			writer.write(jObj.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/* 진료 번호 별 진료 상세 보기 (soap, 검사기록, 약처방기록) */
	@GetMapping("/historyRead")
	public void historyread(String treatment_id, HttpServletRequest request,
			HttpServletResponse response) {
		
		int treatmentId = Integer.parseInt(treatment_id);
		List<Treatments> treatmentSoaplist = treatmentsService.getTreatmentSoap(treatmentId);
		List<Inspections> treatmentInspectionlist = treatmentsService.getTreatmentInspection(treatmentId);
		List<DrugsInjections> treatmentDrugsInjectionlist = treatmentsService.getTreatmentDrugsInjection(treatmentId);

		response.setContentType("application/json;charset=UTF-8");
		JSONObject jObj = new JSONObject();
		jObj.put("treatmentSoaplist", treatmentSoaplist);
		jObj.put("treatmentInspectionlist", treatmentInspectionlist);
		jObj.put("treatmentDrugsInjectionlist", treatmentDrugsInjectionlist);
		try {
			Writer writer = response.getWriter();
			writer.write(jObj.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PutMapping("")
	public void update(@RequestBody Treatments treatment, HttpServletResponse response) {

		List<Inspections> InspectionList = new ArrayList<Inspections>();
		List<Inspections> InspectionList2 = new ArrayList<Inspections>();
		List<DrugsInjections> DrugInjectionsList = new ArrayList<DrugsInjections>();
		List<Users> Userlist = new ArrayList<Users>();
		List<Users> Userlist2 = new ArrayList<Users>();
		Userlist = treatmentsService.getBloodInspectorId();
		Userlist2 = treatmentsService.getImgInspectorId();
		//logger.info("userlist///" + Userlist);
		//logger.info("userlist:2" + Userlist2);
		Collections.shuffle(Userlist);
		Collections.shuffle(Userlist2);
		//logger.info("//////Collections.shuffle(Userlist)/////");
		//logger.info("userlist///" + Userlist);
		//logger.info("userlist:2" + Userlist2);

		int Inspection_id = 0;
		for (int i = 0; i < treatment.getSelectedInspection().length; i++) {
			Inspections newInspection = new Inspections();
			newInspection.setInspection_patient_id(treatment.getTreatment_patient_id());
			newInspection.setInspection_doctor_id(treatment.getTreatment_user_id());
			newInspection.setInspection_treatment_id(treatment.getTreatment_id());
			newInspection.setInspection_state("대기");
			newInspection.setInspection_result("");
//			newInspection.setInspection_inspector_id("I138010001");
			newInspection.setInspection_list_category(treatment.getInspectionOption());
			Inspection_id = Integer.parseInt(treatment.getSelectedInspection()[i]);
			newInspection.setInspection_inspection_list_id(Inspection_id);
			InspectionList.add(newInspection);
			for (int j = 0; j < Userlist.size(); j++) {
				String value1 = String.valueOf(Userlist.get(j));
				newInspection.setInspection_inspector_id(value1);
			}
		}
		int result2 = treatmentsService.createInspections(InspectionList);

		int Inspection_id2 = 0;
		for (int i = 0; i < treatment.getSelectedInspection2().length; i++) {
			Inspections newInspection2 = new Inspections();
			newInspection2.setInspection_patient_id(treatment.getTreatment_patient_id());
			newInspection2.setInspection_doctor_id(treatment.getTreatment_user_id());
			newInspection2.setInspection_treatment_id(treatment.getTreatment_id());
			newInspection2.setInspection_state("대기");
			newInspection2.setInspection_result("");

			newInspection2.setInspection_list_category(treatment.getInspectionOption());
			Inspection_id2 = Integer.parseInt(treatment.getSelectedInspection2()[i]);
			newInspection2.setInspection_inspection_list_id(Inspection_id2);
			InspectionList2.add(newInspection2);
			for (int j = 0; j < Userlist2.size(); j++) {
				String value2 = String.valueOf(Userlist2.get(j));
				newInspection2.setInspection_inspector_id(value2);
			}
		}
		int result4 = treatmentsService.createInspections2(InspectionList2);
		/* 처방 */
		for (int i = 0; i < treatment.getSelectedDrug().length; i++) {
			DrugsInjections newDrugInjections = new DrugsInjections();
			String Druglist_id = treatment.getSelectedDrug()[i];
			newDrugInjections.setDrug_injection_drug_injection_list_id(Druglist_id);
			newDrugInjections.setDrug_injection_patient_id(treatment.getTreatment_patient_id());
			newDrugInjections.setDrug_injection_id_doctor_id(treatment.getTreatment_user_id());
			newDrugInjections.setDrug_injection_treatment_id(treatment.getTreatment_id());
			DrugInjectionsList.add(newDrugInjections);

		}
		int result3 = treatmentsService.createDrugsInjections(DrugInjectionsList);
		//logger.info("약품" + result3);

		//logger.info("혈액" + result2);
		//logger.info("영상" + result4);

		int result1 = 0;

		/* 검사 */
		result1 = treatmentsService.update(treatment);

		if (result2 == 1 || result4 == 1) {
			result1 = treatmentsService.update1(treatment);
			
//			msg.send("검사가 등록되었습니다.");
		}

		response.setContentType("application/json;charset=UTF-8");
		JSONObject jObj = new JSONObject();
		jObj.put("result1", result1);
		jObj.put("result2", result2);
		jObj.put("result3", result3);
		jObj.put("result4", result4);
		try {
			Writer writer = response.getWriter();
			writer.write(jObj.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
