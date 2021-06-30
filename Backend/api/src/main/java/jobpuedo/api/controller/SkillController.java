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

import jobpuedo.api.entity.Skill;
import jobpuedo.api.entity.User;
import jobpuedo.api.security.jwt.JwtUtils;
import jobpuedo.api.service.SkillService;

@RestController
@RequestMapping("/skill")
public class SkillController {

	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private SkillService skillService;

	//Add
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/add")
	public ResponseEntity<User> addSkill(@RequestBody Skill skill, HttpServletRequest req) {
		User user = jwtUtils.getUserFromtoken(req);
		return ResponseEntity.ok(skillService.add(skill, user).getUser());
	}

	//Edit
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/edit/{id}")
	public ResponseEntity<User> editSkill(@PathVariable("id") int id, @RequestBody Skill skill) {
		return ResponseEntity.ok(skillService.edit(id, skill).getUser());
	}

	//Delete
	@PreAuthorize("hasRole('USER')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<User> deleteSkill(@PathVariable("id") int id) {
		return ResponseEntity.ok(skillService.delete(id));
	}

}
