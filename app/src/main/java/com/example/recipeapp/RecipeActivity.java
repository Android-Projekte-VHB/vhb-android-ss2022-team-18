package com.example.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.Adapters.IngredientsListAdapter;
import com.example.recipeapp.Listeners.IngredientsClickListener;
import com.example.recipeapp.Listeners.onFinishedListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity implements IngredientsClickListener, onFinishedListener {

    RecyclerView recyclerView;;
    Boolean oldLikedStatus;
    Button favouriteButton;
    ArrayList<String> shoppingList;

    RecipeInformation recipeInformation;
    Recipe recipe;
    ArrayList<Recipe> currentHomeRecipes;

    IngredientsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recipe_details);
        shoppingList= new ArrayList<>();
        getRecipeInformation();

        recyclerView = findViewById(R.id.ingredients_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(RecipeActivity.this));
        setupButtons();
        setLikedButtonColor();

    }

    private void getRecipeInformation(){
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("recipe");
        currentHomeRecipes = intent.getParcelableArrayListExtra("currentHomeRecipes");
        recipeInformation = new RecipeInformation(this, recipe.id, this);
        oldLikedStatus = recipe.liked;
    }

    private void setupUI() {
        TextView recipeTitle = findViewById(R.id.recipe_title);
        recipeTitle.setText(recipeInformation.title);

        ImageView recipeImage = findViewById(R.id.recipe_image);
        Picasso.get().load(recipeInformation.image).into(recipeImage);

        TextView recipeTime = findViewById(R.id.time_amount);
        recipeTime.setText(recipeInformation.time + " min");

        TextView recipeServings = findViewById(R.id.servings_amount);
        recipeServings.setText(recipeInformation.servings + " per");

        TextView recipeDescription = findViewById(R.id.recipe_description);
        recipeDescription.setText(recipeInformation.instruction);

        TextView calories = findViewById(R.id.calories_amount);
        calories.setText(recipeInformation.calories + " kcal");

        adapter = new IngredientsListAdapter(recipeInformation.ingredients, recipeInformation.ingredientsImages, this);
        recyclerView.setAdapter(adapter);


    }

    private void setLikedButtonColor() {
        if (oldLikedStatus) {
            favouriteButton.setBackgroundResource(R.drawable.ic_baseline_favorite_red_24);
        } else {
            favouriteButton.setBackgroundResource(R.drawable.ic_baseline_favorite_grey_24);
        }
    }

    private void setupButtons() {
        Button backButton = findViewById(R.id.back_button);
        favouriteButton = findViewById(R.id.favourite_button);
        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!recipe.liked) {
                    favouriteButton.setBackgroundResource(R.drawable.ic_baseline_favorite_red_24);
                    recipe.liked = true;
                }else{
                    favouriteButton.setBackgroundResource(R.drawable.ic_baseline_favorite_grey_24);
                    recipe.liked = false;
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(RecipeActivity.this, MainActivity.class);
                newIntent.putExtra("recipe", recipe);
                newIntent.putExtra("LikedStatusBefore", oldLikedStatus);
                newIntent.putParcelableArrayListExtra("currentHomeRecipes", currentHomeRecipes);
                if(shoppingList.size() > 0) {
                    newIntent.putExtra("productsToBuy", true);
                    newIntent.putExtra("ingredientList", shoppingList);
                } else {
                    newIntent.putExtra("productsToBuy", false);
                }
                setResult(RESULT_OK, newIntent);
                finish();
            }
        });
    }

    @Override
    public void onClick(int i) {
        shoppingList.add(recipeInformation.ingredients.get(i));
        Toast.makeText(this, "Product added", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setupIU() {
        setupUI();
    }
}
