package com.mycompany.webapp.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
   private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
   
   
   //비밀 키 (누출되면 안됨.)
   private static final String secretKey = "secret";
   
   //JWT 생성:  사용자 아이디 포함.
   public static String createToken(String uid) {
      String token = null;
      try {                  
         JwtBuilder builder = Jwts.builder();
         //토큰의 종류
         builder.setHeaderParam("typ", "JWT");
         //암호화 알고리즘 종류 //JWT 헤더
         builder.setHeaderParam("alg", "HS256");
         //토큰의 유효기간
         builder.setExpiration(new Date(new Date().getTime() + 1000*60*60*12));
         //토큰의 데이터
         builder.claim("uid",uid);
         //비밀키
         builder.signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8"));
         //모든 내용을 묶기.
         token= builder.compact();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
      return token;
   }
   
   //JWT를 파싱해서 uid 얻기
   public static String getUid(String token) {
      String uid = null;
      try {
         JwtParser parser = Jwts.parser();
         parser.setSigningKey(secretKey.getBytes("UTF-8"));
         Jws<Claims> jws = parser.parseClaimsJws(token);
         Claims claims = jws.getBody();
         uid = claims.get("uid", String.class);
      }
      catch(Exception e) {
         e.printStackTrace();
      }
      return uid;
   }
   
   //JWT 유효성 검사: 유효기간 확인 
   public static boolean validateToken(String token) {
	   boolean validate = false;
	   
	   try {
	         JwtParser parser = Jwts.parser();
	         parser.setSigningKey(secretKey.getBytes("UTF-8"));
	         Jws<Claims> jws = parser.parseClaimsJws(token);
	         Claims claims = jws.getBody();
	         validate = claims.getExpiration().after(new Date());
	         /*
	         if(validate) {
	        	 long remainMillSec = claims.getExpiration().getTime() - new Date().getTime();
	        	 logger.info(""+remainMillSec/1000 + "초 남았음");
	         }
	         */
	      }
	      catch(Exception e) {
	         e.printStackTrace();
	      }
	   
	   return validate;
   }
   
   //테스트
//   public static void main(String[] args) {
//      //토큰 생성
//	  String jwt = createToken("user1");
//      logger.info(jwt);
//      //지연
//      try { 
//    	  Thread.sleep(5000); 
//      } catch (Exception e) {};
//      
//      if(validateToken(jwt)) {
//    	  //토큰 정보 얻기
//    	  String uid = getUid(jwt);
//    	  logger.info(uid);
//      }    
//   }
}