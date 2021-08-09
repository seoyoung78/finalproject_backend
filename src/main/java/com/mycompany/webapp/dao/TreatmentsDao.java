package com.mycompany.webapp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.webapp.dto.Registers;
import com.mycompany.webapp.dto.Treatments;


@Mapper
public interface TreatmentsDao {
	
//	public List<Treatments> selectAllTreatment(@Param("register_starttime")String date_time,@Param("state") String state);
//	 public List<Treatments> selectAllTreatment(); 
	
	/* public int insert(Treatments treatment); */
	
	public int update(Treatments treatment);
	
	public int update1(Treatments treatment);

	public List<Treatments> selectByPatientId(int treatment_patient_id);

	public List<Treatments> selectByTreatmentSoap(int treatment_id);

//	public Treatments selectByTreatment(int treatment_id);
	
	public List<Treatments> selectTreatments(@Param("treatmentDate") String treatmentDate, @Param("state") String state);
	
	public int updateIstateW(int treatmentId);

	public int updateIstateI(int treatmentId);
	
	public int updateIstateC(int treatmentId);

	//진료 시작시 생성되는 함수
	public int insertNewTreatment(Registers register);

	public List<Treatments> selectAllTreatment(@Param("register_starttime")String date_time,@Param("state") String state,@Param("globalUid") String globalUid);

	
	
}
