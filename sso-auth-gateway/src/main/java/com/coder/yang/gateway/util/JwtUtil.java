package com.coder.yang.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author ： coder.Yang
 * @date ： 2022/4/20 18:13
 * @description ：
 */
public class JwtUtil {
    private static String SECRET_KEY="zaxh";

    private static Key getKeyInstance(){
        SignatureAlgorithm signatureAlgorithm=SignatureAlgorithm.HS256;
        byte[] keySecretByte= DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key key=new SecretKeySpec(keySecretByte,signatureAlgorithm.getJcaName());
        return key;
    }


    /**
     * shengcheng jwt token
     * @param payLoad
     * @return
     */
    public static String createToken(Map<String,Object> payLoad){
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE,480);
        //有效时间：30分钟
        Date expiresDate = nowTime.getTime();
        return Jwts.builder().setClaims(payLoad).
                signWith(SignatureAlgorithm.HS256,getKeyInstance())
                .setExpiration(expiresDate)
                .compact();
    }

    /**
     * 根据token解析出token中的内容
     * @param token
     * @return
     */
    public static Claims parseToken(String token){
        Jws<Claims> claimsJwt=Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token);
        return  claimsJwt.getBody();
    }
}
