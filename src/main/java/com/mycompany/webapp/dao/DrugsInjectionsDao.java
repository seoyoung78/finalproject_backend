package com.mycompany.webapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.DrugsInjections;
import com.mycompany.webapp.dto.Patients;

@Mapper
public interface DrugsInjectionsDao {

	public List<DrugsInjections> selectByTreatmentDrugsInjection(int treatment_id);

	public int insertDrugsInjections(DrugsInjections drugsInjections);

}
