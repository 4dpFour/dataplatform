package com.nuaa.dataplatform.entity;

public class Url {
    private String urlName;
    private String seedPage;
    private String detailPage;

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

    public Url() {
    }

    public Url(String urlName, String seedPage, String detailPage) {
        this.urlName = urlName;
        this.seedPage = seedPage;
        this.detailPage = detailPage;
    }
}
