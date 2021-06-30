package jobpuedo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jobpuedo.api.entity.Experience;

@Repository
public interface IExperienceRepository extends JpaRepository<Experience, Integer> {
	
}
