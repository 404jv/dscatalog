package com.jv.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jv.dscatalog.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
