package com.nuaa.dataplatform;

import com.nuaa.dataplatform.service.ContractService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DataplatformApplicationTests {
    @Autowired
    ContractService contractService;

    @Test
    public void TestCrawl() {
        //默认关闭测试
        int TEST = 0;
        if (TEST == 0) {
            return;
        }


        //测试代码
        String[] urls = {
                "中国政府采购网",
                "浙江省政府采购网",
                "天津市政府采购网",
                "湖北省政府采购网",
                "河北省政府采购网",
                "北京市政府采购网",
                "江苏省政府采购网",
                "江西省政府采购网",
                "山东省政府采购网",
                "安徽省政府采购网"
        };
        Map<String, Double> resultDouble = new HashMap<>();
        Map<String, Integer> resultInt = new HashMap<>();
        long startTime;
        long endTime;
        double usedTime = 0.0;
        int num = 0;
        for (String url : urls) {
            List<String> urlList = new ArrayList<>();
            urlList.add(url);
            startTime = System.currentTimeMillis();
            try {
                num = contractService.crawl(urlList, 1, 2, 1);
            } catch (Exception e) {
                System.out.println(url + " - 错误信息:" + e.getMessage());
            }
            endTime = System.currentTimeMillis();
            usedTime = (endTime - startTime) / 1000.0;
            resultDouble.put(url, usedTime);
            resultInt.put(url, num);
        }

        System.out.println("--------------结果---------------");
        for (String url : urls) {
            System.out.println(url + "：" + resultInt.get(url) + "条 " + String.format("%.2f", resultDouble.get(url)) + " 秒");
        }
    }
}
