package jobpuedo.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jobpuedo.api.entity.Application;
import jobpuedo.api.entity.Offer;
import jobpuedo.api.enumeration.ApplicationStatus;
import jobpuedo.api.response.ApplicationResponse;
import jobpuedo.api.security.jwt.JwtUtils;
import jobpuedo.api.service.ApplicationService;
import jobpuedo.api.service.OfferService;

@RestController
@RequestMapping("/application")
public class ApplicationController {

	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private OfferService offerService;

	@PreAuthorize("hasRole('USER')")
	@PostMapping("/create")
	public ResponseEntity<Application> apply(@RequestParam("offer_id") int offer_id,
			@RequestParam("comments") String comments, HttpServletRequest req) {
		//Inscribirse a una oferta
		return ResponseEntity.ok(applicationService.create(offer_id, comments, req));
	}

	@PreAuthorize("hasRole('ENTERPRISE')")
	@GetMapping("/accept")
	public ResponseEntity<Application> acceptCandidate(@RequestParam("application_id") int application_id) {
		//Aceptar candidato
		return ResponseEntity.ok(applicationService.select(application_id, ApplicationStatus.ACCEPTED));
	}

	@PreAuthorize("hasRole('ENTERPRISE')")
	@GetMapping("/reject")
	public ResponseEntity<Application> rejectCandidate(@RequestParam("application_id") int application_id) {
		//Rechazar candidato
		return ResponseEntity.ok(applicationService.select(application_id, ApplicationStatus.REJECTED));
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/dashboard")
	public ResponseEntity<List<ApplicationResponse>> getMyApplications(HttpServletRequest req) {
		//Obtener mis inscripciones a ofertas
		List<Application> applications = applicationService.getMyApplications(jwtUtils.getUserFromtoken(req));
		return ResponseEntity.ok(applicationService.getApplicationResponse(applications));
	}

	@PreAuthorize("hasRole('ENTERPRISE')")
	@GetMapping("/offer")
	public ResponseEntity<List<ApplicationResponse>> getApplicationsFromOffer(@RequestParam("offer_id") int offer_id) {
		//Obtener las inscripciones a una oferta
		Offer offer = offerService.findById(offer_id);
		List<Application> applications = offer.getApplications();
		return ResponseEntity.ok(applicationService.getApplicationResponse(applications));
	}

}
