package com.snacksprint.service;

import com.snacksprint.model.Category;
import com.snacksprint.model.Food;
import com.snacksprint.model.Restaurant;
import com.snacksprint.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    void deleteFood(Long foodId)throws Exception;

    public List<Food> getRestaurantFoods(Long restaurantId,
                                         boolean isvegetarian,
                                         boolean isNonveg,
                                         boolean isSeasonal,
                                         String foodCategory




    );
    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long foodId)throws Exception;

    public Food updateAvailibilityStatus(Long foodId)throws Exception;


}
