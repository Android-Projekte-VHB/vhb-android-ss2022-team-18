package com.example.recipeapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.R;

import java.util.ArrayList;

public class TagsListAdapter extends RecyclerView.Adapter<TagsListViewHolder>{

    Context context;
    String[] list;
    ArrayList<String> checkedList;

    public TagsListAdapter(Context context, String[] list){
        this.context = context;
        this.list = list;
        checkedList = new ArrayList<>();
    }

    @NonNull
    @Override
    public TagsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TagsListViewHolder(LayoutInflater.from(context).inflate(R.layout.tag_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TagsListViewHolder holder, int position) {
        holder.tagName.setText(list[position]);
        holder.tagName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.tagName.isChecked()){
                    checkedList.add(holder.tagName.getText().toString());
                } else {
                    checkedList.remove(holder.tagName.getText().toString());
                }
            }
        });
    }

    public ArrayList<String> getCheckedList(){
        return checkedList;
    }

    @Override
    public int getItemCount() {
        return list.length;
    }
}

class TagsListViewHolder extends RecyclerView.ViewHolder {

    CheckBox tagName;

    public TagsListViewHolder(@NonNull View itemView) {
        super(itemView);
        tagName = itemView.findViewById(R.id.tag_checkbox);
    }
}
