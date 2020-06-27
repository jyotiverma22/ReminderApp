package com.example.jyoti.myproject.Bean_Class;

/**
 * Created by Jyoti on 7/10/2017.
 */

public class ReminderBean {


    int reminder_id;
    String title;
    String text;
    int type;
    int repeat;
    String date;
    String time;
    String tone;
    int status;
    int delete;

    public ReminderBean()
    {}

    public ReminderBean(String t,String text,int type,int repeat,String date,String time,String tone,int status,int delete)
    {
        title=t;
        this.text=text;
        this.type=type;
        this.repeat=repeat;
        this.date=date;
        this.time=time;
        this.status=status;
        this.delete=delete;
        this.tone=tone;
    }

    public ReminderBean(int rid, String t,String text,int type,int repeat,String date,String time,String tone,int status,int delete)
    {
        reminder_id=rid;
        title=t;
        this.text=text;
        this.type=type;
        this.repeat=repeat;
        this.date=date;
        this.time=time;
        this.status=status;
        this.delete=delete;
        this.tone=tone;
    }


    public int getReminder_id() {
        return reminder_id;
    }

    public void setReminder_id(int reminder_id) {
        this.reminder_id = reminder_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }

}
