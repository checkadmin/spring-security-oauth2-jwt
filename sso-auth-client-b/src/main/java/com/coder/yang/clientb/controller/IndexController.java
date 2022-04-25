package com.coder.yang.clientb.controller;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @author ： coder.Yang
 * @date ： 2022/4/15 14:23
 * @description ：
 */
@Slf4j
@RestController
public class IndexController {

    @GetMapping("/index")
    public String index(){
        log.info( SecurityContextHolder.getContext().getAuthentication().toString());
        return "hello coder this client B";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    @GetMapping("/index2")
    public String index2(){
        return "需要角色验证";
    }


    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping("/index3")
    public String index3(){
        return "需要角色验证3";
    }


    /**
     * 获取登录用户信息
     * @return
     */
    @RequestMapping("/getCurrentUser")
    public Object getCurrentUser(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = header.substring(header.indexOf("bearer") + 7);
        return Jwts.parser()
                .setSigningKey("test_key".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }
}
