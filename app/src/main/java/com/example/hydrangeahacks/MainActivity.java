package com.example.hydrangeahacks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText user_email, user_password;
    private FirebaseAuth mAuth;
    private Button btnLogin, btnSignUp;


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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = user_email.getText().toString();
                final String password = user_password.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Authenticate user
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if successful, navigate to HomeScreen
                        //else display an error message to user
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                            startActivity(intent);
                            finish();

                        } else {
                            if (password.length() < 8) {
                                user_password.setError("Password too short--Please enter minimum 8 characters");
                            } else {
                                Toast.makeText(MainActivity.this, "Authentication failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                btnSignUp.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, Signup.class));

                    }
                });

            }
        });

    }
}