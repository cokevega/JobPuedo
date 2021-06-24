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

import jobpuedo.api.entity.Language;
import jobpuedo.api.entity.User;
import jobpuedo.api.security.jwt.JwtUtils;
import jobpuedo.api.service.LanguageService;

@RestController
@RequestMapping("/language")
public class LanguageController {

	@Autowired
	private LanguageService languageService;
	@Autowired
	private JwtUtils jwtUtils;

	@PreAuthorize("hasRole('USER')")
	@PostMapping("/add")
	public ResponseEntity<User> add(@RequestBody Language language, HttpServletRequest req) {
		//Añadir nuevo idioma al CV
		User user = jwtUtils.getUserFromtoken(req);
		return ResponseEntity.ok(languageService.add(language, user).getUser());
	}

	@PreAuthorize("hasRole('USER')")
	@PutMapping("/edit/{id}")
	public ResponseEntity<User> edit(@PathVariable("id") int id, @RequestBody Language language) {
		//Editar idioma
		return ResponseEntity.ok(languageService.edit(id, language).getUser());
	}

	@PreAuthorize("hasRole('USER')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<User> delete(@PathVariable("id") int id) {
		//Eliminar idioma
		return ResponseEntity.ok(languageService.delete(id));
	}

}
