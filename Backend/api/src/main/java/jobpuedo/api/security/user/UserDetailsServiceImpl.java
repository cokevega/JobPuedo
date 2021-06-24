package jobpuedo.api.security.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jobpuedo.api.entity.User;
import jobpuedo.api.repository.IUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	IUserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Buscar por email a los usuarios que se autentican
		User user=userRepository.findByEmail(username)
				.orElseThrow(()->new UsernameNotFoundException("No hay ningún usuario registrado con el email: "+username));
		return UserDetailsImpl.build(user);
	}
	

}
