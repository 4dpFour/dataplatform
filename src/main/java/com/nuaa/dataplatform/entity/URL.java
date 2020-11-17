package com.nuaa.dataplatform.entity;

import java.io.Serializable;

public class URL implements Serializable {
    private int id;
    private String name;
    private String address;
    private int initAuthorId;
    private int lastAuthorId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getInitAuthorId() {
        return initAuthorId;
    }

    public void setInitAuthorId(int initAuthorId) {
        this.initAuthorId = initAuthorId;
    }

    public int getLastAuthorId() {
        return lastAuthorId;
    }

    public void setLastAuthorId(int lastAuthorId) {
        this.lastAuthorId = lastAuthorId;
    }

    public URL(String name, String address, int initAuthorId) {
        this.name = name;
        this.address = address;
        this.initAuthorId = initAuthorId;
    }
}
