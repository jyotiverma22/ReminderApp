package com.example.jyoti.myproject.SimDetails;

/**
 * Created by Jyoti on 7/16/2017.
 */

public class SimInfo {

    private String display_name;
    private int slot;

    public SimInfo(String display_name, int slot) {
        this.display_name = display_name;
        this.slot = slot;
    }


    public String getDisplay_name() {
        return display_name;
    }


    public int getSlot() {
        return slot;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

}