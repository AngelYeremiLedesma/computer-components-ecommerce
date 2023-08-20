package com.aledesma.app.models.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aledesma.app.dtos.CreateUserDto;
import com.aledesma.app.exceptions.RoleNotFoundException;
import com.aledesma.app.models.entity.Cart;
import com.aledesma.app.models.entity.Customer;
import com.aledesma.app.models.entity.Role;
import com.aledesma.app.models.entity.UserEntity;
import com.aledesma.app.models.repositories.ICartRepository;
import com.aledesma.app.models.repositories.ICustomerRepository;
import com.aledesma.app.models.repositories.IRoleRepository;
import com.aledesma.app.models.repositories.IUserRepository;

import jakarta.mail.MessagingException;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private IUserRepository userRepository;
	
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

	@Override
	public ResponseEntity<?> save(CreateUserDto createUserDto) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			Set<Role> roles = createUserDto.getRoles().stream()
			        .map((roleName) -> {
			            Role role = roleRepository.findByName(roleName)
			                    .orElseThrow(() -> new RoleNotFoundException("No matching roles in the DB"));
			            return role;
			        })
			        .collect(Collectors.toSet());
			
			Customer customer = null;
			
			if(roles.stream().noneMatch(role -> role.getName().equals("ADMIN"))) {
			    Cart cart = Cart.builder().build(); 
			    customer = Customer.builder().email(createUserDto.getEmail()).cart(cart).build();
			    cartRepository.save(cart); 
			    customerRepository.save(customer); 
			}
			
			UserEntity userEntity = UserEntity.builder()
					.username(createUserDto.getUsername())
					.email(createUserDto.getEmail())
					.password(passwordEncoder.encode(createUserDto.getPassword()))
					.roleList(roles)
					.customer(customer)
					.build();
			
			userRepository.save(userEntity);
			
			/*
			 * emailService.sendEmailWithFile("ledesma.ayala.angel.yeremi.21.1@gmail.com",
			 * "Mensaje muestra", "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" +
			 * "<style>\r\n" + "  body {\r\n" + "    font-family: Arial, sans-serif;\r\n" +
			 * "    margin: 0;\r\n" + "    padding: 0;\r\n" +
			 * "    background-color: #f2f2f2;\r\n" + "    display: flex;\r\n" +
			 * "    flex-direction: column;\r\n" + "    align-items: center;\r\n" +
			 * "    justify-content: center;\r\n" + "    height: 100vh;\r\n" +
			 * "    width: 100%;\r\n" + "  }\r\n" + "\r\n" + "  header {\r\n" +
			 * "    width: 50%;\r\n" + "    background-color: #333;\r\n" +
			 * "    color: #fff;\r\n" + "    padding: 0;\r\n" +
			 * "    text-align: center;\r\n" + "  }\r\n" + "\r\n" + "  section {\r\n" +
			 * "    background-color: #fff;\r\n" + "    padding: 20px;\r\n" +
			 * "    margin: 20px;\r\n" + "    border-radius: 5px;\r\n" +
			 * "    box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);\r\n" + "  }\r\n" + "\r\n" +
			 * "  h1 {\r\n" + "    color: #fff;\r\n" + "  }\r\n" + "\r\n" + "  p {\r\n" +
			 * "    color: #666;\r\n" + "  }\r\n" + "\r\n" + "  .button {\r\n" +
			 * "    background-color: #007bff;\r\n" + "    color: #fff;\r\n" +
			 * "    padding: 10px 20px;\r\n" + "    border: none;\r\n" +
			 * "    border-radius: 5px;\r\n" + "    cursor: pointer;\r\n" + "  }\r\n" +
			 * "</style>\r\n" + "</head>\r\n" + "<body>\r\n" + "  <header>\r\n" +
			 * "    <h1>Welcome to Our Newsletter</h1>\r\n" + "  </header>\r\n" +
			 * "  <section>\r\n" + "    <h2>Featured Article</h2>\r\n" +
			 * "    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed ac justo non orci facilisis eleifend vel a sapien.</p>\r\n"
			 * + "    <a href=\"#\" class=\"button\">Read More</a>\r\n" + "  </section>\r\n"
			 * + "  <section>\r\n" + "    <h2>Latest Updates</h2>\r\n" + "    <ul>\r\n" +
			 * "      <li>New Product Release</li>\r\n" +
			 * "      <li>Upcoming Events</li>\r\n" + "      <li>Special Offers</li>\r\n" +
			 * "    </ul>\r\n" + "  </section>\r\n" + "  <footer>\r\n" +
			 * "    <p>Contact us at: info@example.com</p>\r\n" + "  </footer>\r\n" +
			 * "</body>\r\n" + "</html>");
			 */
			
			response.put("message", "User created correctly");
					
		}catch(DataAccessException e) {
			response.put("message", "Error creating the user");
			response.put("error", e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		catch(RoleNotFoundException e) {
			response.put("message", "Error with the roles");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);	
		} /*
			 * catch (MessagingException e) { response.put("message",
			 * "Error sending the verification mail"); response.put("error",
			 * e.getMessage()); return new ResponseEntity<Map<String,
			 * Object>>(response,HttpStatus.CREATED); }
			 */
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);	
	}

}
