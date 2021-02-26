package com.example.demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title.
        getSupportActionBar().hide();
        setContentView(R.layout.activity_update);
        txtfirst=findViewById(R.id.firstid);
        submitb=findViewById(R.id.submituid);
        myname=findViewById(R.id.nameuid);
        myemail=findViewById(R.id.emailuid);
        mypassword=findViewById(R.id.passworduid);
        //myconpassword=findViewById(R.id.conpassworduid);
        Intent intent=getIntent();
        String first=intent.getStringExtra("updatephone");
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




                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
       submitb.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               rootNode=FirebaseDatabase.getInstance();
               reference=rootNode.getReference("user");
               String name= myname.getText().toString();
               String mail=myemail.getText().toString();
               String password=mypassword.getText().toString();


               if(name.equals(oldname) &&mail.equals(oldemail)&& password.equals(oldpassword)) {
                   AlertDialog.Builder b = new AlertDialog.Builder(ActivityUpdate.this);
                   b.setTitle("No change ")
                           .setCancelable(false)
                           .setMessage("Data is Same as Previous ")
                           .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   Intent i = new Intent(ActivityUpdate.this, ActivityDsiplayAll.class);
                                   i.putExtra("second", first);
                                   startActivity(i);
                               }
                           });
                   AlertDialog d = b.create();
                   d.show();
               }
               else {


                   if (mail.endsWith("@gmail.com")) {
                       reference.child(first).child("name").setValue(name);
                       reference.child(first).child("mailid").setValue(mail);
                       reference.child(first).child("password").setValue(password);
                       reference.child(first).child("conpassword").setValue(password);




                       AlertDialog.Builder b = new AlertDialog.Builder(ActivityUpdate.this);
                       b.setTitle("Conformation")
                               .setCancelable(false)
                               .setMessage("Data updated Successfully ")
                               .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                       Intent i = new Intent(ActivityUpdate.this, ActivityDsiplayAll.class);
                                       i.putExtra("second", first);
                                       startActivity(i);
                                   }
                               });
                       AlertDialog d = b.create();
                       d.show();


                   } else {
                       Toast.makeText(ActivityUpdate.this, " Please fill correct Details ", Toast.LENGTH_LONG).show();
                   }


               }


           }
       });



    }
}