package com.example.orderyourfood;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderyourfood.databasemanagement.Dbhelper;

import java.util.ArrayList;

public class FoodItemsAdapter extends RecyclerView.Adapter<FoodItemsAdapter.FoodItemsHolder> {


    ArrayList<FoodItems> foodItemsArrayList;
    String phone;

    public FoodItemsAdapter() {
        this.foodItemsArrayList =MainActivity.foodItems;
    }

    @NonNull
    @Override
    public FoodItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.food_items_layout,parent,false);
        return new FoodItemsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemsHolder holder, int position) {
        position= holder.getAdapterPosition();
        holder.foodImage.setImageResource(foodItemsArrayList.get(position).image_id);
        holder.name.setText(foodItemsArrayList.get(position).name);
        holder.price.setText(String.valueOf("₹"+foodItemsArrayList.get(position).price+".00"));
        int finalPosition = position;
        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), String.valueOf(foodItemsArrayList.get(finalPosition).name)+" added to cart", Toast.LENGTH_SHORT).show();
                Dbhelper dbHelper=new Dbhelper(holder.name.getContext());
                dbHelper.addCart(foodItemsArrayList.get(finalPosition).image_id,foodItemsArrayList.get(finalPosition).name,foodItemsArrayList.get(finalPosition).price,MainActivity.userEmail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodItemsArrayList.size();
    }


    public static class FoodItemsHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView name,price;
        Button cart;
        public FoodItemsHolder(@NonNull View itemView) {
            super(itemView);
            foodImage=itemView.findViewById(R.id.imageViewFoodItems);
            name=itemView.findViewById(R.id.foodName);
            price=itemView.findViewById(R.id.foodPrice);
            cart=itemView.findViewById(R.id.btncart);
        }
    }
}
