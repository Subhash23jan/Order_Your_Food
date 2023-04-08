package com.example.orderyourfood.cartmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.orderyourfood.MainActivity;
import com.example.orderyourfood.R;
import com.example.orderyourfood.databasemanagement.Dbhelper;
import com.example.orderyourfood.databinding.ActivityCartPageBinding;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Objects;

public class CartPageActivity extends AppCompatActivity {
    ActivityCartPageBinding cartPageBinding;
    Toolbar myToolbar;
    ArrayList<OrderCart>orderCarts=new ArrayList<>();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartPageBinding=ActivityCartPageBinding.inflate(getLayoutInflater());
        setContentView(cartPageBinding.getRoot());
       // myToolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(cartPageBinding.toolbar);
//        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Your Cart");
        Dbhelper dbhelper=new Dbhelper(CartPageActivity.this);
        orderCarts=dbhelper.getAllOrders(MainActivity.userEmail);
        CartImagesAdapter cartImagesAdapter=new CartImagesAdapter(orderCarts);
        cartPageBinding.recyclerView.setLayoutManager(new LinearLayoutManager(CartPageActivity.this));
        cartPageBinding.recyclerView.setAdapter(cartImagesAdapter);
        cartPageBinding.HomeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartPageActivity.this, MainActivity.class));
                finish();
            }
        });
        cartPageBinding.continueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderCarts=dbhelper.getAllOrders(MainActivity.userEmail);
                OrderList_fragment fragment=new OrderList_fragment(orderCarts);
                fragment.show(getSupportFragmentManager(),fragment.getTag());
            }
        });



    }
}