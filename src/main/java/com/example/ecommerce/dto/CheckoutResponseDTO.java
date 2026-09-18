package com.example.ecommerce.dto;

public class CheckoutResponseDTO {

    private Long orderId;
    private double totalAmount;
    private String message;

    public CheckoutResponseDTO() {
    }

    public CheckoutResponseDTO(
            Long orderId,
            double totalAmount,
            String message) {

        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.message = message;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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