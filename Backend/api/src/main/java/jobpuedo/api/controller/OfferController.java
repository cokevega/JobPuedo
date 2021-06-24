package jobpuedo.api.controller;

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

import jobpuedo.api.entity.Offer;
import jobpuedo.api.entity.User;
import jobpuedo.api.enumeration.OfferStatus;
import jobpuedo.api.exception.BadRequestException;
import jobpuedo.api.request.OfferRequest;
import jobpuedo.api.request.OfferSearchedRequest;
import jobpuedo.api.security.jwt.JwtUtils;
import jobpuedo.api.service.OfferService;

@RestController
@RequestMapping("/offer")
public class OfferController {
	@Autowired
	private OfferService service;
	@Autowired
	private JwtUtils jwtUtils;

	@GetMapping("/all/status")
	public ResponseEntity<List<Offer>> getOffersByStatus(@RequestParam String status) {
		//Obtener ofertas por status
		//Comprobar que el status es uno de los posibles status
		if (!status.equals("Active") && !status.equals("Created") && !status.equals("Deleted"))
			throw new BadRequestException("Los posibles estados de una oferta son: 'Created', 'Deleted' y 'Active'.");
		else
			return ResponseEntity.ok(service.findOffersByStatus(OfferStatus.valueOf(status)));
	}

	@PostMapping("/search")
	public ResponseEntity<List<Offer>> searchOffers(@RequestBody OfferSearchedRequest offer) {
		//Buscar ofertas ACTIVAS mediante los filtros introducidos
		offer.setStatus("Active");
		return ResponseEntity.ok(service.findFilteredOffer(offer));
	}

	@GetMapping("/show")
	public ResponseEntity<Offer> findOffer(@RequestParam int id) {
		//Mostrar oferta
		return ResponseEntity.ok(service.findById(id));
	}

	@PreAuthorize("hasRole('ENTERPRISE')")
	@PostMapping("/create")
	public ResponseEntity<Offer> createOffer(@RequestBody OfferRequest offer, HttpServletRequest request) {
		//Crear oferta
		return ResponseEntity.ok(service.createOffer(offer, request));
	}

	@PreAuthorize("hasAnyRole('ENTERPRISE','USER')")
	@GetMapping("/dashboard")
	public ResponseEntity<List<Offer>> getMyOffers(HttpServletRequest req) {
		//Obtener las ofertas de mi dashboard: las que estoy inscrito si soy usuario y las que he subido si soy empresa
		User user = jwtUtils.getUserFromtoken(req);
		return ResponseEntity.ok(service.getDashboard(user));
	}

	@PreAuthorize("hasRole('ENTERPRISE')")
	@PutMapping("/edit")
	public ResponseEntity<Offer> editOffer(@RequestBody Offer offer, HttpServletRequest req) {
		//Editar oferta
		return ResponseEntity.ok(service.editOffer(offer, req));
	}

	@PreAuthorize("hasRole('ENTERPRISE')")
	@DeleteMapping("/delete")
	public ResponseEntity<Offer> deleteOffer(@RequestParam int id, HttpServletRequest req) {
		//Soft delete de oferta: inactivarla
		int enterprise_id = jwtUtils.getIdFromJwtToken(jwtUtils.getTokenFromRequest(req));
		Offer offer = service.findById(id);
		if (enterprise_id == offer.getEnterprise().getId())
			return ResponseEntity.ok(service.softDelete(offer));
		else
			throw new AccessDeniedException("Solo el creador de esta oferta puede eliminarla.");
	}

	@PreAuthorize("hasRole('ENTERPRISE')")
	@GetMapping("/activate")
	public ResponseEntity<Offer> activateOffer(@RequestParam int id, HttpServletRequest req) {
		//Activar una oferta creada, pero no activa aún
		int enterprise_id = jwtUtils.getIdFromJwtToken(jwtUtils.getTokenFromRequest(req));
		Offer offer = service.findById(id);
		if (enterprise_id == offer.getEnterprise().getId())
			return ResponseEntity.ok(service.activateOffer(offer));
		else
			throw new AccessDeniedException("Solo el creador de esta oferta puede eliminarla.");
	}

}
