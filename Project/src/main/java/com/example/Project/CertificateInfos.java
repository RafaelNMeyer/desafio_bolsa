package com.example.Project;

import java.math.BigInteger;
import java.util.Date;

public class CertificateInfos {
    private int id;
    private String name;
    private BigInteger serial;
    private Date validate;
    private String publicKey;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public BigInteger getSerial() {
        return serial;
    }
    public void setSerial(BigInteger serial) {
        this.serial = serial;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getvalidate() {
        return validate;
    }
    public void setvalidate(Date validate) {
        this.validate = validate;
    }
    public String getPublicKey() {
        return publicKey;
    }
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
