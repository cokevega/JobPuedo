package jobpuedo.api.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jobpuedo.api.entity.User;
import jobpuedo.api.repository.IUserRepository;
import jobpuedo.api.request.UserSearchedRequest;
import jobpuedo.api.service.UserService;

@RestController
@RequestMapping("/admin/user")
public class UserAdminController {

	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private UserService userService;

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/all")
	public ResponseEntity<List<User>> findAll() {
		//Obtener todos los usuarios
		return ResponseEntity.ok(userRepository.findAll());
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/filter")
	public ResponseEntity<List<User>> filterUser(@RequestBody UserSearchedRequest userSearched) {
		//Obtener usuario con los filtros especificados
		return ResponseEntity.ok(userService.filterUsers(userSearched));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteUser(@PathVariable("id") int id) {
		//Eliminar definitivamente usuario: removerlo de la base de datos
		User user = userService.findById(id);
		userService.deleteUserDefinitely(user);
		return ResponseEntity.ok(true);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin/{id}")
	public ResponseEntity<User> newAdmin(@PathVariable("id") int id) {
		//Hacer administrador a otro usuario
		return ResponseEntity.ok(userService.newAdmin(id));
	}

}
