package com.nuaa.dataplatform;

import com.nuaa.dataplatform.service.ContractService;
import com.nuaa.dataplatform.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DataplatformApplication.class)
public class DataplatformApplicationTests {
    @Autowired
    ContractService contractService;

    @Test
    public void test() {
    }
}
