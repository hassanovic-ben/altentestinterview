package com.hassan.altensecurity.controller;

import com.hassan.altensecurity.model.Product;
import com.hassan.altensecurity.repository.ProductRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@NonNull @RequestBody Product product) {
        setCreationAndUpdateDate(product);
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    private void setCreationAndUpdateDate(@NonNull Product product) {
        product.setCreatedAt(LocalDate.now());
        product.setUpdatedAt(LocalDate.now());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Integer id) {
        try {
            Product product = productRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
            productRepository.deleteById(product.getProduct_id());
            return ResponseEntity.ok().build();
        }
        catch (EmptyResultDataAccessException exception){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        try {
            Product savedProduct = productRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
            Product updatedProduct = updateProduct(product, savedProduct);
            productRepository.save(updatedProduct);
            return ResponseEntity.ok(updatedProduct);
        } catch (EmptyResultDataAccessException e) {
                throw new RuntimeException(e);
        }
    }

    private static Product updateProduct(Product product, Product savedProduct) {
        savedProduct.setName(product.getName());
        savedProduct.setDescription(product.getDescription());
        savedProduct.setPrice(product.getPrice());
        savedProduct.setCategory(product.getCategory());
        savedProduct.setPrice(product.getPrice());
        savedProduct.setQuantity(product.getQuantity());
        savedProduct.setCode(product.getCode());
        savedProduct.setInternalReference(product.getInternalReference());
        savedProduct.setRating(product.getRating());
        savedProduct.setShellId(product.getShellId());
        savedProduct.setUpdatedAt(LocalDate.now());
        return savedProduct;
    }
}