package com.example.note;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddTask extends AppCompatActivity {
    private EditText note, editTextTime, editTextDate;
    private ExtendedFloatingActionButton save;
    //private DbHelper dbHelper;
    private ArrayList<Tasks> TaskArrayList;
    String timeTonotify;
    DbHelper dbHelper = new DbHelper(this);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
        note = findViewById(R.id.note);
        save = findViewById(R.id.save);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);

        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(AddTask.this, (view, hourOfDay1, minute1) -> {
                    timeTonotify = hourOfDay1 + ":" + minute1;
                    editTextTime.setText(FormatTime(hourOfDay1, minute1));
                    }, hourOfDay, minute, true);
                timePicker.setTitle("Saat Seçiniz");
                timePicker.setButton(DialogInterface.BUTTON_POSITIVE, "ayarla", timePicker);
                timePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "iptal", timePicker);
                timePicker.show();

            }
        });
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker;

                datePicker = new DatePickerDialog(AddTask.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //editTextDate.setText(dayOfMonth+"/"+month+"/"+year);
                        editTextDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                }, year, month, dayOfMonth);
                datePicker.setButton(DialogInterface.BUTTON_POSITIVE, "ayarla", datePicker);
                datePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "iptal", datePicker);
                datePicker.show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kaydet();
            }


            private void kaydet() {
                if (note.getText().toString().trim().equals("")) {
                    Toast.makeText(AddTask.this, "Not Boş", Toast.LENGTH_LONG).show();
                } else {
                    String task = note.getText().toString().trim();
                    String dayOfMonth = editTextDate.getText().toString().trim();
                    String month = note.getText().toString().trim();
                    String year = note.getText().toString().trim();
                    String minute = note.getText().toString().trim();
                    String hourOfDay = editTextTime.getText().toString().trim();
                    new Tasksdao().taskAdd(dbHelper, task, dayOfMonth, month, year, minute, hourOfDay);//
                    TaskArrayList = new Tasksdao().getTasks(dbHelper);
                    Intent intent = new Intent(AddTask.this, MainActivity.class);
                    startActivity(intent);
                    setAlarm(task, dayOfMonth, hourOfDay);//veriler alarma gönderilir
                }
//
//


            }
        });


    }

    private void setAlarm(String task, String dayOfMonth, String hourOfDay) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getApplicationContext(), AlarmBrodcast.class);
        intent.putExtra("task", task);
        intent.putExtra("dayOfMonth", dayOfMonth);
        intent.putExtra("hourOfDay", hourOfDay);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String dateandtime = dayOfMonth + " " + timeTonotify;
        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
        try {
            Date date1 = formatter.parse(dateandtime);
            alarmManager.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        finish();

    }


    public String FormatTime(int hour, int minute) {
        String time;
        time = "";
        String formattedMinute;
        if (minute / 10 == 0) {
            formattedMinute = "0" + minute;
        } else {
            formattedMinute = "" + minute;
        }

        if (hour == 0) {
            time = "00" + ":" + formattedMinute;
        } else if (hour < 12) {
            time = hour + ":" + formattedMinute ;
        } else if (hour == 12) {
            time = "12" + ":" + formattedMinute ;
        } else {

            time = hour + ":" + formattedMinute ;
        }

        return time;
    }



}
