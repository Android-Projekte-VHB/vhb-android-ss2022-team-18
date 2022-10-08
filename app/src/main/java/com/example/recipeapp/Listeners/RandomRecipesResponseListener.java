package com.example.recipeapp.Listeners;


import com.example.recipeapp.RecipeData.RandomRecipes.RandomRecipesApiResponse;

public interface RandomRecipesResponseListener {
    void didFetch(RandomRecipesApiResponse response, String message);
    void error(String message);
}
