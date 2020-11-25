package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityDsiplayAll extends AppCompatActivity {
    //EditText txtfirst;
    TextView txts;
    Button updateB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsiplay_all);
      //  txtfirst=findViewById(R.id.firstid);
        txts=findViewById(R.id.secondid);
        updateB=findViewById(R.id.updateid);
       // txts=findViewById(R.id.secondid);
        //Toast.makeText(this, "You are in", Toast.LENGTH_SHORT).show();
        Intent intent=getIntent();
        String first=intent.getStringExtra("first");
        //txtfirst.setText(first);
        txts.setText(first);
        updateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDsiplayAll.this, ActivityUpdate.class);
                intent.putExtra("name",first);
                startActivity(intent);

            }
        });

    }
}