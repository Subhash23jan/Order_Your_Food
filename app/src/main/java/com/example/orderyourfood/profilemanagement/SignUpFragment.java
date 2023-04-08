package com.example.orderyourfood.profilemanagement;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.orderyourfood.MainActivity;
import com.example.orderyourfood.R;
import com.example.orderyourfood.databasemanagement.Dbhelper;
import com.example.orderyourfood.databinding.FragmentSignUpBinding;

import java.util.Objects;


public class SignUpFragment extends Fragment {

 FragmentSignUpBinding fragmentSignUpBinding;
    String email,password;
    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentSignUpBinding=FragmentSignUpBinding.inflate(inflater,container,false);
        Dbhelper dbhelper=new Dbhelper(getContext());

        fragmentSignUpBinding.Olduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),LoginActivity.class));
            }
        });
        fragmentSignUpBinding.forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm= requireActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.signUpFrame,new passwordResetFragment());
                ft.commit();
            }
        });
        fragmentSignUpBinding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=fragmentSignUpBinding.EditextEmail.getText().toString();
                String password=fragmentSignUpBinding.Editextpassword.getText().toString();
               if(dbhelper.isPresent(email))
               {
                   fragmentSignUpBinding.EditextEmail.setText(null);
                   fragmentSignUpBinding.EditextEmail.setHintTextColor(getResources().getColor(android.R.color.holo_red_light));
                   fragmentSignUpBinding.EditextEmail.setHint("Email already registered");
               }else {
                   createPermissionDialog();
               }
            }
        });
        return fragmentSignUpBinding.getRoot();
    }

    private void createPermissionDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(requireContext());
        builder.setCancelable(false);
        builder.setTitle("Terms and Condition");
        builder.setMessage("Until next update ,All data is stored in your system cache. \nPlease accept out terms and conditions");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getContext(), "Account created Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), MainActivity.class));
                SharedPreferences sharedPreferences= requireActivity().getSharedPreferences("Login",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("log",true);
                editor.putString("user", email);
                editor.putString("password",password);
                editor.apply();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Please accept our terms and conditions", Toast.LENGTH_SHORT).show();
                createPermissionDialog();
            }
        });
        builder.show();
    }
}