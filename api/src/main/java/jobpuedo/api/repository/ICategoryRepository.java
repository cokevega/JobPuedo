package jobpuedo.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jobpuedo.api.entity.Category;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Integer> {
	List<Category> findByActiveOrderByName(boolean active);
}
