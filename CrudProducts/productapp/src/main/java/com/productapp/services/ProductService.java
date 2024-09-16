package com.productapp.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productapp.dtos.ProductRecordDTO;
import com.productapp.exceptions.ProductNotFoundException;
import com.productapp.models.ProductModel;
import com.productapp.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired ProductRepository productRepository;

    public ProductModel createProduct(ProductRecordDTO productRecordDTO){
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDTO, productModel);
        return productRepository.save(productModel);
    }

    public List<ProductModel> getAllProducts(){
        List<ProductModel> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found.");
        }
        return products;
    }

    public ProductModel getOneProduct(UUID id){
        return productRepository.findById(id).
            orElseThrow(() -> new ProductNotFoundException("Product with ID " +id + " not found."));
    }

    public ProductModel updateProduct(UUID id, ProductRecordDTO productRecordDTO){
        ProductModel existingProduct = productRepository.findById(id)
            .orElseThrow( () -> new ProductNotFoundException("Product with ID " +id + " not found to update.") ); 
        BeanUtils.copyProperties(productRecordDTO, existingProduct);
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(UUID id){
        ProductModel existingProduct = productRepository.findById(id)
            .orElseThrow( () -> new ProductNotFoundException("Product with ID " +id + " not found to delete.") );    
        productRepository.delete(existingProduct);
    }

}
