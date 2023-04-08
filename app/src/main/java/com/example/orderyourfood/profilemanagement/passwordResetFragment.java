package com.example.orderyourfood.profilemanagement;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.orderyourfood.R;
import com.example.orderyourfood.databasemanagement.Dbhelper;
import com.example.orderyourfood.databinding.FragmentPasswordResetBinding;


public class passwordResetFragment extends Fragment {


    public passwordResetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentPasswordResetBinding resetBinding=FragmentPasswordResetBinding.inflate(inflater,container,false);
        resetBinding.EditextEmail.setHint("Enter your email");
        resetBinding.Editextpassword.setVisibility(View.GONE);
        Dbhelper dbhelper=new Dbhelper(getContext());
        resetBinding.gotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),LoginActivity.class));
            }
        });
        resetBinding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dbhelper.isPresent(resetBinding.EditextEmail.getText().toString()))
                {
                    resetBinding.Editextpassword.setVisibility(View.VISIBLE);
                    resetBinding.setPassword.setHint("Enter new password");
                    resetBinding.nextButton.setVisibility(View.GONE);
                    resetBinding.setPassword.setVisibility(View.VISIBLE);
                }else {
                    resetBinding.EditextEmail.setText(null);
                    resetBinding.EditextEmail.setHintTextColor(getResources().getColor(android.R.color.holo_red_light));
                    resetBinding.EditextEmail.setHint("email not found");
                }
            }
        });
        resetBinding.setPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbhelper.setPassword(resetBinding.EditextEmail.getText().toString(),resetBinding.Editextpassword.getText().toString());
                Toast.makeText(getContext(), "Password rest successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(),LoginActivity.class));
            }
        });

        return  resetBinding.getRoot();
    }
}