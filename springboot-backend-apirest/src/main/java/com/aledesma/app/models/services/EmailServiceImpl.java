package com.aledesma.app.models.services;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements IEmailService{

	@Autowired
	private JavaMailSender mailSender;

	@Value("${email.sender}")
	private String emailSender;
	
	@Override
	public void sendEmail(String toUser, String subject, String message) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		mailMessage.setFrom(emailSender);
		mailMessage.setTo(toUser);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);
		
		mailSender.send(mailMessage);
		
	}

	@Override
	public void sendEmailWithFile(String toUser, String subject, String message) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        
        helper.setTo(toUser);
        helper.setSubject(subject);
        helper.setText(message, true); // Set the HTML content to be true
        
        mailSender.send(mimeMessage);
		
	}

}
