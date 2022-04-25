package com.coder.yang.clienta.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ： coder.Yang
 * @date ： 2022/4/20 13:49
 * @description ：授权获取token
 */
@Slf4j
@RestController
public class OauthController {

    @Value("${security.oauth2.client.client-id:}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret:}")
    private String clientSecret;

    @Value("${security.oauth2.client.access-token-uri:}")
    private String accessTokenUri;

    @Value("${security.oauth2.client.registered-redirect-uri:}")
    private String redirectUri;


    @GetMapping("/token/{code}")
    public Map<String,String> tokenInfo(@PathVariable String code) throws UnsupportedEncodingException {
        //获取token
        Map tokenMap = getAccessToken(code);
        Map<String,String> map = new HashMap<>();
        map.put("access_token", (String) tokenMap.get("access_token"));
        map.put("refresh_token", (String) tokenMap.get("refresh_token"));
        map.put("expires_in", String.valueOf(tokenMap.get("expires_in").toString()));
        return map;
    }

    /**
     * 获取token
     */
    public Map getAccessToken(String code) throws UnsupportedEncodingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        byte[] authorization = (clientId + ":" + clientSecret).getBytes("UTF-8");
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Auth = encoder.encode(authorization);
        headers.add("Authorization", "Basic " + base64Auth);

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("grant_type", "authorization_code");
        param.add("code", code);
        param.add("redirect_uri", redirectUri);
        param.add("scope", "all");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(param, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(accessTokenUri, request , Map.class);
        Map result = response.getBody();
        return result;
    }


    @GetMapping("/token/refresh/{code}")
    public Map<String,String> refresh(@PathVariable String code) throws UnsupportedEncodingException {
        //获取token
        Map tokenMap = getRefreshToken(code);
        Map<String,String> map = new HashMap<>();
        map.put("access_token", (String) tokenMap.get("access_token"));
        map.put("refresh_token", (String) tokenMap.get("refresh_token"));
        map.put("expires_in", String.valueOf(tokenMap.get("expires_in").toString()));
        return map;
    }

    /**
     * 刷新获取token
     */
    public Map getRefreshToken(String code) throws UnsupportedEncodingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        byte[] authorization = (clientId + ":" + clientSecret).getBytes("UTF-8");
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Auth = encoder.encode(authorization);
        headers.add("Authorization", "Basic " + base64Auth);
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("grant_type", "refresh_token");
        param.add("refresh_token", code);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(param, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(accessTokenUri, request , Map.class);
        Map result = response.getBody();
        return result;
    }

}
