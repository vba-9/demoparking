package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityUpdate extends AppCompatActivity {
    TextView txtfirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        txtfirst=findViewById(R.id.firstid);
        Intent intent=getIntent();
        String first=intent.getStringExtra("name");
        txtfirst.setText(first);

    }
}