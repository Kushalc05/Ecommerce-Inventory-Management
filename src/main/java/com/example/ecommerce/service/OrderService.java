package com.example.ecommerce.service;

import com.example.ecommerce.dto.MultiProductOrderDTO;
import com.example.ecommerce.dto.OrderItemRequestDTO;
import com.example.ecommerce.dto.OrderRequestDTO;
import com.example.ecommerce.dto.OrderResponseDTO;
import com.example.ecommerce.dto.OrderSummaryDTO;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.model.OrderItems;
import com.example.ecommerce.model.Orders;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.OrderItemRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;
    private final OrderItemRepository itemRepo;
    private final UserRepository userRepo;

    public OrderService(
            ProductRepository productRepo,
            OrderRepository orderRepo,
            OrderItemRepository itemRepo,
            UserRepository userRepo) {

        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.itemRepo = itemRepo;
        this.userRepo = userRepo;
    }

    // Place single-product order
    @Transactional
    public OrderResponseDTO placeOrder(
            OrderRequestDTO request) {

        if (request.getQuantity() <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than 0"
            );
        }

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String username = authentication.getName();

        Users user = userRepo
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        Product product = productRepo
                .findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found: "
                                        + request.getProductId()
                        ));

        if (product.getStock() < request.getQuantity()) {
            throw new IllegalArgumentException(
                    "Insufficient stock for product: "
                            + product.getName()
            );
        }

        double totalAmount =
                product.getPrice()
                        * request.getQuantity();

        // Reduce stock
        product.setStock(
                product.getStock()
                        - request.getQuantity()
        );

        productRepo.save(product);

        // Create order
        Orders order = new Orders();

        order.setUserId(user.getId());
        order.setTotalAmount(totalAmount);

        orderRepo.save(order);

        // Create order item
        OrderItems orderItem = new OrderItems();

        orderItem.setOrderId(order.getId());
        orderItem.setProductId(product.getId());
        orderItem.setQuantity(request.getQuantity());

        itemRepo.save(orderItem);

        return new OrderResponseDTO(
                order.getId(),
                user.getId(),
                totalAmount,
                "Order placed successfully"
        );
    }

    // Place multi-product order
    @Transactional
    public String placeMultiProductOrder(
            MultiProductOrderDTO orderDTO) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String username = authentication.getName();

        Users user = userRepo
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        if (orderDTO == null ||
                orderDTO.getItems() == null ||
                orderDTO.getItems().isEmpty()) {

            throw new IllegalArgumentException(
                    "Order must contain at least one product"
            );
        }

        double totalAmount = 0;

        // Validate all products and stock first
        for (OrderItemRequestDTO item :
                orderDTO.getItems()) {

            if (item.getQuantity() <= 0) {
                throw new IllegalArgumentException(
                        "Quantity must be greater than 0"
                );
            }

            Product product = productRepo
                    .findById(item.getProductId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Product not found: "
                                            + item.getProductId()
                            ));

            if (product.getStock() < item.getQuantity()) {

                throw new IllegalArgumentException(
                        "Insufficient stock for product: "
                                + product.getName()
                );
            }

            totalAmount +=
                    product.getPrice()
                            * item.getQuantity();
        }

        // Create order
        Orders order = new Orders();

        order.setUserId(user.getId());
        order.setTotalAmount(totalAmount);

        orderRepo.save(order);

        // Reduce stock and create order items
        for (OrderItemRequestDTO item :
                orderDTO.getItems()) {

            Product product = productRepo
                    .findById(item.getProductId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Product not found: "
                                            + item.getProductId()
                            ));

            product.setStock(
                    product.getStock()
                            - item.getQuantity()
            );

            productRepo.save(product);

            OrderItems orderItem =
                    new OrderItems();

            orderItem.setOrderId(order.getId());

            orderItem.setProductId(
                    item.getProductId()
            );

            orderItem.setQuantity(
                    item.getQuantity()
            );

            itemRepo.save(orderItem);
        }

        return "Order placed successfully";
    }

    // Get orders of currently logged-in user
    public List<OrderSummaryDTO> getMyOrders() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String username = authentication.getName();

        Users user = userRepo
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        return orderRepo.findByUserId(user.getId())
                .stream()
                .map(order -> new OrderSummaryDTO(
                        order.getId(),
                        order.getUserId(),
                        order.getTotalAmount()
                ))
                .toList();
    }
}