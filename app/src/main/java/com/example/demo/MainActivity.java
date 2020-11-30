package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button signup,login;
    EditText txtname,txtpassword;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signup=findViewById(R.id.signupid);
        login=findViewById(R.id.loginid);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,ActivitySignup.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtname=findViewById(R.id.usernameid);
                txtpassword=findViewById(R.id.passwordid);

                String usernameenter=txtname.getText().toString().trim();
                String passwordenter=txtpassword.getText().toString().trim();

                reference=FirebaseDatabase.getInstance().getReference("user") ;
                Query checkuser=reference.orderByChild("phone").equalTo(usernameenter);
                checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){


                            String passwordDB=snapshot.child(usernameenter).child("password").getValue(String.class);
                                int flag=0;
                            if(passwordDB.equals(passwordenter)){

                                String u=usernameenter;
                                Toast.makeText(MainActivity.this, " Welcome", Toast.LENGTH_SHORT).show();
                                    flag=1;
                                Intent intent= new Intent(MainActivity.this,ActivityDsiplayAll.class);

                                intent.putExtra("first",u);
                                startActivity(intent);
                                txtname.getText().clear();
                                txtpassword.getText().clear();

                            }
                            if(flag==0)

                            Toast.makeText(MainActivity.this, "Password and Username not match", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Invalid Credential", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }
        });
    }
}