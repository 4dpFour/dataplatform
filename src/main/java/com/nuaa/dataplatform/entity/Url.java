package com.nuaa.dataplatform.entity;

public class Url {
    private String urlName;
    private String seedPage;
    private String detailPage;
    private String className;

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getSeedPage() {
        return seedPage;
    }

    public void setSeedPage(String seedPage) {
        this.seedPage = seedPage;
    }

    public String getDetailPage() {
        return detailPage;
    }

    public void setDetailPage(String detailPage) {
        this.detailPage = detailPage;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Url() {
    }

    public Url(String urlName, String seedPage, String detailPage, String className) {
        this.urlName = urlName;
        this.seedPage = seedPage;
        this.detailPage = detailPage;
        this.className = className;
    }
}
