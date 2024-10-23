package com.example.cis183_homework03_studentregistration;

import java.io.Serializable;

public class Major implements Serializable {
    private int ID;
    private String name;
    private String prefix;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


}
