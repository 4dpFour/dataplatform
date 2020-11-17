package com.nuaa.dataplatform;

import com.alibaba.fastjson.JSONObject;
import com.nuaa.dataplatform.entity.User;
import com.nuaa.dataplatform.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DataplatformApplication.class)
public class DataplatformApplicationTests {
    @Autowired
    UserService userService;

    @Test
    public void test() {
        User user1 = userService.getUser(1);
        System.out.println(JSONObject.toJSONString(user1));
        User user2 = userService.getUser(7);
        System.out.println(JSONObject.toJSONString(user2));
    }
}
