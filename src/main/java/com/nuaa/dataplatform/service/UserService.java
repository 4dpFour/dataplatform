package com.nuaa.dataplatform.service;

import com.nuaa.dataplatform.dao.UserDAO;
import com.nuaa.dataplatform.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }
}
