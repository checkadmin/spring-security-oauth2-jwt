package com.coder.yang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ChengJianSheng
 * @date 2019-02-12
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/signout")
    public String signout() {
        System.out.println("退出成功，请重新登录");
        return "signout";
    }

}
