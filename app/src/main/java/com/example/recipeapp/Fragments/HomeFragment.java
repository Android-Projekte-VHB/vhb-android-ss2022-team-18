package com.example.recipeapp.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recipeapp.Adapters.RandomRecipeAdapter;
import com.example.recipeapp.Listeners.ComplexRecipeSearchListener;
import com.example.recipeapp.Listeners.RandomRecipesResponseListener;
import com.example.recipeapp.Listeners.RecyclerViewListResponseListener;
import com.example.recipeapp.Listeners.onFinishedListener;
import com.example.recipeapp.MainActivity;
import com.example.recipeapp.R;
import com.example.recipeapp.Recipe;
import com.example.recipeapp.RecipeActivity;
import com.example.recipeapp.RecipeData.ComplexSearch.ComplexRecipesApiResponse;
import com.example.recipeapp.RecipeData.RandomRecipes.RandomRecipesApiResponse;
import com.example.recipeapp.RecipeSearchActivity;
import com.example.recipeapp.api.RecipesRequest;


import java.util.ArrayList;


public class HomeFragment extends Fragment implements RecyclerViewListResponseListener {

    RecyclerView recyclerView;
    RandomRecipeAdapter adapter;
    ArrayList<Recipe> recipesList;
    ImageButton searchButton;


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipesList = new ArrayList<>();
        getRandomRecipes();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.homeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        setupSearchButton(view);
        adapter = new RandomRecipeAdapter(getContext(), recipesList, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void update(){
        adapter.updateList(recipesList);
    }

    private void getRandomRecipes(){
        RecipesRequest request = new RecipesRequest(getActivity());
        request.getRandomRecipes(listener);
    }

    public void getSpecificRecipes(String query, String cuisine, String diet, String type, String intolerances){
        RecipesRequest requestRecipes = new RecipesRequest(getActivity());
        requestRecipes.getSpecificRecipes(recipeslistener, query, cuisine, diet, type, intolerances);

    }

    private RandomRecipesResponseListener listener = new RandomRecipesResponseListener() {
        @Override
        public void didFetch(RandomRecipesApiResponse response, String message) {
            ArrayList<Recipe> list = new ArrayList<>();
            for(int i = 0; i < response.recipes.size(); i++){
                Recipe recipe = new Recipe(response.recipes.get(i).id, response.recipes.get(i).title, response.recipes.get(i).image, false );
                list.add(recipe);
            }
            recipesList = list;
            update();

        }
        @Override
        public void error(String message) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        }
    };

    private ComplexRecipeSearchListener recipeslistener = new ComplexRecipeSearchListener() {
        @Override
        public void didFetch(ComplexRecipesApiResponse response, String message) {
            ArrayList<Recipe> list = new ArrayList<>();
            for(int i = 0; i < response.results.size(); i++){
                Recipe recipe = new Recipe(response.results.get(i).id, response.results.get(i).title, response.results.get(i).image, false );
                list.add(recipe);
            }
            recipesList = list;
            update();
        }
        @Override
        public void error(String message) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        }
    };


    private void setupSearchButton(View view){
        searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RecipeSearchActivity.class);
                getActivity().startActivityForResult(intent, 3);
            }
        });
    }

    @Override
    public void onItemClicked(Recipe recipe) {
        Intent intent = new Intent(getActivity(), RecipeActivity.class);
        intent.putExtra("recipe", recipe);
        intent.putExtra("currentHomeRecipes", recipesList);
        getActivity().startActivityForResult(intent, 1);
    }


}
