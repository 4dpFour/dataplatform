package com.nuaa.dataplatform.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private Integer authority;
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

    public Integer getAuthority() {
        return authority;
    }

    public void setAuthority(Integer authority) {
        this.authority = authority;
    }

    public String getCareUrls() {
        return careUrls;
    }

    public void setCareUrls(String careUrls) {
        this.careUrls = careUrls;
    }

    public User() {}

    public User(String username, String password, Integer authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    public User(String password, Integer authority, String careUrls) {
        this.password = password;
        this.authority = authority;
        this.careUrls = careUrls;
    }

    public List<String> getUrlsList() {
        List<String> urlsList = new ArrayList<>();
        String urls = this.careUrls.substring(1, careUrls.length() - 1);
        String[] urlArray = urls.split(",");
        for (int i = 0; i <  urlArray.length; i++) {
            urlArray[i] = urlArray[i].trim();
            if (urlArray[i].charAt(urlArray[i].length() - 1) == '/') {
                urlArray[i] = urlArray[i].substring(0, urlArray[i].length() - 1);
            }
            urlsList.add(urlArray[i]);
        }
        return urlsList;
    }
}
