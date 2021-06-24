package jobpuedo.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jobpuedo.api.entity.Offer;
import jobpuedo.api.entity.User;
import jobpuedo.api.security.jwt.JwtUtils;
import jobpuedo.api.service.OfferService;
import jobpuedo.api.service.UserService;

@RestController
@RequestMapping("/accessibility")
//Todos los recursos están mapeados a la URL /accesibility
public class AccessController {

	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private UserService userService;
	@Autowired
	private OfferService offerService;

	@GetMapping("/profile")//Mapeado a petición GET a /accedibility/profile
	public ResponseEntity<Boolean> accessProfiles(@RequestParam("id") int id, HttpServletRequest req) {
		//Determinar si se puede acceder a ver un perfil/CV
		User user = jwtUtils.getUserFromtoken(req);
		if (user.isEnterprise())
			return ResponseEntity.ok(true);
		else if (user.getId() == id)
			return ResponseEntity.ok(true);
		else
			throw new AccessDeniedException("Acceso denegado.");
	}

	@PreAuthorize("hasAnyRole('USER','ENTERPRISE')")
	@GetMapping("/identity")
	public ResponseEntity<Boolean> checkIdentity(@RequestParam int id, HttpServletRequest req) {
		//Comprobar si el usuario que accede al perfil es el usuario dueño del perfil
		User user = jwtUtils.getUserFromtoken(req);
		if (id == user.getId())
			return ResponseEntity.ok(true);
		else
			throw new AccessDeniedException("Acceso denegado");
	}

	@PreAuthorize("hasRole('ENTERPRISE')")
	@GetMapping("/offer/owner")
	public ResponseEntity<Boolean> isMyOffer(@RequestParam int offer_id, HttpServletRequest req) {
		//Comprobar dueño de la oferta
		int user_id = jwtUtils.getUserFromtoken(req).getId();
		User user = userService.findById(user_id);
		Offer offer = offerService.findById(offer_id);
		if (offer.getEnterprise().getId() == user.getId())
			return ResponseEntity.ok(true);
		else
			throw new AccessDeniedException("Acceso denegado.");
	}

	@PreAuthorize("hasAnyRole('USER','ENTERPRISE')")
	@GetMapping("/authenticated")
	public ResponseEntity<Boolean> isEnterpriseUser() {
		//Comprobar si el usuario está autenticado, la anotación PreAuthorize lanzará una excepción controlada
		return ResponseEntity.ok(true);
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/user")
	public ResponseEntity<Boolean> accessUsers() {
		return ResponseEntity.ok(true);
	}

	@PreAuthorize("hasRole('ENTERPRISE')")
	@GetMapping("/enterprise")
	public ResponseEntity<Boolean> accessEnterprise() {
		return ResponseEntity.ok(true);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public ResponseEntity<Boolean> accessAdmin() {
		return ResponseEntity.ok(true);
	}

}
