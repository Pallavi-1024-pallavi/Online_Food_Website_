package com.snacksprint.service;

import com.snacksprint.model.IngredientCategory;
import com.snacksprint.model.IngredientsItem;
import com.snacksprint.model.Restaurant;
import com.snacksprint.repository.IngredientCategoryRepository;
import com.snacksprint.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientsService{


    @Autowired
    private IngredientItemRepository ingredientItemRepository;
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;


    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category=new IngredientCategory();
        category.setRestaurant(restaurant);
        category.setName(name);

        return ingredientCategoryRepository.save(category);
    }

    @Override
    public IngredientCategory findIngredientById(Long id) throws Exception {
        Optional<IngredientCategory> opt=ingredientCategoryRepository.findById(id);
        if(opt.isEmpty()){
            throw new Exception("Ingredient Category not found");
        }
        return opt.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
       IngredientCategory category=findIngredientById(categoryId);
        IngredientsItem item=new IngredientsItem();
        item.setName(ingredientName);
        item.setRestaurant(restaurant);
        item.setCategory(category);
        IngredientsItem ingredient=ingredientItemRepository.save(item);
        category.getIngredients().add(ingredient);

        return ingredient;
    }

    @Override
    public List<IngredientsItem> findRestaurantsINGREDIENTS(Long restaurantId) throws Exception {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> opt=ingredientItemRepository.findById(id);
        if(opt.isEmpty()){
            throw new Exception("Ingredient Item not found");
        }
        IngredientsItem ingredientsitem=opt.get();
        ingredientsitem.setInstoke(!ingredientsitem.isInstoke());
        return ingredientItemRepository.save(ingredientsitem);
    }
}
