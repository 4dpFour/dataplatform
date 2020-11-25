package com.nuaa.dataplatform.util;

import com.nuaa.dataplatform.entity.User;
import org.springframework.stereotype.Component;


@Component
public class HostHolder {
    /*【线程本地变量】每条线程只会存储(setUser)、获得(getUser)自己的变量，用于 passport 保存当前用户的信息*/
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();;
    }
}
