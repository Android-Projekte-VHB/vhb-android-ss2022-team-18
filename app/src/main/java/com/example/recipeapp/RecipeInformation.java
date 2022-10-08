package com.example.recipeapp;

import android.content.Context;

import com.example.recipeapp.Listeners.RecipeInfoResponseListener;
import com.example.recipeapp.Listeners.onFinishedListener;
import com.example.recipeapp.RecipeData.RecipeInfo.RecipeInfoApiResponse;
import com.example.recipeapp.api.RecipesRequest;

import java.util.ArrayList;

public class RecipeInformation {

    public String title;
    public String image;
    public String calories;
    public String time;
    public String servings;
    public ArrayList<String> ingredients ;
    public ArrayList<String> ingredientsImages;
    public String instruction;
    public int id;
    public Context context;
    private com.example.recipeapp.Listeners.onFinishedListener onFinishedListener;


    public RecipeInformation(Context context, int id, onFinishedListener onFinishedListener){
        this.id  = id;
        this.context = context;
        this.onFinishedListener = onFinishedListener;
        ingredients = new ArrayList<>();
        ingredientsImages = new ArrayList<>();
        getInformation();
    }

    private void getInformation(){
        RecipesRequest request = new RecipesRequest(context);
        request.getRecipeInfo(listener, id);
    }

    private RecipeInfoResponseListener listener = new RecipeInfoResponseListener() {
        @Override
        public void didFetch(RecipeInfoApiResponse response, String message) {

            title = response.title;
            image = response.image;
            calories = Double.toString(response.nutrition.nutrients.get(0).amount);
            time = Integer.toString(response.readyInMinutes);
            servings = Integer.toString(response.servings);
            instruction = response.instructions;
            instruction = instruction.replaceAll("<ol>", "");
            instruction = instruction.replaceAll("</ol>", "");
            instruction = instruction.replaceAll("<li>", "");
            instruction = instruction.replaceAll("</li>", "");
            for(int j = 0; j < response.extendedIngredients.size(); j++){
                String ingredient = response.extendedIngredients.get(j).amount + " " +  response.extendedIngredients.get(j).unit + " " + response.extendedIngredients.get(j).nameClean;
                ingredients.add(ingredient);
                ingredientsImages.add(response.extendedIngredients.get(j).image);
            }
            onFinishedListener.setupIU();
        }
        @Override
        public void error(String message) {

        }
    };



}
