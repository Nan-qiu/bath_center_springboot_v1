package com.nanqiu.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class JWTUtils {

    //创建token
    public static String createToken(Map<String,String> map){
        //设置过期时间
        Calendar expireData = Calendar.getInstance();
        expireData.add(Calendar.WEEK_OF_MONTH,1);

        //通过map，存入payload
        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);

        String token=builder
                .withIssuedAt(new Date())
                .withExpiresAt(expireData.getTime())
                .sign(Algorithm.HMAC256(Const.SING));
        return token;
    }

    //验证token合法性，并返回
    public static DecodedJWT verify(String token){
        return JWT.require(Algorithm.HMAC256(Const.SING)).build().verify(token);
    }

}
