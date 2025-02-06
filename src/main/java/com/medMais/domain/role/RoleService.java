package com.medMais.domain.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	public Role findByNameRole(String role) {
		return roleRepository.findByName(role).get();
	}

}
