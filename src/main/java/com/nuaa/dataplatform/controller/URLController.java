package com.nuaa.dataplatform.controller;

import com.nuaa.dataplatform.entity.User;
import com.nuaa.dataplatform.service.UserService;
import com.nuaa.dataplatform.util.HostHolder;
import com.nuaa.dataplatform.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.nuaa.dataplatform.util.ResultCode.*;

@RestController
@RequestMapping("/url")
public class URLController {

    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;

    @GetMapping()
    public Result getCareURLs() {
        try {
            User user = hostHolder.getUser();
            List<String> urls = user.getUrlsList();
            if (urls != null && urls.size() > 0) {
                return Result.success(urls);
            } else {
                return Result.failure(NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(SERVER_ERROR);
        }
    }

    @PostMapping()
    public Result setURL(@RequestBody Map<String, String[]> requestMap) {
        try {
            if (requestMap == null || requestMap.size() == 0) {
                return Result.failure(FORBIDDEN, "输入URL为空");
            }
            userService.updateCareUrlsById(hostHolder.getUser().getId(), Arrays.toString(requestMap.get("urls")));
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(SERVER_ERROR);
        }
    }
}
