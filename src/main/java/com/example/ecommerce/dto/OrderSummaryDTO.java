package com.example.ecommerce.dto;

public class OrderSummaryDTO {

    private String productName;
    private double price;
    private int quantity;

    public OrderSummaryDTO(
            String productName,
            double price,
            int quantity) {

        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}