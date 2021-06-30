package jobpuedo.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jobpuedo.api.entity.Education;
import jobpuedo.api.entity.User;
import jobpuedo.api.security.jwt.JwtUtils;
import jobpuedo.api.service.EducationService;

@RestController
@RequestMapping("/education")
public class EducationController {

	@Autowired
	private EducationService educationService;
	@Autowired
	private JwtUtils jwtUtils;

	//Add
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/add")
	public ResponseEntity<User> add(@RequestBody Education education, HttpServletRequest req) {
		User user = jwtUtils.getUserFromtoken(req);
		return ResponseEntity.ok(educationService.add(education, user).getUser());
	}

	//Edit
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/edit/{id}")
	public ResponseEntity<User> edit(@PathVariable("id") int id, @RequestBody Education education) {
		return ResponseEntity.ok(educationService.edit(id, education).getUser());
	}

	//Delete
	@PreAuthorize("hasRole('USER')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<User> delete(@PathVariable("id") int id) {
		return ResponseEntity.ok(educationService.delete(id));
	}
}
