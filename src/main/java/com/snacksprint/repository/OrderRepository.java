package com.snacksprint.repository;

import com.snacksprint.model.order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<order, Long> {

    public List<order> findByCustomerId(Long userId);

    public List<order>findByRestaurantId(Long restaurantId);


}
