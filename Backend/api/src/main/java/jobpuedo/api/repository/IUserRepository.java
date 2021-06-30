package jobpuedo.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jobpuedo.api.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
	public Optional<User> findByEmail(String email);
	public boolean existsByEmail(String email);
	public List<User> findByStatusAndEnterprise(int status,boolean enterprise);
}