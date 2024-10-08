package com.productapp.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.productapp.models.ProductModel;


@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID>{

    
}
