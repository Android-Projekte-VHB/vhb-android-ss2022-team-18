package com.example.recipeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.recipeapp.Fragments.FavoritesFragment;
import com.example.recipeapp.Fragments.HomeFragment;
import com.example.recipeapp.Fragments.ShoppingListFragment;
import com.example.recipeapp.Fragments.TimerFragment;
import com.example.recipeapp.Listeners.ComplexRecipeSearchListener;
import com.example.recipeapp.Listeners.DeleteListener;
import com.example.recipeapp.Listeners.RandomRecipesResponseListener;
import com.example.recipeapp.Listeners.ShoppingListResponseListener;
import com.example.recipeapp.Listeners.onFinishedListener;
import com.example.recipeapp.Manager.FavouritesListManager;
import com.example.recipeapp.Manager.ShoppingListManager;
import com.example.recipeapp.RecipeData.ComplexSearch.ComplexRecipesApiResponse;
import com.example.recipeapp.RecipeData.RandomRecipes.RandomRecipesApiResponse;
import com.example.recipeapp.api.RecipesRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ShoppingListResponseListener, FavouritesListManager.ListManager, ShoppingListManager.ListManager , DeleteListener {

    HomeFragment homeFragment;
    FavoritesFragment favoritesFragment;
    ShoppingListFragment shoppingListFragment;
    TimerFragment timerFragment;

    FavouritesListManager favouritesListManager;
    ShoppingListManager shoppingListManager;

    DeleteListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupDataBaseManager();
        setupFragments();
        setupBottomNavigationBar();
    }


    private void setupDataBaseManager(){
        favouritesListManager = new FavouritesListManager(getApplicationContext(), this);
        shoppingListManager = new ShoppingListManager(getApplicationContext(), this);
    }

    private void setupBottomNavigationBar(){
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        setFragment(homeFragment);
                        return true;
                    case R.id.favorites:
                        setFragment(favoritesFragment);
                        favoritesFragment.update(favouritesListManager.getFavouritesRecipesList());
                        return true;
                    case R.id.shoppingList:
                        setFragment(shoppingListFragment);
                        shoppingListFragment.updateList(shoppingListManager.getShoppingList());
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("test", listener);
                        shoppingListFragment.setArguments(bundle);
                        shoppingListFragment.updateList(shoppingListManager.getShoppingList());
                        return true;
                    case R.id.timer:
                        setFragment(timerFragment);
                        return true;
                }
                return false;
            }
        });
    }


    private void setupFragments(){
        homeFragment = new HomeFragment();
        favoritesFragment = new FavoritesFragment();
        favoritesFragment.update(favouritesListManager.getFavouritesRecipesList());
        shoppingListFragment = new ShoppingListFragment();
        shoppingListFragment.updateList(shoppingListManager.getShoppingList());
        shoppingListFragment.getListener(this);
        timerFragment = new TimerFragment();
        setFragment(homeFragment);
    }

    private void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    private void checkData(Intent data){
        Recipe recipe = data.getParcelableExtra("recipe");
        Boolean oldStatus = data.getExtras().getBoolean("LikedStatusBefore");
        Boolean productsToBuy = data.getExtras().getBoolean("productsToBuy");
        ArrayList<String> ingredientsList = data.getStringArrayListExtra("ingredientList");
        checkLikedStatus(recipe, oldStatus);
        checkIfProductsAdded(ingredientsList, productsToBuy);
    }

    private void checkLikedStatus(Recipe recipe, Boolean oldStatus){
        if(recipe.liked){
            if(!oldStatus) {
                favouritesListManager.addRecipe(recipe);
                favouritesListManager.requestUpdate();
                favoritesFragment.update(favouritesListManager.getFavouritesRecipesList());
                setFragment(favoritesFragment);
            }
        }else{
            if(oldStatus){
                favouritesListManager.deleteRecipe(recipe);
                favouritesListManager.requestUpdate();
                favoritesFragment.update(favouritesListManager.getFavouritesRecipesList());
                setFragment(favoritesFragment);
            }
        }
    }

    private void checkIfProductsAdded(ArrayList<String> list, Boolean newData){
        if(newData ){
            for(int i = 0; i < list.size(); i ++ ){
                Product product = new Product(list.get(i), "1");
                shoppingListManager.addProduct(product);
                shoppingListManager.requestUpdate();
            }
            shoppingListFragment.updateList(shoppingListManager.getShoppingList());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //from HomeFragment
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                checkData(data);
                ArrayList<Recipe> recipes = data.getParcelableExtra("currentHomeRecipes");

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("currentHomeRecipes", recipes);
                homeFragment.setArguments(bundle);
                setFragment(homeFragment);
            }
        }

        //from FavFragment
        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                setFragment(favoritesFragment);
                checkData(data);
            }
        }

        //from SearchActivity
        if(requestCode == 3){
            if(resultCode == RESULT_OK){
                String query = data.getStringExtra("query");
                String cuisine = data.getStringExtra("cuisine");
                String diet = data.getStringExtra("diet");
                String type = data.getStringExtra("type");
                String intolerance = data.getStringExtra("intolerance");
                homeFragment.getSpecificRecipes(query, cuisine, diet, type, intolerance);
            }
        }
    }

    @Override
    public void onButtonClicked(Product product) {
        shoppingListManager.addProduct(product);
        shoppingListManager.requestUpdate();
        shoppingListFragment.updateList(shoppingListManager.getShoppingList());
        setFragment(shoppingListFragment);
    }

    @Override
    public void onListUpdated() {
    }

    @Override
    public void onDelete(Product product) {
        shoppingListManager.deleteProduct(product);
        Log.d("tag", product.name);
        shoppingListManager.requestUpdate();
        shoppingListFragment.updateList(shoppingListManager.getShoppingList());
        Log.d("tag", Integer.toString(shoppingListManager.getShoppingList().size()));
        setFragment(shoppingListFragment);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}