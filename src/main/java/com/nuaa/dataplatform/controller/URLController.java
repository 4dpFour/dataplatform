package com.nuaa.dataplatform.controller;

import com.nuaa.dataplatform.entity.URL;
import com.nuaa.dataplatform.service.URLService;
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
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }

    @PostMapping("/list")
    public Result getURLList(@RequestParam(value = "offset") int offset,
                         @RequestParam(value = "limit") int limit) {
        try {
            List<URL> urls = urlService.getURLList(offset, limit);
            if (urls != null && urls.size() > 0) {
                return Result.success(urls);
            } else {
                return Result.failure(ResultCode.NOT_FOUND);
            }
        } catch (Exception e) {
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }

    @PostMapping()
    public Result addURL(@RequestParam(value = "name") String name,
                         @RequestParam(value = "address") String address,
                         @RequestParam(value = "initAuthorId") int initAuthorId) {
        try {
            if (name == null || name.length() == 0) {
                return Result.failure(ResultCode.FORBIDDEN, "name 为空");
            }
            if (address == null || address.length() == 0) {
                return Result.failure(ResultCode.FORBIDDEN, "address 为空");
            }
            URL url = urlService.addURL(name, address, initAuthorId);
            return Result.success(url);
        } catch (Exception e) {
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteURL(@PathVariable int id) {
        try {
            urlService.deleteURL(id);
            return Result.success();
        } catch (Exception e) {
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public Result updateURL(@PathVariable int id,
                            @RequestParam(value = "name", required=false) String name,
                            @RequestParam(value = "address", required=false) String address,
                            @RequestParam(value = "lastAuthorId") int lastAuthorId) {
        try {
            urlService.updateURL(id, name, address, lastAuthorId);
            return Result.success();
        } catch (Exception e) {
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }
}
