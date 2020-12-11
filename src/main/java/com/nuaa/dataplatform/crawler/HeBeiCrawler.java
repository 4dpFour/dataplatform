package com.nuaa.dataplatform.crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import com.nuaa.dataplatform.entity.Contract;
import com.nuaa.dataplatform.util.StrUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import java.util.List;

public class HeBeiCrawler extends ContractCrawler {

    /**
     * 构造函数，爬虫入口
     * @param urlName          网址名称
     * @param seedPageFormat   目录页 Format
     * @param detailPageRegex  详情页 Regex
     * @param start            起始页码
     * @param end              结束页码
     * @param thread           启用线程数
     */
    public HeBeiCrawler(String urlName, String seedPageFormat, String detailPageRegex, int start, int end, int thread) {
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
            Document doc = page.doc();
            String[] details = doc.select("span.txt7").get(1).html().split("</span>");

            String contractNo = StrUtil.clearTrim(details[1].split("<br>")[0]);
            String contractName = StrUtil.clearTrim(details[2].split("<br>")[0]);
            String projectNo = StrUtil.clearTrim(details[3].split("<br>")[0]);
            String projectName = StrUtil.clearTrim(details[4].split("<br>")[0]);
            String purchaser = StrUtil.clearTrim(details[5].split("<span>")[1]);
            String purchaserTelNo = StrUtil.clearTrim(details[7].split("<span>")[1]);
            String supplier = StrUtil.clearTrim((details[8].split("<span id=\"supplier\">")[1]));
            String supplierTelNo = StrUtil.clearTrim(details[11].split("<span>")[1]);
            String subjectName = StrUtil.clearTrim(details[13].split("<span>")[1]);
            String subjectUnitPrice = StrUtil.clearTrim(details[16].split("<span>")[1]);
            String contractValue = StrUtil.clearTrim(details[17].split("<span>")[1]);
            String announceDate = StrUtil.dateFormat(StrUtil.clearTrim(details[23].split("<span>")[1]));

            Contract contract = new Contract(urlName, contractNo, contractName, projectNo, projectName,
                    purchaser, purchaserTelNo, supplier, supplierTelNo, subjectName, subjectUnitPrice,
                    contractValue, announceDate);

            if (!contract.indexEmpty()) {
                contracts.add(contract);
            }
        } catch (Exception e) {
            System.out.println(page.url());
            e.printStackTrace();
        }
    }

    public List<Contract> getContracts() {
        return contracts;
    }
}