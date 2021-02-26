package com.example.demo;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

//import android.graphics.Color

public class ActivityBook extends AppCompatActivity {
    Button b1;
    TextView empty,full;
    TextView p1,p3,p2,p4,txtdateview,txtdateview1,txtmob;
    EditText txtdate,txtstart,txtend,txtslot;
    int t1hor,t1min;
    int emptycolor,fullcolor;

    DatePickerDialog.OnDateSetListener sl;
    Calendar cal=Calendar.getInstance();
    FirebaseDatabase rootNode;
    DatabaseReference reference,referencephone,reference2;
    String d1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title.
        getSupportActionBar().hide();
        setContentView(R.layout.activity_book);

        b1=findViewById(R.id.b1id);
        empty=findViewById(R.id.displayemptyid);
        full=findViewById(R.id.displatfullid);
        txtdate=findViewById(R.id.dateid);
        txtdateview=findViewById(R.id.dateviewid);
        //txtdateview1=findViewById(R.id.dateviewid1);
        //txtdateview1=findViewById(R.id.dateviewid1);
        txtstart=findViewById(R.id.startid);
        txtend=findViewById(R.id.endid);
        txtslot=findViewById(R.id.slotid);
        txtmob=findViewById(R.id.mobid);

        p1=findViewById(R.id.p1id);
        p2=findViewById(R.id.p2id) ;
        p3=findViewById(R.id.p3id);
        p4=findViewById(R.id.p4id);
        emptycolor= Color.rgb(0,153,76);
        fullcolor= Color.parseColor("RED");
        empty.setBackgroundColor(emptycolor);
        full.setBackgroundColor(fullcolor);

        p1.setBackgroundColor(emptycolor);
        p2.setBackgroundColor(emptycolor);
        p3.setBackgroundColor(emptycolor);
        p4.setBackgroundColor(emptycolor);

        Intent intent=getIntent();
        String mob=intent.getStringExtra("mob");
        txtmob.setText(mob);


