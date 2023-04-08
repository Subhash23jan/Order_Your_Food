package com.example.orderyourfood.cartmanagement;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderyourfood.MainActivity;
import com.example.orderyourfood.R;
import com.example.orderyourfood.databasemanagement.Dbhelper;
import com.example.orderyourfood.databasemanagement.PaymentActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class OrderList_fragment extends BottomSheetDialogFragment {
 RecyclerView recyclerView;
 Button continueBtn;
 TextView total;

    ArrayList<OrderCart>orderCarts=new ArrayList<>();
    public OrderList_fragment(ArrayList<OrderCart>orderCarts) {
        this.orderCarts=orderCarts;
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_order_list_fragment, container, false);
        recyclerView=view.findViewById(R.id.FoodImagesRv);
        continueBtn=view.findViewById(R.id.proceedButton);
        total=view.findViewById(R.id.totalAmount);
        OrderListAdapter orderListAdapter=new OrderListAdapter(orderCarts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(orderListAdapter);
        Dbhelper dbhelper=new Dbhelper(getContext());
        int Total=dbhelper.getTotal(MainActivity.userEmail);
        total.setText(String.valueOf("total : ₹"+Total+".00 only..!!"));
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), PaymentActivity.class);
                intent.putExtra("amount",Total);
                 startActivity(intent);
            }
        });
        return view;
    }
}