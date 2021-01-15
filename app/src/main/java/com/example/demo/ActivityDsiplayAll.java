 package com.example.demo;

 import android.content.Context;
 import android.content.DialogInterface;
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
 import androidx.appcompat.app.AlertDialog;
 import androidx.appcompat.app.AppCompatActivity;

 import com.google.firebase.database.DataSnapshot;
 import com.google.firebase.database.DatabaseError;
 import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
 import com.google.firebase.database.Query;
 import com.google.firebase.database.ValueEventListener;

 public class ActivityDsiplayAll extends AppCompatActivity {

    TextView txts;
    EditText cancleslot;
    Button updateB,bookB,cancleB,viewbooking,logout;
    DatabaseReference reference;
    DatabaseReference reference1;
    FirebaseDatabase rootNode;
    String impphonefirst,impphonesecond,impphonethird,impphonefourth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title.
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dsiplay_all);




        txts=findViewById(R.id.secondid);
        updateB=findViewById(R.id.updateid);
        bookB=findViewById(R.id.bookid);
        cancleB=findViewById(R.id.cancleid);
        cancleslot=findViewById(R.id.cancleslotid);
        viewbooking=findViewById(R.id.displayrecid);
        logout=findViewById(R.id.logoutid);

        Intent i=getIntent();
        impphonefirst=i.getStringExtra("first");
        txts.setText(impphonefirst);
        if(impphonefirst==null) {
            impphonesecond = i.getStringExtra("second");
            if (impphonesecond != null)
                txts.setText(impphonesecond);
            else {
                impphonethird = i.getStringExtra("third");
                if(impphonethird!=null)
                    txts.setText(impphonethird);
                else {
                    impphonefourth = i.getStringExtra("fourth");
                    txts.setText(impphonefourth);
                }
            }
        }
       bookDisplay();
        viewbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connect()==1) {
                    Intent i = new Intent(ActivityDsiplayAll.this, ActivityViewBooking.class);
                    i.putExtra("name",txts.getText().toString());
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(ActivityDsiplayAll.this, "Check Network connection!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connect() == 1) {
                    AlertDialog.Builder b = new AlertDialog.Builder(ActivityDsiplayAll.this);
                    b.setTitle("Conformation")
                            .setCancelable(false)

                            .setMessage("Do You want to exit")
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(ActivityDsiplayAll.this, MainActivity.class);
                                    startActivity(i);
                                }

                            });

                    AlertDialog d = b.create();
                    d.show();
                }
                else{
                    Toast.makeText(ActivityDsiplayAll.this, "Check Network connection!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bookB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connect()==1){
                    reference= FirebaseDatabase.getInstance().getReference("BookPhone") ;
                    Query checkuser=reference.orderByChild("mobilenumber").equalTo(txts.getText().toString());
                    checkuser.addListenerForSingleValueEvent(new ValueEventListener(){
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                //bookB.setClickable(false);
                                AlertDialog.Builder b=new AlertDialog.Builder(ActivityDsiplayAll.this);
                                b.setTitle("Warning")
                                        .setCancelable(false)

                                        .setMessage("You are Already Book a slot")
                                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                                AlertDialog d=b.create();
                                d.show();
                            }
                            else{
                                Intent i=new Intent(ActivityDsiplayAll.this,ActivityBook.class);
                                i.putExtra("mob",txts.getText().toString());
                                startActivity(i);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
                else{
                    Toast.makeText(ActivityDsiplayAll.this, "Check Network connection!!!", Toast.LENGTH_SHORT).show();

                }
            }
        });

        updateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connect()==1) {
                    Intent i = new Intent(ActivityDsiplayAll.this, ActivityUpdate.class);
                    i.putExtra("updatephone",txts.getText().toString());
                    startActivity(i);
                }
                else   {
                    Toast.makeText(ActivityDsiplayAll.this, "Check Network connection!!!", Toast.LENGTH_SHORT).show();
                }
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

                                snapshot.child(scancleslot).getRef().setValue(null);
                                bookB.setClickable(true);
                                bookB.setAlpha((float)1.0);
                                Toast.makeText(ActivityDsiplayAll.this, "Successfully Cancel !!!  ", Toast.LENGTH_LONG).show();
                                flag=1;
                                cancleslot.setVisibility(View.GONE);

                            }
                            else if((flag == 0 && scancleslot.isEmpty()) == false){
                                Toast.makeText(ActivityDsiplayAll.this, " slot not found ", Toast.LENGTH_LONG).show();
                                cancleslot.setText(null);
                                cancleslot.setVisibility(View.GONE);


                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    ////
                    if(cancleslot.getText().toString().isEmpty()==false){
                        reference1= FirebaseDatabase.getInstance().getReference("BookPhone");
                        String s=txts.getText().toString();
                        //Toast.makeText(ActivityDsiplayAll.this, s, Toast.LENGTH_SHORT).show();
                        Query check=reference1.orderByChild("mobilenumber").equalTo(s);

                        check.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int flag=0;
                                if(snapshot.exists()){
                                    bookB.setClickable(true);
                                    bookB.setAlpha((float)1.0);
                                    snapshot.child(s).getRef().setValue(null);
                                    Toast.makeText(ActivityDsiplayAll.this, "Successfully Cancel !!!  ", Toast.LENGTH_LONG).show();
                                    flag=1;
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    else{
                        Toast.makeText(ActivityDsiplayAll.this, "Enter slot number", Toast.LENGTH_LONG).show();
                    }

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
void bookDisplay(){
    reference= FirebaseDatabase.getInstance().getReference("BookPhone") ;
    Query checkuser=reference.orderByChild("mobilenumber").equalTo(txts.getText().toString());
    checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.exists()){
                bookB.setAlpha((float) 0.3);
                bookB.setClickable(false);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    });
}

}