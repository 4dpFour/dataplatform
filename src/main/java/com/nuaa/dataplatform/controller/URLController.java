package com.nuaa.dataplatform.controller;

import com.nuaa.dataplatform.entity.URL;
import com.nuaa.dataplatform.service.URLService;
import com.nuaa.dataplatform.util.HostHolder;
import com.nuaa.dataplatform.util.Result;
import com.nuaa.dataplatform.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/url")
public class URLController {

    @Autowired
    private URLService urlService;
    @Autowired
    private HostHolder hostHolder;

    @GetMapping("/{id}")
    public Result getURL(@PathVariable int id) {
        try {
            URL url = urlService.getURL(id);
            if (url != null) {
                return Result.success(url);
            } else {
                return Result.failure(ResultCode.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }

    @PostMapping("/list")
    public Result getURLList(@RequestParam("offset") int offset,
                         @RequestParam("limit") int limit) {
        try {
            List<URL> urls = urlService.getURLList(offset, limit);
            if (urls != null && urls.size() > 0) {
                return Result.success(urls);
            } else {
                return Result.failure(ResultCode.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }

    @PostMapping()
    public Result addURL(@RequestParam("name") String name,
                         @RequestParam("address") String address) {
        try {
            if (name == null || name.length() == 0) {
                return Result.failure(ResultCode.FORBIDDEN, "name 为空");
            }
            if (address == null || address.length() == 0) {
                return Result.failure(ResultCode.FORBIDDEN, "address 为空");
            }
            URL url = urlService.addURL(name, address, hostHolder.getUser().getId());
            return Result.success(url);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteURL(@PathVariable int id) {
        try {
            urlService.deleteURL(id);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public Result updateURL(@PathVariable int id,
                            @RequestParam(value = "name", required=false) String name,
                            @RequestParam(value = "address", required=false) String address) {
        try {
            urlService.updateURL(id, name, address, hostHolder.getUser().getId());
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }
}
