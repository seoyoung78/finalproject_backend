package com.mycompany.webapp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.webapp.dto.Users;

@Mapper
public interface UsersDao {

	public List<Users> selectAllUser();
	public List<Users> selectAllDoctors();
	
	// 서영
	public List<Users> selectUsers(@Param("keyword") String keyword, @Param("condition") String condition);
	public void updateUser(Users user);
	public void insertUser(Users user);
	public Users selectUser(String user_id);
	public void deleteUser(String user_id);
	public void updateEnabled(Users user);
	
	//지현
//	public List<Users> getInspectorId();
	public List<Users> getBloodInspectorId();
	public List<Users> getImgInspectorId();
	public int selectSsnUnique(String user_ssn2);
}
