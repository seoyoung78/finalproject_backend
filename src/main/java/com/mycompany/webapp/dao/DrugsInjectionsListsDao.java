package com.mycompany.webapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.webapp.dto.DrugsInjectionsLists;

@Mapper
public interface DrugsInjectionsListsDao {

	
//	public List<DrugsInjectionsLists> selectByDruglist(String keyword);
	
	public List<DrugsInjectionsLists> selectByDruglist(@Param("keyword") String keyword, @Param("condition") String condition);
}
