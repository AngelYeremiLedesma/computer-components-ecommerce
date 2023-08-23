package com.aledesma.app.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.mail.MessagingException;

@ControllerAdvice
public class ExceptionsHandlerController {

	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<?> dataAccessError(DataAccessException ex) {
		Map<String,Object> response = new HashMap<>();
		response.put("error", "Error accessing the DB");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<?> customerNotFoundError(CustomerNotFoundException ex) {
		Map<String,Object> response = new HashMap<>();
		response.put("error", "Customer Not Found");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<?> productNotFoundError(ProductNotFoundException ex) {
		Map<String,Object> response = new HashMap<>();
		response.put("error", "Product Not Found");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CartItemNotFoundException.class)
	public ResponseEntity<?> cartItemNotFoundError(CartItemNotFoundException ex) {
		Map<String,Object> response = new HashMap<>();
		response.put("error", "Cart Item Not Found");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CartNotFoundException.class)
	public ResponseEntity<?> cartItemNotFoundError(CartNotFoundException ex) {
		Map<String,Object> response = new HashMap<>();
		response.put("error", "Cart Not Found");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RoleNotFoundException.class)
	public ResponseEntity<?> roleNotFoundError(RoleNotFoundException ex) {
		Map<String,Object> response = new HashMap<>();
		response.put("error", "Error with the roles");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MessagingException.class)
	public ResponseEntity<?> messagingError(MessagingException ex) {
		Map<String,Object> response = new HashMap<>();
		response.put("error", "Failed to send email");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ConfirmationTokenNotFoundException.class)
	public ResponseEntity<?> confirmationTokenNotFoundError(ConfirmationTokenNotFoundException ex) {
		Map<String,Object> response = new HashMap<>();
		response.put("error", "Confirmation Token Not FoundException");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(EmailAlreadyConfirmedException.class)
	public ResponseEntity<?> confirmationTokenNotFoundError(EmailAlreadyConfirmedException ex) {
		Map<String,Object> response = new HashMap<>();
		response.put("error", "Email Already Confirmed Exception");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(ConfirmationTokenExpiredException.class)
	public ResponseEntity<?> confirmationTokenExpiredError(ConfirmationTokenExpiredException ex) {
		Map<String,Object> response = new HashMap<>();
		response.put("error", "Confirmation Token Expired");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CONFLICT);
	}
}
