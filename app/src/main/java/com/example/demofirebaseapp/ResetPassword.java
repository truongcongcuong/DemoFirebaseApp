package com.example.demofirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    EditText edt_rpa_email;
    Button btt_rpa_reset_password , btt_rpa_back;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar_rpa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        firebaseAuth = FirebaseAuth.getInstance();

        edt_rpa_email = findViewById(R.id.email);
        btt_rpa_reset_password = findViewById(R.id.btn_reset_password);
        btt_rpa_back = findViewById(R.id.btn_back);
        progressBar_rpa = findViewById(R.id.prb_reset_password);

        btt_rpa_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResetPassword.this, LoginActivity.class));
            }
        });

        btt_rpa_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_rpa_email.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar_rpa.setVisibility(View.VISIBLE);
                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Send email successfully , pls check your email",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"Failed to send email , pls try again",Toast.LENGTH_SHORT).show();
                                }
                                progressBar_rpa.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }
}