package com.example.recipeapp.Listeners;


import com.example.recipeapp.RecipeData.ComplexSearch.ComplexRecipesApiResponse;

public interface ComplexRecipeSearchListener {
    void didFetch(ComplexRecipesApiResponse response, String message);
    void error(String message);
}
