package com.mycompany.webapp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Hospitals;
import com.mycompany.webapp.dto.Users;
import com.mycompany.webapp.service.UsersService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/auth")
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private UsersService usersService;
	@Resource(name="daoAuthenticationManager")
	private AuthenticationManager authenticationManager;
	@PostMapping("/login")
	//{"uid":"user1", "upassword":"12345"}
	
	public Map<String, String> login(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> user) {
		//인증 데이터 얻기
		String uid = user.get("userId");
		String upassword = user.get("userPassword");
		
		Map<String, String> map = new HashMap<String, String>();
		
		Users dbUser = usersService.getUser(uid);	
		if (dbUser == null) {
			map.put("result","notFindID");
			return map;
		} else {
			if (dbUser.getUser_enabled() == 1) {
				BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
				boolean result = bpe.matches(upassword, dbUser.getUser_password());
			
				if (result) {			
					map.put("result","success");
					String uauthority = dbUser.getUser_authority();
					map.put("uauthority", uauthority);
					
					String hid = dbUser.getUser_hospital_id();
					Hospitals hospital = usersService.getHospital(hid);
					
					String hname = hospital.getHospital_name();
					String haddress = hospital.getHospital_address();
					String hurl = hospital.getHospital_url();
					String hlat = hospital.getHospital_lat();
					String hlong = hospital.getHospital_long();
					
					map.put("hid", hid);
					map.put("hname", hname);
					map.put("haddress", haddress);
					map.put("hurl", hurl);
					map.put("hlat", hlat);
					map.put("hlong", hlong);
				} else if(result == false) {					
					map.put("result", "notCorrectPW");
					return map;
				}
			} else {
				map.put("result", "notEnabled");	
				return map;			
			}
		}
		
		//사용자 인증
	    UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(uid, upassword);		
		
		//Spring Security에 인증 객체 등록
	    Authentication authentication = authenticationManager.authenticate(upat);
		SecurityContext securityContext = SecurityContextHolder.getContext();
	    securityContext.setAuthentication(authentication);
		//JWT 생성
		String jwt = com.mycompany.webapp.security.JwtUtil.createToken(uid);
	
		//JSON 응답 보내기
		map.put("uid", uid);
		map.put("authToken", jwt);
				
		return map;
	}	
	
	// 회원 정보 읽기
	@GetMapping("/read")
	public Users read (HttpServletRequest request, HttpServletResponse response, @RequestParam String user_id) {
		Users user = usersService.getUser(user_id);
		
		user.setUser_tel1(user.getUser_tel().split("-")[0]);
		user.setUser_tel2(user.getUser_tel().split("-")[1]);
		user.setUser_tel3(user.getUser_tel().split("-")[2]);
		user.setUser_email1(user.getUser_email().split("@")[0]);
		user.setUser_email2(user.getUser_email().split("@")[1]);
		user.setUser_ssn1(user.getUser_ssn().split("-")[0]);
		user.setUser_ssn2(user.getUser_ssn().split("-")[1]);	
		
		return user;
	}
	
	// 회원 정보 수정
	@PutMapping("/update")
	public String updateUser (HttpServletRequest request, HttpServletResponse response, @RequestBody Users user) {
		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
		boolean result = bpe.matches(user.getOld_password(), user.getUser_password());
		if (result) {
			user.setUser_password(bpe.encode(user.getNew_password()));
			user.setUser_tel(user.getUser_tel1() + "-" + user.getUser_tel2() + "-" + user.getUser_tel3());
			user.setUser_email(user.getUser_email1() + "@" + user.getUser_email2());
			usersService.updateUser(user);
			return "success";
		} else {
			return "notCorrectPW";
		}
	}
}
