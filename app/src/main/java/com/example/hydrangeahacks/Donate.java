package com.example.hydrangeahacks;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class Donate extends AppCompatActivity implements View.OnClickListener {
    private ImageButton meds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_donate);

        meds = findViewById(R.id.imageButton);
        meds.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton:
                startActivity(new Intent(this, DonationCheck.class));
        }
    }
}