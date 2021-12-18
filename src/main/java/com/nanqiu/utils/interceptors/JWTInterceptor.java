package com.nanqiu.utils.interceptors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nanqiu.utils.Const;
import com.nanqiu.utils.JWTUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception{
        String token = request.getHeader("token");
        HashMap<String, Object> map = new HashMap<>();
        try {
            JWTUtils.verify(token);

            JWTVerifier build = JWT.require(Algorithm.HMAC256(Const.SING)).build();
            DecodedJWT decodedJWT = build.verify(token);
            //重新设置过期时间
            Calendar expireData = Calendar.getInstance();
            expireData.add(Calendar.WEEK_OF_MONTH,1);

            //把旧token的值取出来，重新生成token
            String newToken=JWT.create()
                    .withIssuedAt(new Date())
                    .withExpiresAt(expireData.getTime())
                    .withClaim("adminId",decodedJWT.getClaim("adminId").asString())
                    .withClaim("adminName",decodedJWT.getClaim("adminName").asString())
                    .sign(Algorithm.HMAC256(Const.SING));

            response.setHeader("token",newToken);
            response.setHeader("Access-Control-Expose-Headers","token");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg","token无效!");
            map.put("state",false);
        }
        //手动把map转为json
        String json = new ObjectMapper().writeValueAsString(map); //转化
        response.setContentType("application/json;charset=UTF-8"); //设置json格式
        response.getWriter().println(json);

        return false;
    }
}
