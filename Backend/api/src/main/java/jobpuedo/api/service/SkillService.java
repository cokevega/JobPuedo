package jobpuedo.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jobpuedo.api.entity.Skill;
import jobpuedo.api.entity.User;
import jobpuedo.api.exception.NoExistsResourceException;
import jobpuedo.api.repository.ISkillRepository;

@Service
public class SkillService {

	@Autowired
	private ISkillRepository skillRepository;

	public Skill save(Skill skill) {
		return skillRepository.save(skill);
	}

	public Skill findById(int id) {
		Optional<Skill> op = skillRepository.findById(id);
		if (op.isPresent())
			return op.get();
		else
			throw new NoExistsResourceException("No se ha encontrado la habilidad solicitada.");
	}

	public Skill add(Skill skill, User user) {
		skill.setUser(user);
		return this.save(skill);
	}

	public Skill edit(int id, Skill skill) {
		Skill oldSkill = this.findById(id);
		oldSkill.setLevel(skill.getLevel());
		oldSkill.setName(skill.getName());
		return this.save(oldSkill);
	}

	public User delete(int id) {
		Skill skill = this.findById(id);
		User user = skill.getUser();
		skillRepository.delete(skill);
		return user;
	}

}
