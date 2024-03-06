package com.example.Bai1.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	SpringTemplateEngine engine;
	
	public void testMail() {
		String to = "viethai31102002@gmail.com";
		String subJect = "Chuc mung sinh nhat";
		String body = "<h1><i>Cam on ban</i></h1>";
		
		sendEmail(to, subJect, body);
		
	}
	
	
	public void sendMailBirthday(String to , String name) {
		String subJect = "Happy Birthday " + name;
		
		//gán cho view
		Context context = new Context();
		context.setVariable("name", name);
		
		String body = engine.process("/hpbd.html", context);
		
		sendEmail(to, subJect, body);
	}
	
	public void sendEmail(String to, String subject, String body) {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message , StandardCharsets.UTF_8.name());
		
		try {
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);
			helper.setFrom("vhai31102002@gmail.com");
			
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
