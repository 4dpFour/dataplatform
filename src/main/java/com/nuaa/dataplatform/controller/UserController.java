package com.nuaa.dataplatform.controller;

import com.nuaa.dataplatform.entity.User;
import com.nuaa.dataplatform.service.UserService;
import com.nuaa.dataplatform.util.HostHolder;
import com.nuaa.dataplatform.util.Result;
import com.nuaa.dataplatform.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;
import static com.nuaa.dataplatform.util.ResultCode.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;

    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> requestMap,
                        HttpServletResponse response) {
        try {
            String username = requestMap.get("username");
            String password = requestMap.get("password");
            if (StrUtil.isEmpty(username.trim())) {
                return Result.failure(FORBIDDEN, "用户名不能为空");
            }
            User currentUser = hostHolder.getUser();
            if (currentUser != null) {
                return Result.failure(FORBIDDEN, "用户" + currentUser.getUsername() + "已经登陆");
            }
            String ticket = userService.login(username, password);
            if (!StrUtil.isEmpty(ticket)) {
                Cookie cookie = new Cookie("ticket", ticket);
                cookie.setPath("/");
                cookie.setMaxAge(3600*24*30);    //cookie有效期设置一个月
                response.addCookie(cookie);
                return Result.success();
            } else {
                return Result.failure(FORBIDDEN, "用户名或密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public Result register(@RequestBody Map<String, Object> requestMap,
                           HttpServletResponse response) {
        try {
            String username = (String) requestMap.get("username");
            String password = (String) requestMap.get("password");
            Integer authority = (Integer) requestMap.get("authority");
            if (StrUtil.isEmpty(username.trim())) {
                return Result.failure(FORBIDDEN, "用户名不能为空");
            }
            if (StrUtil.isEmpty(password.trim())) {
                return Result.failure(FORBIDDEN, "密码不能为空");
            }
            if (userService.getUserByUsername(username) != null) {
                return Result.failure(FORBIDDEN, "用户名已存在");
            }
            String ticket;
            if (authority == null) {
                ticket = userService.register(username, password, 0);
            } else {
                ticket = userService.register(username, password, authority);
            }
            if (!StrUtil.isEmpty(ticket)) {
                Cookie cookie = new Cookie("ticket", ticket);
                cookie.setPath("/");
                cookie.setMaxAge(3600*24*30);    //cookie有效期设置一个月
                response.addCookie(cookie);
            }
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(SERVER_ERROR);
        }
    }

    @GetMapping("/logout")
    public Result logout(@CookieValue("ticket") String ticket) {
        try {
            if (userService.logout(ticket)) {
                return Result.success();
            }
            return Result.failure(FORBIDDEN, "未登录用户");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public Result getUser(@PathVariable int id) {
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                return Result.success(user);
            }
            return Result.failure(FORBIDDEN, "用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteUser(@PathVariable int id) {
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                userService.deleteUserById(id);
                return Result.success();
            }
            return Result.failure(FORBIDDEN, "用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public Result updateUser(@PathVariable int id,
                             @RequestBody Map<String, Object> requestMap) {
        try {
            String password = (String) requestMap.get("password");
            Integer authority = (Integer) requestMap.get("authority");
            ArrayList<String> careUrls = ((ArrayList<String>) requestMap.get("urls"));
            if (password == null && authority == null && (careUrls == null || careUrls.size() == 0)) {
                return Result.failure(BAD_REQUEST, "未输入更新项");
            }
            userService.updateUserById(id, password, authority, careUrls.toString());
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(SERVER_ERROR);
        }
    }

    @GetMapping("/current")
    public Result getCurrentUser() {
        try {
            User user = hostHolder.getUser();
            if (user != null) {
                return Result.success(user);
            }
            return Result.failure(FORBIDDEN, "用户未登录");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(SERVER_ERROR);
        }
    }
}
