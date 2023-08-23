package com.aledesma.app.models.services;

import java.util.Set;

import com.aledesma.app.models.entity.Role;

public interface IRoleService {

	Set<Role> convertStrToRoles(Set<String> strRoles);

	Boolean isNotAdmin(Set<Role> roles);

}
