package com.example.recipeapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.recipeapp.Recipe;
import java.util.List;

@Dao
public interface RecipeDAO {

    @Insert
    void addRecipe(Recipe recipe);

    @Delete
    void deleteRecipe(Recipe recipe);

    @Query("SELECT * from recipe")
    List<Recipe> getAllRecipes();

    @Query("DELETE FROM recipe")
     void deleteAll();

    @Query("SELECT * FROM recipe WHERE title = :recipeName LIMIT 1 ")
    Recipe getRecipe(String recipeName);


}
