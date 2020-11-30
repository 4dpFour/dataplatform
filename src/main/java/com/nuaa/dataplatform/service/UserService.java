package com.nuaa.dataplatform.service;

import com.nuaa.dataplatform.dao.LoginTicketDAO;
import com.nuaa.dataplatform.dao.UserDAO;
import com.nuaa.dataplatform.entity.LoginTicket;
import com.nuaa.dataplatform.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LoginTicketDAO loginTicketDAO;

    public User getUserById(int id) {
        return userDAO.selectById(id);
    }

    public User getUserByUsername(String username) {
        return userDAO.selectByUsername(username);
    }

    public void updateUserById(int id, String password, int authority, String careUrls) {
        userDAO.updateUserById(id, password, authority, careUrls);
    }

    public void updateCareUrlsById(int id, String urls) {
        userDAO.updateCareUrlsById(id, urls);
    }

    public void deleteUserById(int id) {
        loginTicketDAO.deleteByUserId(id);
        userDAO.deleteById(id);
    }

    public String register(String username, String password, int authority) {
        User user = new User(username, password, authority);
        userDAO.addUser(user);
        //注册成功也发一个机票 自动登录
        return addLoginTicket(user.getId());
    }

    public String login(String username, String password) {
        User user = userDAO.selectByUsername(username);
        if (user == null || !password.equals(user.getPassword())) {
            return null;
        }
        return addLoginTicket(user.getId());
    }

    public boolean logout(String ticket) {
        if (ticket == null || ticket.length() == 0) {
            return false;
        }
        loginTicketDAO.updateStatus(ticket, 1);    //登出只要前端传回来一个票，这里把 ticket 设置为无效即可
        return true;
    }

    public User verifyTicket(String ticket) {
        if (ticket != null) {
            LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);    //通过 DAO 在数据库中取出 ticket 实体
            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0) {
                return null;    //无效机票
            }

            //取得 ticket 字符串对应的 user
            int userId = loginTicket.getUserId();
            User user = userDAO.selectById(userId);
            return user;
        }
        return null;
    }

    private String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));    //把随机的 UUID 横杠去掉
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }
}
