package com.nuaa.dataplatform.crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import com.nuaa.dataplatform.entity.Contract;
import com.nuaa.dataplatform.util.StrUtil;
import org.jsoup.select.Elements;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TianJinCrawler extends ContractCrawler {

    public TianJinCrawler(String urlName, String seedPage, String detailPage, int start, int end, int thread) {
        super(urlName, seedPage, detailPage);
        this.depth = 3;

        for (int i = start; i <= end; i++) {
            this.addSeed(String.format(seedPage, i));
        }
        this.addRegex(detailPage);
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
                Matcher matcher = Pattern.compile("(?<=id=)[0-9]*(?=&)").matcher(page.html());
                while (matcher.find()) {
                    crawlDatums.add("http://ccgp-tianjin.gov.cn/portal/documentView.do?method=view&id=" + matcher.group());
                }
                return;
            }
            Elements details1 = page.doc().select("div.segement1");
            Elements details2 = page.doc().select("div.line");

            String contractNo = StrUtil.clearTrim(details1.get(0).text().split("：")[1]);
            String contractName = StrUtil.clearTrim(details1.get(1).text().split("：")[1]);
            String projectNo = StrUtil.clearTrim(details1.get(2).text().split("：")[1]);
            String projectName = StrUtil.clearTrim(details1.get(3).text().split("：")[1]);
            String purchaser = StrUtil.clearTrim(details2.get(0).text().split("：")[1]);
            String purchaserTelNo = StrUtil.clearTrim(details2.get(2).text().split("：")[1]);
            String supplier = StrUtil.clearTrim(details2.get(3).text().split("：")[1]);
            String supplierTelNo = StrUtil.clearTrim(details2.get(5).text().split("：")[1]);
            String subjectName = StrUtil.clearTrim(details2.get(6).text().split("：")[1]);
            String subjectUnitPrice = StrUtil.clearTrim(details2.get(9).text().split("：")[1]);
            String contractValue = StrUtil.clearTrim(details2.get(10).text().split("：")[1]);
            String announceDate = StrUtil.dateFormat(StrUtil.clearTrim(details1.get(5).text().split("：")[1]));

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