package com.example.orderyourfood.profilemanagement;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.orderyourfood.MainActivity;
import com.example.orderyourfood.R;
import com.example.orderyourfood.cartmanagement.CartPageActivity;
import com.example.orderyourfood.databasemanagement.Dbhelper;
import com.example.orderyourfood.databinding.ActivityProfilePageBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class ProfilePageActivity extends AppCompatActivity {

    ActivityProfilePageBinding profilePageBinding;
    int GALLERY_REQ_CODE=56;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profilePageBinding=ActivityProfilePageBinding.inflate(getLayoutInflater());
        setContentView(profilePageBinding.getRoot());
//        setSupportActionBar(profilePageBinding.toolbar);
//        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Your profile");
        fetchData();
        profilePageBinding.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileEditDialogue();
            }
        });
        profilePageBinding.cartPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilePageActivity.this, CartPageActivity.class));
            }
        });
        profilePageBinding.profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage();
            }
        });
        profilePageBinding.TextViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfilePageActivity.this, "You logged out", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences1=getSharedPreferences("Login",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences1.edit();
                editor.putBoolean("log",false);
                editor.apply();
                startActivity(new Intent(ProfilePageActivity.this, LoginActivity.class));
                finish();
            }
        });
        profilePageBinding.addressTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert=new AlertDialog.Builder(ProfilePageActivity.this);
                alert.setTitle("Your address");
                String address=new Dbhelper(ProfilePageActivity.this).getAddress(MainActivity.userEmail);
                if(address==null)
                {
                    address="No Address found!!";
                }
                alert.setMessage(address);
                alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }
        });
    }

    private void fetchData() {
        Dbhelper dbhelper=new Dbhelper(ProfilePageActivity.this);
        String name,phone;
        name=dbhelper.getName(MainActivity.userEmail);
        phone=dbhelper.getNumber(MainActivity.userEmail);
        byte[] bytes= dbhelper.getImage(MainActivity.userEmail);
        if(name!=null)
        {
            profilePageBinding.UserName.setText(String.valueOf("Name : "+name));
        }
        if(phone!=null)
        {
            profilePageBinding.UserPhone.setText(String.valueOf("Phone : "+phone));
        }
        if(bytes!=null)
        {
            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            profilePageBinding.profilePic.setImageBitmap(bitmap);
        }
    }

    private void profileEditDialogue() {
        AlertDialog.Builder dialogue=new AlertDialog.Builder(ProfilePageActivity.this);
        View view=View.inflate(getApplicationContext(),R.layout.editprofile,null);
        dialogue.setView(view);
        dialogue.setCancelable(false);
        EditText Ediname,Ediphone,EdiAddress;
        Ediname=view.findViewById(R.id.EditName);
        EdiAddress=view.findViewById(R.id.EditextAddress);
        Ediphone=view.findViewById(R.id.EditPhone);
        dialogue.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name,phone,address;
                name=Ediname.getText().toString();
                Dbhelper dbhelper=new Dbhelper(ProfilePageActivity.this);
                phone=Ediphone.getText().toString();
                address=EdiAddress.getText().toString();
                if(name.length()>=1)
                {
                    dbhelper.setName(MainActivity.userEmail,name);
                }else {
                    Toast.makeText(ProfilePageActivity.this, "Name should be non empty", Toast.LENGTH_SHORT).show();
                    profileEditDialogue();
                }
                if(phone.length()>=1)
                {
                    dbhelper.setPhone(MainActivity.userEmail,phone);
                }else {
                    Toast.makeText(ProfilePageActivity.this, "phone should be 10 numbers", Toast.LENGTH_SHORT).show();
                    profileEditDialogue();
                }
                if(address.length()>=10)
                {
                    dbhelper.setAddress(MainActivity.userEmail,address);
                }else if(address.length()!=0) {
                    Toast.makeText(ProfilePageActivity.this, "Address is too short", Toast.LENGTH_SHORT).show();
                }
                fetchData();
                Toast.makeText(ProfilePageActivity.this, "Information edited successfully", Toast.LENGTH_SHORT).show();
            }
        });
        dialogue.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ProfilePageActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });

        dialogue.show();
    }

    private void setImage()
    {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,GALLERY_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_REQ_CODE && resultCode==RESULT_OK && data!=null)
        {
            Uri uri=data.getData();
            profilePageBinding.profilePic.setImageURI(uri);
            ByteArrayOutputStream stream=new ByteArrayOutputStream();
            Bitmap bitmap;
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            byte[] bytes=stream.toByteArray();
            Dbhelper dbhelper=new Dbhelper(getApplicationContext());
            dbhelper.setImage(MainActivity.userEmail,bytes);
        }
    }
}