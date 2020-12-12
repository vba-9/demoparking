package com.example.demo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ActivityViewBooking extends AppCompatActivity {
    TextView txtmob;
    DatabaseReference reference;
    FirebaseDatabase rootNode;
    EditText start,end,slot;
    String getstart,getend,getslot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);
        txtmob=findViewById(R.id.mobviewid);
        start=findViewById(R.id.startviewid);
        end=findViewById(R.id.endvid);
        slot=findViewById(R.id.slotid);

        Intent intent = getIntent();
        String first = intent.getStringExtra("name");
        if(connect()==1) {

            txtmob.setText(first);

            reference= FirebaseDatabase.getInstance().getReference("BookPhone") ;
            Query checkuser=reference.orderByChild("mobilenumber").equalTo(first);
            //Toast.makeText(ActivityUpdate.this, " Wellcome", Toast.LENGTH_SHORT).show();
            checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        getstart=snapshot.child(first).child("startingTime").getValue(String.class);
                        start.setText(getstart);
                       /* oldemail=snapshot.child(first).child("mailid").getValue(String.class);
                        myemail.setText(oldemail);
                        oldpassword=snapshot.child(first).child("password").getValue(String.class);
                        mypassword.setText(oldpassword);
                        //oldconpassword=snapshot.child(first).child("conpassword").getValue(String.class);
                        //myconpassword.setText(oldconpasswo    rd);
*/


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }
    private int connect() {
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activenetwork=manager.getActiveNetworkInfo();
        int flag=0;
        if(null!=activenetwork){
            if(activenetwork.getType()==ConnectivityManager.TYPE_MOBILE){
                flag=1;
            }
        }
        else {
            flag=0;
        }
        return flag;
    }
}