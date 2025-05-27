package com.snacksprint.service;

import com.snacksprint.model.IngredientCategory;
import com.snacksprint.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {

    public IngredientCategory createIngredientCategory(String name, Long restaurantId)throws Exception;

    public IngredientCategory findIngredientById(Long id)throws Exception;

    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id)throws Exception;

    public IngredientsItem createIngredientItem(Long restaurantId,String ingredientName,Long categoryId)throws Exception;


    public List<IngredientsItem> findRestaurantsINGREDIENTS(Long restaurantId)throws Exception;

    public IngredientsItem updateStock(Long id)throws Exception;



}
