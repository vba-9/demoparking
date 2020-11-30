package com.example.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//import android.graphics.Color

public class ActivityBook extends AppCompatActivity {
    Button b1;
    TextView empty,full;
    TextView p1,p3,p2,p4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        b1=findViewById(R.id.b1id);
        empty=findViewById(R.id.displayemptyid);
        full=findViewById(R.id.displatfullid);
        p1=findViewById(R.id.p1id);
        p2=findViewById(R.id.p2id) ;
        p3=findViewById(R.id.p3id);
        p4=findViewById(R.id.p4id);
        int emptycolor= Color.rgb(0,153,76);
        int fullcolor= Color.parseColor("RED");



        empty.setBackgroundColor(emptycolor);
        full.setBackgroundColor(fullcolor);
        p1.setBackgroundColor(emptycolor);
        p2.setBackgroundColor(emptycolor);
        p3.setBackgroundColor(emptycolor);
        p4.setBackgroundColor(emptycolor);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    //            int R = (color >> 16) & 0xff;
                //b1.setBackgroundColor(0XFF8800);
               // b1.setBackgroundColor(0x3aa8c1);
            }
        });
    }
}