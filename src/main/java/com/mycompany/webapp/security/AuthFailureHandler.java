package com.mycompany.webapp.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	private static final Logger logger = LoggerFactory.getLogger(AuthFailureHandler.class);
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		//로그인 실패 시 추가로 실행하고자 하는 코드
		logger.info("에러실행");
		super.onAuthenticationFailure(request, response, exception);
	}
	
	
}
