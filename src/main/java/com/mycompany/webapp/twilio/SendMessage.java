package com.mycompany.webapp.twilio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber; 
 
public class SendMessage { 		
    // Find your Account Sid and Token at twilio.com/console 
    public static final String ACCOUNT_SID = "AC77a3eeaa3988e77f2276aa6720fc492c"; 
    public static final String AUTH_TOKEN = "88122d26c9ed22b7a11feb3bbfa9fd5a"; 
 
    public void send(String msg) { 
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN); 
    	
        Message message = Message.creator( 
        		//to
                new PhoneNumber("+821051914399"),
                //from
                new PhoneNumber("+12818880558"),
                //message
                msg).create(); 
        System.out.println("내용: " + msg);
    } 
}