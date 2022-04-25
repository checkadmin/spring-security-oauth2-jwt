package com.coder.yang.gateway.service;

import com.coder.yang.model.redis.constants.LogoutConstants;
import com.coder.yang.model.redis.service.RedisService;
import com.coder.yang.exception.ErrCodeEnum;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;

/**
 * @author ： coder.Yang
 * @date ： 2022/4/20 17:45
 * @description ：token合法性校验
 */
@Slf4j
@Component
public class TokenService {

    @Autowired
    private RedisService redisService;

    private String getToken(ServerHttpRequest request){
        String token = null;
        String header = request.getHeaders().getFirst("Authorization");
        if (StringUtils.isNotBlank(header) && header.length() > 50) {
            token = header.substring(header.indexOf("bearer") + 7);
        }
        return token;
    }

    /**
     * token合法性校验：
     *   1、token合法性
     *   2、token是否在redis中
     * @param request
     * @return 状态码
     */
    public int checkAccessToken(ServerHttpRequest request) {
        String token =this.getToken(request);
        if (StringUtils.isBlank(token)){
            return ErrCodeEnum.GLOBAL_SUCCESS.getRespCode();
        }
        try {
            Jwts.parser().setSigningKey("test_key".getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
        }catch (SignatureException e){
            log.error("token不合法,签名不正确",e);
            return ErrCodeEnum.GlOBAL_ACCESSTOKEN_ERROR.getRespCode();
        }catch (MalformedJwtException e){
            log.error("token不合法，被篡改了",e);
            return ErrCodeEnum.GlOBAL_ACCESSTOKEN_ERROR.getRespCode();
        }catch (ExpiredJwtException e){
            log.error("token过期",e);
            return ErrCodeEnum.GlOBAL_ACCESSTOKEN_ERROR.getRespCode();
        }
        //在redis token 黑名单中校验是否存在
        if (redisService.hasKey(LogoutConstants.LOGOUT_ACCESS_TOKEN+token)){
            log.error("......LOGOUT_ACCESS_TOKEN存在黑名单中");
            return ErrCodeEnum.GlOBAL_ACCESSTOKEN_ERROR.getRespCode();
        }
        return ErrCodeEnum.GLOBAL_SUCCESS.getRespCode();
    }


    /**
     * 刷新token校验时校验是否在黑名单中
     * @param request
     */
    public int checkRefreshToken(ServerHttpRequest request) {
        String path = request.getURI().getPath();
        if (path.length() > 14 && "/token/refresh".equals(path.substring(path.length()-14))){
            log.info("....path:"+path.substring(path.length()-14));
            String token =this.getToken(request);
            if (redisService.hasKey(LogoutConstants.LOGOUT_REFRESH_TOKEN+token)){
                log.error("......LOGOUT_REFRESH_TOKEN存在黑名单中");
                return ErrCodeEnum.GlOBAL_ACCESSTOKEN_ERROR.getRespCode();
            }
        }
        return ErrCodeEnum.GLOBAL_SUCCESS.getRespCode();
    }
}
