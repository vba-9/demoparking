package com.example.demo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityFront extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title.
        getSupportActionBar().hide();
        setContentView(R.layout.activity_front);
        connection();
    }
    public  void connection(){
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activenetwork=manager.getActiveNetworkInfo();

        if(null!=activenetwork){
            if(activenetwork.getType()==ConnectivityManager.TYPE_MOBILE){
                Toast.makeText(ActivityFront.this, "You are online..", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(ActivityFront.this,MainActivity.class);
                startActivity(intent);
                //return 1;
            }
        }
        else {
            Toast.makeText(ActivityFront.this, "Check Your Connection & restart the Application", Toast.LENGTH_LONG).show();
            //return 0;
        }

    }
}