package com.example.jyoti.myproject.Preference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jyoti on 7/17/2017.
 */

public  class ReminderPreference {
    private  static SharedPreferences.Editor reminderEditor;
    private  static SharedPreferences reminderPref;
    private  static SharedPreferences appPref;
    private  static SharedPreferences.Editor appEditor;

    ReminderPreference()
    {}

    public static void setAppUser(Context context,String name,String email,int pin, boolean userStatus)
    {
        appPref=context.getSharedPreferences("appPref",Context.MODE_PRIVATE);
        appEditor=appPref.edit();
        appEditor.putString("username",name);
        appEditor.putString("email",email);
        appEditor.putInt("pin",pin);
        appEditor.putBoolean("userStatus",userStatus);
        appEditor.commit();
    }
    public static String getAppUserName()
    {
        return appPref.getString("username",null);

    }

    public static int getAppUserPin()
    {
        return appPref.getInt("pin",0);
    }

    public static String getAppEmail(){return appPref.getString("email",null);}

    public static boolean getAppStatus(Context context)
    {
        appPref=context.getSharedPreferences("appPref",Context.MODE_PRIVATE);
        return appPref.getBoolean("userStatus",false);
    }


    public static void setReminderPreference(Context context,String title,String text,String date,String time,String filename,int repeat) {
      reminderPref=context.getSharedPreferences("reminderPref",Context.MODE_PRIVATE);
        reminderEditor=reminderPref.edit();
        reminderEditor.putString("title",title);
        reminderEditor.putString("text",text);
        reminderEditor.putString("date",date);
        reminderEditor.putString("time",time);
        reminderEditor.putString("filename",filename);
        reminderEditor.putInt("repeat",repeat);
        reminderEditor.commit();

    }

    public static String getReminderPreferenceTitle()
    {
     return reminderPref.getString("title",null);
    }

    public static String getReminderPreferenceText()
    {
        return reminderPref.getString("text",null);
    }
    public static String getReminderPreferenceDate()
    {
        return reminderPref.getString("date",null);
    }
    public static String getReminderPreferenceTime()
    {
        return reminderPref.getString("time",null);
    }

    public static String getReminderPreferenceFilename()
    {
        return reminderPref.getString("filename",null);
    }
    public static int getReminderPreferenceRepeat()
    {
        return reminderPref.getInt("repeat",0);
    }
    public static void clearReminderPref()
    {
        reminderEditor.clear();
        reminderEditor.commit();
    }
}
