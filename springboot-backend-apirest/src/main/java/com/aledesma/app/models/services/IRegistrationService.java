package com.aledesma.app.models.services;

import org.springframework.http.ResponseEntity;

import com.aledesma.app.dtos.CreateUserDto;

import jakarta.mail.MessagingException;

public interface IRegistrationService {

	ResponseEntity<?> registerNewUser(CreateUserDto createUserDto) throws MessagingException;
	
    public ResponseEntity<?> confirmEmail(String token);

	String generateConfirmationEmailHtml(String name, String link);
 
}
