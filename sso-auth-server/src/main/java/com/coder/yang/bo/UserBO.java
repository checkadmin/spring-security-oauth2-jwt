package com.coder.yang.bo;

import com.coder.yang.entity.UserDO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author ： coder.Yang
 * @date ： 2022/4/14 21:32
 * @description ：
 */
public class UserBO implements UserDetails {
    private static final long serialVersionUID = 7009363318615354404L;
    private String username;
    private String password;
    private UserDO userDO;

    private Collection<? extends GrantedAuthority> grantedAuthorities;


    public UserBO(UserDO userDO,String username, String password, Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.userDO = userDO;
        this.username = username;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserDO getUserDO() {
        return userDO;
    }
}
