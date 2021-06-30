package jobpuedo.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jobpuedo.api.entity.Category;
import jobpuedo.api.exception.NoExistsResourceException;
import jobpuedo.api.repository.ICategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private ICategoryRepository repo;

	public List<Category> findAll() {
		return repo.findByActiveOrderByName(true);
	}

	public List<Category> findAllAdmin() {
		return repo.findAll(Sort.by("name"));
	}

	public Category findCategory(int id) {
		Optional<Category> op = repo.findById(id);
		if (op.isPresent())
			return op.get();
		else
			throw new NoExistsResourceException("La categoría solicitada no existe.");
	}

	public Category addCategory(Category category) {
		category.setActive(true);
		return repo.save(category);
	}

	public Category editCategory(Category category) {
		Category oldCategory = this.findCategory(category.getId());
		oldCategory.setName(category.getName());
		oldCategory.setDescription(category.getDescription());
		return repo.save(oldCategory);
	}

	public Category deleteCategory(int id) {
		Category category = this.findCategory(id);
		category.setActive(false);
		return repo.save(category);
	}
	
	public Category reactivateCategory(int id) {
		Category category=this.findCategory(id);
		category.setActive(true);
		return repo.save(category);
	}

}
