package jobpuedo.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jobpuedo.api.entity.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {
	public Optional<Role> findByName(String name);
}
