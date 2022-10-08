package com.example.recipeapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.recipeapp.Product;
import java.util.List;

@Dao
public interface ProductDAO {

    @Insert
    void addProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Query("SELECT * FROM shoppingList")
    List<Product> getAllProducts();

    @Query("DELETE FROM shoppingList")
    void deleteAll();
}
