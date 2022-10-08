package com.example.recipeapp.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.recipeapp.Product;
import com.example.recipeapp.Recipe;


@Database(entities = {Recipe.class, Product.class}, version = 2, exportSchema = false)
@TypeConverters({MyTypeConverter.class})
public abstract class DataBase extends RoomDatabase {


    public abstract RecipeDAO recipeDAO();
    public abstract ProductDAO productDAO();
    private static DataBase INSTANCE;


    public static DataBase getDatabase(Context context, String name) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DataBase.class, name)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }


}
