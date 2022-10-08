package com.example.recipeapp.Manager;

import android.content.Context;

import com.example.recipeapp.Product;
import com.example.recipeapp.db.ProductDataBaseHelper;
import java.util.List;

public class ShoppingListManager {

    private final ListManager listener;
    private final List<Product> shoppingList;
    private final ProductDataBaseHelper dataBase;

    public ShoppingListManager(Context context, ListManager listener){
        this.listener = listener;
        this.dataBase = new ProductDataBaseHelper(context);
        this.shoppingList = dataBase.getShoppingList();
    }

    public void requestUpdate(){
        listener.onListUpdated();
    }

    public void addProduct(Product product){
        dataBase.addProduct(product);
        shoppingList.add(product);
    }

    public void deleteProduct(Product product){
        dataBase.deleteProduct(product);
        shoppingList.remove(product);
    }

    public List<Product> getShoppingList(){
        return shoppingList;
    }

    public void deleteAll(){
        dataBase.deleteAll();
        shoppingList.clear();
    }

    public interface ListManager{
        void onListUpdated();
    }

}
