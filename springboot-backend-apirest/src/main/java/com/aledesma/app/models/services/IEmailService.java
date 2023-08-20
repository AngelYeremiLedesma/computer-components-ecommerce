package com.aledesma.app.models.services;

import java.io.File;

import jakarta.mail.MessagingException;

public interface IEmailService {

	void sendEmail(String toUser, String subject, String message);
	
	void sendEmailWithFile(String toUser, String subject, String message) throws MessagingException ;
}
