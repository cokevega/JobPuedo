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

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/all")
	public ResponseEntity<List<Category>> findAll() {
		//Obtener todas las categorías
		return ResponseEntity.ok(categoryService.findAllAdmin());
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add")
	public ResponseEntity<Category> addCategory(@RequestBody Category category) {
		//Añadir nueva categoría
		return ResponseEntity.ok(categoryService.addCategory(category));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/edit")
	public ResponseEntity<Category> editCategory(@RequestBody Category category) {
		//Editar categoría
		return ResponseEntity.ok(categoryService.editCategory(category));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Category> editCategory(@PathVariable("id") int id) {
		//Soft delete de categoría: inactivarla
		return ResponseEntity.ok(categoryService.deleteCategory(id));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/reactivate/{id}")
	public ResponseEntity<Category> reactivateCategory(@PathVariable("id") int id) {
		//Activar categoría inactiva
		return ResponseEntity.ok(categoryService.reactivateCategory(id));
	}

}
