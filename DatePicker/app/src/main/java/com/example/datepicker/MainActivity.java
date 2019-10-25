package com.example.datepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tvw=(TextView)findViewById(R.id.textView);
       final DatePicker  picker=(DatePicker)findViewById(R.id.datepiker);
       Button btnGet=(Button)findViewById(R.id.button);
        final TimePicker time=(TimePicker) findViewById(R.id.timepicker);
        time.setIs24HourView(true);

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  tvw.setText("Selected Date: "+picker.getDayOfMonth()+"/"+ (picker.getMonth())+"/"+picker.getYear());

                int hour, minute;
                String am_pm;
                if (Build.VERSION.SDK_INT >= 23) {
                    hour = time.getHour();
                    minute = time.getMinute();
                } else {
                    hour = time.getCurrentHour();
                    minute = time.getCurrentMinute();
                }
                if (hour > 12) {
                    am_pm = "PM";
                    hour = hour - 12;
                } else {
                    am_pm = "AM";
                }

                tvw.setText("Selected Date: " + hour + ":" + minute + " " + am_pm);
            }

        });

        EditText edit =(EditText) findViewById(R.id.editText3);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                 DatePickerDialog datedialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                     @Override
                     public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tvw.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year,month,day);
                datedialog.show();
            }
        });

        final EditText edittime = (EditText) findViewById(R.id.editText4);

        //final TimePicker time = (TimePicker) findViewById(R.id.timepicker);


        edittime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
             TimePickerDialog   time1= new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                tvw.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                time1.show();
            }
        });
    }
    }

