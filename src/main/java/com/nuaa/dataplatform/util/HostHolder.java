package com.nuaa.dataplatform.util;

import com.nuaa.dataplatform.entity.User;
import org.springframework.stereotype.Component;

@Component
public class HostHolder {
    /** 用线程本地变量保存当前用户的信息 */
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    /** 获取当前用户 */
    public User getUser() {
        return users.get();
    }

    /** 设置当前用户 */
    public void setUser(User user) {
        users.set(user);
    }

    /** 清除当前用户 */
    public void clear() {
        users.remove();;
    }
}
