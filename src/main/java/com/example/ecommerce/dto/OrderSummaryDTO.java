package com.example.ecommerce.dto;

public class OrderSummaryDTO {

    private Long orderId;
    private Long userId;
    private double totalAmount;

    public OrderSummaryDTO() {
    }

    public OrderSummaryDTO(
            Long orderId,
            Long userId,
            double totalAmount) {

        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
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
}