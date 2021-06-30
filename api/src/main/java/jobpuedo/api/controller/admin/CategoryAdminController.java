package jobpuedo.api.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jobpuedo.api.entity.Category;
import jobpuedo.api.service.CategoryService;

@RestController
@RequestMapping("/admin/category")
public class CategoryAdminController {

	@Autowired
	private CategoryService categoryService;

	//Get all categories
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/all")
	public ResponseEntity<List<Category>> findAll() {
		return ResponseEntity.ok(categoryService.findAllAdmin());
	}

	//Add new category
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add")
	public ResponseEntity<Category> addCategory(@RequestBody Category category) {
		return ResponseEntity.ok(categoryService.addCategory(category));
	}
	
	//Edit category
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/edit")
	public ResponseEntity<Category> editCategory(@RequestBody Category category) {
		return ResponseEntity.ok(categoryService.editCategory(category));
	}
	
	//Soft delete: inactivate category
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Category> editCategory(@PathVariable("id") int id) {
		return ResponseEntity.ok(categoryService.deleteCategory(id));
	}
	
	//Activate category
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/reactivate/{id}")
	public ResponseEntity<Category> reactivateCategory(@PathVariable("id") int id) {
		return ResponseEntity.ok(categoryService.reactivateCategory(id));
	}

}
