package com.example.recipeapp.Listeners;


import com.example.recipeapp.RecipeData.RecipeInfo.RecipeInfoApiResponse;

public interface RecipeInfoResponseListener {
    void didFetch(RecipeInfoApiResponse response, String message);
    void error(String message);
}
