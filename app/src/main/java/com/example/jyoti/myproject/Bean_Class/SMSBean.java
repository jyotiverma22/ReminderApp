package com.example.jyoti.myproject.Bean_Class;

/**
 * Created by Jyoti on 7/10/2017.
 */

public class SMSBean {
     int sms_id;
    int rem_id;
    String reciever;
    int sender;
    String message;

    public SMSBean()
    {}

    public SMSBean(int rem_id,String reciever,int sender,String message )
    {
        this.rem_id=rem_id;
        this.reciever=reciever;
        this.sender=sender;
        this.message=message;
    }

    public int getSms_id() {
        return sms_id;
    }

    public void setSms_id(int sms_id) {
        this.sms_id = sms_id;
    }

    public int getRem_id() {
        return rem_id;
    }

    public void setRem_id(int rem_id) {
        this.rem_id = rem_id;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
