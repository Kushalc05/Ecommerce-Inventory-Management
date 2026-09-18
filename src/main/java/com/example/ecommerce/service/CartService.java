package com.example.ecommerce.service;

import com.example.ecommerce.dto.CartItemResponseDTO;
import com.example.ecommerce.dto.CheckoutResponseDTO;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.OrderItems;
import com.example.ecommerce.model.Orders;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.OrderItemRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public CartService(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository,
            UserRepository userRepository,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository) {

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    // Get existing cart or create a new cart
    public Cart getOrCreateCart() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String username = authentication.getName();

        Users user = userRepository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        return cartRepository
                .findByUserId(user.getId())
                .orElseGet(() -> {

                    Cart cart = new Cart();

                    cart.setUserId(user.getId());

                    return cartRepository.save(cart);
                });
    }

    // Add product to cart
    public String addToCart(Long productId, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than 0"
            );
        }

        Product product = productRepository
                .findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found: " + productId
                        ));

        Cart cart = getOrCreateCart();

        CartItem existingItem = cartItemRepository
                .findByCartIdAndProductId(
                        cart.getId(),
                        productId
                )
                .orElse(null);

        if (existingItem != null) {

            int newQuantity =
                    existingItem.getQuantity() + quantity;

            if (newQuantity > product.getStock()) {
                throw new IllegalArgumentException(
                        "Insufficient stock"
                );
            }

            existingItem.setQuantity(newQuantity);

            cartItemRepository.save(existingItem);

        } else {

            if (quantity > product.getStock()) {
                throw new IllegalArgumentException(
                        "Insufficient stock"
                );
            }

            CartItem cartItem = new CartItem();

            cartItem.setCartId(cart.getId());
            cartItem.setProductId(productId);
            cartItem.setQuantity(quantity);

            cartItemRepository.save(cartItem);
        }

        return "Product added to cart";
    }

    // Get current user's cart as DTOs
    public List<CartItemResponseDTO> getMyCart() {

        Cart cart = getOrCreateCart();

        List<CartItem> cartItems =
                cartItemRepository
                        .findByCartId(cart.getId());

        List<CartItemResponseDTO> response =
                new ArrayList<>();

        for (CartItem cartItem : cartItems) {

            Product product = productRepository
                    .findById(cartItem.getProductId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Product not found: "
                                            + cartItem.getProductId()
                            ));

            double subtotal =
                    product.getPrice()
                            * cartItem.getQuantity();

            response.add(
                    new CartItemResponseDTO(
                            cartItem.getId(),
                            product.getId(),
                            product.getName(),
                            product.getSku(),
                            product.getPrice(),
                            cartItem.getQuantity(),
                            subtotal
                    )
            );
        }

        return response;
    }

    // Remove product from cart
    public String removeFromCart(Long cartItemId) {

        CartItem cartItem = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart item not found: "
                                        + cartItemId
                        ));

        Cart cart = getOrCreateCart();

        if (!cartItem.getCartId().equals(cart.getId())) {
            throw new IllegalArgumentException(
                    "Unauthorized cart item"
            );
        }

        cartItemRepository.deleteById(cartItemId);

        return "Product removed from cart";
    }

    // Update cart quantity
    public String updateCartQuantity(
            Long cartItemId,
            int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than 0"
            );
        }

        CartItem cartItem = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart item not found: "
                                        + cartItemId
                        ));

        Cart cart = getOrCreateCart();

        if (!cartItem.getCartId().equals(cart.getId())) {
            throw new IllegalArgumentException(
                    "Unauthorized cart item"
            );
        }

        Product product = productRepository
                .findById(cartItem.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found: "
                                        + cartItem.getProductId()
                        ));

        if (quantity > product.getStock()) {
            throw new IllegalArgumentException(
                    "Insufficient stock"
            );
        }

        cartItem.setQuantity(quantity);

        cartItemRepository.save(cartItem);

        return "Cart quantity updated";
    }

    // Checkout cart
    @Transactional
    public CheckoutResponseDTO checkout() {

        Cart cart = getOrCreateCart();

        List<CartItem> cartItems =
                cartItemRepository
                        .findByCartId(cart.getId());

        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException(
                    "Cart is empty"
            );
        }

        double totalAmount = 0;

        // Validate all cart items first
        for (CartItem cartItem : cartItems) {

            Product product = productRepository
                    .findById(cartItem.getProductId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Product not found: "
                                            + cartItem.getProductId()
                            ));

            if (cartItem.getQuantity() <= 0) {
                throw new IllegalArgumentException(
                        "Invalid quantity"
                );
            }

            if (product.getStock() < cartItem.getQuantity()) {
                throw new IllegalArgumentException(
                        "Insufficient stock for product: "
                                + product.getName()
                );
            }

            totalAmount +=
                    product.getPrice()
                            * cartItem.getQuantity();
        }

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String username = authentication.getName();

        Users user = userRepository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        // Create order
        Orders order = new Orders();

        order.setUserId(user.getId());
        order.setTotalAmount(totalAmount);

        orderRepository.save(order);

        // Create order items and decrease stock
        for (CartItem cartItem : cartItems) {

            Product product = productRepository
                    .findById(cartItem.getProductId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Product not found: "
                                            + cartItem.getProductId()
                            ));

            product.setStock(
                    product.getStock()
                            - cartItem.getQuantity()
            );

            productRepository.save(product);

            OrderItems orderItem = new OrderItems();

            orderItem.setOrderId(order.getId());

            orderItem.setProductId(
                    cartItem.getProductId()
            );

            orderItem.setQuantity(
                    cartItem.getQuantity()
            );

            orderItemRepository.save(orderItem);
        }

        // Clear cart after successful checkout
        cartItemRepository.deleteAll(cartItems);

        return new CheckoutResponseDTO(
                order.getId(),
                totalAmount,
                "Checkout successful"
        );
    }
}