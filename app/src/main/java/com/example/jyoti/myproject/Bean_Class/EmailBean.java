package com.example.jyoti.myproject.Bean_Class;

/**
 * Created by Jyoti on 7/10/2017.
 */

public class EmailBean {
    int email_id;
    int rem_id;
    String name_email,email_email,type_email,number_email,message_email,subject_email;

    public EmailBean()
    {}

    public EmailBean(int rem_id,String name,String email,String type,String number,String message,String subject)
    {
        this.rem_id=rem_id;
        name_email=name;
        email_email=email;
        type_email=type;
        number_email=number;
        message_email=message;
        subject_email=subject;

    }

    public EmailBean(int emailid,int rem_id,String email,String name,String message,String subject,String number,String type)
    {
        this.email_id=emailid;
        this.rem_id=rem_id;
        name_email=name;
        email_email=email;
        type_email=type;
        number_email=number;
        message_email=message;
        subject_email=subject;

    }

    public int getEmail_id() {
        return email_id;
    }

    public void setEmail_id(int email_id) {
        this.email_id = email_id;
    }

    public int getRem_id() {
        return rem_id;
    }

    public void setRem_id(int rem_id) {
        this.rem_id = rem_id;
    }

    public String getName_email() {
        return name_email;
    }

    public void setName_email(String name_email) {
        this.name_email = name_email;
    }

    public String getEmail_email() {
        return email_email;
    }

    public void setEmail_email(String email_email) {
        this.email_email = email_email;
    }

    public String getType_email() {
        return type_email;
    }

    public void setType_email(String type_email) {
        this.type_email = type_email;
    }

    public String getNumber_email() {
        return number_email;
    }

    public void setNumber_email(String numder_email) {
        this.number_email = numder_email;
    }

    public String getMessage_email() {
        return message_email;
    }

    public void setMessage_email(String message_email) {
        this.message_email = message_email;
    }

    public String getSubject_email() {
        return subject_email;
    }

    public void setSubject_email(String sbject_email) {
        this.subject_email = sbject_email;
    }

}
