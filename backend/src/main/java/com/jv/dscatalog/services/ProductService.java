package com.jv.dscatalog.services;


import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jv.dscatalog.dto.ProductDTO;
import com.jv.dscatalog.entities.Product;
import com.jv.dscatalog.repositories.ProductRepository;
import com.jv.dscatalog.services.exceptions.DatabaseException;
import com.jv.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
  @Autowired
  private ProductRepository repository;

  @Transactional(readOnly = true)
  public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
    Page<Product> list = repository.findAll(pageRequest);
    Page<ProductDTO> listDto = list.map(product -> new ProductDTO(product));
    return listDto;
  }


  @Transactional(readOnly = true)
  public ProductDTO findById(Long id) {
    Optional<Product> productOptional = repository.findById(id);
    Product entity = productOptional.orElseThrow(() ->
      new ResourceNotFoundException("Product not found")
    );
    return new ProductDTO(entity, entity.getCategories());
  }

  @Transactional
  public ProductDTO insert(ProductDTO dto) {
    Product entity = new Product();
    // entity.setName(dto.getName());
    entity = repository.save(entity);
    return new ProductDTO(entity);
  }

  @Transactional
  public ProductDTO update(Long id, ProductDTO dto) {
    try {
      Product entity = repository.getReferenceById(id);
      //entity.setName(dto.getName());
      entity = repository.save(entity);
      return new ProductDTO(entity);
    } catch (EntityNotFoundException error) {
      throw new ResourceNotFoundException("ID " + id + " not found");
    }
  }

  @Transactional
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