        final int year=cal.get(Calendar.YEAR);
        final int month=cal.get(Calendar.MONTH);
        final int day=cal.get(Calendar.DAY_OF_MONTH);
        checkBookOrNot("P1",p1);
        txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd= new DatePickerDialog(
                        ActivityBook.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;
                         d1=day+"/"+month+"/"+year;
                        txtdate.setText(d1);
                    }
                },year,month,day);
                dpd.show();
            }
        });

       String currdate= DateFormat.getDateInstance(DateFormat.SHORT).format(cal.getTime());
        StringTokenizer st=new StringTokenizer(currdate,"/");
        int curday=Integer.parseInt(st.nextToken());
        int curmonth=Integer.parseInt(st.nextToken());
        int curyear=Integer.parseInt(st.nextToken());


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode=FirebaseDatabase.getInstance();
                reference=rootNode.getReference("Book");
                referencephone=rootNode.getReference("BookPhone");
                String date=txtdate.getText().toString();
                String stime=txtstart.getText().toString();
                String etime=txtend.getText().toString();
                String slot=txtslot.getText().toString();
                String mobnumber=txtmob.getText().toString();



                if(connect()==1) {
                    int flag = 0,sflag=0,pflag=0;
                    int selday = 0,selmonth,selyear;
                    if(txtdate.getText().toString().isEmpty()==true) {
                            sflag=1;                        
                    }
                    else{
                        StringTokenizer st1 = new StringTokenizer(d1, "/");
                        selday = Integer.parseInt(st1.nextToken());
                        selmonth = Integer.parseInt(st1.nextToken());
                        selyear = Integer.parseInt(st1.nextToken());

                    }
                    if (selday == curday) {
                        //Toast.makeText(ActivityBook.this, "selected", Toast.LENGTH_SHORT).show();
                        flag = 1;
                        txtdateview.setVisibility(View.GONE);
                       // txtdateview1.setVisibility(View.GONE);
                    }
                    else {
                        txtdateview.setVisibility(1);


                    }
                    /*if(selday<curday){
                        txtdateview.setVisibility(0);

                    } else {
                        txtdateview.setVisibility(1);


                    }
                    if (selday > curday) {
                        fflag = 1;
                        //  Toast.makeText(ActivityBook.this, "selected", Toast.LENGTH_SHORT).show();

                    } else {
                        txtdateview1.setVisibility(1);

                    }*/

                    if( stime.isEmpty()==true || etime.isEmpty()==true|| slot.isEmpty()==true){
                        pflag=1;

                    }

                    try {


                        if (flag == 1  && sflag == 0 && pflag==0 &&(slot.equals("P1") || (slot.equals("P2"))||(slot.equals("P3")) || (slot.equals("P4")))) {
                            String random=randomString();
                            BookInfo binfo = new BookInfo(date, stime, etime, slot,mobnumber);
                            reference.child(slot).setValue(binfo);
                            BookPhoneInfo bphoneinfo = new BookPhoneInfo (date, stime, etime, slot,mobnumber,random);
                            referencephone.child(mobnumber).setValue(bphoneinfo);
                            AlertDialog.Builder b=new AlertDialog.Builder(ActivityBook.this);
                            b.setTitle("Information")
                                    .setCancelable(false)

                                    .setMessage("Reference Id is -->"+random)
                                    .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(ActivityBook.this, "Successfully Book !!!", Toast.LENGTH_LONG).show();
                                            ActivityCompat.requestPermissions(ActivityBook.this,new String []{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
                                            String message="Thank You for Booking with Booking ID : "+random +" for slot "+slot + "From "+stime +" to "+etime + "\nHAPPY Gourney!!!";
                                            SmsManager mysms=SmsManager.getDefault();
                                            String number="+91"+mobnumber;
                                            mysms.sendTextMessage(mobnumber,null,message,null,null);



                                            Intent i =new Intent(ActivityBook.this,ActivityDsiplayAll.class);
                                            i.putExtra("third",mob);

                                            startActivity(i);
                                        }
                                    });
                            AlertDialog d=b.create();
                            d.show();


                        } else {
                            Toast.makeText(ActivityBook.this, "Please Enter All the Details !!!", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(ActivityBook.this, " Sorry..", Toast.LENGTH_SHORT).show();
                    }
         }

            }
            String  randomString(){
                // chose a Character random from this String
                String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                        + "0123456789"
                        + "abcdefghijklmnopqrstuvxyz";

                // create StringBuffer size of AlphaNumericString
                StringBuilder sb = new StringBuilder(6);

                for (int i = 0; i < 6; i++) {

                    // generate a random number between
                    // 0 to AlphaNumericString variable length
                    int index
                            = (int)(AlphaNumericString.length()
                            * Math.random());

                    // add Character one by one in end of sb
                    sb.append(AlphaNumericString
                            .charAt(index));
                }
            return sb.toString();
            }

        });

        txtstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd=new TimePickerDialog(
                        ActivityBook.this,
                        android.R.style.Theme_Material_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                t1hor=hourOfDay;
                                t1min=minute;
                                String t=t1hor +":"+t1min;
                                SimpleDateFormat f24Hourse=new SimpleDateFormat(
                                        "HH:mm"

                                );
                                try {
                                    Date date= f24Hourse.parse(t);
                                    SimpleDateFormat f12Hours=new SimpleDateFormat(
                                            "HH:mm aa"
                                    );
                                    txtstart.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },12,0,false

                );
                tpd.updateTime(t1hor,t1min);
                tpd.show();
            }
        });
        txtend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd=new TimePickerDialog(
                        ActivityBook.this,
                        android.R.style.Theme_Material_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                t1hor=hourOfDay;
                                t1min=minute;
                                String t=t1hor +":"+t1min;
                                SimpleDateFormat f24Hourse=new SimpleDateFormat(
                                        "HH:mm"

                                );
                                try {
                                    Date date= f24Hourse.parse(t);
                                    SimpleDateFormat f12Hours=new SimpleDateFormat(
                                            "HH:mm aa"
                                    );
                                    txtend.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },12,0,false

                );
                tpd.updateTime(t1hor,t1min);
                tpd.show();
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

    void checkBookOrNot(String  s,TextView p){
        reference2= FirebaseDatabase.getInstance().getReference("Book");
        //String s=txts.getText().toString();
        //Toast.makeText(ActivityDsiplayAll.this, s, Toast.LENGTH_SHORT).show();
        Query check=reference2.orderByChild("slot").equalTo(s);

        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int flag=0;
                if(snapshot.exists()){

                    p1.setBackgroundColor(fullcolor);


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

}