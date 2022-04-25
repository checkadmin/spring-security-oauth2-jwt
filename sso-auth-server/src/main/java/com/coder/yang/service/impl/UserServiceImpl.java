package com.coder.yang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coder.yang.bo.UserBO;
import com.coder.yang.entity.UserDO;
import com.coder.yang.mapper.UserMapper;
import com.coder.yang.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author ： coder.Yang
 * @date ： 2022/4/14 21:25
 * @description ：
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO user = userMapper.selectOne(new QueryWrapper<UserDO>().lambda().eq(UserDO::getUsername,username));
        Assert.notNull(user,"用户名或密码错误");
        return new UserBO(user,username,passwordEncoder.encode(user.getPassword()),
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_SUPER_ADMIN"));
    }

    public static void main(String[] args) {
        String abc = new BCryptPasswordEncoder().encode("zaxh");
        System.out.println(abc);
    }
}
