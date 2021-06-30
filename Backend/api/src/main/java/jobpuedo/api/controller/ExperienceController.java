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

import jobpuedo.api.entity.Experience;
import jobpuedo.api.entity.User;
import jobpuedo.api.security.jwt.JwtUtils;
import jobpuedo.api.service.ExperienceService;

@RestController
@RequestMapping("/experience")
public class ExperienceController {
	@Autowired
	private ExperienceService experienceService;
	@Autowired
	private JwtUtils jwtUtils;

	//Add
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/add")
	public ResponseEntity<User> add(@RequestBody Experience experience, HttpServletRequest req) {
		User user = jwtUtils.getUserFromtoken(req);
		return ResponseEntity.ok(experienceService.add(experience, user).getUser());
	}

	//Edit
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/edit/{id}")
	public ResponseEntity<User> edit(@PathVariable("id") int id, @RequestBody Experience experience) {
		return ResponseEntity.ok(experienceService.edit(id, experience).getUser());
	}

	//Delete
	@PreAuthorize("hasRole('USER')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<User> delete(@PathVariable("id") int id) {
		return ResponseEntity.ok(experienceService.delete(id));
	}

}
