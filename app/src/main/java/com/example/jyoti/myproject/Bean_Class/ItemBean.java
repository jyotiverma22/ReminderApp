package com.example.jyoti.myproject.Bean_Class;

/**
 * Created by Jyoti on 7/17/2017.
 */

public class ItemBean {
    int item_id;
    int id_todolist;
    String item;


    int status;
    public ItemBean()
    {}
    public ItemBean(int item_id,int id_todolist,String item,int status)
    {
        this.id_todolist=id_todolist;
        this.item_id=item_id;
        this.item=item;
        this.status=status;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getId_todolist() {
        return id_todolist;
    }

    public void setId_todolist(int id_todolist) {
        this.id_todolist = id_todolist;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
