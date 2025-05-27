package com.snacksprint.service;

import com.snacksprint.model.Restaurant;
import com.snacksprint.model.dto.RestaurantDto;
import com.snacksprint.model.user;
import com.snacksprint.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {

    public Restaurant createRestaurant(CreateRestaurantRequest req, user user);

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant)  throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurant();

    public List<Restaurant>searchRestaurant(String keyword);

    public Restaurant findRestaurantById(Long restaurantId) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addTofavourites(Long restaurantId, user user) throws Exception;

    public Restaurant updateRestaurantStatus(Long id) throws Exception;


}
