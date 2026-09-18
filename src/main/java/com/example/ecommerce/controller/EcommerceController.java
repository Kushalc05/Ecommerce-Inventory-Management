package com.example.ecommerce.controller;

import com.example.ecommerce.dto.MultiProductOrderDTO;
import com.example.ecommerce.dto.OrderRequestDTO;
import com.example.ecommerce.dto.OrderResponseDTO;
import com.example.ecommerce.dto.ProductRequestDTO;
import com.example.ecommerce.dto.ProductResponseDTO;
import com.example.ecommerce.dto.OrderSummaryDTO;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class EcommerceController {

    private final ProductService productService;
    private final OrderService orderService;

    public EcommerceController(
            ProductService productService,
            OrderService orderService) {

        this.productService = productService;
        this.orderService = orderService;
    }

    // Add product
    @PostMapping("/product")
    public ProductResponseDTO addProduct(
            @RequestBody @Valid ProductRequestDTO productRequest) {

        return productService.addProduct(productRequest);
    }

    // Get all products
    @GetMapping("/products")
    public List<ProductResponseDTO> getProducts() {

        return productService.getProducts();
    }

    // Place single-product order
    @PostMapping("/order")
    public OrderResponseDTO placeOrder(
            @RequestBody @Valid OrderRequestDTO request) {

        return orderService.placeOrder(request);
    }

    // Place multi-product order
    @PostMapping("/orders/multi")
    public String placeMultiProductOrder(
            @RequestBody @Valid MultiProductOrderDTO orderDTO) {

        return orderService.placeMultiProductOrder(orderDTO);
    }

    // Delete product
    @DeleteMapping("/product/{id}")
    public String deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return "Product deleted successfully";
    }

    // Get current user's orders
    @GetMapping("/orders/my")
public List<OrderSummaryDTO> getMyOrders() {

        return orderService.getMyOrders();
    }
}