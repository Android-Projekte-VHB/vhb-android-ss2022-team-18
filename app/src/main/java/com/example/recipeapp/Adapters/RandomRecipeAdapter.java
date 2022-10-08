package com.example.recipeapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.Listeners.RecyclerViewListResponseListener;
import com.example.recipeapp.R;
import com.example.recipeapp.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RandomRecipeAdapter extends RecyclerView.Adapter<RandomRecipeViewHolder> {


    RecyclerViewListResponseListener listener;
    Context context;
    ArrayList<Recipe> list;

    public RandomRecipeAdapter(Context context, ArrayList<Recipe> list, RecyclerViewListResponseListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public void updateList(ArrayList<Recipe> newList){
        list = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RandomRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RandomRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.random_recipe_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RandomRecipeViewHolder holder, int position) {
        holder.recipe_title.setText(list.get(position).title);
        holder.recipe_title.setSelected(true);
        Picasso.get().load(list.get(position).image).into(holder.recipe_image);

        int i = holder.getAdapterPosition();
        holder.random_recipe_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(list.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

}

class RandomRecipeViewHolder extends RecyclerView.ViewHolder{
    CardView random_recipe_container;
    TextView recipe_title;
    ImageView recipe_image;

    public RandomRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        random_recipe_container = itemView.findViewById(R.id.random_recipes_container);
        recipe_title = itemView.findViewById(R.id.random_recipe_title);
        recipe_image = itemView.findViewById(R.id.random_recipe_image);

    }



}
