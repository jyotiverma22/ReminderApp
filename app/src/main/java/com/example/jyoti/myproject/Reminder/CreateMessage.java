package com.example.jyoti.myproject.Reminder;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jyoti.myproject.Bean_Class.ReminderBean;
import com.example.jyoti.myproject.Bean_Class.SMSBean;
import com.example.jyoti.myproject.Contacts.SelectContact;
import com.example.jyoti.myproject.Database.database;
import com.example.jyoti.myproject.MainActivity;
import com.example.jyoti.myproject.Preference.ReminderPreference;
import com.example.jyoti.myproject.R;
import com.example.jyoti.myproject.Service.ScheduleEventService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateMessage extends AppCompatActivity {
    Spinner spsim;
    EditText etto,etmessage;
    TextView tvcount;
    Button bsubmit,selectcontactbutton;
    String contacts;
    database db;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Intent intent;
    int sim;
    int ReminderId=0;
    int msg,msglength;
    String bdate,btime;

    final static int MY_REQUEST_CODE = 100;
    SharedPreferences prefslot;
    SharedPreferences.Editor editor;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);

        spsim=(Spinner)findViewById(R.id.spsim);
        etto=(EditText)findViewById(R.id.etto);
        selectcontactbutton=(Button)findViewById(R.id.button);
        etmessage=(EditText) findViewById(R.id.ettext);
        tvcount=(TextView)findViewById(R.id.tvcount);
        bsubmit=(Button)findViewById(R.id.bAdd);
         contacts = getIntent().getStringExtra("contacts");
  //      etto.setText(""+contacts.toString().replace("[", "").replace("]", ""));
            db=new database(getApplicationContext());
       List<String> phone=getSIMInfo(getApplicationContext());
     //   String[] phone=new String[]{"SIM 1","SIM 2"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, phone);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spsim.setAdapter(adapter);
        final String[] countlength=tvcount.getText().toString().split("/");
          msg=Integer.parseInt(countlength[0]);
         msglength=Integer.parseInt(countlength[1]);
        Log.e("preferbece:",""+ReminderPreference.getReminderPreferenceTitle()+" "+ReminderPreference.getReminderPreferenceText()+" "+ ReminderPreference.getReminderPreferenceRepeat()+" "+ReminderPreference.getReminderPreferenceDate()+" "+ReminderPreference.getReminderPreferenceTime()+" "+ReminderPreference.getReminderPreferenceFilename());


