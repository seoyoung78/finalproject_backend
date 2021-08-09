package com.mycompany.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping(value={"/","/Patient","/Register","/Treatment","/Inspection","/DataAnalysis","/User"})
	public String mainPage() {
		
		return "/index.html";
	}
}
