package jobpuedo.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jobpuedo.api.entity.Application;
import jobpuedo.api.entity.Education;
import jobpuedo.api.entity.Experience;
import jobpuedo.api.entity.FileDB;
import jobpuedo.api.entity.Language;
import jobpuedo.api.entity.Offer;
import jobpuedo.api.entity.Role;
import jobpuedo.api.entity.Skill;
import jobpuedo.api.entity.User;
import jobpuedo.api.enumeration.OfferStatus;
import jobpuedo.api.exception.BadRequestException;
import jobpuedo.api.exception.FileHandlingException;
import jobpuedo.api.exception.UserNotFoundException;
import jobpuedo.api.repository.IEducationRepository;
import jobpuedo.api.repository.IExperienceRepository;
import jobpuedo.api.repository.ILanguageRepository;
import jobpuedo.api.repository.IRoleRepository;
import jobpuedo.api.repository.ISkillRepository;
import jobpuedo.api.repository.IUserRepository;
import jobpuedo.api.request.UserSearchedRequest;
import jobpuedo.api.request.WorkerSearchedRequest;
import jobpuedo.api.security.request.SignupRequest;

@Service
public class UserService {

	@Autowired
	private IUserRepository repo;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private OfferService offerService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private IEducationRepository educationRepository;
	@Autowired
	private IExperienceRepository experienceRepository;
	@Autowired
	private ILanguageRepository languageRepository;
	@Autowired
	private ISkillRepository skillRepository;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private IRoleRepository roleRepository;
	@Autowired
	private FileService fileService;

	public User findByEmail(String email) {
		Optional<User> op = repo.findByEmail(email);
		if (op.isPresent())
			return op.get();
		else
			throw new UserNotFoundException("No hay ningún usuario con el email " + email);
	}

	public List<User> findByStatusAndEnterprise(int status, boolean enterprise) {
		return repo.findByStatusAndEnterprise(status, enterprise);
	}

	public User createUser(SignupRequest signUpRequest) {
		User user = new User();
		user.setName(signUpRequest.getName());
		user.setEmail(signUpRequest.getEmail());
		user.setEnterprise(signUpRequest.isEnterprise());
		user.setPassword(encoder.encode(signUpRequest.getPassword()));
		user.setRegistered_at(new Date());
		user.setStatus(1);
		String userRole;
		// Is enterprise
		if (user.isEnterprise()) {
			user.setCif(signUpRequest.getCif());
			user.setContact_name(signUpRequest.getContact_name());
			user.setContact_last_name(signUpRequest.getContact_last_name());
			user.setDescription(signUpRequest.getDescription());
			userRole = "ROLE_ENTERPRISE";
		} else {
			// Is user (worker)
			user.setLast_name(signUpRequest.getLast_name());
			user.setBorn(signUpRequest.getBorn());
			if (!signUpRequest.getPhone().equals(""))
				user.setPhone(signUpRequest.getPhone());
			userRole = "ROLE_USER";
		}
		Role newRole = roleRepository.findByName(userRole)
				.orElseThrow(() -> new RuntimeException("No se encontró el rol a asignar."));
		List<Role> newRoles = new ArrayList<Role>();
		newRoles.add(newRole);
		user.setRoles(newRoles);
		return repo.save(user);
	}

	public User edit(User user) {
		User oldUser = this.findById(user.getId());
		oldUser.setBorn(user.getBorn());
		oldUser.setEmail(user.getEmail());
		oldUser.setLast_name(user.getLast_name());
		oldUser.setPhone(user.getPhone());
		oldUser.setName(user.getName());
		oldUser.setCif(user.getCif());
		oldUser.setContact_last_name(user.getContact_last_name());
		oldUser.setContact_name(user.getContact_name());
		oldUser.setDescription(user.getDescription());
		return repo.save(oldUser);
	}

	public User editImage(User user, MultipartFile file) {
		if (file != null) {
			FileDB imageFile = null;
			try {
				imageFile = fileService.store(file);
				if (user.getImage() != null) {
					String deleteId = user.getImage().getId();
					user.setImage(null);
					fileService.delete(deleteId);
				}
				user.setImage(imageFile);
				return this.save(user);
			} catch (IOException e) {
				throw new FileHandlingException("No se pudo cargar el archivo. Pruebe con otro formato.");
			}
		} else
			throw new BadRequestException("No se ha enviado ningún archivo");
	}

	public List<User> showUsers() {
		return repo.findAll();
	}

	public User findById(int id) {
		Optional<User> op = repo.findById(id);
		if (op.isPresent())
			return op.get();
		else
			throw new UserNotFoundException("No existe el usuario seleccionado.");
	}

	public User save(User user) {
		return repo.save(user);
	}

	public void delete(User user) {
		// Soft delete
		if (user.isEnterprise()) {
			// Inactivate offers
			List<Offer> offers = user.getOffers();
			Iterator<Offer> it = offers.iterator();
			while (it.hasNext()) {
				Offer offer = it.next();
				offer.setStatus(OfferStatus.Deleted);
				offerService.save(offer);
			}
		} else {
			// Delete applications
			List<Application> applications = user.getApplications();
			Iterator<Application> it = applications.iterator();
			while (it.hasNext()) {
				Application application = it.next();
				applicationService.deleteApplication(application);
			}
		}
		user.setStatus(0);
		this.save(user);
	}

