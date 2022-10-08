package com.example.recipeapp.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.Listeners.DeleteListener;
import com.example.recipeapp.Product;
import com.example.recipeapp.R;
import com.example.recipeapp.Recipe;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListViewHolder>{

    List<Product> list;
    DeleteListener listener;

    public ShoppingListAdapter(DeleteListener listener, List<Product> list){
        this.listener = listener;
        this.list = list;

    }
    public void updateShoppingList(List<Product> newList){
        list = newList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_container, parent, false);
        return new ShoppingListViewHolder(v).linkAdapter(this, listener);
    }


    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {


        holder.productName.setText(list.get(position).name);
        holder.productAmount.setText(list.get(position).amount);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class ShoppingListViewHolder extends RecyclerView.ViewHolder{
    CardView shopping_list_container;
    TextView productName;
    TextView productAmount;
    private ShoppingListAdapter adapter;
    DeleteListener listener;


    public ShoppingListViewHolder(@NonNull View itemView) {
        super(itemView);
        shopping_list_container = itemView.findViewById(R.id.shopping_list_container);
        productName = itemView.findViewById(R.id.product_name);
        productAmount = itemView.findViewById(R.id.product_amount);
        itemView.findViewById(R.id.delete_product_button).setOnClickListener(view -> {
            adapter.list.remove(getAdapterPosition());
            adapter.notifyItemRemoved(getAdapterPosition()) ;
        });
    }

    public ShoppingListViewHolder linkAdapter(ShoppingListAdapter adapter, DeleteListener listener){
        this.listener = listener;
        this.adapter = adapter;
        return this;
    }
}