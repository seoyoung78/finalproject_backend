package com.mycompany.webapp.security;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

//로그인 성공되면 홈으로 이동하는 것이 SimpleUrlAuthenticationSuccessHandler
//페이지 기억은 SavedRequestAwareAuthenticationSuccessHandler

public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	
	private static final Logger logger = LoggerFactory.getLogger(AuthSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
	
		logger.info("성공실행");
		super.onAuthenticationSuccess(request, response, authentication);
		
		HttpSession session = request.getSession();
		session.setAttribute("loginId",authentication.getName());
		logger.info((String) session.getAttribute("loginId"));
	}
}
