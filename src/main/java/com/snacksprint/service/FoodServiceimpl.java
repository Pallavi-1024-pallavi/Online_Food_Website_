package com.snacksprint.service;

import com.snacksprint.model.Category;
import com.snacksprint.model.Food;
import com.snacksprint.model.Restaurant;
import com.snacksprint.repository.FoodRepository;
import com.snacksprint.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service

public class FoodServiceimpl implements FoodService {


  @Autowired
    private FoodRepository foodRepository;


    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
        Food food = new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setImages(req.getImages());
        food.setPrice(req.getPrice());
        food.setName(req.getName());
        food.setIngredients(req.getIngredients());
        food.setSeasonal(req.isSeasonal());
        food.setVegetarian(req.isVegetarian());
        Food saveFood=foodRepository.save(food);
        restaurant.getFoods().add(saveFood);
        return saveFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food=findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);

    }

    @Override
    public List<Food> getRestaurantFoods(Long restaurantId,
                                         boolean isvegetarian,
                                         boolean isNonveg,
                                         boolean isSeasonal,
                                         String foodCategory) {

         List<Food>foods=foodRepository.findByRestaurantId(restaurantId);
         if(isvegetarian){
             foods=filterByVegetarian(foods,isvegetarian);

         }
         if(isNonveg){
             foods=filterByNonveg(foods,isNonveg);
         }
         if(isSeasonal){
             foods=filterBySeasonal(foods,isSeasonal);
         }
         if(foodCategory!=null && !foodCategory.isEmpty()){
             foods=filterByCategory(foods,foodCategory);
         }
        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food ->{
            if(food.getFoodCategory()!=null){
                return food.getFoodCategory().getName().equals(foodCategory);

            }
            return false;
        }).collect(Collectors.toList());
    }


    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByNonveg(List<Food> foods, boolean isNonveg) {
        return foods.stream().filter(food -> food.isVegetarian() == false).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isvegetarian) {

        return foods.stream().filter(food -> food.isVegetarian() == isvegetarian).collect(Collectors.toList());

    }

    @Override
    public List<Food> searchFood(String keyword) {

        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood=foodRepository.findById(foodId);
        if(optionalFood.isEmpty()){
            throw new Exception("food not exist...");

        }
        return optionalFood.get();
    }

    @Override
    public Food updateAvailibilityStatus(Long foodId) throws Exception {
        Food food=findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);


    }
}
