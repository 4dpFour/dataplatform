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
            Elements details = doc.select(".suojin");
            List<Node> nodes = doc.select("span.txt7").get(1).childNodes();

            String contractNo = StrUtil.clearTrim(nodes.get(2).toString());
            String contractName = StrUtil.clearTrim(nodes.get(6).toString());
            String projectNo = StrUtil.clearTrim(nodes.get(10).toString());
            String projectName = StrUtil.clearTrim(nodes.get(14).toString());
            String purchaser = StrUtil.clearTrim(nodes.get(20).childNode(0).toString());
            String purchaserTelNo = StrUtil.clearTrim(nodes.get(26).childNode(0).toString());
            String supplier = StrUtil.clearTrim(nodes.get(29).childNode(0).toString());
            String supplierTelNo = StrUtil.clearTrim(nodes.get(36).childNode(0).toString());
            String subjectName = StrUtil.clearTrim(nodes.get(42).childNode(0).toString());
            String subjectUnitPrice = StrUtil.clearTrim(nodes.get(51).childNode(0).toString());
            String contractValue = StrUtil.clearTrim(nodes.get(54).childNode(0).toString());
            String announceDate = StrUtil.dateFormat(StrUtil.clearTrim(nodes.get(68).childNode(0).toString()));

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