package com.example.demo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ActivityDsiplayAll extends AppCompatActivity {
    //EditText txtfirst;
    TextView txts;
    EditText cancleslot;
    Button updateB,bookB,cancleB;
    DatabaseReference reference;
    FirebaseDatabase rootNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title.
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dsiplay_all);
      //  txtfirst=findViewById(R.id.firstid);
        txts=findViewById(R.id.secondid);
        updateB=findViewById(R.id.updateid);
        bookB=findViewById(R.id.bookid);
        cancleB=findViewById(R.id.cancleid);
        cancleslot=findViewById(R.id.cancleslotid);
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
                if(connect()==1) {
                    intent.putExtra("name", first);
                    startActivity(intent);
                }
                else
                    Toast.makeText(ActivityDsiplayAll.this,"Check Netork Connection !!! ",Toast.LENGTH_LONG).show();

            }
        });
        bookB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(connect()==1) {



                    Intent intent= new Intent(ActivityDsiplayAll.this,ActivityBook.class);
                    intent.putExtra("mob", first);
                    startActivity(intent);
                }
                else
                    Toast.makeText(ActivityDsiplayAll.this, "Check Network connection", Toast.LENGTH_SHORT).show();
            }
        });
        cancleB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(connect()==1){
                    cancleslot.setVisibility(1);
                    String scancleslot=cancleslot.getText().toString();


                    reference= FirebaseDatabase.getInstance().getReference("Book");
                    Query checkuser=reference.orderByChild("slot").equalTo(scancleslot);

                    checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int flag=0;
                            if(snapshot.exists()){
                            //Toast.makeText(ActivityDsiplayAll.this, "In", Toast.LENGTH_SHORT).show();
                                //snapshot.child().child("password").getValue(String.class);
                                snapshot.child(scancleslot).getRef().setValue(null);
                                Toast.makeText(ActivityDsiplayAll.this, "Successfully Cancel !!!  ", Toast.LENGTH_LONG).show();
                                flag=1;



                            }
                            else if((flag == 0 && scancleslot.isEmpty()) == false){
                                Toast.makeText(ActivityDsiplayAll.this, " slot not found ", Toast.LENGTH_LONG).show();
                                 cancleslot.setText(null);


                            }



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });



                }
                else{
                    Toast.makeText(ActivityDsiplayAll.this, "Check Network connection", Toast.LENGTH_SHORT).show();

                }
            }
        });


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