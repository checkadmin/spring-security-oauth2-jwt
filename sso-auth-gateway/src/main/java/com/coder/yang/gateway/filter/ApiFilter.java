package com.coder.yang.gateway.filter;

import com.coder.yang.exception.ErrCodeEnum;
import com.coder.yang.gateway.service.TokenService;
import com.coder.yang.gateway.util.BaseResp;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author ： coder.Yang
 * @date ： 2022/4/20 17:40
 * @description ：请求拦截器
 */
@Slf4j
@Configuration
public class ApiFilter implements GlobalFilter, Ordered {

    @Autowired
    private TokenService tokenService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        //token校验
        int code = tokenService.checkAccessToken(request);
        if (code != ErrCodeEnum.GLOBAL_SUCCESS.getRespCode()){
            return setUnauthorizedResponse(exchange, BaseResp.fail(code));
        }

        //token校验
        code = tokenService.checkRefreshToken(request);
        if (code != ErrCodeEnum.GLOBAL_SUCCESS.getRespCode()){
            return setUnauthorizedResponse(exchange, BaseResp.fail(code));
        }

        return chain.filter(exchange);
    }

    private Mono<Void> setUnauthorizedResponse(ServerWebExchange exchange, BaseResp resp){
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            return bufferFactory.wrap(JSON.toJSONBytes(resp));
        }));
    }

    @Override
    public int getOrder() {
        return 10001;
    }
}
