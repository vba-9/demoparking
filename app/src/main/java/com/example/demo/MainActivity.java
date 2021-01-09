package com.example.demo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class MainActivity extends AppCompatActivity {
    Button signup,login;
    EditText txtname,txtpassword;
    TextView  view;
    CheckBox remember;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    public void onBackPressed()  {
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setMessage("You want to exit..")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog ad=b.create();
        ad.show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title.
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        remember=findViewById(R.id.checkBox);
        signup=findViewById(R.id.signupid);
        login=findViewById(R.id.loginid);
        view=findViewById(R.id.view1id);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,ActivitySignup.class);
                int ans=connect();
                if(ans==1) {
                    startActivity(intent);
                    finish();

                }
                else
                    Toast.makeText(MainActivity.this,"Check Network Connection !!!",Toast.LENGTH_LONG).show();
            }
        });
        SharedPreferences p=getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox= p.getString("remember","");
        if(checkbox.equals("true")){
            Toast.makeText(MainActivity.this, "wellcoe", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(MainActivity.this,ActivityDsiplayAll.class);
            startActivity(i);
        }else if(checkbox.equals("false")){
            Toast.makeText(MainActivity.this, "please sign in", Toast.LENGTH_SHORT).show();

        }
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    SharedPreferences p=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=p.edit();
                    editor.putString( "remember","true");
                    editor.apply();
                    Toast.makeText(MainActivity.this, "checked", Toast.LENGTH_SHORT).show();
                }
                else if(!buttonView.isChecked()){
                    SharedPreferences p=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=p.edit();
                    editor.putString( "remember","false");
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtname = findViewById(R.id.usernameid);
                txtpassword = findViewById(R.id.passwordi);

                String usernameenter = txtname.getText().toString().trim();
                String passwordenter = txtpassword.getText().toString().trim();

                int ans=connect();
                if(ans==1){
                reference = FirebaseDatabase.getInstance().getReference("user");
                Query checkuser = reference.orderByChild("phone").equalTo(usernameenter);
                checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {


                            String passwordDB = snapshot.child(usernameenter).child("password").getValue(String.class);
                            int flag = 0;
                            if (passwordDB.equals(passwordenter)) {

                                String u = usernameenter;
                                Toast.makeText(MainActivity.this, " Welcome", Toast.LENGTH_SHORT).show();
                                view.setVisibility(View.GONE);
                                flag = 1;
                                Intent intent = new Intent(MainActivity.this, ActivityDsiplayAll.class);

                                intent.putExtra("first", u);

                               /* AlertDialog adg= new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("Info")
                                        .setMessage("HEllo")
                                        .setPositiveButton("Okk", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .create();
                                adg.show();
                                adg.seton
*/
                                startActivity(intent);
                                MainActivity.this.finish();

                                txtname.getText().clear();
                                txtpassword.getText().clear();

                            }
                            if (flag == 0) {

                                //Toast.makeText(MainActivity.this, "Password and Username not match", Toast.LENGTH_LONG).show();
                                view.setVisibility(1);
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Invalid Credential", Toast.LENGTH_SHORT).show();
                            view.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
                else
                    Toast.makeText(MainActivity.this, "Check Network Connection !!!", Toast.LENGTH_SHORT).show();

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