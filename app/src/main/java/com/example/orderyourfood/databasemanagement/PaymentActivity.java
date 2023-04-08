package com.example.orderyourfood.databasemanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.orderyourfood.R;
import com.example.orderyourfood.cartmanagement.CartPageActivity;
import com.example.orderyourfood.databinding.ActivityPaymentBinding;
import com.example.orderyourfood.profilemanagement.ProfilePageActivity;

import java.util.Objects;

public class PaymentActivity extends AppCompatActivity  {

    Toolbar myToolbar;
    int PAYMENT_REQ_CODE=37;
    private String status="failed";
    int total=0;
    ActivityPaymentBinding paymentBinding;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paymentBinding=ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(paymentBinding.getRoot());
//        setSupportActionBar(paymentBinding.toolbar);
//        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
//        //Objects.requireNonNull().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Payment gateway");
        Intent intent=getIntent();
        total=intent.getIntExtra("amount",0);
        paymentBinding.totalAmount.setText(String.valueOf("total amount : ₹"+total+".00"));
        paymentBinding.payUsing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePayment(total);
            }
        });
        paymentBinding.backToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentActivity.this, CartPageActivity.class));
            }
        });
        paymentBinding.ProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentActivity.this, ProfilePageActivity.class));
            }
        });


    }

    private void makePayment(int totalAmount) {
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", "9019865449@ybl")
                .appendQueryParameter("pn", "Subhash")
                .appendQueryParameter("tn", "note")
                .appendQueryParameter("am", String.valueOf(totalAmount))
                .appendQueryParameter("cu", "INR")
                .build();
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        startActivityForResult(Intent.createChooser(intent,"Pay Using"),PAYMENT_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PAYMENT_REQ_CODE)
        {
            if(resultCode==RESULT_OK )
            {
                if(data!=null)
                {
                    String transactionId = data.getStringExtra("txnId");
                    String responseCode = data.getStringExtra("responseCode");
                    status = data.getStringExtra("Status");
                    Log.d("Payment status",status);
                }else {
                    Toast.makeText(this, "null data", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "payment cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}