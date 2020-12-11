package com.nuaa.dataplatform.crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import com.nuaa.dataplatform.entity.Contract;
import com.nuaa.dataplatform.util.StrUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.net.URL;
import java.util.List;

public class ShanDongCrawler extends ContractCrawler {

    /**
     * 构造函数，爬虫入口
     * @param urlName          网址名称
     * @param seedPageFormat   目录页 Format
     * @param detailPageRegex  详情页 Regex
     * @param start            起始页码
     * @param end              结束页码
     * @param thread           启用线程数
     */
    public ShanDongCrawler(String urlName, String seedPageFormat, String detailPageRegex, int start, int end, int thread) {
        super(urlName, seedPageFormat, detailPageRegex);
        this.depth = 2;

        for (int i = start; i <= end; i++) {
            String seedUrl = String.format(seedPageFormat, i);
            this.addSeed(seedUrl);
        }
        this.addRegex(detailPageRegex);
        setThreads(thread);
        getConf().setTopN(500);
    }

    /**
     * 主要的字段提取逻辑
     * @param page
     * @param crawlDatums
     */
    public void visit(Page page, CrawlDatums crawlDatums) {
        try {
            if (!page.matchUrl(detailPage)) {
                return;
            }
            String id = page.url().split("id=")[1];
            Document doc = Jsoup.parse(new URL(String.format("http://www.ccgp-shandong.gov.cn/sdgp2017/site/readcontractnew.jsp?id=%d", Integer.parseInt(id))), 2000);

            Elements elements = page.doc().select("div#textarea").get(0).child(0).children();
            String contractNo = StrUtil.clearTrim(doc.select("span#Code").text());
            String contractName = StrUtil.clearTrim(doc.select("span#PlanName").text());
            String projectNo = StrUtil.clearTrim(doc.select("span#ProjectCode_1").text());
            String projectName = StrUtil.clearTrim(doc.select("span#PlanName_").text());
            String purchaser = StrUtil.clearTrim(doc.select("span#UnitName_3").text());
            String purchaserTelNo = StrUtil.clearTrim(elements.get(10).text().split("：")[2]);
            String supplier = StrUtil.clearTrim(doc.select("span#SupplierName_4").text());
            String supplierTelNo = StrUtil.clearTrim(elements.get(6).text().split("：")[2]);
            String subjectName = StrUtil.clearTrim(elements.get(2).text().split("：")[1]);
            String subjectUnitPrice = null;
            String contractValue = StrUtil.clearTrim(elements.get(7).text().split("：")[1]);
            String announceDate = StrUtil.dateFormat(StrUtil.clearTrim(page.doc().select("div.info>midea").get(0).text().split("：")[1]));

            Contract contract = new Contract(urlName, contractNo, contractName, projectNo, projectName,
                    purchaser, purchaserTelNo, supplier, supplierTelNo, subjectName, subjectUnitPrice,
                    contractValue, announceDate);

            if (!contract.indexEmpty()) {
                contracts.add(contract);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Contract> getContracts() {
        return contracts;
    }
}