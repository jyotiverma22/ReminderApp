package com.example.jyoti.myproject.Reminder;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.jyoti.myproject.Bean_Class.ReminderBean;
import com.example.jyoti.myproject.Database.database;
import com.example.jyoti.myproject.R;
import com.example.jyoti.myproject.Service.ScheduleEventService;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class ShowReminder extends AppCompatActivity {
    Spinner repeat;
    TextView remind;
    int Remind,Repeat;
    Button bmusic,badd;
    EditText ettime, etdate, ettone,ettile,ettext;
    TimePickerDialog mTimePicker;
    DatePickerDialog mDatePicker;
    ToggleButton bplay;
    final static int MY_REQUEST_CODE = 100;
    Uri audioFileUri;
    String MP3Path;
    MediaPlayer mp;
    database db;
    Intent intent;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    String reminder_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reminder);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ettime = (EditText) findViewById(R.id.ettime);
        repeat = (Spinner) findViewById(R.id.spinner);
        remind = (TextView) findViewById(R.id.spinner2);
        etdate = (EditText) findViewById(R.id.etdate);
        ettone = (EditText) findViewById(R.id.ettone);
        ettile = (EditText) findViewById(R.id.ettitle);
        ettext = (EditText) findViewById(R.id.ettext);
        bmusic = (Button) findViewById(R.id.bmusic);
        badd= (Button) findViewById(R.id.bAdd);
        bplay= (ToggleButton) findViewById(R.id.bplay);
        db=new database(getApplicationContext());

        //Get REminder Id From previous activity
        reminder_id=getIntent().getStringExtra("Rid");
        Log.e("reminder id",""+reminder_id);

        ReminderBean reminderBean=db.getReminderDetailsById(reminder_id);

        ettile.setText(""+reminderBean.getTitle());
        ettext.setText(""+reminderBean.getText());
        ettime.setText(""+reminderBean.getTime());
        etdate.setText(""+reminderBean.getDate());
        ettone.setText(""+reminderBean.getTone());
        int reminderType=reminderBean.getType();
        if (reminderType==0)
        {
            remind.setText("SMS Reminder");

        }
        else if(reminderType==1)
        {
            remind.setText("Email Reminder");

        }
        else {
            remind.setText("Simple Reminder");

        }

        List<String> repeatType = db.getRepeatType();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, repeatType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeat.setAdapter(adapter);
        ettile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    if(ettile.getText().toString().equals(""))
                    {
                        Toast.makeText(ShowReminder.this, "Please Enter Title", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        ettime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager im = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);

                im.hideSoftInputFromWindow(ettime.getWindowToken(), 0);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                mTimePicker = new TimePickerDialog(ShowReminder.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        ettime.setText("" + selectedHour + ":" + selectedMinute);
                        ettime.clearFocus();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                mDatePicker = new DatePickerDialog(ShowReminder.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        etdate.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                        etdate.clearFocus();
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();

            }
        });

        bmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ActivityCompat.checkSelfPermission(ShowReminder.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(ShowReminder.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(ShowReminder.this , new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, MY_REQUEST_CODE);

                } else {

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("audio/mpeg");
                    startActivityForResult(Intent.createChooser(intent, getString(R.string.select_audio_file_title)), 001);
                }
            }
        });


        bplay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try
                {
                    if (isChecked) {
                        bplay.setBackgroundResource(R.drawable.pause);

                        //play the default tone
                        if (ettone.getText().toString().equals("Default.mp3")) {
                            AssetFileDescriptor afd = getAssets().openFd("alrm.mp3");
                            mp = new MediaPlayer();
                            mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                            mp.prepare();
                            mp.start();}
                        else
                        {
                            // play music from file
                            mp = new MediaPlayer();
                            mp.setDataSource(ettone.getText().toString());
                            mp.prepare();
                            mp.start();

                        }
                    }
                    else {
                        bplay.setBackgroundResource(R.drawable.play);
                        mp.stop();
                    }
                }
                catch (Exception e)
                {
                    Log.e("Exception play: ",""+e);
                }
            }
        });

        repeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Get repeat type
                Repeat=position;
                Log.e("Repeat type1",""+Repeat);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename=null;
                try {
                    if(!ettone.getText().toString().equals("Default.mp3"))
                    {  filename= savefile(MP3Path);}
                    else
                    {
                        filename="Default.mp3";
                    }
                } catch (IOException e) {
                    Log.e("Exception at b add",""+e);
                }


                if((ettile.getText().toString().length()<1)||(etdate.getText().toString().length()<1)||(ettime.getText().toString().length()<1))
                {
                    Toast.makeText(ShowReminder.this, "Please Fill all fields", Toast.LENGTH_SHORT).show();
                }
                else if(ettext.getText().toString().length()<1)
                {
                    Toast.makeText(ShowReminder.this, "Please Fill Description about Reminder", Toast.LENGTH_SHORT).show();
                }

                else {
                    if (remind.getText().toString().equals("Simple Reminder")) {
                        db.updateReminder(new ReminderBean(ettile.getText().toString(), ettext.getText().toString(), Remind, Repeat, etdate.getText().toString(), ettime.getText().toString(), filename, 1, 0),reminder_id);
                        Toast.makeText(ShowReminder.this, "Changes Saved", Toast.LENGTH_SHORT).show();
                        try {
                            startAlert();
                        } catch (ParseException e) {
                            Log.e("Exception at snotify", "" + e);
                        }
                    } else if (remind.getText().toString().equals("Email Reminder")) {
                        db.updateReminder(new ReminderBean(ettile.getText().toString(), ettext.getText().toString(), Remind, Repeat, etdate.getText().toString(), ettime.getText().toString(), filename, 1, 0),reminder_id);
                        Toast.makeText(ShowReminder.this, "Changes Saved", Toast.LENGTH_SHORT).show();
                        try {
                            startAlert();
                        } catch (ParseException e) {
                            Log.e("Exception at snotify", "" + e);
                        }
                        /*Bundle bundle=new Bundle();
                        bundle.putString("title",ettile.getText().toString());
                        bundle.putString("text",ettext.getText().toString());
                        bundle.putInt("repeat",Repeat);
                        bundle.putString("date",etdate.getText().toString());
                        bundle.putString("time",ettime.getText().toString());
                        bundle.putString("filename",filename);
                        bundle.putInt("reminderId",Integer.parseInt(reminder_id));
                        Intent intent = new Intent(ShowReminder.this, ShowEmail.class);
                        intent.putExtras(bundle);
                        startActivity(intent);*/
                    } else if(remind.getText().toString().equals("SMS Reminder")){
                        db.updateReminder(new ReminderBean(ettile.getText().toString(), ettext.getText().toString(), Remind, Repeat, etdate.getText().toString(), ettime.getText().toString(), filename, 1, 0),reminder_id);
                        Toast.makeText(ShowReminder.this, "Changes Saved", Toast.LENGTH_SHORT).show();
                        try {
                            startAlert();
                        } catch (ParseException e) {
                            Log.e("Exception at snotify", "" + e);
                        }
                        /*ReminderPreference.setReminderPreference(getApplicationContext(),ettile.getText().toString(),ettext.getText().toString(),etdate.getText().toString(),ettime.getText().toString(),filename,Repeat);
                        Bundle bundle=new Bundle();
                        bundle.putInt("reminderId",Integer.parseInt(reminder_id));
                        Log.e("Repeat",""+Repeat);
                        Log.e("re",""+ReminderPreference.getReminderPreferenceRepeat());
                        Intent intent = new Intent(ShowReminder.this, ShowMessage.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
*/
                    }
                }
            }
        });


    }


    public void startAlert() throws ParseException {
        //convert the date into specific format
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String edate=etdate.getText().toString();
        String time=ettime.getText().toString();
//        String dd=edate+" "+time;
//
//        Date date = sdf.parse(dd);
//        Log.e("date..  ",""+dd);
//        Log.e("click Date and time",""+date);
        int ReminderId=0;
        // Get last inserted id
        Cursor cursor=db.getLastReminderId();
        if(cursor.moveToFirst())
        {
            ReminderId=Integer.parseInt(cursor.getString(0));
        }
        Log.e("Reminder id",""+ReminderId);

        //Set the intent to sevice
        intent = new Intent(this, ScheduleEventService.class);
        String[] timeParts = time.split(":");
        pendingIntent = PendingIntent.getService(ShowReminder.this, ReminderId, intent, 0);
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


        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
      //  Toast.makeText(this, "Alarm at "+edate+ " " + time , Toast.LENGTH_SHORT).show();


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 001 && resultCode == RESULT_OK) {
            if ((data != null) && (data.getData() != null)) {
                audioFileUri = data.getData();
                Cursor cursor = null;
                try {
                    String[] proj = { MediaStore.Audio.Media.DATA};
                    cursor = getApplicationContext().getContentResolver().query(audioFileUri,  proj, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                    cursor.moveToFirst();
                    MP3Path= cursor.getString(column_index);

                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
                Log.d("mymp3",""+audioFileUri.getLastPathSegment());
              /*  String name[]=new String[]{MP3Path.split(".")};
               if(!name[1].equals("mp3"))
                {
                    Toast.makeText(this, "Invalid file type", Toast.LENGTH_SHORT).show();
                }*/
//                MP3Path = audioFileUri.getPath();
                ettone.setText("" + MP3Path);
                // Now you can use that Uri to get the file path, or upload it, ...
            }
        }


    }
    private String savefile(String path) throws IOException {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Reminder/ringtones");
        if(!myDir.exists())
            myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "MyMusic-"+ n +".mp3";
        File file = new File (myDir, fname);
        String destination=file.getPath();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        Log.e("file path",""+destination);
        try{

            bis = new BufferedInputStream(new FileInputStream(path));
            bos = new BufferedOutputStream(new FileOutputStream(destination, false));
            byte[] buf = new byte[1024];
            bis.read(buf);
            do {
                bos.write(buf);
            } while(bis.read(buf) != -1);

        }

        catch (Exception e)
        {Log.e("Exception 1 ",""+e);}

        finally {
            try{
                Toast.makeText(this, fname+" saved at "+destination, Toast.LENGTH_SHORT).show();
                if (bis != null) bis.close();
                if (bos != null) bos.close();
            }
            catch (Exception e)
            {Log.e("Exception 2 ",""+e);}
        }

        return destination;
    }



}
