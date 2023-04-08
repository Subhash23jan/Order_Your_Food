package com.example.orderyourfood;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderyourfood.cartmanagement.CartPageActivity;
import com.example.orderyourfood.databasemanagement.Dbhelper;
import com.example.orderyourfood.databinding.ActivityMainBinding;
import com.example.orderyourfood.profilemanagement.LoginActivity;
import com.example.orderyourfood.profilemanagement.ProfilePageActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static String userEmail,uniqueUserId, userPassword;
    public static ArrayList<FoodItems>foodItems=new ArrayList<>();
    ActivityMainBinding mainBinding;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        SharedPreferences sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        if(!sharedPreferences.getBoolean("log", false))
        {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        userEmail=sharedPreferences.getString("user"," ");
        userPassword =sharedPreferences.getString("password"," ");
        uniqueUserId=generateId(userEmail);
        Log.d("user details",userEmail);
        Dbhelper dbhelper=new Dbhelper(MainActivity.this);
        dbhelper.setBasicInfo(userEmail, userPassword,uniqueUserId);
        addFoodItems();
        FoodItemsAdapter foodItemsAdapter=new FoodItemsAdapter();
        mainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mainBinding.recyclerView.setAdapter(foodItemsAdapter);
        mainBinding.cartTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CartPageActivity.class));
            }
        });
        mainBinding.profileTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ProfilePageActivity.class));
            }
        });
    }

    private void addFoodItems() {
        foodItems.add(new FoodItems(R.drawable.dosa,"Dosa",60));
        foodItems.add(new FoodItems(R.drawable.biscuit,"Biscuit",40));
        foodItems.add(new FoodItems(R.drawable.burger,"Burger",150));
        foodItems.add(new FoodItems(R.drawable.fries,"Fries",50));
        foodItems.add(new FoodItems(R.drawable.lays,"Lays",30));
        foodItems.add(new FoodItems(R.drawable.swiggy,"SwiggyIcon Cake",300));
        foodItems.add(new FoodItems(R.drawable.zomato,"ZomatoIcon Cake",400));
    }

    public static String generateId(String userEmail)
    {
        int ans=0;
        for(int i=0;i<userEmail.length();i++)
        {
            ans*=10;
            ans=(int)userEmail.charAt(i);
        }
        return String.valueOf(Math.abs(ans));
    }
}