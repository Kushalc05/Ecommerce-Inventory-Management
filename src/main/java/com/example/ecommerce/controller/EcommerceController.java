package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.MultiProductOrderDTO;
import com.example.ecommerce.dto.OrderRequestDTO;
import com.example.ecommerce.dto.OrderResponseDTO;
import com.example.ecommerce.dto.OrderSummaryDTO;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.ProductService;

import jakarta.validation.Valid;

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

    // =========================
    // PRODUCT APIs
    // =========================

    @PostMapping("/product")
    public Product addProduct(
            @RequestBody @Valid Product product) {

        return productService.addProduct(product);
    }

    @GetMapping("/products")
    public List<Product> getProducts() {

        return productService.getProducts();
    }

    @DeleteMapping("/product/{id}")
    public String deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return "Product deleted successfully";
    }

    // =========================
    // ORDER APIs
    // =========================

    @PostMapping("/order")
    public OrderResponseDTO placeOrder(
            @RequestBody @Valid OrderRequestDTO request) {

        return orderService.placeOrder(request);
    }

    @PostMapping("/orders/multi")
    public String placeMultiProductOrder(
            @RequestBody @Valid MultiProductOrderDTO orderDTO) {

        return orderService.placeMultiProductOrder(orderDTO);
    }

    @GetMapping("/orders/my")
    public List<OrderSummaryDTO> getMyOrders() {

        return orderService.getMyOrders();
    }
}