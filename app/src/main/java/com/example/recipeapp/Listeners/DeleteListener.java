package com.example.recipeapp.Listeners;

import android.os.Parcelable;

import com.example.recipeapp.Product;

public interface DeleteListener extends Parcelable {
    void onDelete(Product product);
}
