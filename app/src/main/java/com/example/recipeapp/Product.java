package com.example.recipeapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shoppingList")
public class Product {

    @PrimaryKey
    @NonNull
    public String name;
    public String amount;

    public Product(String name, String amount){
        this.name = name;
        this.amount = amount;

    }
}
