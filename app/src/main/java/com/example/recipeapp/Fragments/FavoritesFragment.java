package com.example.recipeapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.recipeapp.Adapters.FavouritesListAdapter;
import com.example.recipeapp.Manager.FavouritesListManager;
import com.example.recipeapp.Listeners.RecyclerViewListResponseListener;
import com.example.recipeapp.R;
import com.example.recipeapp.Recipe;
import com.example.recipeapp.RecipeActivity;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment implements RecyclerViewListResponseListener {


    FavouritesListAdapter favouritesListAdapter;
    RecyclerView recyclerView;
    List<Recipe> list;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_favorites, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.favoritesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        favouritesListAdapter = new FavouritesListAdapter( this, list);
        recyclerView.setAdapter(favouritesListAdapter);
    }



    public void update(List<Recipe> newList){
        list = newList;

    }


    @Override
    public void onItemClicked(Recipe recipe) {
        Intent newIntent = new Intent( getActivity() , RecipeActivity.class);
        newIntent.putExtra("recipe", recipe);
        startActivityForResult(newIntent, 3);
    }

}