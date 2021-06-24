package jobpuedo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jobpuedo.api.entity.Category;
import jobpuedo.api.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService service;

	@GetMapping("/all")
	public ResponseEntity<List<Category>> allCategories() {
		//Obtener todas las categorías
		return ResponseEntity.ok(service.findAll());
	}
}
