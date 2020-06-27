package com.example.jyoti.myproject.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.example.jyoti.myproject.R;

/**
 * Created by Jyoti on 7/21/2017.
 */

 public class NotificationClass {

    public static void ReminderNotification(Context context, String title, String text, int id,PendingIntent pendingIntent)
    {

        //Get an instance of NotificationCompat.Builder //
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.reminder)
                        .setContentTitle("My Reminder  "+title)
                        .setContentText(""+text);
        NotificationManager mNotificationManager =
                (NotificationManager)context. getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(id, mBuilder.build());

    }
    public static void SMSNotification(Context context, String title, String text, int id,PendingIntent pendingIntent)
    {

        //Get an instance of NotificationCompat.Builder //
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.smsicon)
                        .setContentTitle("Message sent "+title)
                        .setContentText(""+text);
        NotificationManager mNotificationManager =
                (NotificationManager)context. getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(id, mBuilder.build());

    }

    public static void EmailNotification(Context context, String title, String text, int id,PendingIntent pendingIntent)
    {

        //Get an instance of NotificationCompat.Builder //
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.mailicon)
                        .setContentTitle("Email sent "+title)
                        .setContentText(""+text);
        NotificationManager mNotificationManager =
                (NotificationManager)context. getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());

    }

    public static void NoIternetNotification(Context context, PendingIntent pendingIntent)
    {

        //Get an instance of NotificationCompat.Builder //
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.nointenet)
                        .setContentTitle("Reminder App")
                        .setContentText("Some mails might not sent check your internet connection");

        NotificationManager mNotificationManager =
                (NotificationManager)context. getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());
    }

}


