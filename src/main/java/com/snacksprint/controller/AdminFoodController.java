package com.snacksprint.controller;


import com.snacksprint.model.Food;
import com.snacksprint.model.Restaurant;
import com.snacksprint.model.user;
import com.snacksprint.request.CreateFoodRequest;
import com.snacksprint.response.MessageResponse;
import com.snacksprint.service.FoodService;
import com.snacksprint.service.RestaurantService;
import com.snacksprint.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;
   @Autowired
    private RestaurantService restaurantService;

   @PostMapping
   public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
                                          @RequestHeader("Authorization")String jwt) throws Exception {
       user user=userService.findUserByJwtToken(jwt);
       Restaurant restaurant=restaurantService.findRestaurantById(req.getRestaurantId());
       Food food=foodService.createFood(req,req.getCategory(),restaurant);

       return new ResponseEntity<>(food, HttpStatus.CREATED);
   }
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id,
                                                      @RequestHeader("Authorization")String jwt) throws Exception {
        user user=userService.findUserByJwtToken(jwt);

        foodService.deleteFood(id);
        MessageResponse res=new MessageResponse();
        res.setMessage("Successfully deleted food");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Food> updateFoodAvaibilityStatus(@PathVariable Long id,
                                           @RequestHeader("Authorization")String jwt) throws Exception {
        user user=userService.findUserByJwtToken(jwt);

        Food food=foodService.updateAvailibilityStatus(id);


        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }


}
