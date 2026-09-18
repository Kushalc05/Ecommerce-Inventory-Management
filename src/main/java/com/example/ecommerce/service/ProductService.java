package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductRequestDTO;
import com.example.ecommerce.dto.ProductResponseDTO;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepo;

    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    // Add product / increase existing SKU stock
    public ProductResponseDTO addProduct(
            ProductRequestDTO request) {

        String sku =
                request.getSku()
                        .trim()
                        .toUpperCase();

        Optional<Product> existingProduct =
                productRepo.findBySkuIgnoreCase(sku);

        if (existingProduct.isPresent()) {

            Product existing =
                    existingProduct.get();

            existing.setStock(
                    existing.getStock()
                            + request.getStock()
            );

            Product saved =
                    productRepo.save(existing);

            return convertToResponse(saved);
        }

        Product product = new Product();

        product.setSku(sku);
        product.setName(request.getName().trim());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product saved =
                productRepo.save(product);

        return convertToResponse(saved);
    }

    // Get all products
    public List<ProductResponseDTO> getProducts() {

        return productRepo.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Delete product
    public void deleteProduct(Long id) {

        Product product =
                productRepo.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found: " + id
                                ));

        productRepo.delete(product);
    }

    // Convert Entity → Response DTO
    private ProductResponseDTO convertToResponse(
            Product product) {

        return new ProductResponseDTO(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getPrice(),
                product.getStock()
        );
    }
}