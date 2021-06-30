package jobpuedo.api.controller;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jobpuedo.api.entity.User;
import jobpuedo.api.request.WorkerSearchedRequest;
import jobpuedo.api.security.jwt.JwtUtils;
import jobpuedo.api.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;
	@Autowired
	private JwtUtils jwtUtils;

	//List all users
	@GetMapping("/all")
	public ResponseEntity<List<User>> showUsers() {
		return ResponseEntity.ok(service.showUsers());
	}

	//Find by id
	@GetMapping("/find")
	public ResponseEntity<User> findUser(@RequestParam("id") int id) {
		return ResponseEntity.ok(service.findById(id));
	}

	//Get logged user
	@GetMapping("/me")
	public ResponseEntity<User> whoAmI(HttpServletRequest req) {
		return ResponseEntity.ok(jwtUtils.getUserFromtoken(req));
	}

	//Edit profile
	@PreAuthorize("hasAnyRole('USER','ENTERPRISE')")
	@PutMapping("/edit")
	public ResponseEntity<User> editUser(@RequestBody User user, HttpServletRequest req) {
		User requestUser = jwtUtils.getUserFromtoken(req);
		if (requestUser.getId() != user.getId())
			throw new AccessDeniedException("Solo el autor de este perfil puede editarlo");
		return ResponseEntity.ok(service.edit(user));
	}

	//Edit profile image
	@PreAuthorize("hasAnyRole('USER','ENTERPRISE')")
	@PutMapping("/edit/image")
	public ResponseEntity<User> editImage(@RequestParam("imageFile") MultipartFile file, HttpServletRequest req) {
		User user = jwtUtils.getUserFromtoken(req);
		return ResponseEntity.ok(service.editImage(user, file));
	}

	//Soft delete: inactivate account
	@PreAuthorize("hasAnyRole('USER','ENTERPRISE')")
	@DeleteMapping("/delete")
	public ResponseEntity<Boolean> deleteAccount(@RequestParam("id") int id, HttpServletRequest req) {
		User user = service.findById(id);
		User requestUser = jwtUtils.getUserFromtoken(req);
		if (requestUser.getId() != user.getId())
			throw new AccessDeniedException("Solo el autor de este perfil puede eliminarlo");
		service.delete(user);
		return ResponseEntity.ok(true);
	}

	//Filter by status and role
	@PreAuthorize("hasRole('ENTERPRISE')")
	@GetMapping("/all/status")
	public ResponseEntity<List<User>> findUsersByStatus(@RequestParam("status") int status) {
		return ResponseEntity.ok(service.findByStatusAndEnterprise(status, false));
	}

	//Filter workers (experiences and education)
	@PreAuthorize("hasRole('ENTERPRISE')")
	@PostMapping("/filter")
	public ResponseEntity<Collection<User>> filterWorkers(@RequestBody WorkerSearchedRequest worker) {
		return ResponseEntity.ok(service.searchWorkers(worker));
	}

}
