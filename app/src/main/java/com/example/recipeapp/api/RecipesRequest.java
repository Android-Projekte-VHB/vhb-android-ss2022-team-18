package com.example.recipeapp.api;

import android.content.Context;

import com.example.recipeapp.Listeners.ComplexRecipeSearchListener;
import com.example.recipeapp.Listeners.RandomRecipesResponseListener;
import com.example.recipeapp.Listeners.RecipeInfoResponseListener;
import com.example.recipeapp.RecipeData.ComplexSearch.ComplexRecipesApiResponse;
import com.example.recipeapp.RecipeData.RandomRecipes.RandomRecipesApiResponse;
import com.example.recipeapp.RecipeData.RecipeInfo.RecipeInfoApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RecipesRequest {

    private final String api_key = "87a4c988a6d94899a9b62ae6fbd39264";
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RecipesRequest(Context context) {
        this.context = context;
    }

    public void getSpecificRecipes(ComplexRecipeSearchListener listener, String query, String cuisine, String diet, String type , String intolerances){
        GetComplexRecipes specificRecipes = retrofit.create(GetComplexRecipes.class);
        Call<ComplexRecipesApiResponse> call = specificRecipes.callSpecificRecipes(api_key, "10", query,cuisine, diet, type, intolerances);
        call.enqueue(new Callback<ComplexRecipesApiResponse>() {
            @Override
            public void onResponse(Call<ComplexRecipesApiResponse> call, Response<ComplexRecipesApiResponse> response) {
                if(!response.isSuccessful()){
                    listener.error(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }
            @Override
            public void onFailure(Call<ComplexRecipesApiResponse> call, Throwable t) {
                listener.error(t.getMessage());
            }
        });
    }

    public void getRecipeInfo(RecipeInfoResponseListener listener, int id){
        GetRecipeInfo recipeInfo = retrofit.create(GetRecipeInfo.class);
        Call<RecipeInfoApiResponse> call = recipeInfo.callRecipeInfo(id, true,  api_key);
        call.enqueue(new Callback<RecipeInfoApiResponse>() {
            @Override
            public void onResponse(Call<RecipeInfoApiResponse> call, Response<RecipeInfoApiResponse> response) {
                if(!response.isSuccessful()){
                    listener.error(response.message());
                    return;
                }
                listener.didFetch( response.body(), response.message());
            }
            @Override
            public void onFailure(Call<RecipeInfoApiResponse> call, Throwable t) {
                listener.error(t.getMessage());
            }
        });
    }


    public void getRandomRecipes(RandomRecipesResponseListener listener){
        GetRandomRecipes randomRecipes = retrofit.create(GetRandomRecipes.class);
        Call<RandomRecipesApiResponse> call = randomRecipes.callRandomRecipes(api_key, "10");
        call.enqueue(new Callback<RandomRecipesApiResponse>() {
            @Override
            public void onResponse(Call<RandomRecipesApiResponse> call, Response<RandomRecipesApiResponse> response) {
                if(!response.isSuccessful()){
                    listener.error(response.message());
                    return;
                }
                listener.didFetch( response.body(), response.message());
            }
            @Override
            public void onFailure(Call<RandomRecipesApiResponse> call, Throwable t) {
               listener.error(t.getMessage());
            }
        });
    }



    private interface GetRandomRecipes{
        @GET("recipes/random")
        Call<RandomRecipesApiResponse> callRandomRecipes(
                @Query("apiKey") String apiKey,
                @Query("number") String number
        );
    }

    private interface GetComplexRecipes{
        @GET("recipes/complexSearch")
        Call<ComplexRecipesApiResponse> callSpecificRecipes(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("query") String query,
                @Query("cuisine") String cuisine,
                @Query("diet") String diet,
                @Query("type") String type,
                @Query("intolerances") String intolerances
        );
    }

    private interface GetRecipeInfo{
        @GET("recipes/{id}/information")
        Call<RecipeInfoApiResponse> callRecipeInfo(
                @Path("id") int id,
                @Query("includeNutrition") boolean value,
                @Query("apiKey") String apiKey
        );
    }

}
