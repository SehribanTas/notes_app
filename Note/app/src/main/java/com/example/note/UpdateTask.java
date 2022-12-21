package com.example.note;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UpdateTask extends AppCompatActivity {
    EditText noteupdate,time,date;
    private Button update_save;
    //private DbHelper dbHelper;
    private ArrayList<Tasks> TaskArrayList;
    private Adapter adapter;
    DbHelper dbHelper = new DbHelper(this);
    private Tasks task;
    String timeTonotify;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_task);
        noteupdate=findViewById(R.id.noteupdate);
        time=findViewById(R.id.time);
        date=findViewById(R.id.date);
        update_save=findViewById(R.id.update_save);

        Bundle gelenVeri=getIntent().getExtras();// intentten gelen verilerin alınması ve belirlenen alanalara set edilmesi
        String gelenyazi=gelenVeri.getString("task");
        noteupdate.setText(gelenyazi);
        int gelen_id=gelenVeri.getInt("tas_id");

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int hourOfDay=calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePicker;
                timePicker =new TimePickerDialog(UpdateTask.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay+":"+minute);
                        timeTonotify = hourOfDay + ":" + minute;
                        time.setText(FormatTime(hourOfDay, minute));

                    }
                },hourOfDay,minute,true);
                timePicker.setTitle("Saat Seçiniz");
                timePicker.setButton(DialogInterface.BUTTON_POSITIVE,"ayarla",timePicker);
                timePicker.setButton(DialogInterface.BUTTON_NEGATIVE,"iptal",timePicker);
                timePicker.show();

            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker;

                datePicker = new DatePickerDialog(UpdateTask.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                       // date.setText(dayOfMonth+"/"+month+"/"+year);
                        date.setText(dayOfMonth + "-" + (month + 1) + "-" + year);


                    }
                },year,month,dayOfMonth);
                datePicker.setButton(DialogInterface.BUTTON_POSITIVE,"ayarla",datePicker);
                datePicker.setButton(DialogInterface.BUTTON_NEGATIVE,"iptal",datePicker);
                datePicker.show();
            }
        });

        update_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              guncelle();
            }

            private void guncelle() {
                int task_id=gelenVeri.getInt("task_id");
                String task = noteupdate.getText().toString().trim();
                String dayOfMonth = date.getText().toString().trim();
                //String month = note.getText().toString().trim();
                //String year = note.getText().toString().trim();
                //String minute = note.getText().toString().trim();
                String hourOfDay = time.getText().toString().trim();

                new Tasksdao().updateTask(dbHelper,task_id,task,dayOfMonth,hourOfDay);//
                TaskArrayList= new Tasksdao().getTasks(dbHelper);
                Intent intent = new Intent(UpdateTask.this,MainActivity.class);
                startActivity(intent);
                setAlarm(task, dayOfMonth, hourOfDay);

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
        });

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
            time = "12" + ":" + formattedMinute + " AM";
        } else if (hour < 12) {
            time = hour + ":" + formattedMinute + " AM";
        } else if (hour == 12) {
            time = "12" + ":" + formattedMinute + " PM";
        } else {
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
        }


        return time;
    }
}
