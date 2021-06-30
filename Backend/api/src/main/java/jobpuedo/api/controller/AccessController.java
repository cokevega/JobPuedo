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

//Help to the Frontend's security (for example: Angular guards)
@RestController
@RequestMapping("/accessibility")
public class AccessController {

	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private UserService userService;
	@Autowired
	private OfferService offerService;

	//Access to a concrete profile (it depends on the owner's role and user's role)
	@GetMapping("/profile")
	public ResponseEntity<Boolean> accessProfiles(@RequestParam("id") int id, HttpServletRequest req) {
		User user = jwtUtils.getUserFromtoken(req);
		if (user.isEnterprise())
			return ResponseEntity.ok(true);
		else if (user.getId() == id)
			return ResponseEntity.ok(true);
		else
			throw new AccessDeniedException("Acceso denegado.");
	}

	//Check if the user is the owner of the profile
	@PreAuthorize("hasAnyRole('USER','ENTERPRISE')")
	@GetMapping("/identity")
	public ResponseEntity<Boolean> checkIdentity(@RequestParam int id, HttpServletRequest req) {
		User user = jwtUtils.getUserFromtoken(req);
		if (id == user.getId())
			return ResponseEntity.ok(true);
		else
			throw new AccessDeniedException("Acceso denegado");
	}

	//Check the owner of the offer
	@PreAuthorize("hasRole('ENTERPRISE')")
	@GetMapping("/offer/owner")
	public ResponseEntity<Boolean> isMyOffer(@RequestParam int offer_id, HttpServletRequest req) {
		int user_id = jwtUtils.getUserFromtoken(req).getId();
		User user = userService.findById(user_id);
		Offer offer = offerService.findById(offer_id);
		if (offer.getEnterprise().getId() == user.getId())
			return ResponseEntity.ok(true);
		else
			throw new AccessDeniedException("Acceso denegado.");
	}

	//Basic ENTERPRISE/USER role authorization
	@PreAuthorize("hasAnyRole('USER','ENTERPRISE')")
	@GetMapping("/authenticated")
	public ResponseEntity<Boolean> isEnterpriseUser() {
		return ResponseEntity.ok(true);
	}

	//Basic USER role authorization
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/user")
	public ResponseEntity<Boolean> accessUsers() {
		return ResponseEntity.ok(true);
	}

	//Basic ENTERPRISE role authorization
	@PreAuthorize("hasRole('ENTERPRISE')")
	@GetMapping("/enterprise")
	public ResponseEntity<Boolean> accessEnterprise() {
		return ResponseEntity.ok(true);
	}

	//Basic ADMIN role authorization
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public ResponseEntity<Boolean> accessAdmin() {
		return ResponseEntity.ok(true);
	}

}
