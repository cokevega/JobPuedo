package jobpuedo.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import jobpuedo.api.entity.Application;
import jobpuedo.api.entity.Offer;
import jobpuedo.api.entity.User;
import jobpuedo.api.enumeration.OfferStatus;
import jobpuedo.api.exception.NoExistsResourceException;
import jobpuedo.api.repository.IOfferRepository;
import jobpuedo.api.request.OfferRequest;
import jobpuedo.api.request.OfferSearchedRequest;
import jobpuedo.api.security.jwt.JwtUtils;

@Service
public class OfferService {

	@Autowired
	private IOfferRepository repo;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private ApplicationService applicationService;

	public List<Offer> findAllOffers() {
		return (List<Offer>) repo.findAll(Sort.by("date").descending());
	}

	public Offer editOffer(Offer offer, HttpServletRequest req) {
		int enterprise_id = jwtUtils.getIdFromRequest(req);
		if (enterprise_id == offer.getEnterprise().getId()) {
			Offer oldOffer = this.findById(offer.getId());
			oldOffer.setName(offer.getName());
			oldOffer.setSalary(offer.getSalary());
			oldOffer.setDescription(offer.getDescription());
			oldOffer.setCategory(categoryService.findCategory(offer.getCategory().getId()));
			oldOffer.setDetails(offer.getDetails());
			this.save(oldOffer);
			return oldOffer;
		} else
			throw new AccessDeniedException("Solo la empresa creadora de esta oferta puede editarla.");
	}

	public Offer createOffer(OfferRequest offer, HttpServletRequest req) {
		int enterpriseId = jwtUtils.getIdFromRequest(req);
		Offer newOffer = new Offer();
		newOffer.setName(offer.getName());
		newOffer.setDescription(offer.getDescription());
		newOffer.setDate(new Date());
		newOffer.setSalary(offer.getSalary());
		if (offer.isStatus())
			newOffer.setStatus(OfferStatus.Active);
		else
			newOffer.setStatus(OfferStatus.Created);
		newOffer.setDetails(offer.getDetails());
		newOffer.setCategory(categoryService.findCategory(offer.getCategory()));
		newOffer.setEnterprise(userService.findById(enterpriseId));
		this.save(newOffer);
		return newOffer;
	}

	public List<Offer> findFilteredOffer(OfferSearchedRequest offerSearched) {
		Offer offer = new Offer();
		ExampleMatcher matcher = ExampleMatcher.matching();
		if (offerSearched.getCategory() != null && !offerSearched.getCategory().isBlank()) {
			offer.setCategory(categoryService.findCategory(Integer.parseInt(offerSearched.getCategory())));
		}
		if (offerSearched.getDescription() != null && !offerSearched.getDescription().isBlank()) {
			offer.setDetails(offerSearched.getDescription());
			matcher = matcher.withMatcher("details", ExampleMatcher.GenericPropertyMatchers.contains());
		}
		if (offerSearched.getEnterprise() != null && !offerSearched.getEnterprise().isBlank()) {
			offer.setEnterprise(new User());
			offer.getEnterprise().setEnterprise(true);
			offer.getEnterprise().setStatus(null);
			offer.getEnterprise().setId(Integer.parseInt(offerSearched.getEnterprise()));
		}
		if (offerSearched.getName() != null && !offerSearched.getName().isBlank()) {
			//Add more matchers, if necessary
			offer.setName(offerSearched.getName());
			matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
		}
		if (offerSearched.getStatus() != null && !offerSearched.getStatus().isBlank()) {
			offer.setStatus(OfferStatus.valueOf(offerSearched.getStatus()));
		}
		Example<Offer> example = Example.of(offer, matcher);
		return repo.findAll(example, Sort.by("date").descending());
	}

	public List<Offer> findOffersByStatus(OfferStatus status) {
		return repo.findByStatusOrderByDateDesc(status);
	}

	public Offer findById(int id) {
		Optional<Offer> op = repo.findById(id);
		if (op.isPresent())
			return op.get();
		else
			throw new NoExistsResourceException("La oferta solicitada no existe");
	}

	public Offer save(Offer offer) {
		return repo.save(offer);
	}

	public List<Offer> findByEnterprise(User user) {
		return repo.findByEnterprise(user);
	}

	public List<Offer> getDashboard(User user) {
		//If worker, the user will see his/her applications, if enterprise, his/her offers
		List<Offer> offers = new ArrayList<Offer>();
		if (user.isEnterprise())
			offers = this.findByEnterprise(user);
		else {
			List<Application> applications = user.getApplications();
			Iterator<Application> it = applications.iterator();
			while (it.hasNext()) {
				Application application = it.next();
				if (application.getOffer().getStatus().equals(OfferStatus.Active))
					offers.add(application.getOffer());
			}
		}
		return offers;
	}

	public Offer softDelete(Offer offer) {
		offer.setStatus(OfferStatus.Deleted);
		return this.save(offer);
	}

	public void deleteDefinitely(Offer offer) {
		//Delete orphan children
		if (offer.getApplications() != null) {
			Iterator<Application> it = offer.getApplications().iterator();
			while (it.hasNext()) {
				Application app = it.next();
				applicationService.deleteApplication(app);
			}
		}
		repo.delete(offer);
	}
	
	public Offer activateOffer(Offer offer) {
		offer.setStatus(OfferStatus.Active);
		return this.save(offer);
	}

}
