package com.jv.dscatalog.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jv.dscatalog.dto.CategoryDTO;
import com.jv.dscatalog.entities.Category;
import com.jv.dscatalog.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping()
	public ResponseEntity<List<CategoryDTO>> findAll() {
		List<Category> list = service.findAll();
		List<CategoryDTO> listDto = list
			.stream()
			.map(x -> new CategoryDTO(x))
			.collect(Collectors.toList()); 
		return ResponseEntity.ok().body(listDto);
	}
}
