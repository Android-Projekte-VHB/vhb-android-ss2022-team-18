package com.example.recipeapp.Manager;

import android.content.Context;

import com.example.recipeapp.Recipe;
import com.example.recipeapp.db.RecipeDataBaseHelper;
import java.util.List;

public class FavouritesListManager {

    private final ListManager listener;
    private final List<Recipe> recipes;
    private final RecipeDataBaseHelper dataBase;

    public FavouritesListManager(Context context, ListManager listener){
        this.listener = listener;
        this.dataBase = new RecipeDataBaseHelper(context);
        this.recipes = dataBase.getAllRecipes();
    }

    public void requestUpdate(){
        listener.onListUpdated();
    }

    public void addRecipe(Recipe recipe){
        dataBase.addRecipe(recipe);
        recipes.add(recipe);
    }

    public void deleteRecipe(Recipe recipe){
        dataBase.deleteRecipe(recipe);
        recipes.remove(recipe);
    }

    public List<Recipe> getFavouritesRecipesList(){
        return recipes;
    }

    public void deleteAll(){
        dataBase.deleteAll();
        recipes.clear();}

    public Recipe getRecipe(String title){
        return dataBase.getRecipe(title);
    }

    public interface ListManager{
        void onListUpdated();
    }
}
