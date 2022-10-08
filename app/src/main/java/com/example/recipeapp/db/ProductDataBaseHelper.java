package com.example.recipeapp.db;

import android.content.Context;
import com.example.recipeapp.Product;
import java.util.ArrayList;

public class ProductDataBaseHelper {

    private static final String DATABASE_NAME = "products-db";

    private DataBase dataBase;

    public ProductDataBaseHelper(Context context) {
        dataBase = DataBase.getDatabase(context, DATABASE_NAME);
    }

    public void addProduct(Product product){
        dataBase.productDAO().addProduct(product);
    }

    public void deleteProduct(Product product){
        dataBase.productDAO().deleteProduct(product);

    }

    public ArrayList<Product> getShoppingList(){
        return new ArrayList<Product>(dataBase.productDAO().getAllProducts());
    }

    public void deleteAll(){
        dataBase.recipeDAO().deleteAll();
    }

}
