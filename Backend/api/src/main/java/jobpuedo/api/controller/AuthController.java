package jobpuedo.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jobpuedo.api.entity.User;
import jobpuedo.api.exception.EmailAlreadyRegisteredException;
import jobpuedo.api.exception.InactivatedUserException;
import jobpuedo.api.repository.IUserRepository;
import jobpuedo.api.security.jwt.JwtUtils;
import jobpuedo.api.security.request.LoginRequest;
import jobpuedo.api.security.request.SignupRequest;
import jobpuedo.api.security.response.JwtResponse;
import jobpuedo.api.security.user.UserDetailsImpl;
import jobpuedo.api.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private UserService userService;

	@PreAuthorize("hasAnyRole('USER','ENTERPRISE')")
	@GetMapping("/id")
	public ResponseEntity<Integer> getId(HttpServletRequest req) {
		//Obtener mi id
		return ResponseEntity.ok(jwtUtils.getIdFromRequest(req));
	}

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
		//Iniciar sesión, comprobación de credenciales
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		User user = userService.findByEmail(request.getEmail());
		//Comprobar que el usuario no está inactivo
		if (user.getStatus() == 0)
			throw new InactivatedUserException("El usuario está inactivo.", user.getId());
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		//Enviar respuesta con los datos del usuario
		JwtResponse response = new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail(), userDetails.getEmail(),
				roles);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/register")
	public ResponseEntity<JwtResponse> registerUser(@RequestBody SignupRequest signUpRequest) {
		//Registrar nuevo usuario
		//Comprobar que el email está disponible
		if (userRepository.existsByEmail(signUpRequest.getEmail()))
			throw new EmailAlreadyRegisteredException("El email ya se encuentra registrado.");
		//Crear nuevo usuario
		userService.createUser(signUpRequest);
		//Iniciar sesión
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(), signUpRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		JwtResponse response = new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail(), userDetails.getEmail(),
				roles);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/reactivate")
	public ResponseEntity<User> reactivateAccount(@RequestParam("id") int id) {
		//Reactivar usuario inactivo
		User user = userService.findById(id);
		user.setStatus(1);
		userService.save(user);
		return ResponseEntity.ok(user);
	}
}
