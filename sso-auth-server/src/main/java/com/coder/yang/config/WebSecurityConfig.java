package com.coder.yang.config;

import com.coder.yang.handler.AuthLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author ： coder.Yang
 * @date ： 2022/4/14 21:22
 * @description ：Security配置类
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthLogoutHandler authLogoutHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/less/**");
    }

    /**
     * 允许基于选择匹配在资源级别配置基于Web的安全性
     *
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        //全局拦截相关
        httpSecurity
                .authorizeRequests()
                .antMatchers("/oauth/**", "/login/**").permitAll()
                .anyRequest().authenticated()
        ;

        //登录相关
        httpSecurity
                .csrf()
                .disable()
                .formLogin()
                .loginPage("http://192.168.56.1:9000/sso-auth-server/login")
                .loginProcessingUrl("/login")
        ;

        //退出相关
        httpSecurity
                .logout()
                //退出url，可以自定义
                .logoutUrl("/logout")
                //退出成功跳转页面
                .logoutSuccessUrl("http://192.168.56.1:9000/sso-auth-server/login")
                .addLogoutHandler(authLogoutHandler)
                //指定退出成功后删除的cookie
                .deleteCookies("JSESSIONID")
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
