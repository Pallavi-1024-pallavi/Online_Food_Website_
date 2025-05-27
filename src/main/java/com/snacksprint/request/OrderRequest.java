package com.snacksprint.request;


import com.snacksprint.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private Address deliveryAddress;
}
