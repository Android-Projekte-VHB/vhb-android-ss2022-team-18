package com.example.recipeapp.Listeners;


import com.example.recipeapp.RecipeData.Nutrition.NutritionApiResponse;

public interface NutritionResponseListener {
    void didFetch(NutritionApiResponse response, String message);
    void error(String message);
}
