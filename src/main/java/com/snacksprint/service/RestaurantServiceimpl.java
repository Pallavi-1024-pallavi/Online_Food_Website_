package com.snacksprint.service;

import com.snacksprint.model.Address;
import com.snacksprint.model.Restaurant;
import com.snacksprint.model.dto.RestaurantDto;
import com.snacksprint.model.user;
import com.snacksprint.repository.AddressRepository;
import com.snacksprint.repository.RestaurantRepository;
import com.snacksprint.repository.UserRepository;
import com.snacksprint.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@Service
public class RestaurantServiceimpl implements RestaurantService {
    @Autowired

    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;


    public Restaurant createRestaurant(CreateRestaurantRequest req, user user) {
        Address address = addressRepository.save(req.getAddress());
        Restaurant restaurant=new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception {
        Restaurant restaurant=findRestaurantById(restaurantId);
        if(restaurant.getCuisineType()!=null)
        {
            restaurant.setCuisineType(updateRestaurant.getCuisineType());
        }
        if(restaurant.getDescription()!=null)
        {
            restaurant.setDescription(updateRestaurant.getDescription());
        }
        if(restaurant.getName()!=null)
        {
            restaurant.setName(updateRestaurant.getName());
        }

        return restaurantRepository.save(restaurant);

    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
      Restaurant restaurant=findRestaurantById(restaurantId);
      restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt=restaurantRepository.findById(id);
        if(opt.isEmpty()){
            throw new Exception("Restaurant not found with id"+id);
        }

        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant=restaurantRepository.findByOwnerId(userId);
        if(restaurant==null)
        {
            throw new Exception("Restaurant not found with owner id"+userId);
        }


        return restaurant;
    }

    @Override
    public RestaurantDto addTofavourites(Long id , user user) throws Exception {
        Restaurant restaurant = findRestaurantById(id);

        RestaurantDto dto = new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurant.getId());

        boolean isFavorited = false;
        List<RestaurantDto>favorites=user.getFavorites();
        for(RestaurantDto favorite:favorites){
            if(favorite.getId().equals(id)){
                isFavorited=true;
                break;
            }
        }
        if(user.getFavorites().contains(dto))
        {
            user.getFavorites().remove(dto);
        }
        else {

            user.getFavorites().add(dto);
        }

        userRepository.save(user);
        return dto;
    }


    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
       Restaurant restaurant=findRestaurantById(id);
       restaurant.setOpen(!restaurant.isOpen());
       restaurantRepository.save(restaurant);


        return restaurantRepository.save(restaurant);
    }
}
