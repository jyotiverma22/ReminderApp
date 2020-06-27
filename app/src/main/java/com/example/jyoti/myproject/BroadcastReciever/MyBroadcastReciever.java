package com.example.jyoti.myproject.BroadcastReciever;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.example.jyoti.myproject.API_classes.APIService;
import com.example.jyoti.myproject.API_classes.ApiClient;
import com.example.jyoti.myproject.API_classes.MailClass;
import com.example.jyoti.myproject.Bean_Class.EmailBean;
import com.example.jyoti.myproject.Database.database;
import com.example.jyoti.myproject.MainActivity;
import com.example.jyoti.myproject.Notification.NotificationClass;
import com.example.jyoti.myproject.Service.ScheduleEventService;
import com.example.jyoti.myproject.networkConnection.NetworkConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jyoti on 7/11/2017.
 */

public class MyBroadcastReciever extends BroadcastReceiver {
database db;
    @Override
    public void onReceive(final Context context, Intent intent) {
        db=new database(context);
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent service = new Intent(context, ScheduleEventService.class);
            context.startService(service);
        }
        else   // Intent service = new Intent(context, ScheduleEventService.class);
          //  context.startService(service);
            if (NetworkConnection.isNetworkAvailable(context)) {
                Intent intent2 = new Intent(context, MainActivity.class);
                final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);


                final Cursor cursor=db.getEmailInternet();
         //       EmailBean emailBean=null;
                if(cursor.moveToFirst())
                {
                    do{
                       final EmailBean emailBean=new EmailBean(cursor.getShort(0),cursor.getShort(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7));
                        APIService service2 = ApiClient.getClient().create(APIService.class);

                        Call<MailClass> userCall = service2.getEmailDetails(emailBean.getType_email(),emailBean.getEmail_email(),emailBean.getName_email(), emailBean.getSubject_email(), emailBean.getMessage_email());
                        userCall.enqueue(new Callback<MailClass>() {
                            @Override
                            public void onResponse(Call<MailClass> call, Response<MailClass> response) {

                                if (response.isSuccessful()) {
                                    if (response.body().getStatus() == true) {
                                        Log.e("mail sent",""+emailBean.getEmail_id());
                                        db.deleteEmailInternet(emailBean.getEmail_id());

                                        NotificationClass.EmailNotification(context,"to "+emailBean.getEmail_email(),emailBean.getMessage_email(),0,pendingIntent);
//                                        Toast.makeText(context, "Email sent", Toast.LENGTH_SHORT).show();
                                        //
                                         } else {
                                        Toast.makeText(context, "Error in mail sending", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            @Override
                            public void onFailure(Call<MailClass> call, Throwable t) {
                                //hidinintreepDialog();

                                Log.d("onFailure", t.toString());
                            }
                        });
                    }while(cursor.moveToNext());

                }
            }
        else  {
                Intent service = new Intent(context, ScheduleEventService.class);
                  context.startService(service);
        }

    }
}
