package com.jv.dscatalog.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jv.dscatalog.dto.CategoryDTO;
import com.jv.dscatalog.entities.Category;
import com.jv.dscatalog.repositories.CategoryRepository;
import com.jv.dscatalog.services.exceptions.DatabaseException;
import com.jv.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<Category> findAll() {
		return repository.findAll();
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(
			() -> new ResourceNotFoundException("Category not found")
		);
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getReferenceById(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
		  return new CategoryDTO(entity);
		} catch (EntityNotFoundException error) {
			throw new ResourceNotFoundException("ID " + id + " not found");
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException error) {
			throw new ResourceNotFoundException("ID " + id + " not found");
		} catch (DataIntegrityViolationException error) {
			throw new DatabaseException("Integrity violation");
		}
	}
}
