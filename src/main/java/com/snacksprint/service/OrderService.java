package com.snacksprint.service;

import com.snacksprint.model.order;
import com.snacksprint.model.user;
import com.snacksprint.request.OrderRequest;

import java.util.List;

public interface OrderService {

    public order createOrder(OrderRequest order, user user) throws Exception;

    public order updateOrder(Long orderId,String orderStatus)throws Exception;

    public void cancelOrder(Long orderId)throws Exception;

    public List<order> getUsersOrder(Long userId)throws Exception;

    public List<order>getRestaurantsOrder(Long restaurantId,String orderStatus)throws Exception;

    public order findOrderById(Long orderId)throws Exception;





}
