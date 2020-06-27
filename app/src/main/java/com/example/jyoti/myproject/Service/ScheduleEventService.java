package com.example.jyoti.myproject.Service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.example.jyoti.myproject.API_classes.APIService;
import com.example.jyoti.myproject.API_classes.ApiClient;
import com.example.jyoti.myproject.API_classes.MailClass;
import com.example.jyoti.myproject.Bean_Class.EmailBean;
import com.example.jyoti.myproject.Bean_Class.SMSBean;
import com.example.jyoti.myproject.Database.database;
import com.example.jyoti.myproject.MainActivity;
import com.example.jyoti.myproject.Notification.NotificationClass;
import com.example.jyoti.myproject.SimDetails.SimUtil;
import com.example.jyoti.myproject.networkConnection.NetworkConnection;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jyoti on 7/11/2017.
 */

public class ScheduleEventService extends Service {
    database db;
    MediaPlayer mp;
    Calendar c;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
         super.onStartCommand(intent, flags, startId);
        db=new database(ScheduleEventService.this);
        Calendar calendar = Calendar.getInstance();

        Calendar c = Calendar.getInstance();
        Log.e("Service time",""+calendar.getTimeInMillis());
      //  c.setTimeInMillis(calendar.getTimeInMillis());;
        int y=c.get(Calendar.YEAR);
        int M=c.get(Calendar.MONTH)+1;
        int d=c.get(Calendar.DAY_OF_MONTH);
        int  h = c.get(Calendar.HOUR_OF_DAY);
        int  m = c.get(Calendar.MINUTE);
        String date=Integer.toString(d)+"/"+Integer.toString(M)+"/"+Integer.toString(y);
        String time=Integer.toString(h)+":"+Integer.toString(m);
        String t=Integer.toString(y)+"/"+Integer.toString(M)+"/"+Integer.toString(d)+" "+Integer.toString(h)+":"+Integer.toString(m);
        Log.e("System time",""+t);

        Cursor cursor=db.getSimpleReminderOnTime(time);
        if(cursor.moveToFirst()) {
            do {
                String Reminder_date=cursor.getString(5);
                if (cursor.getString(4).equals("0"))
                {
                    if(Reminder_date.equals(date))
                    {hitAlarm(cursor.getString(1), cursor.getString(2), cursor.getString(9),Integer.parseInt(cursor.getString(0)),0);}
                } else if (cursor.getString(4).equals("1"))
                {
                    hitAlarm(cursor.getString(1), cursor.getString(2), cursor.getString(9),Integer.parseInt(cursor.getString(0)),0);
                } else if (cursor.getString(4).equals("2"))
                {
                    String[] new_date=Reminder_date.split("/");
                    if(new_date[0].equals(Integer.toString(d)))
                    {
                        hitAlarm(cursor.getString(1), cursor.getString(2), cursor.getString(9),Integer.parseInt(cursor.getString(0)),0);
                    }
                } else if (cursor.getString(4).equals("3"))
                {
                    String[] new_date=Reminder_date.split("/");
                    if((new_date[0].equals(Integer.toString(d)))&&(new_date[1].equals(Integer.toString(M))))
                    {
                        hitAlarm(cursor.getString(1), cursor.getString(2), cursor.getString(9),Integer.parseInt(cursor.getString(0)),0);
                    }
                }

            } while (cursor.moveToNext());
        }

