package com.example.demo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    EditText start,end,slot,refcode;
    String getstart,getend,getslot,getrefcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title.
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_booking);
        txtmob=findViewById(R.id.mobviewid);
        start=findViewById(R.id.startviewid);
        end=findViewById(R.id.endvid);
        slot=findViewById(R.id.slotviewid);
        refcode=findViewById(R.id.refcodeid);


        Intent intent = getIntent();
        String first = intent.getStringExtra("name");
        if(connect()==1) {

            txtmob.setText(first);


            reference= FirebaseDatabase.getInstance().getReference("BookPhone") ;
            Query checkuser=reference.orderByChild("mobilenumber").equalTo(first);

            checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        getend=snapshot.child(first).child("endingTime").getValue(String.class);
                        end.setText(getend);
                        getslot=snapshot.child(first).child("slot").getValue(String.class);
                        slot.setText(getslot);
                        getstart=snapshot.child(first).child("startingTime").getValue(String.class);
                        start.setText(getstart);
                        getrefcode=snapshot.child(first).child("refcode").getValue(String.class);
                        refcode.setText(getrefcode);
                    }else{
                        AlertDialog.Builder b=new AlertDialog.Builder(ActivityViewBooking.this);
                        b.setTitle("Sorry")
                                .setCancelable(false)

                                .setMessage("No Booking Found...")
                                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                       Intent i=new Intent(ActivityViewBooking.this,ActivityDsiplayAll.class);
                                        i.putExtra("fourth", first);
                                       startActivity(i);
                                    }
                                });
                        AlertDialog d=b.create();
                        d.show();
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