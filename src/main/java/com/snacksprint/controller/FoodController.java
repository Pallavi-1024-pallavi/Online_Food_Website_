package com.snacksprint.controller;


import com.snacksprint.model.Food;
import com.snacksprint.model.Restaurant;
import com.snacksprint.model.user;
import com.snacksprint.request.CreateFoodRequest;
import com.snacksprint.service.FoodService;
import com.snacksprint.service.RestaurantService;
import com.snacksprint.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name,
                                           @RequestHeader("Authorization")String jwt) throws Exception {
        user user=userService.findUserByJwtToken(jwt);

       List<Food>foods=foodService.searchFood(name);

        return new ResponseEntity<>(foods, HttpStatus.CREATED);
    }
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(
            @RequestParam boolean vegetarian,
            @RequestParam boolean seasonal,
            @RequestParam boolean nonveg,
            @RequestParam(required = false)String food_category,
            @PathVariable Long restaurantId,
            @RequestHeader("Authorization")String jwt) throws Exception {
        user user=userService.findUserByJwtToken(jwt);

        List<Food>foods=foodService.getRestaurantFoods(restaurantId,vegetarian,seasonal,nonveg,food_category);


        return new ResponseEntity<>(foods, HttpStatus.OK);
    }



}
