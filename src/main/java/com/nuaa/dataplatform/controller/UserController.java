package com.nuaa.dataplatform.controller;

import com.nuaa.dataplatform.entity.User;
import com.nuaa.dataplatform.service.UserService;
import com.nuaa.dataplatform.util.HostHolder;
import com.nuaa.dataplatform.util.Result;
import com.nuaa.dataplatform.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

//TODO: 鉴权还没做
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;

    @PostMapping(value = "/login")
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletResponse response) {
        try {
            if (username.trim().length() == 0) {
                return Result.failure(ResultCode.FORBIDDEN, "用户名不能为空");
            }
            String ticket = userService.login(username, password);
            if (ticket != null && ticket.length() > 0) {
                Cookie cookie = new Cookie("ticket", ticket);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            return Result.success(ticket);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }

    @PostMapping(value = "/register")
    public Result register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam(value = "authority", required=false) Integer authority,
                           HttpServletResponse response) {
        try {
            if (username.trim().length() == 0) {
                return Result.failure(ResultCode.FORBIDDEN, "用户名不能为空");
            }
            if (username.length() == 0) {
                return Result.failure(ResultCode.FORBIDDEN, "密码不能为空");
            }
            if (userService.getUserByUsername(username) != null) {
                return Result.failure(ResultCode.FORBIDDEN, "用户名已存在");
            }
            String ticket;
            if (authority == null) {
                ticket = userService.register(username, password, 0);
            } else {
                ticket = userService.register(username, password, authority);
            }
            if (ticket != null && ticket.length() > 0) {
                Cookie cookie = new Cookie("ticket", ticket);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            return Result.success(ticket);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }

    @GetMapping(path = {"/logout"})
    public Result logout(@CookieValue("ticket") String ticket) {
        try {
            if (userService.logout(ticket)) {
                return Result.success();
            }
            return Result.failure(ResultCode.FORBIDDEN, "未登录用户");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public Result getUser(@PathVariable int id) {
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                return Result.success(user);
            }
            return Result.failure(ResultCode.FORBIDDEN, "用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCode.SERVER_ERROR);
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
            return Result.failure(ResultCode.FORBIDDEN, "用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public Result updateUser(@PathVariable int id,
                            @RequestParam(value = "password", required=false) String password,
                            @RequestParam(value = "authority", required=false) String authority) {
        try {
            if (password == null && authority == null) {
                return Result.failure(ResultCode.BAD_REQUEST, "未输入更新项");
            }
            userService.updateUserById(id, password, authority);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }

    @GetMapping(path = {"/current"})
    public Result getCurrentUser() {
        try {
            User user = hostHolder.getUser();
            if (user != null) {
                return Result.success(user);
            }
            return Result.failure(ResultCode.FORBIDDEN, "用户未登录");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }
}
