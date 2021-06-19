package com.example.hydrangeahacks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText user_email, user_password;
    private FirebaseAuth mAuth;
    private Button btnLogin,btnSignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            //go to HomeScreen if user is logged in
            startActivity(new Intent(MainActivity.this, HomeScreen.class));
            finish();
        }

        user_email = (EditText) findViewById(R.id.email);
        user_password = (EditText) findViewById(R.id.editTextTextPassword);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnSignUp = (Button) findViewById(R.id.btn_signup);


        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent( MainActivity.this, Signup.class));

            }
        });

    }
}