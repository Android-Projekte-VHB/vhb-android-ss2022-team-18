package com.example.recipeapp.db;

import android.content.Context;

import com.example.recipeapp.Recipe;

import java.util.ArrayList;

public class RecipeDataBaseHelper {

    private static final String DATABASE_NAME = "recipes-db";
    private DataBase dataBase;

    public RecipeDataBaseHelper(Context context) {
        dataBase = DataBase.getDatabase(context, DATABASE_NAME);
    }

    public void addRecipe(Recipe recipe){
        dataBase.recipeDAO().addRecipe(recipe);
    }

    public void deleteRecipe(Recipe recipe){
        dataBase.recipeDAO().deleteRecipe(recipe);

    }

    public ArrayList<Recipe> getAllRecipes(){
        return new ArrayList<Recipe>(dataBase.recipeDAO().getAllRecipes());
    }

    public Recipe getRecipe(String title){
        return dataBase.recipeDAO().getRecipe(title);
    }

    public void deleteAll(){
        dataBase.recipeDAO().deleteAll();
    }


}

