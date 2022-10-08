package com.example.recipeapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.Listeners.IngredientsClickListener;
import com.example.recipeapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListViewHolder> {

    ArrayList<String> list;
    ArrayList<String> imageList;
    String imageUrl = "https://spoonacular.com/cdn/ingredients_100x100/";
    IngredientsClickListener listener;

    public IngredientsListAdapter(ArrayList<String> list, ArrayList<String> imageList, IngredientsClickListener listener){
        this.list = list;
        this.imageList = imageList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public IngredientsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_container, parent, false);
        return new IngredientsListViewHolder(v).linkAdapter(this, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsListViewHolder holder, int position) {
        holder.ingredient.setText(list.get(position));
        Picasso.get().load( imageUrl + imageList.get(position)).into(holder.ingredientImage);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}

class IngredientsListViewHolder extends RecyclerView.ViewHolder{
    CardView ingredientsList;
    TextView ingredient;
    ImageView ingredientImage;
    IngredientsListAdapter adapter;
    IngredientsClickListener listener;

    public IngredientsListViewHolder(@NonNull View itemView) {
        super(itemView);
        ingredientsList = itemView.findViewById(R.id.ingredient_container);
        ingredient = itemView.findViewById(R.id.ingredient_name);
        ingredientImage = itemView.findViewById(R.id.ingredient_image);
        itemView.findViewById(R.id.add_ingredient_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(getAdapterPosition());
            }
        });
    }

    public IngredientsListViewHolder linkAdapter(IngredientsListAdapter adapter, IngredientsClickListener listener){
        this.adapter = adapter;
        this.listener = listener;
        return this;
    }


}
