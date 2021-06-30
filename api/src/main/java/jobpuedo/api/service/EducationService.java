package jobpuedo.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jobpuedo.api.entity.Education;
import jobpuedo.api.entity.User;
import jobpuedo.api.exception.NoExistsResourceException;
import jobpuedo.api.repository.IEducationRepository;

@Service
public class EducationService {

	@Autowired
	private IEducationRepository educationRepository;

	public Education findById(int id) {
		Optional<Education> op = educationRepository.findById(id);
		if (op.isPresent())
			return op.get();
		else
			throw new NoExistsResourceException("No existe la formación solicitada.");
	}

	public Education save(Education education) {
		return educationRepository.save(education);
	}

	public Education add(Education education, User user) {
		education.setUser(user);
		return this.save(education);
	}
	
	public Education edit(int id,Education education) {
		Education oldEducation = this.findById(id);
		oldEducation.setBegin(education.getBegin());
		oldEducation.setEnd(education.getEnd());
		oldEducation.setName(education.getName());
		oldEducation.setSchool(education.getSchool());
		oldEducation.setTitle(education.getTitle());
		return this.save(oldEducation);
	}

	public User delete(int id) {
		Education education = this.findById(id);
		User user = education.getUser();
		educationRepository.delete(education);
		return user;
	}

}
