package com.example.ecommerce.controller;

import com.example.ecommerce.dto.CartItemResponseDTO;
import com.example.ecommerce.dto.CheckoutResponseDTO;
import com.example.ecommerce.service.CartService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Add product to cart
    @PostMapping("/add")
    public String addToCart(
            @RequestParam Long productId,
            @RequestParam int quantity) {

        return cartService.addToCart(
                productId,
                quantity
        );
    }

    // Get current user's cart
    @GetMapping
    public List<CartItemResponseDTO> getMyCart() {

        return cartService.getMyCart();
    }

    // Remove product from cart
    @DeleteMapping("/{cartItemId}")
    public String removeFromCart(
            @PathVariable Long cartItemId) {

        return cartService.removeFromCart(
                cartItemId
        );
    }

    // Update cart quantity
    @PutMapping("/{cartItemId}")
    public String updateCartQuantity(
            @PathVariable Long cartItemId,
            @RequestParam int quantity) {

        return cartService.updateCartQuantity(
                cartItemId,
                quantity
        );
    }

    // Checkout cart
    @PostMapping("/checkout")
    public CheckoutResponseDTO checkout() {

        return cartService.checkout();
    }
}