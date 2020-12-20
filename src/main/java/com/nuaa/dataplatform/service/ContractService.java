package com.nuaa.dataplatform.service;

import com.nuaa.dataplatform.crawler.ContractCrawler;
import com.nuaa.dataplatform.dao.ContractDAO;
import com.nuaa.dataplatform.dao.UrlDAO;
import com.nuaa.dataplatform.entity.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ContractService {

    @Autowired
    private ContractDAO contractDAO;
    @Autowired
    private UrlDAO urlDAO;
    @Value("${crawl.depth}")
    private int CRAWL_DEPTH;

    public Contract getContractById(int id) {
        return contractDAO.selectById(id);
    }

    public Contract getContractByNoAndName(String contractNo, String contractName) {
        return contractDAO.selectByNoAndName(contractNo, contractName);
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

    public int crawl(List<String> urlNames, int start, int end, int thread) throws Exception {
        AtomicInteger num = new AtomicInteger();
        //给爷一个个爬
//        for (String urlName : urlNames) {
//            Url url = urlDAO.selectByUrlName(urlName);
//            if (url != null) {
//                //反射到具体的类
//                Class cls = Class.forName(url.getClassName());
//                Constructor con = cls.getConstructor(String.class, String.class, String.class, int.class, int.class, int.class);
//                ContractCrawler crawler = (ContractCrawler) con.newInstance(url.getUrlName(), url.getSeedPage(), url.getDetailPage(), start, end, thread);
//                crawler.start(crawler.getDepth());
//                List<Contract> temp =  crawler.getContracts();
//                if (temp != null && temp.size() > 0) {
//                    num = num + contractDAO.addUnrepeatedly(temp);
//                }
//            }
//        }
        // 使用Java 8中的Stream相关API能够快速并行爬取
        urlNames
                .parallelStream()
                .map(urlName -> urlDAO.selectByUrlName(urlName))
                .filter(Objects::nonNull)
                .forEach(url -> {
                    try {
                        //反射到具体的类
                        Class cls = Class.forName(url.getClassName());
                        Constructor con = cls.getConstructor(String.class, String.class, String.class, int.class, int.class, int.class);
                        ContractCrawler crawler = (ContractCrawler) con.newInstance(url.getUrlName(), url.getSeedPage(), url.getDetailPage(), start, end, thread);
                        crawler.start(crawler.getDepth());
                        List<Contract> newContracts = crawler.getContracts();
                        if (newContracts.size() > 0) {
                            num.addAndGet(contractDAO.addUnrepeatedly(newContracts));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        return num.get();
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
}