//        prefslot=getApplicationContext().getSharedPreferences("prefslot",MODE_PRIVATE);
//        editor=prefslot.edit();
     //   getSIMInfo(getApplicationContext());
        selectcontactbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ActivityCompat.checkSelfPermission(CreateMessage.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(CreateMessage.this, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, MY_REQUEST_CODE);

                } else {

                    Intent intent = new Intent(CreateMessage.this, SelectContact.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        });

        etmessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    msglength=msglength-1;
                if(msglength==0)
                {
                    msg=msg+1;
                    msglength=160;

                }
                tvcount.setText(""+msg+"/"+msglength);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        spsim.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Get sim slot
            if ((ActivityCompat.checkSelfPermission(CreateMessage.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(CreateMessage.this, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_CONTACTS}, MY_REQUEST_CODE);
            }
            else{
                    sim = position;
                }      }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });



        bsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etto.getText().toString().length()<1)
                {
                    Toast.makeText(CreateMessage.this, "Please Enter Sender Number", Toast.LENGTH_SHORT).show();
                }
                else if(etmessage.getText().toString().length()<1)
                {
                    Toast.makeText(CreateMessage.this, "Please fill message field", Toast.LENGTH_SHORT).show();
                }
                else                if ((ActivityCompat.checkSelfPermission(CreateMessage.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(CreateMessage.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.WRITE_CONTACTS}, MY_REQUEST_CODE);

                }
                else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CreateMessage.this, R.style.MyDialogTheme);
                    final LinearLayout layout = new LinearLayout(CreateMessage.this);
                    layout.setOrientation(LinearLayout.VERTICAL);

                    final EditText tvpin = new EditText(CreateMessage.this);
                    tvpin.setHint("Secret Key");
                    tvpin.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    tvpin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    layout.addView(tvpin);
                    dialog.setView(layout);

                    dialog.setTitle("Enter Secret Key").setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Do nothing here because we override this button later to change the close behaviour.
                            //However, we still need this because on older versions of Android unless we
                            //pass a handler the button doesn't get instantiated
                        }
                    });

                    final AlertDialog dialog2 = dialog.create();
                    dialog2.show();

                    //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
                    dialog2.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (tvpin.getText().length() < 1) {
                                Toast.makeText(CreateMessage.this, "Enter Secret key", Toast.LENGTH_SHORT).show();
                                tvpin.requestFocus();

                            } else if (tvpin.getText().toString().equals(Integer.toString(ReminderPreference.getAppUserPin())))
                            {
                                db.createReminder(new ReminderBean(ReminderPreference.getReminderPreferenceTitle(),ReminderPreference.getReminderPreferenceText(),0, ReminderPreference.getReminderPreferenceRepeat(),ReminderPreference.getReminderPreferenceDate(),ReminderPreference.getReminderPreferenceTime(), ReminderPreference.getReminderPreferenceFilename(), 1, 0));
                                Cursor cursor=db.getLastReminderId();
                                if(cursor.moveToFirst())
                                {
                                    ReminderId=Integer.parseInt(cursor.getString(0));
                                }

                                db.createMessage(new SMSBean(ReminderId,etto.getText().toString(),sim,etmessage.getText().toString()));

                                Toast.makeText(CreateMessage.this, "Message Reminder Added", Toast.LENGTH_SHORT).show();
                                try {
                                    startAlert(ReminderPreference.getReminderPreferenceDate(),ReminderPreference.getReminderPreferenceTime());
                                } catch (ParseException e) {
                                    Log.e("Exception at snotify", "" + e);
                                }
                                Intent intent=new Intent(CreateMessage.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();


                            } else {

                                Toast.makeText(CreateMessage.this, "Secret key not correct", Toast.LENGTH_SHORT).show();
                                tvpin.requestFocus();
                                tvpin.setText("");


                            }

                        }
                    });
     }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //get contacts from select content activity and put them into edittext
        contacts = getIntent().getStringExtra("contacts");
        etto.setText(""+contacts);

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
        pendingIntent = PendingIntent.getService(CreateMessage.this, ReminderId, intent, 0);
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


        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),1000*60*60*24 , pendingIntent);
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



    public  List<String> getSIMInfo(Context context) {
        List<String> simInfoList = new ArrayList<>();
        Uri URI_TELEPHONY = Uri.parse("content://telephony/siminfo/");
        Cursor c = context.getContentResolver().query(URI_TELEPHONY, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                Log.e("0",""+c.getString(0));
                Log.e("1",""+c.getString(1));

                Log.e("2",""+c.getString(2));//sim slot
//                editor.putInt("sim"+c.getString(2),Integer.parseInt(c.getString(2)));
//                editor.commit();
                Log.e("3",""+c.getString(3));//diaplay name
                Log.e("4",""+c.getString(4));
                Log.e("5",""+c.getString(5));
                Log.e("6",""+c.getString(6));
            //    SimInfo simInfo = new SimInfo(c.getString(3),  Integer.parseInt(c.getString(2)));

                /*
                int id = c.getInt(c.getColumnIndex("_id"));
                int slot = c.getInt(c.getColumnIndex("slot"));
                String display_name = c.getString(c.getColumnIndex("display_name"));
                String icc_id = c.getString(c.getColumnIndex("icc_id"));
                Log.d("apipas_sim_info",display_name );
                */
                simInfoList.add(c.getString(3));
            } while (c.moveToNext());
        }

        c.close();

        return simInfoList;
    }


}