	public List<User> filterUsers(UserSearchedRequest userSearched) {
		User user = new User();
		user.setEnterprise(null);
		ExampleMatcher matcher = ExampleMatcher.matching();
		if (!userSearched.getEmail().isBlank()) {
			user.setEmail(userSearched.getEmail());
			matcher = matcher.withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains());
		}
		if (!userSearched.getName().isBlank()) {
			// Add another field to the matcher, if necessary
			user.setName(userSearched.getName());
			matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
		}
		if (!userSearched.getStatus().isBlank()) {
			user.setStatus(Integer.parseInt(userSearched.getStatus()));
		}
		Example<User> example = Example.of(user, matcher);
		List<User> users = repo.findAll(example);
		if (!userSearched.getRole().isBlank()) {
			List<User> usersWithRole = new LinkedList<User>();
			Iterator<User> it = users.iterator();
			while (it.hasNext()) {
				User userIt = it.next();
				if (userIt.getRoles().contains(roleService.findByName(userSearched.getRole()))) {
					usersWithRole.add(userIt);
				}
			}
			return usersWithRole;
		} else
			return users;
	}

	public void deleteUserDefinitely(User user) {
		// Delete orphan children
		user.setRoles(null);
		repo.save(user);
		if (user.getApplications() != null) {
			Iterator<Application> itApp = user.getApplications().iterator();
			while (itApp.hasNext()) {
				Application app = itApp.next();
				applicationService.deleteApplication(app);
			}
		}
		if (user.getEducation() != null) {
			Iterator<Education> itEd = user.getEducation().iterator();
			while (itEd.hasNext()) {
				Education ed = itEd.next();
				educationRepository.delete(ed);
			}
		}
		if (user.getExperiences() != null) {
			Iterator<Experience> itEx = user.getExperiences().iterator();
			while (itEx.hasNext()) {
				Experience ex = itEx.next();
				experienceRepository.delete(ex);
			}
		}
		if (user.getLanguages() != null) {
			Iterator<Language> itLa = user.getLanguages().iterator();
			while (itLa.hasNext()) {
				Language la = itLa.next();
				languageRepository.delete(la);
			}
		}
		if (user.getOffers() != null) {
			Iterator<Offer> itOf = user.getOffers().iterator();
			while (itOf.hasNext()) {
				Offer of = itOf.next();
				offerService.deleteDefinitely(of);
			}
		}
		if (user.getSkills() != null) {
			Iterator<Skill> itSk = user.getSkills().iterator();
			while (itSk.hasNext()) {
				Skill sk = itSk.next();
				skillRepository.delete(sk);
			}
		}
		repo.delete(user);
	}

	public User newAdmin(int id) {
		User user = this.findById(id);
		user.addRole(roleService.findByName("ROLE_ADMIN"));
		repo.save(user);
		return user;
	}

	public Collection<User> searchWorkers(WorkerSearchedRequest worker) {
		// Filter workers
		if (worker.getEducation().isBlank() && worker.getExperiences().isBlank())
			return repo.findByStatusAndEnterprise(1, false);
		Set<User> usersEducation = new HashSet<User>();
		Set<User> usersExperience = new HashSet<User>();
		if (!worker.getEducation().isBlank()) {
			// Search coincidences in the workers' education
			ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name",
					ExampleMatcher.GenericPropertyMatchers.contains());
			Education education = new Education();
			education.setName(worker.getEducation());
			Example<Education> example = Example.of(education, matcher);
			List<Education> matchedEducation = educationRepository.findAll(example);
			Iterator<Education> it = matchedEducation.iterator();
			while (it.hasNext()) {
				usersEducation.add(it.next().getUser());
			}
			if (worker.getExperiences().isBlank())
				return usersEducation;
		}
		if (!worker.getExperiences().isBlank()) {
			// Search coincidences in the workers' experiences
			// 2 fields evaluated: position and comments
			// Position
			ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("position",
					ExampleMatcher.GenericPropertyMatchers.contains());
			Experience experience = new Experience();
			experience.setPosition(worker.getExperiences());
			Example<Experience> example = Example.of(experience, matcher);
			List<Experience> matchedExperience = experienceRepository.findAll(example);
			Iterator<Experience> it = matchedExperience.iterator();
			while (it.hasNext()) {
				usersExperience.add(it.next().getUser());
			}
			// Comments
			matcher = ExampleMatcher.matching().withMatcher("comments",
					ExampleMatcher.GenericPropertyMatchers.contains());
			experience = new Experience();
			experience.setComments(worker.getExperiences());
			example = Example.of(experience, matcher);
			matchedExperience = experienceRepository.findAll(example);
			it = matchedExperience.iterator();
			while (it.hasNext()) {
				usersExperience.add(it.next().getUser());
			}
			if (worker.getEducation().isBlank())
				return usersExperience;
		}
		// Merge both Set
		Set<User> usersFound = new HashSet<User>();
		Iterator<User> it = usersEducation.iterator();
		while (it.hasNext()) {
			User user = it.next();
			if (usersExperience.contains(user))
				usersFound.add(user);
		}
		return usersFound;
	}

}
