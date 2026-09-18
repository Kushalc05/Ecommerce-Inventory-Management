package com.example.ecommerce.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class MultiProductOrderDTO {

    @NotEmpty(message = "Order must contain at least one product")
    @Valid
    private List<OrderItemRequestDTO> items;

    public MultiProductOrderDTO() {
    }

    public List<OrderItemRequestDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequestDTO> items) {
        this.items = items;
    }
}