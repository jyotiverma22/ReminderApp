package com.example.jyoti.myproject.Bean_Class;

/**
 * Created by Jyoti on 7/15/2017.
 */

public class ContactBean {
    String name;
    String phone;
    boolean checkbox;

    public ContactBean()
    {}
    public ContactBean(String name,String phone,boolean checkbox)
    {
        this.name=name;
        this.phone=phone;
        this.checkbox=checkbox;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }


}
