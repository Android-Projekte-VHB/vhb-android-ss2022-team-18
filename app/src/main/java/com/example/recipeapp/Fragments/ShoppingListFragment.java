package com.example.recipeapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.Adapters.ShoppingListAdapter;
import com.example.recipeapp.Listeners.DeleteListener;
import com.example.recipeapp.Listeners.ShoppingListResponseListener;
import com.example.recipeapp.Product;
import com.example.recipeapp.R;

import java.util.ArrayList;
import java.util.List;


public class ShoppingListFragment extends Fragment {

    ShoppingListAdapter shoppingListAdapter;
    ShoppingListResponseListener listener;
    RecyclerView shoppingview;
    EditText inputProduct;
    EditText inputAmount;
    Button addProductButton;
    Button deleteProductButton;
    List<Product> list ;
    DeleteListener deleteListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        deleteListener = getArguments().getParcelable("test");
        shoppingview = view.findViewById(R.id.shoppingListRecyclerView);
        addProductButton = view.findViewById(R.id.add_product_button);
        deleteProductButton =view.findViewById(R.id.delete_product_button);
        inputProduct = view.findViewById(R.id.add_product);
        inputAmount = view.findViewById(R.id.add_amount);
        setupShoppingList();
    }



    public void setupShoppingList(){
        shoppingview.setLayoutManager(new LinearLayoutManager(getContext()));
        shoppingListAdapter = new ShoppingListAdapter(deleteListener, list);
        shoppingview.setAdapter(shoppingListAdapter);
        initShoppingButton();
    }

    private void initShoppingButton(){
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText inputProduct = getView().findViewById(R.id.add_product);
                EditText inputAmount = getView().findViewById(R.id.add_amount);
                String product = inputProduct.getText().toString();
                String amount = inputAmount.getText().toString();
                Product newProduct = new Product(product,amount + "x");
                if(product.trim().length() > 0 && amount.trim().length() > 0){
                    listener.onButtonClicked(newProduct);
                    inputProduct.setText("");
                    inputAmount.setText("");
                } else {
                    Toast.makeText(getActivity(), "Invalid Input", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void updateList(List<Product> shoppingList){
        list = shoppingList;
    }

    public void getListener(ShoppingListResponseListener shoppingListResponseListener){
        listener = shoppingListResponseListener;
    }

}