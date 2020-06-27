package com.example.jyoti.myproject.Reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jyoti.myproject.Bean_Class.EmailBean;
import com.example.jyoti.myproject.Bean_Class.ReminderBean;
import com.example.jyoti.myproject.Database.database;
import com.example.jyoti.myproject.Preference.ReminderPreference;
import com.example.jyoti.myproject.R;
import com.example.jyoti.myproject.Service.ScheduleEventService;

import java.text.ParseException;
import java.util.Calendar;

public class ShowEmail extends AppCompatActivity {
    EditText etto, etmessage, etsubject, etfrom;
    Button bsubmit;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Intent intent;
    int ReminderId;
    database db;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_email);
        etto = (EditText) findViewById(R.id.etto);
        etfrom = (EditText) findViewById(R.id.etfrom);
        etmessage = (EditText) findViewById(R.id.ettext);
        etsubject = (EditText) findViewById(R.id.etsub);
        bsubmit = (Button) findViewById(R.id.bAdd);
        db = new database(getApplicationContext());
        Log.e("Reminder from", "" + ReminderPreference.getAppUserName());
        etfrom.setText(ReminderPreference.getAppUserName());
        bundle = getIntent().getExtras();
//        Log.e("bundle values;",""+bundle.getString("title")+" "+bundle.getString("text")+" 0 "+ bundle.getInt("repeat")+" "+ bundle.getString("date")+" "+bundle.getString("time")+" "+bundle.getString("filename")+",1,0 ");
        final int reminder_ID= bundle.getInt("reminderId");
        final String bdate = bundle.getString("date");
        final String btime = bundle.getString("time");
        EmailBean email=db.getMailByID(Integer.toString(reminder_ID));
        etto.setText(email.getEmail_email());
        etmessage.setText(email.getMessage_email());
        etsubject.setText(email.getSubject_email());
        bsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((etto.getText().length() < 1) || (etsubject.getText().length() < 1) || (etmessage.getText().length() < 1)) {
                    Toast.makeText(ShowEmail.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    db.updateReminder(new ReminderBean(bundle.getString("title"), bundle.getString("text"), 1, bundle.getInt("repeat"), bundle.getString("date"), bundle.getString("time"), bundle.getString("filename"), 1, 0),Integer.toString(reminder_ID));
                    Cursor cursor = db.getLastReminderId();
                    if (cursor.moveToFirst()) {
                        ReminderId = Integer.parseInt(cursor.getString(0));
                    }
                    db.updateMail(new EmailBean(ReminderId, ReminderPreference.getAppUserName(), etto.getText().toString(), "no enquiry", "12321", etmessage.getText().toString(), etsubject.getText().toString()),Integer.toString(reminder_ID));
                    Toast.makeText(ShowEmail.this, "Saved Changes", Toast.LENGTH_SHORT).show();
                    try {
                        startAlert(bdate, btime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void startAlert(String bdate, String btime) throws ParseException {
        //convert the date into specific format
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String edate=bdate;
        String time=btime;
//        String dd=edate+" "+time;
//
//        Date date = sdf.parse(dd);
//        Log.e("date..  ",""+dd);
//        Log.e("click Date and time",""+date);
        // Get last inserted id
        Log.e("Reminder id",""+ReminderId);

        //Set the intent to sevice
        intent = new Intent(this, ScheduleEventService.class);
        pendingIntent = PendingIntent.getService(ShowEmail.this, ReminderId, intent, 0);
        String[] timeParts = time.split(":");
        String[] dateparts = edate.split("/");

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calNow = Calendar.getInstance();
        //Set the time as given by user
        Calendar calendar = (Calendar) calNow.clone();;


        calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(timeParts[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
        calendar.set(Calendar.SECOND,0);
        Log.e("calender1",""+calendar.getTimeInMillis());
        if(calendar.compareTo(calNow) <= 0){
            //Today Set time passed, count to tomorrow
            calendar.add(Calendar.DATE, 1);
            Log.e("Calender date ",""+calendar.get(Calendar.DATE));
        }


        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000*60*60*24 , pendingIntent);
        //   Toast.makeText(this, "Alarm at "+edate+ " " + time , Toast.LENGTH_SHORT).show();


  /*      calendar.set(Calendar.YEAR,Integer.parseInt(dateparts[2]));
          calendar.set(Calendar.MONTH,Integer.parseInt(dateparts[1]));
        calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dateparts[0]));
        calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(timeParts[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
        calendar.set(Calendar.SECOND,0);
        Log.e("calender",""+calendar.getTimeInMillis());
        //Log.e("Time date",""+date.getTime());
       Calendar c = Calendar.getInstance();
        c.setTimeInMillis(calendar.getTimeInMillis());;
*/      Log.e("System time",""+ System.currentTimeMillis());
    }

}