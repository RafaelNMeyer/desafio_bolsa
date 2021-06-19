package com.example.Project;

import java.math.BigInteger;

public class UserData {
    private String id;
    private String name;
    private String validate;
    private int serial;

    public int getSeral() {
        return serial;
    }
    public void setSerial(int serial) {
        this.serial = serial;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getvalidate() {
        return validate;
    }
    public void setvalidate(String validate) {
        this.validate = validate;
    }
}