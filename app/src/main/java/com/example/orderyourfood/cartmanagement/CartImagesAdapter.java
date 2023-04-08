package com.example.orderyourfood.cartmanagement;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderyourfood.MainActivity;
import com.example.orderyourfood.R;
import com.example.orderyourfood.databasemanagement.Dbhelper;
import com.example.orderyourfood.databasemanagement.PaymentActivity;

import java.util.ArrayList;

public class CartImagesAdapter extends RecyclerView.Adapter<CartImagesAdapter.CartImageHolder> {

    ArrayList<OrderCart> OrderCartItems=new ArrayList<>();
    public CartImagesAdapter(ArrayList<OrderCart> orderCartItems) {
        OrderCartItems = orderCartItems;
    }

    @NonNull
    @Override
    public CartImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_,parent,false);
        return new CartImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartImageHolder holder, int position) {
        position=holder.getAdapterPosition();
        final int finalPosition=position;
        holder.imageView.setImageResource(OrderCartItems.get(position).image_id);
        holder.name.setText(OrderCartItems.get(position).foodName);
        holder.price.setText(String.valueOf("₹"+OrderCartItems.get(position).price+".00"));
        holder.counts.setText(String.valueOf(OrderCartItems.get(position).counts));
        Dbhelper dbHelper=new Dbhelper(holder.add.getContext());
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addCart(OrderCartItems.get(finalPosition).image_id,OrderCartItems.get(finalPosition).foodName,OrderCartItems.get(finalPosition).price,MainActivity.userEmail);
                int number_of_items=Integer.parseInt(holder.counts.getText().toString());
                holder.counts.setText(String.valueOf(number_of_items+1));
            }
        });
        int finalPosition1 = position;
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.remove(OrderCartItems.get(finalPosition1).image_id, MainActivity.userEmail);
                int number_of_items=Integer.parseInt(holder.counts.getText().toString());
                if(number_of_items>0)
                {
                    holder.counts.setText(String.valueOf(number_of_items-1));
                }
            }
        });
        int finalPosition2 = position;
        holder.btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number_of_items=Integer.parseInt(holder.counts.getText().toString());
                int price_of_item= OrderCartItems.get(finalPosition2).price;
                int totalPrice=number_of_items*price_of_item;

                Intent intent=new Intent(holder.btnBuyNow.getContext(), PaymentActivity.class);
                intent.putExtra("amount",totalPrice);
                holder.btnBuyNow.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return OrderCartItems.size();
    }

    static class CartImageHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,price;
        TextView add,remove;
        TextView counts;
        Button btnBuyNow;
        public CartImageHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.foodCartImage);
            name=itemView.findViewById(R.id.CartFoodName);
            price=itemView.findViewById(R.id.foodCartPrice);
            add=itemView.findViewById(R.id.BtnCartAdd);
            remove=itemView.findViewById(R.id.BtnCartRemove);
            counts=itemView.findViewById(R.id.foodCartCounts);
            btnBuyNow=itemView.findViewById(R.id.buyThisOnly);
        }
    }
}
