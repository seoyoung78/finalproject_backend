package com.mycompany.webapp.controller;

import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Patients;
import com.mycompany.webapp.dto.Registers;
import com.mycompany.webapp.dto.RegistersCountByDate;
import com.mycompany.webapp.dto.Schedules;
import com.mycompany.webapp.dto.Users;
import com.mycompany.webapp.service.RegistersService;
import com.mycompany.webapp.twilio.SendMessage;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/register")
public class RegisterController {
	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
	@Autowired
	private RegistersService registersService;

	private SendMessage msg = new SendMessage();

	// 해당 날짜의 접수 내역 불러오기
	@GetMapping("")
	public void getRegisterList(HttpServletRequest request, HttpServletResponse response, @RequestParam String date, @RequestParam(defaultValue = "") String state){ 
		List<Registers> registerList = registersService.getTodayRegisters(date, state);
		List<Registers> tableRegisterList = registersService.getTableRegisters(date);
		//List<Registers> timeRegisterList = new 
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jObj = new JSONObject();
		jObj.put("registerList", registerList);
		jObj.put("tableRegisterList", tableRegisterList);
		try {
			Writer writer = response.getWriter();
			writer.write(jObj.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 새로운 register 만들기
	@PostMapping("")
	public void createRegister(HttpServletRequest request, HttpServletResponse response, @RequestBody Registers register) {
		//logger.info("create");
		//logger.info(register.getRegister_date());
		//logger.info(register.getRegister_user_id());
		if(register.getRegister_state().equals("취소")) {
			register.setRegister_state("대기");
		}
		String result = registersService.createNewRegister(register);
		
//		msg.send("접수가 등록 되었습니다.");
		
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jObj = new JSONObject();
		jObj.put("result", result);
		try {
			Writer writer = response.getWriter();
			writer.write(jObj.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// register 수정
	@PutMapping("")
	public void updateRegister(HttpServletRequest request, HttpServletResponse response, @RequestBody Registers register) {
		//logger.info("update");
		//logger.info(register.getRegister_date());
		//logger.info(register.getRegister_user_id());
		String result = registersService.changeRegister(register);
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jObj = new JSONObject();
		jObj.put("result", result);
		try {
			Writer writer = response.getWriter();
			writer.write(jObj.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// register 상태 수정 (대기 > 완료) (대기 > 취소)
	@PutMapping("/state")
	public void changeRegisterState(HttpServletRequest request, HttpServletResponse response, @RequestBody Registers register) {
		int result = registersService.changeStateRegister(register);
		
		if(register.getRegister_state().equals("완료")) {
			//logger.info("완료");
			int newTreatment = registersService.createNewTreatment(register);

		} else if(register.getRegister_state().equals("취소")) {
			//logger.info("취소");
			
//			msg.send("접수가 취소되었습니다.");
		} 
		
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jObj = new JSONObject();
		jObj.put("result", result);
		try {
			Writer writer = response.getWriter();
			writer.write(jObj.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//************** User ******************************************************************
	
	// 의사 목록
	@GetMapping("/doctors")
	public void getDoctorList(HttpServletRequest request, HttpServletResponse response){ 
		// 모든 의사 불러오기
		List<Users> doctorList = registersService.getAllDoctors();
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jObj = new JSONObject();
		jObj.put("doctorList", doctorList);
		try {
			Writer writer = response.getWriter();
			writer.write(jObj.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 환자 목록
	@GetMapping("/patients")
	public void getPatientList(HttpServletRequest request, HttpServletResponse response){ 
		// 모든 의사 불러오기
		List<Patients> patientList = registersService.getAllPatients();
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jObj = new JSONObject();
		jObj.put("patientList", patientList);
		try {
			Writer writer = response.getWriter();
			writer.write(jObj.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//*********** To Do List ********************************************************

	// 해당 날짜의 접수 내역 불러오기
	@GetMapping("/todolists")
	public void getToDoLists(HttpServletRequest request, HttpServletResponse response, @RequestParam String date, @RequestParam String user_id){ 	
		//		logger.info(date);
		//		logger.info(user_id);
		Schedules myschedule = new Schedules();
		myschedule.setSchedule_user_id(user_id);
		myschedule.setSchedule_regdate(date);
		List<Schedules> todolist = registersService.getToDoList(myschedule);
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jObj = new JSONObject();
		jObj.put("todolist", todolist);
		try {
			Writer writer = response.getWriter();
			writer.write(jObj.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 새로운 할일 추가
	@PostMapping("/todolists")
	public void createToDoLists(HttpServletRequest request, HttpServletResponse response, @RequestBody Schedules schedule){ 
		//logger.info(""+schedule.getSchedule_id());
		//logger.info(""+schedule.getSchedule_user_id());
		//logger.info(""+schedule.getSchedule_content());
		//logger.info(""+schedule.getSchedule_regdate());
		//logger.info(""+schedule.getSchedule_state());
		int result = registersService.createNewToDoList(schedule);
		//logger.info(""+result);
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jObj = new JSONObject();
		jObj.put("result", result);
		try {
			Writer writer = response.getWriter();
			writer.write(jObj.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 할일 변경
	@PutMapping("/todolists")
	public void updateToDoLists(HttpServletRequest request, HttpServletResponse response, @RequestBody Schedules schedule){ 
//		logger.info(""+schedule.getSchedule_id());
//		logger.info(""+schedule.getSchedule_user_id());
//		logger.info(""+schedule.getSchedule_content());
//		logger.info(""+schedule.getSchedule_regdate());
//		logger.info(""+schedule.getSchedule_state());
		int result = registersService.updateToDoList(schedule);
//		logger.info(""+result);
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jObj = new JSONObject();
		jObj.put("result", result);
		try {
			Writer writer = response.getWriter();
			writer.write(jObj.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 할일 삭제
	@DeleteMapping("/todolists")
	public void deleteToDoLists(HttpServletRequest request, HttpServletResponse response, @RequestParam String id){

		//logger.info(id);
		int deleteId = Integer.parseInt(id);
		int result = registersService.deleteToDoList(deleteId);
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jObj = new JSONObject();
		jObj.put("result", result);
		try {
			Writer writer = response.getWriter();
			writer.write(jObj.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 해당 날짜의 접수 내역 불러오기
	@GetMapping("/calendar")
	public void getRegisterByDoctor(HttpServletRequest request, HttpServletResponse response, @RequestParam String date, @RequestParam String user_id){ 	
		//logger.info(date);
		List<RegistersCountByDate> registerList = registersService.getRegisterByDoctor(user_id, date);
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jObj = new JSONObject();
		jObj.put("registerList", registerList);
		try {
			Writer writer = response.getWriter();
			writer.write(jObj.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
