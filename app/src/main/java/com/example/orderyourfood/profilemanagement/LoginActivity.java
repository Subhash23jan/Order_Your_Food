package com.example.orderyourfood.profilemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.orderyourfood.MainActivity;
import com.example.orderyourfood.databasemanagement.Dbhelper;
import com.example.orderyourfood.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding loginBinding;
    String EdiEmail =null;
    String EdiPassword=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
        Dbhelper dbhelper=new Dbhelper(LoginActivity.this);
        loginBinding.rltvlyt.setVisibility(View.VISIBLE);
        loginBinding.NewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBinding.toolbartextView.setText("Signup Page");
                loginBinding.rltvlyt.setVisibility(View.GONE);
                SignUpFragment fragment=new SignUpFragment();
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(loginBinding.signUpFrame.getId(),fragment);
                ft.commit();
            }
        });
        loginBinding.forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBinding.toolbartextView.setText("Signup Page");
                loginBinding.rltvlyt.setVisibility(View.GONE);
                passwordResetFragment fragment=new passwordResetFragment();
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(loginBinding.signUpFrame.getId(),fragment);
                ft.commit();
            }
        });
        loginBinding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                EdiEmail =loginBinding.EditextEmail.getText().toString();
                EdiPassword =loginBinding.Editextpassword.getText().toString();
                if(EdiEmail ==null)
                {
                    Toast.makeText(LoginActivity.this, "Email shouldn't be empty", Toast.LENGTH_SHORT).show();
                } else if (EdiPassword.equals("")) {
                    Toast.makeText(LoginActivity.this, "password shouldn't be empty", Toast.LENGTH_SHORT).show();
                }else if(!dbhelper.isPresent(EdiEmail,EdiPassword))
                {
                    if(!dbhelper.isPresent(EdiEmail))
                    {
                        loginBinding.EditextEmail.setText(null);
                        loginBinding.EditextEmail.setHintTextColor(getResources().getColor(android.R.color.holo_red_light));
                        loginBinding.EditextEmail.setHint("email not found");
                    }
                    else {
                        loginBinding.Editextpassword.setText(null);
                        loginBinding.Editextpassword.setHintTextColor(getResources().getColor(android.R.color.holo_red_light));
                        loginBinding.Editextpassword.setHint("Email and password mismatch");
                    }
                }
                else if(dbhelper.isPresent(EdiEmail,EdiPassword)) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    SharedPreferences sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putBoolean("log",true);
                    editor.putString("user", EdiEmail);
                    editor.putString("password",EdiPassword);
                    editor.apply();
                    finish();
                }
            }
        });


    }
}