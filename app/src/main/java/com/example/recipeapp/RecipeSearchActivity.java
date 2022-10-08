package com.example.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recipeapp.Adapters.TagsListAdapter;
import java.util.ArrayList;

public class RecipeSearchActivity extends AppCompatActivity {

    EditText searchBar;
    ImageButton searchButton;

    String[] diets;
    String[] mealTypes;
    String[] cuisines;
    String[] intolerances;
    TagsListAdapter adapter1;
    TagsListAdapter adapter2;
    TagsListAdapter adapter3;
    TagsListAdapter adapter4;

    private String query;
    private String cuisine;
    private String diet;
    private String type;
    private String intolerance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);
        setupUI();
        setupSearchButton();
        setupTagLists();
    }

    private void setupUI(){
        searchBar = findViewById(R.id.recipe_search_bar);
        searchButton = findViewById(R.id.search_button);
    }

    private void setupSearchButton() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTags();
                if(searchBar.getText().length() > 0){
                    searchBar.getText().clear();
                }
                Intent newIntent = new Intent(RecipeSearchActivity.this, MainActivity.class);
                newIntent.putExtra("query",query);
                newIntent.putExtra("cuisine",cuisine);
                newIntent.putExtra("diets",diet);
                newIntent.putExtra("type",type);
                newIntent.putExtra("intolerance",intolerance);
                setResult(RESULT_OK, newIntent);
                finish();
            }
        });
    }

    private void setupTagLists(){

        diets = getResources().getStringArray(R.array.diets);
        RecyclerView recyclerView = findViewById(R.id.diet_tags_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter1 = new TagsListAdapter(this, diets);
        recyclerView.setAdapter(adapter1);

        mealTypes = getResources().getStringArray(R.array.meal_type);
        RecyclerView recyclerView2 = findViewById(R.id.mealType_tags_list);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new GridLayoutManager(this, 1));
        adapter2 = new TagsListAdapter(this, mealTypes);
        recyclerView2.setAdapter(adapter2);

        cuisines = getResources().getStringArray(R.array.cuisines);
        RecyclerView recyclerView3 = findViewById(R.id.cuisines_tags_list);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new GridLayoutManager(this, 1));
        adapter3 = new TagsListAdapter(this, cuisines);
        recyclerView3.setAdapter(adapter3);

        intolerances = getResources().getStringArray(R.array.intolerances);
        RecyclerView recyclerView4 = findViewById(R.id.intolerances_tags_list);
        recyclerView4.setHasFixedSize(true);
        recyclerView4.setLayoutManager(new GridLayoutManager(this, 1));
        adapter4 = new TagsListAdapter(this, intolerances);
        recyclerView4.setAdapter(adapter4);
    }

    private void getTags(){
        ArrayList<String> dietsList = adapter1.getCheckedList();
        ArrayList<String> typeList = adapter2.getCheckedList();
        ArrayList<String> cuisineList = adapter3.getCheckedList();
        ArrayList<String> intolerancesList  = adapter4.getCheckedList();
        query = searchBar.getText().toString();
        diet = StringArrayToString(dietsList);
        type = StringArrayToString(typeList);
        cuisine = StringArrayToString(cuisineList);
        intolerance = StringArrayToString(intolerancesList);
    }

    private String StringArrayToString(ArrayList<String> strings) {
        String string = String.join(",", strings);
        return string;
    }
}