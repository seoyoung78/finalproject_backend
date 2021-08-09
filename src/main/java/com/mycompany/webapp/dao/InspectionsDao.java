package com.mycompany.webapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.webapp.dto.Inspections;

@Mapper
public interface InspectionsDao {

	public List<Inspections> selectByTreatmentInspection(int treatment_id);
	public List<Inspections> selectInspections(int treatmentId);
	public int updateState(@Param("inspectionId") int inspectionId, @Param("state") String state);
	public int updateResult(@Param("inspectionId") int inspectionId, @Param("inspectionResult") String inspectionResult);
	public int insertInspections(Inspections inspection);
	public int insertInspections2(Inspections inspection);
}
