package com.example.ecommerce.dto;

public class CartItemResponseDTO {

    private Long cartItemId;
    private Long productId;
    private String productName;
    private String sku;
    private double price;
    private int quantity;
    private double subtotal;

    public CartItemResponseDTO() {
    }

    public CartItemResponseDTO(
            Long cartItemId,
            Long productId,
            String productName,
            String sku,
            double price,
            int quantity,
            double subtotal) {

        this.cartItemId = cartItemId;
        this.productId = productId;
        this.productName = productName;
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}