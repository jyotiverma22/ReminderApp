package com.example.jyoti.myproject.Bean_Class;

/**
 * Created by Jyoti on 7/18/2017.
 */

public class TodoListBean {
    int id;
    String title;
    int status;

    public TodoListBean(int id,String title,int status)
    {
        this.id=id;
        this.title=title;
        this.status=status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
