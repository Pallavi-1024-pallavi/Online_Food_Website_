package com.snacksprint.request;


import lombok.Data;

@Data
public class UpdateCartItemRequest {
    private Long CartItemId;
    private int Quantity;


}
