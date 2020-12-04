package com.example.demo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    TextView p1,p3,p2,p4,txtdateview,txtdateview1;
    EditText txtdate,txtstart,txtend;
    int t1hor,t1min;

    DatePickerDialog.OnDateSetListener sl;
    Calendar cal=Calendar.getInstance();
    String d1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        b1=findViewById(R.id.b1id);
        empty=findViewById(R.id.displayemptyid);
        full=findViewById(R.id.displatfullid);
        txtdate=findViewById(R.id.dateid);
        txtdateview=findViewById(R.id.dateviewid);
        //txtdateview1=findViewById(R.id.dateviewid1);
        txtdateview1=findViewById(R.id.dateviewid1);
        txtstart=findViewById(R.id.startid);
        txtend=findViewById(R.id.endid);

        p1=findViewById(R.id.p1id);
        p2=findViewById(R.id.p2id) ;
        p3=findViewById(R.id.p3id);
        p4=findViewById(R.id.p4id);
        int emptycolor= Color.rgb(0,153,76);
        int fullcolor= Color.parseColor("RED");
        empty.setBackgroundColor(emptycolor);
        full.setBackgroundColor(fullcolor);

        p1.setBackgroundColor(emptycolor);
        p2.setBackgroundColor(emptycolor);
        p3.setBackgroundColor(emptycolor);
        p4.setBackgroundColor(emptycolor);


        final int year=cal.get(Calendar.YEAR);
        final int month=cal.get(Calendar.MONTH);
        final int day=cal.get(Calendar.DAY_OF_MONTH);
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
                StringTokenizer st1=new StringTokenizer(d1,"/");
                int selday=Integer.parseInt(st1.nextToken());
                int selmonth=Integer.parseInt(st1.nextToken());
                int selyear=Integer.parseInt(st1.nextToken());
                if(selday>=curday){
                    Toast.makeText(ActivityBook.this, "selected", Toast.LENGTH_SHORT).show();
                    //txtdateview.setVisibility(0);
                    txtdateview.setVisibility(View.GONE);
                }
                else {
                    txtdateview.setVisibility(1);


                }
                if(selday<=(curday+3)) {
                    Toast.makeText(ActivityBook.this, "selected", Toast.LENGTH_SHORT).show();
                    txtdateview1.setVisibility(View.GONE);
                }
                else {
                    txtdateview1.setVisibility(1);

                }

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
}