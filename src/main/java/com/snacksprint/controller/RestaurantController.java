package com.snacksprint.controller;


import com.snacksprint.model.Restaurant;
import com.snacksprint.model.dto.RestaurantDto;
import com.snacksprint.model.user;
import com.snacksprint.request.CreateRestaurantRequest;
import com.snacksprint.service.RestaurantService;
import com.snacksprint.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(

            @RequestHeader("Authorization") String jwt,
            @RequestParam String keyword

    ) throws Exception {
        user user =userService.findUserByJwtToken(jwt);

        List<Restaurant> restaurant = restaurantService.searchRestaurant(keyword);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurant(

            @RequestHeader("Authorization") String jwt


    ) throws Exception {
        user user =userService.findUserByJwtToken(jwt);

        List<Restaurant> restaurant = restaurantService.getAllRestaurant();

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(

            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id

    ) throws Exception {
        user user =userService.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.findRestaurantById(id);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }


    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurantDto>addToFavorites (

            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id

    ) throws Exception {

        user user =userService.findUserByJwtToken(jwt);

        RestaurantDto restaurant = restaurantService.addTofavourites(id,user);


        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }



}
