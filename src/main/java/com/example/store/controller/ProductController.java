package com.example.store.controller;

import com.example.store.dto.ProductDTO;
import com.example.store.entity.Product;
import com.example.store.mapper.ProductMapper;
import com.example.store.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productMapper.productsToProductDTOs(productRepository.findAll());
    }

    @GetMapping("/{idString}")
    public ProductDTO getProduct(@PathVariable("idString") String idString) {
        try {
            long idLong = Long.parseLong(idString);
            return productRepository
                    .findById(idLong)
                    .map(productMapper::productToProductDTO)
                    .orElse(null);
        } catch (NumberFormatException e) {
            System.err.println("Unknown product number: " + e.getMessage());
        }
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createProduct(@RequestBody Product product) {
        return productMapper.productToProductDTO(productRepository.save(product));
    }
}
