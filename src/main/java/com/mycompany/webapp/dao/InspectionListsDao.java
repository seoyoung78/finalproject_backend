package com.mycompany.webapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


import com.mycompany.webapp.dto.InspectionLists;

@Mapper
public interface InspectionListsDao {

	public List<InspectionLists> selectByInspectionlist();
	
//	public List<InspectionLists> selectByInspectionlist(String categoryValue);

}
