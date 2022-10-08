package com.example.recipeapp.Adapters;

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

import java.util.List;

public class FavouritesListAdapter extends RecyclerView.Adapter<FavouritesListViewHolder> {

    RecyclerViewListResponseListener listener;
    List<Recipe> list;

    public FavouritesListAdapter(RecyclerViewListResponseListener listener,List<Recipe> list) {
        this.listener = listener;
        this.list = list;
    }

    public void updateList(List<Recipe> newList) {
        list = newList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavouritesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_recipe_container, parent, false);
        return new FavouritesListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesListViewHolder holder, int position) {
        holder.name.setText(list.get(position).title);
        holder.name.setSelected(true);
        Picasso.get().load(list.get(position).image).into(holder.image);
        int i = holder.getAdapterPosition();
        holder.favouriteRecipe.setOnClickListener(new View.OnClickListener() {
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

class FavouritesListViewHolder extends RecyclerView.ViewHolder {
    CardView favouriteRecipe;
    TextView name;
    ImageView image;

    public FavouritesListViewHolder(@NonNull View itemView) {
        super(itemView);
        favouriteRecipe = itemView.findViewById(R.id.favourite_recipe_container);
        image = itemView.findViewById(R.id.favourite_recipe_image);
        name = itemView.findViewById(R.id.favourite_recipe_title);
    }
}
