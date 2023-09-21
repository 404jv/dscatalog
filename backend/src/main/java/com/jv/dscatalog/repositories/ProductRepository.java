package com.jv.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jv.dscatalog.entities.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {

}
