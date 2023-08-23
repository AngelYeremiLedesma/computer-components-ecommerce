package com.aledesma.app.models.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aledesma.app.dtos.CreateUserDto;
import com.aledesma.app.exceptions.ConfirmationTokenExpiredException;
import com.aledesma.app.exceptions.ConfirmationTokenNotFoundException;
import com.aledesma.app.exceptions.EmailAlreadyConfirmedException;
import com.aledesma.app.exceptions.RoleNotFoundException;
import com.aledesma.app.models.entity.Cart;
import com.aledesma.app.models.entity.ConfirmationToken;
import com.aledesma.app.models.entity.Customer;
import com.aledesma.app.models.entity.Role;
import com.aledesma.app.models.entity.UserEntity;
import com.aledesma.app.models.repositories.ICartRepository;
import com.aledesma.app.models.repositories.ICustomerRepository;
import com.aledesma.app.models.repositories.IRoleRepository;
import com.aledesma.app.models.repositories.IUserRepository;

import jakarta.mail.MessagingException;

@Service
public class RegistrationServiceImpl implements IRegistrationService{

	@Autowired
	IConfirmationTokenService confirmationTokenService;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	IUserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	IRoleRepository roleRepository;

	@Autowired
	ICustomerRepository customerRepository;

	@Autowired
	ICartRepository cartRepository;

	@Autowired
	IEmailService emailService;

	@Autowired
	IRoleService roleService;

	@Autowired
	ICustomerService customerService;
	

	@Override
	public ResponseEntity<?> registerNewUser(CreateUserDto createUserDto) throws MessagingException {

		Map<String, Object> response = new HashMap<>();

		Set<Role> roles = roleService.convertStrToRoles(createUserDto.getRoles());

		Customer customer = null;
		if (roleService.isNotAdmin(roles)) {
			customer = customerService.createNewCustomer(createUserDto);
		}

		UserEntity userEntity = userService.generateUser(createUserDto,roles,customer);
		userRepository.save(userEntity);
		
		ConfirmationToken confirmationToken = confirmationTokenService.generateConfirmationToken(userEntity);
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		String token = confirmationToken.getToken();
		String verificationLink = "http://localhost:8080/sign-up/confirm-email?token=" + token;
		String verificationEmailHtml = generateConfirmationEmailHtml(userEntity.getUsername(), verificationLink);
		emailService.sendEmailWithFile(createUserDto.getEmail(),"Verification link", verificationEmailHtml);
		 

		response.put("message", "User created correctly");
		response.put("token",confirmationToken.getToken());
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Override
    public ResponseEntity<?> confirmEmail(String token) {
		Map<String, Object> response = new HashMap<>(); 
        ConfirmationToken confirmationToken = confirmationTokenService.getConfirmationToken(token).orElseThrow(() -> new ConfirmationTokenNotFoundException("Token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new EmailAlreadyConfirmedException("Email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new ConfirmationTokenExpiredException("Token expired");
        }

        confirmationTokenService.confirmEmail(token);
        userService.enableUser(confirmationToken.getUser().getEmail());
        response.put("message", "Email verificado");
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
    }
	
	@Override
	public String generateConfirmationEmailHtml(String username, String link) {
		return 	"<!DOCTYPE html>\r\n"
				+ "<html>\r\n"
				+ "<head>\r\n"
				+ "    <style>\r\n"
				+ "        body {\r\n"
				+ "            font-family: Arial, sans-serif;\r\n"
				+ "            background-color: #f4f4f4;\r\n"
				+ "            padding: 20px;\r\n"
				+ "            text-align: center;\r\n"
				+ "        }\r\n"
				+ "        .container {\r\n"
				+ "            background-color: #fff;\r\n"
				+ "            border-radius: 5px;\r\n"
				+ "            box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);\r\n"
				+ "            padding: 20px;\r\n"
				+ "            max-width: 500px;\r\n"
				+ "            margin: 0 auto;\r\n"
				+ "        }\r\n"
				+ "        .header {\r\n"
				+ "            background-color: #007bff;\r\n"
				+ "            color: white;\r\n"
				+ "            padding: 10px;\r\n"
				+ "            border-radius: 5px 5px 0 0;\r\n"
				+ "        }\r\n"
				+ "        .content {\r\n"
				+ "            padding: 20px;\r\n"
				+ "        }\r\n"
				+ "        .button {\r\n"
				+ "            background-color: #007bff;\r\n"
				+ "            color: white;\r\n"
				+ "            padding: 10px 20px;\r\n"
				+ "            border-radius: 5px;\r\n"
				+ "            text-decoration: none;\r\n"
				+ "        }\r\n"
				+ "    </style>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+ "    <div class=\"container\">\r\n"
				+ "        <div class=\"header\">\r\n"
				+ "            <h1>Email Verification</h1>\r\n"
				+ "        </div>\r\n"
				+ "        <div class=\"content\">\r\n"
				+ "            <p>Hello <span style=\"font-weight: bold;\">" + username +"</span>,</p>\r\n"
				+ "            <p>Thank you for registering! To complete your registration, please click the following link:</p>\r\n"
				+ "            <p><a class=\"button\" href=\"" + link + "\">Verify Email</a></p>\r\n"
				+ "            <p>This link will expire in 15 minutes.</p>\r\n"
				+ "        </div>\r\n"
				+ "    </div>\r\n"
				+ "</body>\r\n"
				+ "</html>";
	}

}
