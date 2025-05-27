package com.snacksprint.service;

import com.snacksprint.model.*;
import com.snacksprint.repository.*;
import com.snacksprint.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired

    private RestaurantRepository restaurantRepository;
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;


    @Override
    public order createOrder(OrderRequest order, user user) throws Exception {
        Address shippedAddress=order.getDeliveryAddress();

        Address savedAddress=addressRepository.save(shippedAddress);

        if(!user.getAddresses().contains(savedAddress))
        {
            user.getAddresses().add(savedAddress);
            userRepository.save(user);

        }
        Restaurant restaurant=restaurantService.findRestaurantById(order.getRestaurantId());

        order createOrder=new order();
        createOrder.setCustomer(user);
        createOrder.setCreatedAt(new Date());
        createOrder.setOrderStatus("PENDING");
        createOrder.setDeliveryAddress(savedAddress);
        createOrder.setRestaurant(restaurant);

        Cart cart=cartService.findCartByUserId(user.getId());
        List<OrderItem> orderItems=new ArrayList<>();

        for(CartItem cartItem:cart.getItem())
        {
            OrderItem orderItem=new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            OrderItem savedOrderItem=orderItemRepository.save(orderItem);
           orderItems.add(savedOrderItem);
        }
        Long totalPrice=cartService.calculateCartTotals(cart);
        createOrder.setItems(orderItems);
        createOrder.setTotalPrice(totalPrice);

        order savedOrder=orderRepository.save(createOrder);
        restaurant.getOrders().add(savedOrder);




        return createOrder;
    }

    @Override
    public order updateOrder(Long orderId, String orderStatus) throws Exception {
        order order=findOrderById(orderId);
        if(orderStatus.equals("OUT-FOR_DELIVERY")
                || orderStatus.equals("DELIVERED")
                || orderStatus.equals("COMPLETED")
                || orderStatus.equals("PENDING")
        ) {
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);

        }
        throw new  Exception("please enter a valid order status");

    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
     order order=findOrderById(orderId);
     orderRepository.delete(order);

    }

    @Override
    public List<order> getUsersOrder(Long userId) throws Exception {

        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {
        List<order>orders=orderRepository.findByRestaurantId(restaurantId);
        if(orderStatus!=null)
        {
            orders=orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());

        }

        return orders;
    }

    @Override
    public order findOrderById(Long orderId) throws Exception {
        Optional<order> optionalOrder=orderRepository.findById(orderId);
        if(optionalOrder.isEmpty())
        {
            throw new Exception("Order not found");
        }
        return optionalOrder.get();
    }
}
