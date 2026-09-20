package com.example.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {

        if (product.getSku() == null ||
                product.getSku().trim().isEmpty()) {
            throw new IllegalArgumentException("SKU is required");
        }

        Product existingProduct =
                productRepository
                        .findBySkuIgnoreCase(product.getSku().trim())
                        .orElse(null);

        // If SKU already exists, increase stock
        if (existingProduct != null) {

            existingProduct.setStock(
                    existingProduct.getStock() + product.getStock()
            );

            // Update name if provided
            if (product.getName() != null &&
                    !product.getName().trim().isEmpty()) {

                existingProduct.setName(
                        product.getName().trim()
                );
            }

            // Update price if valid
            if (product.getPrice() > 0) {
                existingProduct.setPrice(
                        product.getPrice()
                );
            }

            // Update image if provided
            if (product.getImageUrl() != null &&
                    !product.getImageUrl().trim().isEmpty()) {

                existingProduct.setImageUrl(
                        product.getImageUrl().trim()
                );
            }

            return productRepository.save(existingProduct);
        }

        // New product
        product.setSku(
                product.getSku().trim()
        );

        if (product.getName() != null) {
            product.setName(
                    product.getName().trim()
            );
        }

        if (product.getImageUrl() != null) {
            product.setImageUrl(
                    product.getImageUrl().trim()
            );
        }

        return productRepository.save(product);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(Long id) {

        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "Product not found: " + id
            );
        }

        productRepository.deleteById(id);
    }
}