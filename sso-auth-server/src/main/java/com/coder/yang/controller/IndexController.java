package com.coder.yang.controller;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @author ： coder.Yang
 * @date ： 2022/4/14 21:20
 * @description ：
 */
@RestController
public class IndexController {

    @GetMapping("/index")
    public Object main() {
        //SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        return "hello";
    }

    /**
     * 获取登录用户信息
     * @return
     */
    @GetMapping("/get")
    public Object getCurrentUser(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = header.substring(header.indexOf("bearer") + 7);
        return Jwts.parser()
                .setSigningKey("test_key".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }

}
