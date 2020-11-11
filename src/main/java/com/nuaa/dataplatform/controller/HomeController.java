package com.nuaa.dataplatform.controller;

import com.nuaa.dataplatform.entity.User;
import com.nuaa.dataplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/")
    public String index(Model model) {
        User user = userService.getTestUser();
        model.addAttribute("user", user);
        return "index";
    }

}
