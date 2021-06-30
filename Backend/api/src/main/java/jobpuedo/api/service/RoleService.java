package jobpuedo.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jobpuedo.api.entity.Role;
import jobpuedo.api.exception.NoExistsResourceException;
import jobpuedo.api.repository.IRoleRepository;

@Service
public class RoleService {

	@Autowired
	private IRoleRepository roleRepository;

	public Role findByName(String name) {
		Optional<Role> op = roleRepository.findByName(name);
		if (op.isPresent())
			return op.get();
		else
			throw new NoExistsResourceException("El rol seleccionado no existe.");
	}

}
