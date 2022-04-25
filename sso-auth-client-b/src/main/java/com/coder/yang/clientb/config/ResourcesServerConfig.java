package com.coder.yang.clientb.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author ： coder.Yang
 * @date ： 2022/4/15 14:45
 * @description ：
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启验证.用prePostEnabled注解来做验证
@EnableResourceServer
@Configuration
public class ResourcesServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/token/**").permitAll()
                .anyRequest()
                .authenticated();
        http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
