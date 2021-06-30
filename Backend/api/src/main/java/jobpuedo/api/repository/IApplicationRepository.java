package jobpuedo.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jobpuedo.api.entity.Application;
import jobpuedo.api.entity.User;

@Repository
public interface IApplicationRepository extends JpaRepository<Application, Integer> {
	public List<Application> findByUser(User user);
}
