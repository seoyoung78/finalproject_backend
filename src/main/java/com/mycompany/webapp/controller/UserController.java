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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Users;
import com.mycompany.webapp.service.UsersService;


@CrossOrigin(origins="*")
@RestController
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UsersService usersService;
		
	//직원 목록
	@GetMapping("")
	public void list(HttpServletRequest request, HttpServletResponse response) {
		List<Users> userList = usersService.getAllUsers();
		
		for (int i = 0; i < userList.size(); i++) {
			userList.get(i).setUser_tel1(userList.get(i).getUser_tel().split("-")[0]);
			userList.get(i).setUser_tel2(userList.get(i).getUser_tel().split("-")[1]);
			userList.get(i).setUser_tel3(userList.get(i).getUser_tel().split("-")[2]);
			userList.get(i).setUser_email1(userList.get(i).getUser_email().split("@")[0]);
			userList.get(i).setUser_email2(userList.get(i).getUser_email().split("@")[1]);
			userList.get(i).setUser_ssn1(userList.get(i).getUser_ssn().split("-")[0]);
			userList.get(i).setUser_ssn2(userList.get(i).getUser_ssn().split("-")[1]);
		}
				
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jObj = new JSONObject();
		jObj.put("userList", userList);
		try {
			Writer writer = response.getWriter();
			writer.write(jObj.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 검색 및 직책 선택 시 직원 목록
	@GetMapping("/select")
	public void list(HttpServletRequest request, HttpServletResponse response, 
					@RequestParam String keyword,
					@RequestParam String condition) {		
		 List<Users> userList = usersService.getUsers(keyword, condition);
			
		for (int i = 0; i < userList.size(); i++) {
			userList.get(i).setUser_tel1(userList.get(i).getUser_tel().split("-")[0]);
			userList.get(i).setUser_tel2(userList.get(i).getUser_tel().split("-")[1]);
			userList.get(i).setUser_tel3(userList.get(i).getUser_tel().split("-")[2]);
			userList.get(i).setUser_email1(userList.get(i).getUser_email().split("@")[0]);
			userList.get(i).setUser_email2(userList.get(i).getUser_email().split("@")[1]);
			userList.get(i).setUser_ssn1(userList.get(i).getUser_ssn().split("-")[0]);
			userList.get(i).setUser_ssn2(userList.get(i).getUser_ssn().split("-")[1]);	
		}
		
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jObj = new JSONObject();
		jObj.put("userList", userList);
		try {
			Writer writer = response.getWriter();
			writer.write(jObj.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//직원 정보 수정
	@PutMapping("")
	public Users update(HttpServletRequest request, HttpServletResponse response, @RequestBody Users user) {
		user.setUser_ssn(user.getUser_ssn1() + "-" + user.getUser_ssn2());
		user.setUser_tel(user.getUser_tel1() + "-" + user.getUser_tel2() + "-" + user.getUser_tel3());
		user.setUser_email(user.getUser_email1() + "@" + user.getUser_email2());
		
		usersService.updateUser(user);
		return user;
	}
	
	//직원 활성화 및 비활성화
	@PutMapping("/enabled")
	public void updateEnabled(HttpServletRequest request, HttpServletResponse response, @RequestBody Users user) {
		usersService.updateEnabled(user);
	}
		
	//직원 등록
	@PostMapping("")
	public Map<String, String> create(HttpServletRequest request, HttpServletResponse response, @RequestBody Users user) {
		Map<String, String> map = new HashMap<String, String>();
		
		if (usersService.getSsnUniqe(user.getUser_ssn2()) == 0) {				
			String hcode = user.getUser_hospital_id();
			String uauth = user.getUser_authority();
			String user_id = "";		
			
			int count = usersService.getCount(hcode, uauth) + 1;
			
			String index = "";
			
			if (count < 10 ) {
				index = "00" + count;
			} else if (count < 100) {
				index = "0" + count;
			} else {
				index = Integer.toString(count);
			}
			
			if(uauth.equals("ROLE_DOCTOR")) {
				user_id = "D" + hcode + index;
			} else if(uauth.equals("ROLE_NURSE")) {
				user_id = "N" + hcode + index;
			} else if(uauth.equals("ROLE_INSPECTOR")) {
				user_id = "I" + hcode + index;
			}
			
			// 비밀번호 암호화
			BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
			String password = bpe.encode(user_id);
			
			user.setUser_id(user_id);
			user.setUser_password(password);
			user.setUser_ssn(user.getUser_ssn1() + "-" + user.getUser_ssn2());
			user.setUser_tel(user.getUser_tel1() + "-" + user.getUser_tel2() + "-" + user.getUser_tel3());
			user.setUser_email(user.getUser_email1() + "@" + user.getUser_email2());
			
//			logger.info("count: " + count);
			
			usersService.createUser(user);
			usersService.updateUsercount(hcode, uauth);	
			
			map.put("result", "success");
		} else {
			map.put("result", "notUnique");			
		}	
		return map;
	}	
	
	
}
