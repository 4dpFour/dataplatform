package com.nuaa.dataplatform.service;

import com.nuaa.dataplatform.dao.ContractDAO;
import com.nuaa.dataplatform.dao.UrlDAO;
import com.nuaa.dataplatform.entity.Contract;
import com.nuaa.dataplatform.entity.Url;
import com.nuaa.dataplatform.util.Crawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContractService {

    @Autowired
    private ContractDAO contractDAO;
    @Autowired
    private UrlDAO urlDAO;
    @Value("${default.page.limit}")
    private int MAX_PAGE;

    public Contract getContractById(int id) {
        return contractDAO.selectById(id);
    }

    public List<Contract> getContractsByUrl(String url) {
        return contractDAO.selectByUrl(url);
    }

    public List<Contract> getContractsByUrls(List<String> urls) {
        return contractDAO.selectByUrls(urls);
    }

    public Contract addContract(Contract contract)
    {
        contractDAO.addContract(contract);
        return contract;
    }

    public int deleteContractById(int id) {
        return contractDAO.deleteById(id);
    }

    public int deleteContractByIds(List<Integer> ids) {
        return contractDAO.deleteByIds(ids);
    }

    public void updateContract(int id, Contract contract) {
        contract.setId(id);
        contractDAO.updateContract(contract);
    }

    public int crawl(List<String> urlNames) throws Exception {
        ArrayList<Contract> contracts = new ArrayList<>();
        //给爷一个个爬
        for (String urlName : urlNames) {
            Url url = urlDAO.selectByUrlName(urlName);
            if (url != null) {
                List<Contract> temp = Crawler.crawl(url.getUrlName(), url.getSeedPage(), url.getDetailPage(), MAX_PAGE);
                if (temp != null && temp.size() > 0) {
                    contracts.addAll(temp);
                }
            }
        }
        if (contracts.size() == 0) {
            return 0;
        }
        return contractDAO.addUnrepeatedly(contracts);
    }

    public ArrayList<Contract> dimSelect(String query, List<String> urlNames) {
        ArrayList<Contract> contracts = new ArrayList<>();
        String[] querys = query.split(" ");
        if (!query.isEmpty()) {
            for(String str : querys) {
                List<Contract> temp;
                ArrayList<Contract> temp2 = new ArrayList<>();
                temp = contractDAO.dimSelect(str);
                for(Contract contract:temp) {
                    if(urlNames.contains(contract.getUrl())) {
                        temp2.add(contract);
                    }
                }
                if (temp.size() != 0) {
                    contracts.addAll(temp2);
                    temp2.clear();
                }
            }
        }
        return contracts;
    }

    /* 旧的爬虫模块，无法通用且不稳定
    public ArrayList<Contract> crawlCcgp(String url, int pageLimit) throws IOException {
        ArrayList<Contract> contracts = new ArrayList<>();
        Contract topContract = contractDAO.selectNewestByUrl(url);

        for (int i = 1; i <= pageLimit; i++) {
            Document doc = Jsoup.connect(url + "/index_" + i).get();
            Elements elements = doc.select(".ulst>li");
            elements.remove(0);
            for(Element element : elements){
                Elements children =  element.children();
                String link = url + children.get(1).attr("href").substring(1);
                Document detailDoc = Jsoup.connect(link).get();
                Elements details = detailDoc.select(".content_2020>p");

                String contractNo = splitAndTrim(details.get(0).child(0).text());
                String contractName = splitAndTrim(details.get(1).child(0).text());
                String projectNo = splitAndTrim(details.get(2).child(0).text());
                String projectName = splitAndTrim(details.get(3).child(0).text());
                String purchaser = splitAndTrim(details.get(5).text());
                String purchaserTelNo = splitAndTrim(details.get(7).text());
                String supplier = splitAndTrim(details.get(8).text());
                String supplierTelNo = splitAndTrim(details.get(10).text());
                String subjectName = splitAndTrim(details.get(12).text());
                String subjectUnitPrice = splitAndTrim(details.get(15).text());
                String contractValue = splitAndTrim(details.get(16).text());
                String announceDate = splitAndTrim(details.get(20).child(0).text());

                //爬到上一次最新的一条合同即终止
                if (topContract != null && contractNo.equals(topContract.getContractNo())) {
                    return contracts;
                } else {
                    contracts.add(new Contract(url, contractNo, contractName, projectNo, projectName, purchaser, purchaserTelNo, supplier, supplierTelNo, subjectName, subjectUnitPrice, contractValue, announceDate));
                }
            }
        }
        return contracts;
    }
    */
}
