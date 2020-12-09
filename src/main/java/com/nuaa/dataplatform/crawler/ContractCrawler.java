package com.nuaa.dataplatform.crawler;

import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.nuaa.dataplatform.entity.Contract;
import java.util.ArrayList;
import java.util.List;

public abstract class ContractCrawler  extends BreadthCrawler {
    protected String urlName;
    protected List<Contract> contracts = new ArrayList<>();
    protected String seedPage;
    protected String detailPage;
    public ContractCrawler(String urlName, String seedPage, String detailPage) {
        super(urlName, true);
        this.urlName = urlName;
        this.seedPage = seedPage;
        this.detailPage = detailPage;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }
}
