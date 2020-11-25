package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivitySignup extends AppCompatActivity {
    Button submit,back;
    EditText myname,myphone,myemail,mypassword,myconpassword;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        back=findViewById(R.id.backid);
        submit=findViewById(R.id.submitid);
        myname=findViewById(R.id.nameid);
        myphone=findViewById(R.id.phoneid);
        myemail=findViewById(R.id.mailid);
        mypassword=findViewById(R.id.passwordsid);
        myconpassword=findViewById(R.id.conpasswordid);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode=FirebaseDatabase.getInstance();
                reference=rootNode.getReference("user");
                String name= myname.getText().toString();
                String phone= myphone.getText().toString();
                String mailid= myemail.getText().toString();
                String password= mypassword.getText().toString();
                String conpassword= myconpassword.getText().toString();
                int flag=0,tflag=0,mflag=0,pflag=0,fflag=0;
                if(password.length()<8) {
                    Toast.makeText(ActivitySignup.this, " Password  more than 8 character", Toast.LENGTH_SHORT).show();
                    flag=1;
                }
                if(phone.length()!=10){
                    Toast.makeText(ActivitySignup.this, "Mobile number is 10 digit", Toast.LENGTH_SHORT).show();
                    mflag=1;

                }
                if(mailid.endsWith("@gmail.com")==false) {
                    Toast.makeText(ActivitySignup.this, "Mail end with @-gmail.com", Toast.LENGTH_SHORT).show();
                    pflag = 1;
                }
                if(password.equals(conpassword)==false ){
                    Toast.makeText(ActivitySignup.this, "Password not match", Toast.LENGTH_SHORT).show();
                    fflag=1;
                }
                if(name.isEmpty()==true || phone.isEmpty()==true || mailid.isEmpty()==true || password.isEmpty()==true || conpassword.isEmpty()==true){
                    tflag=1;
                }
                if(fflag==0 && tflag==0 && pflag==0 && flag==0 && mflag==0) {
                    Signupinfo info = new Signupinfo(name, phone, mailid, password, conpassword);
                    //reference.child(phone).setValue(uh);
                    reference.child(phone).setValue(info);
                    Intent intent = new Intent(ActivitySignup.this, ActivityDsiplayAll.class);
                    startActivity(intent);
                }



            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ActivitySignup.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}