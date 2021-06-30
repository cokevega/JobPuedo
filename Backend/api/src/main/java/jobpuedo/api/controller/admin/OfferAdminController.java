package jobpuedo.api.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jobpuedo.api.entity.Offer;
import jobpuedo.api.repository.IOfferRepository;
import jobpuedo.api.request.OfferSearchedRequest;
import jobpuedo.api.service.OfferService;

@RestController
@RequestMapping("/admin/offer")
public class OfferAdminController {

	@Autowired
	private IOfferRepository offerRepository;
	@Autowired
	private OfferService offerService;

	//Get all offers
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/all")
	public ResponseEntity<List<Offer>> getAllOffers() {
		return ResponseEntity.ok(offerRepository.findAll(Sort.by("date").descending()));
	}

	//Filter offers
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/filter")
	public ResponseEntity<List<Offer>> filterOffers(@RequestBody OfferSearchedRequest offerSearched) {
		return ResponseEntity.ok(offerService.findFilteredOffer(offerSearched));
	}

	//Delete
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteOffer(@PathVariable("id") int id) {
		offerService.deleteDefinitely(offerService.findById(id));
		return ResponseEntity.ok(true);
	}

}
