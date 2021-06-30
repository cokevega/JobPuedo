package jobpuedo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jobpuedo.api.entity.Skill;

@Repository
public interface ISkillRepository extends JpaRepository<Skill, Integer> {

}
