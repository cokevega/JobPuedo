package jobpuedo.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jobpuedo.api.entity.Experience;
import jobpuedo.api.entity.User;
import jobpuedo.api.exception.NoExistsResourceException;
import jobpuedo.api.repository.IExperienceRepository;

@Service
public class ExperienceService {

	@Autowired
	private IExperienceRepository experienceRepository;

	public Experience findById(int id) {
		Optional<Experience> op = experienceRepository.findById(id);
		if (op.isPresent())
			return op.get();
		else
			throw new NoExistsResourceException("No existe la experiencia solicitada.");
	}

	public Experience save(Experience experience) {
		return experienceRepository.save(experience);
	}

	public Experience add(Experience experience, User user) {
		experience.setUser(user);
		return this.save(experience);
	}

	public Experience edit(int id, Experience experience) {
		Experience oldExperience = this.findById(id);
		oldExperience.setBegin(experience.getBegin());
		oldExperience.setCity(experience.getCity());
		oldExperience.setComments(experience.getComments());
		oldExperience.setEnd(experience.getEnd());
		oldExperience.setEnterprise(experience.getEnterprise());
		oldExperience.setPosition(experience.getPosition());
		return this.save(oldExperience);
	}

	public User delete(int id) {
		Experience experience = this.findById(id);
		User user = experience.getUser();
		experienceRepository.delete(experience);
		return user;
	}

}
