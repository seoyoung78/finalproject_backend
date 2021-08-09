package com.mycompany.webapp.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.controller.TreatmentController;
import com.mycompany.webapp.dao.DrugsInjectionsDao;
import com.mycompany.webapp.dao.DrugsInjectionsListsDao;
import com.mycompany.webapp.dao.InspectionListsDao;
import com.mycompany.webapp.dao.InspectionsDao;
import com.mycompany.webapp.dao.TreatmentsDao;
import com.mycompany.webapp.dao.UsersDao;
import com.mycompany.webapp.dto.DrugsInjections;
import com.mycompany.webapp.dto.DrugsInjectionsLists;
import com.mycompany.webapp.dto.InspectionLists;
import com.mycompany.webapp.dto.Inspections;
import com.mycompany.webapp.dto.Patients;
import com.mycompany.webapp.dto.Treatments;
import com.mycompany.webapp.dto.Users;


@Service
public class TreatmentsService {
	private static final Logger logger = LoggerFactory.getLogger(TreatmentsService.class);
	@Autowired
	private TreatmentsDao treatmentsDao;
	@Autowired
	private DrugsInjectionsListsDao drugsInjectionsListsDao;
	@Autowired
	private InspectionListsDao inspectionListsDao;
	@Autowired
	private InspectionsDao inspectionsDao;
	@Autowired
	private DrugsInjectionsDao drugsInjectionsDao;
	@Autowired
	private UsersDao usersDao;
	
//	public List<Treatments> getAllTreatment(String date_time, String state) {
//		List<Treatments> treatmentslist = treatmentsDao.selectAllTreatment(date_time, state);
//		return treatmentslist;
//	}
	public List<Treatments> getAllTreatment(String date_time, String state,String globalUid) {
		List<Treatments> treatmentslist = treatmentsDao.selectAllTreatment(date_time, state, globalUid);
		return treatmentslist;
	}
	
//	 public List<Treatments> getAllTreatment() { List<Treatments> treatmentslist =
//	 treatmentsDao.selectAllTreatment(); return treatmentslist; }
	 
	
	/*
	 * public int insert(Treatments treatment) { int result =
	 * treatmentsDao.insert(treatment); return result; }
	 */	
	
	public int update(Treatments treatment) {
//		logger.info("맵맵맵");
		return treatmentsDao.update(treatment);
	}
	
	public int update1(Treatments treatment) {
		// TODO Auto-generated method stub
		return treatmentsDao.update1(treatment);
	}



	public List<Treatments> getHistoryList(int treatment_patient_id) {
		List<Treatments> list = treatmentsDao.selectByPatientId(treatment_patient_id);

		return list;
	}


//	public Treatments getTreatment(int treatment_id) {
//		// TODO Auto-generated method stub
//		return treatmentsDao.selectByTreatment(treatment_id);
//	}


	public List<Treatments> getTreatmentSoap(int treatment_id) {
		List<Treatments> list = treatmentsDao.selectByTreatmentSoap(treatment_id);
		return list;
	}


	
//	public List<DrugsInjectionsLists> getDrug(String keyword) {
////		logger.info("asddsdddaaaaa"+keyword);
//		List<DrugsInjectionsLists> list = drugsInjectionsListsDao.selectByDruglist(keyword);
////		logger.info("aaaaaaaaa"+list.get(1).getDrug_injection_list_category());
//		return list;
//	}
	
	public List<DrugsInjectionsLists> getDrug(String keyword, String condition) {
//		logger.info("asddsdddaaaaa"+keyword);
		List<DrugsInjectionsLists> list = drugsInjectionsListsDao.selectByDruglist(keyword, condition);
//		logger.info("aaaaaaaaa"+list.get(1).getDrug_injection_list_category());
		return list;
	}


	
//	public List<InspectionLists> getInspection(String categoryValue) {
////		logger.info("서비스 카테고리"+categoryValue);
//		List<InspectionLists> list = inspectionListsDao.selectByInspectionlist(categoryValue);
////		logger.info("서비스 리스트"+list);
//		return list;
//	}
	public List<InspectionLists> getInspection() {
//		logger.info("서비스 카테고리"+categoryValue);
		List<InspectionLists> list = inspectionListsDao.selectByInspectionlist();
//		logger.info("서비스 리스트"+list);
		return list;
	}


	public List<Inspections> getTreatmentInspection(int treatment_id) {
		List<Inspections> list = inspectionsDao.selectByTreatmentInspection(treatment_id);
		return list;
	}


	public List<DrugsInjections> getTreatmentDrugsInjection(int treatment_id) {
		List<DrugsInjections> list = drugsInjectionsDao.selectByTreatmentDrugsInjection(treatment_id);
		return list;
	}

//
//	public int createDrugsInjections(String drugsInjections) {
//		int result = drugsInjectionsDao.insertDrugsInjections(drugsInjections);
//		return result;
//	}
//	
	public int createDrugsInjections(List<DrugsInjections> drugsInjections) {
		int result = 0;
		for(int i = 0; i<drugsInjections.size();i++) {
			result = drugsInjectionsDao.insertDrugsInjections(drugsInjections.get(i));
		}
		
		return result;
	}


	public int createInspections(List<Inspections> inspections) {
		int result = 0;
		int count = 0;
		for(int i = 0; i< inspections.size(); i++) {
			count = inspectionsDao.insertInspections(inspections.get(i));
			result = result + count;
		}
		//logger.info("cpun"+count);
		return count;
	}
	public int createInspections2(List<Inspections> InspectionList2) {
		int result = 0;
		int count = 0;
		for(int i = 0; i< InspectionList2.size(); i++) {
			count = inspectionsDao.insertInspections2(InspectionList2.get(i));
			result = result + count;
		}
		return count;
	}

//	public int createInspections(String string) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

	public List<Users> getBloodInspectorId() {
		List<Users> BloodInspectorId = usersDao.getBloodInspectorId();
		return BloodInspectorId;
	}
	
	public List<Users> getImgInspectorId() {
		List<Users> ImgInspectorId = usersDao.getImgInspectorId();
		return ImgInspectorId;
	}



	


}
