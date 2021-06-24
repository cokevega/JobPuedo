package jobpuedo.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jobpuedo.api.entity.Application;
import jobpuedo.api.entity.User;
import jobpuedo.api.enumeration.ApplicationStatus;
import jobpuedo.api.exception.BadRequestException;
import jobpuedo.api.exception.NoExistsResourceException;
import jobpuedo.api.repository.IApplicationRepository;
import jobpuedo.api.response.ApplicationResponse;
import jobpuedo.api.security.jwt.JwtUtils;

@Service
public class ApplicationService {

	@Autowired
	private IApplicationRepository applicationRepository;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private OfferService offerService;
	@Autowired
	private UserService userService;

	public Application save(Application application) {
		//Guardar inscripción
		return applicationRepository.save(application);
	}

	public Application findById(int application_id) {
		//Encontrar inscripción por id
		Optional<Application> op = applicationRepository.findById(application_id);
		if (op.isPresent())
			return op.get();
		else
			throw new NoExistsResourceException("La candidatura seleccionada no existe.");
	}

	public Application select(int application_id, ApplicationStatus status) {
		//Aceptar/rechazar candidato
		Application application = this.findById(application_id);
		if (!status.equals(ApplicationStatus.ACCEPTED) && !status.equals(ApplicationStatus.REJECTED)
				&& !status.equals(ApplicationStatus.UNKNOWN))
			throw new BadRequestException(
					"Los posibles estados de una candidatura son 'ACCEPTED', 'REJECTED' y 'UNKNOWN'");
		else {
			application.setStatus(status);
			applicationRepository.save(application);
			return application;
		}
	}

	public List<Application> getMyApplications(User user) {
		//Obtener mis inscripciones
		return applicationRepository.findByUser(user);
	}

	public User getUserFromApplication(int application_id) {
		//Obtener el usuario de una inscripción
		return applicationRepository.findById(application_id).get().getUser();
	}

	public void deleteApplication(Application application) {
		//Eliminar inscripción
		applicationRepository.delete(application);
	}
	
	public Application create(int offer_id,String comments,HttpServletRequest req) {
		//Crear inscripción
		Application application = new Application();
		application.setComments(comments);
		application.setDate(new Date());
		application.setOffer(offerService.findById(offer_id));
		application.setUser(userService.findById(jwtUtils.getUserFromtoken(req).getId()));
		application.setStatus(ApplicationStatus.UNKNOWN);
		return this.save(application);
	}
	
	public List<ApplicationResponse> getApplicationResponse(List<Application> applications) {
		//Enviar modelo de inscripción ApplicationResponse en lugar de Application
		List<ApplicationResponse> applicationsResponse = new ArrayList<ApplicationResponse>();
		Iterator<Application> it = applications.iterator();
		while (it.hasNext()) {
			Application tmpApplication = it.next();
			ApplicationResponse application = new ApplicationResponse(tmpApplication.getId(), tmpApplication.getDate(),
					tmpApplication.getComments(), tmpApplication.getStatus(), tmpApplication.getOffer(),
					tmpApplication.getUser());
			applicationsResponse.add(application);
		}
		return applicationsResponse;
	}
}
