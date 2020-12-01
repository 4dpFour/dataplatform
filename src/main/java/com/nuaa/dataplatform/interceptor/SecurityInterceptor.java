package com.nuaa.dataplatform.interceptor;

import com.nuaa.dataplatform.util.HostHolder;
import com.nuaa.dataplatform.util.Result;
import com.nuaa.dataplatform.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


@Component
public class SecurityInterceptor implements HandlerInterceptor {
    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (hostHolder.getUser() == null) {
            try {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.append(Result.failure(ResultCode.FORBIDDEN, "用户未登录").toJSONString());
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(500);
            }
            return false;
        }
        return true;
    }
}
