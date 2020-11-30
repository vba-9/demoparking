package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ActivityUpdate extends AppCompatActivity {
    TextView txtfirst;
    EditText myname,myemail,mypassword;//myconpassword;
    String oldname,oldemail,oldpassword;
    Button submitb;
    DatabaseReference reference;
    FirebaseDatabase rootNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        txtfirst=findViewById(R.id.firstid);
        submitb=findViewById(R.id.submituid);
        myname=findViewById(R.id.nameuid);
        myemail=findViewById(R.id.emailuid);
        mypassword=findViewById(R.id.passworduid);
        //myconpassword=findViewById(R.id.conpassworduid);
        Intent intent=getIntent();
        String first=intent.getStringExtra("name");
        txtfirst.setText(first);


        reference= FirebaseDatabase.getInstance().getReference("user") ;
        Query checkuser=reference.orderByChild("phone").equalTo(first);
        //Toast.makeText(ActivityUpdate.this, " Wellcome", Toast.LENGTH_SHORT).show();
       checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                  oldname=snapshot.child(first).child("name").getValue(String.class);
                  myname.setText(oldname);
                  oldemail=snapshot.child(first).child("mailid").getValue(String.class);
                  myemail.setText(oldemail);
                    oldpassword=snapshot.child(first).child("password").getValue(String.class);
                    mypassword.setText(oldpassword);
                    //oldconpassword=snapshot.child(first).child("conpassword").getValue(String.class);
                    //myconpassword.setText(oldconpassword);



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
       submitb.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(ActivityUpdate.this, " Pa", Toast.LENGTH_SHORT).show();
               rootNode=FirebaseDatabase.getInstance();
               reference=rootNode.getReference("user");
               String name= myname.getText().toString();
               String mail=myemail.getText().toString();
               String password=mypassword.getText().toString();


              //for checking the previo and new value
               /*if(oldname.equals(myname.getText().toString().trim())) {
                   Toast.makeText(ActivityUpdate.this, " Cant update", Toast.LENGTH_SHORT).show();
               }*/


                 if(mail.endsWith("@gmail.com")) {
                     reference.child(first).child("name").setValue(name);
                     reference.child(first).child("mailid").setValue(mail);
                     reference.child(first).child("password").setValue(password);
                     reference.child(first).child("conpassword").setValue(password);

                     Toast.makeText(ActivityUpdate.this, " update", Toast.LENGTH_SHORT).show();
                     //reference.child(first).child("name").setValue(name);
                 }
                 else{
                     Toast.makeText(ActivityUpdate.this, " Please fill correct Details ", Toast.LENGTH_LONG).show();
                 }





           }
       });
      //myname.setText(oldname);


    }
}