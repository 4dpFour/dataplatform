package com.nuaa.dataplatform.interceptor;

import com.nuaa.dataplatform.entity.User;
import com.nuaa.dataplatform.service.UserService;
import com.nuaa.dataplatform.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//拦截器执行过程： preHandle -> Controller -> postHandle -> View -> afterCompletion
@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("ticket")) {    //如果检测到含有 ticket，赋值给上面的 ticket 变量
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        //验证 ticket 是否有效
        try {
            if (ticket != null) {
                User user = userService.verifyTicket(ticket);    //user已经是最新的
                hostHolder.setUser(user);    //设定线程本地变量中的 user，方便鉴权
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
        hostHolder.clear();    //结束时清除刚才保存的用户
    }

}
