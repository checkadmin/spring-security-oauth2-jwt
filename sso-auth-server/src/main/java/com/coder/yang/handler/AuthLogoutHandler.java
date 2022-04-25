package com.coder.yang.handler;

import com.coder.yang.model.redis.constants.LogoutConstants;
import com.coder.yang.model.redis.service.RedisService;
import com.alibaba.fastjson.JSONObject;
import com.coder.yang.bo.LogoutBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @author ： coder.Yang
 * @date ： 2022/4/25 13:43
 * @description ：退出处理业务，给所有的token添加黑名单
 */
@Slf4j
@Component
public class AuthLogoutHandler implements LogoutHandler {

    @Autowired
    private RedisService redisService;

    //ACCESS_TOKEN 过期时间配置，要大于实际的ACCESS_TOKEN时间
    private final long ACCESS_TOKEN_EXPIRE=2000;

    //REFRESH_TOKEN 过期时间配置，要大于实际的REFRESH_TOKEN时间
    private final long REFRESH_TOKEN_EXPIRE=2000;

    @Override
    public void logout(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       Authentication authentication) {
        String data = null;
        try {
            data = httpServletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            log.error("读取请求体异常：",e);
        }
        LogoutBO logoutBO = JSONObject.parseObject(data,LogoutBO.class);
        //往redis中添加黑名单
        if (null != logoutBO && !CollectionUtils.isEmpty(logoutBO.getTokenList())){
            logoutBO.getTokenList().forEach(token -> {
                String accessToken = LogoutConstants.LOGOUT_ACCESS_TOKEN+token.getAccessToken();
                String refreshToken = LogoutConstants.LOGOUT_REFRESH_TOKEN+token.getRefreshToken();
                redisService.set(accessToken,accessToken,ACCESS_TOKEN_EXPIRE);
                redisService.set(refreshToken,refreshToken,REFRESH_TOKEN_EXPIRE);
            });
        }
    }
}