        Cursor cursormsg=db.getSMSReminderOnTime(time);
        if(cursormsg.moveToFirst())
        {
            do {
                SMSBean sms=db.getMessageOnTime(Integer.parseInt(cursormsg.getString(0)));
                Log.e("Meassge hit",""+sms.getRem_id());
                String Reminder_date=cursormsg.getString(5);
                Log.e("cursor",""+cursormsg.getString(4));
                if (cursormsg.getString(4).equals("0")) {
                    if(Reminder_date.equals(date))
                    {

                        Log.e("Meassge hit2",""+sms.getReciever());

                        hitAlarm(cursormsg.getString(1), cursormsg.getString(2), cursormsg.getString(9),Integer.parseInt(cursormsg.getString(0)),1);
                        hitMessage(sms.getReciever(),sms.getSender(),sms.getMessage());
                    }
                    Log.e("Service", "" + cursormsg.getString(0));
                    Log.e("Service", "" + cursormsg.getString(9));
                    Log.e("reminder", "" + cursormsg.getString(3));
                    Log.e("reminder", "" + cursormsg.getString(4));
                } else if (cursormsg.getString(4).equals("1")) {
                    hitAlarm(cursormsg.getString(1), cursormsg.getString(2), cursormsg.getString(9),Integer.parseInt(cursormsg.getString(0)),1);
                    hitMessage(sms.getReciever(),sms.getSender(),sms.getMessage());

                } else if (cursormsg.getString(4).equals("2")) {
                    String[] new_date=Reminder_date.split("/");
                    if(new_date[0].equals(Integer.toString(d)))
                    {
                        hitAlarm(cursormsg.getString(1), cursormsg.getString(2), cursormsg.getString(9),Integer.parseInt(cursormsg.getString(0)),1);
                        hitMessage(sms.getReciever(),sms.getSender(),sms.getMessage());

                    }
                } else if (cursormsg.getString(4).equals("3")) {
                    String[] new_date=Reminder_date.split("/");
                    if((new_date[0].equals(Integer.toString(d)))&&(new_date[1].equals(Integer.toString(M))))
                    {
                        hitAlarm(cursormsg.getString(1), cursormsg.getString(2), cursormsg.getString(9),Integer.parseInt(cursormsg.getString(0)),1);
                        hitMessage(sms.getReciever(),sms.getSender(),sms.getMessage());

                    }
                }

            } while (cursormsg.moveToNext());
        }

        Cursor cursormail=db.getEmailReminderOnTime(time);

