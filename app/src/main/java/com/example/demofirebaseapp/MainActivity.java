package com.example.demofirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    TextView hello;
    Button btn_main_remove_user, getBtn_main_sign_out;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        user= firebaseAuth.getCurrentUser();

        hello = findViewById(R.id.tv_hello);
        btn_main_remove_user = findViewById(R.id.btn_main_remove);
        getBtn_main_sign_out = findViewById(R.id.btn_main_signout);
        hello.setText("Hello , "+user.getEmail());

        getBtn_main_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        btn_main_remove_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete your account?")
                        .setMessage("your account will be deleted, still deleted? ")
                        .setPositiveButton("Yes",(dialog, which) -> {
                            if (user!=null){
                                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(MainActivity.this,"User is delete...",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(MainActivity.this,LoginActivity.class));
                                        }
                                        else{
                                            Toast.makeText(MainActivity.this,"Failed to delete your accoout , pls try agian...",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .create();
                alertDialog.show();
            }
        });

    }
}