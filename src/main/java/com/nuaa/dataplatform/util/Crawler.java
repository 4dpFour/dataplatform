package com.nuaa.dataplatform.util;

import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.nuaa.dataplatform.entity.Contract;

import java.util.ArrayList;
import java.util.List;

public class Crawler extends BreadthCrawler {
    private String urlName;
    private List<Contract> contracts = new ArrayList<>();

    /**
     * 爬虫
     * @param urlName           URL名称
     * @param seedPageFormat    合同的目录页，用格式化字符串来标注页码部分。
     * @param detailPageRegex   合同的详情页，用于对目录下的链接进行正则表达式匹配。
     * 调用示例：
     *     Crawler crawler = new Crawler(urlName, seedPage, detailPage);
     *     crawler.start(2);
     */
    public Crawler(String urlName, String seedPageFormat, String detailPageRegex, int maxPage) {
        super(urlName, true);
        this.urlName = urlName;

        for (int i = 1; i <= maxPage; i++) {
            String seedUrl = String.format(seedPageFormat, i);
            this.addSeed(seedUrl);
        }
        this.addRegex(detailPageRegex);
        setThreads(1);
        getConf().setTopN(100);
    }

    public void visit(Page page, CrawlDatums crawlDatums) {
        try {
            Contract contract = new Contract(ContentExtractor.getContentByDoc(page.doc()));
            if (!contract.indexEmpty()) {
                contract.setUrl(urlName);
                contracts.add(contract);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public static List<Contract> crawl(String urlName, String seedPage, String detailPage, int maxPage) throws Exception {
        Crawler crawler = new Crawler(urlName, seedPage, detailPage, maxPage);
        crawler.start(2);
        return crawler.getContracts();
    }
}
