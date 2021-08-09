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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Registers;
import com.mycompany.webapp.service.DataService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/data")
public class DataController {
	private static final Logger logger = LoggerFactory.getLogger(DataController.class);
	@Autowired
	private DataService dataService;
	
	@GetMapping("")
	public void getData(HttpServletRequest request, HttpServletResponse response) {
		
		List<Registers> data1 = dataService.getData1();
		List<Registers> data2 = dataService.getData2();
		List<Registers> data3 = dataService.getData3();
		List<Registers> data4 = dataService.getData4();
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jObj = new JSONObject();
		jObj.put("data1", data1);
		jObj.put("data2", data2);
		jObj.put("data3", data3);
		jObj.put("data4", data4);
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
