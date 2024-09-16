package com.productapp.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.productapp.dtos.ProductRecordDTO;
import com.productapp.models.ProductModel;
import com.productapp.services.ProductService;

import jakarta.validation.Valid;




@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity<ProductModel> createProduct(@RequestBody @Valid ProductRecordDTO productRecordDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productRecordDTO));

        // TODO configurar customValidation, ExceptionHandler;
    }

    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts() {

        List<ProductModel> productsList = productService.getAllProducts();
        for (ProductModel product : productsList) {
                UUID id = product.getIdProduct();
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
            }
        return ResponseEntity.status(HttpStatus.OK).body(productsList);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value="id") UUID id) {
        
        ProductModel product = productService.getOneProduct(id);
        product.add(linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value="id") UUID id, @RequestBody @Valid ProductRecordDTO productRecordDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(id, productRecordDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value="id") UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
    }

    // TODO incluir paginação no metodo de listagem de todos os produtos, incluindo filtros para o cliente

    

}
