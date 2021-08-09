package com.mycompany.webapp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.HospitalsDao;
import com.mycompany.webapp.dao.UsersDao;
import com.mycompany.webapp.dto.Hospitals;
import com.mycompany.webapp.dto.Users;

@Service
public class UsersService {
	
	@Autowired
	private UsersDao usersDao;
	@Autowired
	private HospitalsDao hospitalsDao; 

	public List<Users> getAllUsers() {
		List<Users> usersList = usersDao.selectAllUser();
		return usersList;
	}
	
	public List<Users> getUsers(String keyword, String condition) {
		List<Users> usersList = usersDao.selectUsers(keyword, condition);
		return usersList;
	}

	public void updateUser(Users user) {
		usersDao.updateUser(user);		
	}

	public void createUser(Users user) {
		usersDao.insertUser(user);
	}	
		
	// 직원 index를 hospital 테이블에서 가져오기
	public int getCount(String hcode, String uauth) {
		int count = hospitalsDao.getCount(hcode, uauth);
		return count;
	}
	
	// 직원 등록 시 index+1
	public void updateUsercount(String hcode, String uauth) {
		hospitalsDao.updateUsercount(hcode, uauth);
	}

	// 회원 정보 수정 시 회원 정보 가져오기
	public Users getUser(String user_id) {
		Users user = usersDao.selectUser(user_id);
		return user;
	}

	// 로그인 시 병원 이름 가져오기
	public String getHname(String hid) {
		return hospitalsDao.getHname(hid);
	}

	public void updateEnabled(Users user) {
		usersDao.updateEnabled(user);
	}

	public Hospitals getHospital(String hid) {
		return hospitalsDao.getHospital(hid);
	}

	public int getSsnUniqe(String user_ssn2) {
		return usersDao.selectSsnUnique(user_ssn2);
	}
}
