package com.nuaa.dataplatform.crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import com.nuaa.dataplatform.entity.Contract;
import com.nuaa.dataplatform.util.StrUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.List;

public class BeiJingCrawler extends ContractCrawler {

    /**
     * 构造函数，爬虫入口
     * @param urlName          网址名称
     * @param seedPageFormat   目录页 Format
     * @param detailPageRegex  详情页 Regex
     * @param start            起始页码
     * @param end              结束页码
     * @param thread           启用线程数
     */
    public BeiJingCrawler(String urlName, String seedPageFormat, String detailPageRegex, int start, int end, int thread) {
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

    public void visit(Page page, CrawlDatums crawlDatums) {
        try {
            if (!page.matchUrl(detailPage)) {
                return;
            }
            Document doc = page.doc();
            Elements details = doc.getElementById("queryTable").children().get(0).children();

            String contractNo = StrUtil.clearTrim(details.get(0).child(1).html());
            String contractName = StrUtil.clearTrim(details.get(1).child(1).html());
            String projectNo = StrUtil.clearTrim(details.get(2).child(1).html());
            String projectName = StrUtil.clearTrim(details.get(3).child(1).html());
            String purchaser = StrUtil.clearTrim(details.get(4).child(1).html());
            String purchaserTelNo = null;
            String supplier = StrUtil.clearTrim(details.get(5).child(1).html());
            String supplierTelNo = null;
            String subjectName = null;
            String subjectUnitPrice = null;
            String contractValue = StrUtil.clearTrim(details.get(7).child(1).html());
            String announceDate = StrUtil.dateFormat(StrUtil.clearTrim(details.get(9).child(1).html()));

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
