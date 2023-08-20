package com.aledesma.app.dtos;

import java.util.Set;

import com.aledesma.app.models.entity.Role;
import com.aledesma.app.validation.RoleSetValidation;
import com.aledesma.app.validation.UniqueUsernameValidation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
	@NotBlank(message = "{not.blank}")
	@Size(min = 8,max = 20, message = "{size.message}")
	@UniqueUsernameValidation(message = "{unique.username.validation}")
	private String username;
	
	@NotBlank(message = "{not.blank}")
	@Email(message = "{email.message}")
	private String email;

	@NotBlank(message = "{not.blank}")
	@Size(max = 68, message = "{size.message}")
	private String password;
	
	@NotEmpty(message = "{not.empty}")
	@RoleSetValidation(message = "{valid.roles}")
	private Set<String> roles;
}
