package com.snacksprint.controller;


import com.snacksprint.model.Category;
import com.snacksprint.model.user;
import com.snacksprint.service.CategoryService;
import com.snacksprint.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
   @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @PostMapping("/admin/category")
    public ResponseEntity<Category> createCategory (@RequestBody Category category,
                                                   @RequestHeader("Authorization")String jwt)throws Exception {
       user user=userService.findUserByJwtToken(jwt);

       Category createCategory=categoryService.createCategory(category.getName(),user.getId());

       return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
   }

    @GetMapping("/category/restaurant")
    public ResponseEntity<List<Category>> getRestaurantCategory (
                                                    @RequestHeader("Authorization")String jwt)throws Exception {
        user user=userService.findUserByJwtToken(jwt);

       List<Category> categories=categoryService.findCategoryByRestaurantId(user.getId());

        return new ResponseEntity<>(categories, HttpStatus.CREATED);
    }



}
