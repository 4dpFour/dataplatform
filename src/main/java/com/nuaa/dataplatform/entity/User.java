package com.nuaa.dataplatform.entity;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private int authority;
    private String careUrls;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    public String getCareUrls() {
        return careUrls;
    }

    public void setCareUrls(String careUrls) {
        this.careUrls = careUrls;
    }

    public User() {}

    public User(String username, String password, int authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    public User(String username, String password, int authority, String careUrls) {
        this.username = username;
        this.password = password;
        this.authority = authority;
        this.careUrls = careUrls;
    }
}
