package com.example.ecommerce.dto;

public class OrderResponseDTO {

    private Long orderId;
    private Long userId;
    private double totalAmount;
    private String message;

    public OrderResponseDTO() {
    }

    public OrderResponseDTO(
            Long orderId,
            Long userId,
            double totalAmount,
            String message) {

        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.message = message;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}