        if(cursormail.moveToFirst())
        {
            do{

                EmailBean email=db.getEmailOnTime(Integer.parseInt(cursormail.getString(0)));
                Log.e("Email hit",""+email);
//                hitMessage(sms.getReciever(),sms.getSender(),sms.getMessage());

                String Reminder_date=cursormail.getString(5);
                Log.e("1234",""+Reminder_date);
                Log.e("1234",""+date);
                if (cursormail.getString(4).equals("0")) {
                    if(Reminder_date.equals(date))
                    {
                        hitAlarm(cursormail.getString(1), cursormail.getString(2), cursormail.getString(9),Integer.parseInt(cursormail.getString(0)),2);
                        hitEmail(cursormail.getShort(1),email.getEmail_email(),email.getMessage_email(),email.getType_email(),email.getName_email(),email.getNumber_email(),email.getSubject_email());

                    }
                    }
                    else if (cursormail.getString(4).equals("1"))
                    {
                        hitAlarm(cursormail.getString(1), cursormail.getString(2), cursormail.getString(9),Integer.parseInt(cursormail.getString(0)),2);
                        hitEmail(cursormail.getShort(0),email.getEmail_email(),email.getMessage_email(),email.getType_email(),email.getName_email(),email.getNumber_email(),email.getSubject_email());

                    } else if (cursormsg.getString(4).equals("2")) {
                    String[] new_date=Reminder_date.split("/");
                    if(new_date[0].equals(Integer.toString(d)))
                    {
                        hitAlarm(cursormail.getString(1), cursormail.getString(2), cursormail.getString(9),Integer.parseInt(cursormail.getString(0)),2);
                        hitEmail(cursormail.getShort(0),email.getEmail_email(),email.getMessage_email(),email.getType_email(),email.getName_email(),email.getNumber_email(),email.getSubject_email());

                                            }
                } else if (cursormail.getString(4).equals("3")) {
                    String[] new_date=Reminder_date.split("/");
                    if((new_date[0].equals(Integer.toString(d)))&&(new_date[1].equals(Integer.toString(M))))
                    {
                        hitAlarm(cursormail.getString(1), cursormail.getString(2), cursormail.getString(9),Integer.parseInt(cursor.getString(0)),2);
                        hitEmail(cursormail.getShort(0),email.getEmail_email(),email.getMessage_email(),email.getType_email(),email.getName_email(),email.getNumber_email(),email.getSubject_email());
                    }
                }


            }while (cursormail.moveToNext());
        }
    return START_STICKY;

    }



    public  void hitAlarm(String title,String text,String tone,int id,int type)  {
        try {
            Log.e("Alarm","");
            NotificationCompat.Builder mBuilder;
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
           // Toast.makeText(this, title + "\n" + text, Toast.LENGTH_SHORT).show();
            if(type==0) {
                NotificationClass.ReminderNotification(this, title, text, id,pendingIntent);
            }
            else if(type==1)
            {
               NotificationClass.SMSNotification(this, title, text,id, pendingIntent);
                Log.e("msg sent ",""+title);
            }
            else
            {

//                Log.e("email hit","12 34");
  //              mBuilder = NotificationClass.EmailNotification(this, title, text, pendingIntent);
            }

//            NotificationManager mNotificationManager =
  //                  (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//When you issue multiple notifications about the same type of event, it’s best practice for your app to try to update an existing notification with this new information, rather than immediately creating a new notification. If you want to update this notification at a later date, you need to assign it an ID. You can then use this ID whenever you issue a subsequent notification. If the previous notification is still visible, the system will update this existing notification, rather than create a new one. In this example, the notification’s ID is 001//

//                    NotificationManager.notify().0

         //   mNotificationManager.notify(id, mBuilder.build());

            if (tone.equals("Default.mp3")) {
                AssetFileDescriptor afd = getAssets().openFd("alrm.mp3");
                mp = new MediaPlayer();
                mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mp.prepare();
                mp.start();
            } else {
                mp = new MediaPlayer();
                mp.setDataSource(tone);
                mp.prepare();
                mp.start();
            }
        }
        catch (Exception e)
        {
            Log.e("Exception hit alarm:",""+e);
        }
    }

    public void hitMessage(String reciever,int sim,String message)
    {
        ArrayList<String> messageList=new ArrayList<>();
        if(message.length()>160)
        {
            Log.e("msg length",""+message.length());
           messageList = SmsManager.getDefault().divideMessage(message);
        }

        String[] rec=reciever.split(",");
        for(int i=0;i<rec.length;i++)
        {
            Log.e("multiple reciever",""+rec.length);

            if (messageList.size() > 1) {
           // SimUtil.sendMultipartTextSMS(this, sim, "00972XXXXXXXXX", null, messageList, null, null);
            SimUtil.sendMultipartTextSMS(this,sim,rec[i],null,messageList,null,null);
            }
            else {
                Log.e("msg sent",""+sim);
            SimUtil.sendSMS(this,sim,rec[i],null,message,null,null);
             }
        }
    }


    public void hitEmail(int rem_id, String email, String msg, String type, final String name, String number, String subject) {

        if (NetworkConnection.isNetworkAvailable(this)) {
            APIService service = ApiClient.getClient().create(APIService.class);
            Call<MailClass> userCall = service.getEmailDetails(type, email, name, subject, msg);
            userCall.enqueue(new Callback<MailClass>() {
                @Override
                public void onResponse(Call<MailClass> call, Response<MailClass> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == true) {
                            Intent intent = new Intent(ScheduleEventService.this, MainActivity.class);
                            PendingIntent pendingIntent = PendingIntent.getActivity(ScheduleEventService.this, 0, intent, 0);
                            Log.e("msg creates 1","12434");
                            NotificationClass.EmailNotification(ScheduleEventService.this," ",name,0,pendingIntent);

//                            Toast.makeText(ScheduleEventService.this, "Email sent", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(ScheduleEventService.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<MailClass> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });
        }
        else{
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            Log.e("msg creates","12434");
            NotificationClass.NoIternetNotification(this,pendingIntent);
            db.createEmailInternet(new EmailBean(rem_id,name,email,type,number,msg,subject));
        }
    }


}
