package com.coder.yang.clienta.controller;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
        log.info("......index-用户信息："+SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        log.info("......index-权限信息："+SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return "hello coder this client A";
    }

    @PreAuthorize(value = "hasAnyAuthority('ROOT')")
    @GetMapping("/index3")
    public String index3(){
        log.info("......index3-用户信息："+SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        log.info("......index3-权限信息："+SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return "hello coder this client A-3";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    @GetMapping("/index2")
    public String index2(){
        return "需要角色验证";
    }

    /**
     * 获取登录用户信息
     * @return
     */
    @RequestMapping("getCurrentUser")
    public Object getCurrentUser(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = header.substring(header.indexOf("bearer") + 7);
        return Jwts.parser()
                .setSigningKey("test_key".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }
}
