package com.snacksprint.controller;


import com.snacksprint.model.CartItem;
import com.snacksprint.model.order;
import com.snacksprint.model.user;
import com.snacksprint.request.AddCartItemRequest;
import com.snacksprint.request.OrderRequest;
import com.snacksprint.service.OrderService;
import com.snacksprint.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;
    @PostMapping("/order")
    public ResponseEntity<order> createOrder(@RequestBody OrderRequest req,
                                                  @RequestHeader("Authorization")String jwt)throws Exception {
        user user=userService.findUserByJwtToken(jwt);
        order order=orderService.createOrder(req,user);
        return new ResponseEntity<>(order, HttpStatus.OK);

    }
    @GetMapping("/order/user")
    public ResponseEntity<List<order>> getOrderHistory(
                                             @RequestHeader("Authorization")String jwt)throws Exception {
        user user=userService.findUserByJwtToken(jwt);
        List<order> order=orderService.getUsersOrder(user.getId());
        return new ResponseEntity<>(order, HttpStatus.OK);

    }
}
