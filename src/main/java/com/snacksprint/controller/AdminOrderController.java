package com.snacksprint.controller;

import com.snacksprint.model.order;
import com.snacksprint.model.user;
import com.snacksprint.service.OrderService;
import com.snacksprint.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<order>> getOrderHistory(
            @PathVariable Long id,
            @RequestParam(required = false)String order_status,
            @RequestHeader("Authorization")String jwt)throws Exception {
        user user=userService.findUserByJwtToken(jwt);
        List<order> order=orderService.getRestaurantsOrder(id,order_status);
        return new ResponseEntity<>(order, HttpStatus.OK);

    }
    @PutMapping("/order/{id}/{orderStatus}")
    public ResponseEntity<order> updateOrderStatus(
            @PathVariable Long id,
            @PathVariable String orderStatus,
            @RequestHeader("Authorization")String jwt)throws Exception {
        user user=userService.findUserByJwtToken(jwt);
        order order=orderService.updateOrder(id,orderStatus);
        return new ResponseEntity<>(order, HttpStatus.OK);

    }


}
