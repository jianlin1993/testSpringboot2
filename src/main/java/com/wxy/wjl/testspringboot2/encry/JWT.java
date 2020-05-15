package com.wxy.wjl.testspringboot2.encry;

import io.jsonwebtoken.*;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * JWT:JSON WEB TOKEN
 * 无状态登陆 ：token
 *
 */

public class JWT {
    //加密的Key
    private static final String SECRET_KEY = "123456789";

    @Test
    public void jwtTest() throws InterruptedException {
        // 设置3秒后过期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = System.currentTimeMillis() + 30 * 60 * 1000;
        String jwt = this.buildJwt(new Date(time));
        System.out.println("jwt = " + jwt);
        // 验证token是否可用
        boolean isOk = this.isJwtValid(jwt);
        System.out.println(isOk);
    }

    public String buildJwt(Date exp) {
        String jwt = Jwts
                .builder()
                //SECRET_KEY是加密算法对应的密钥，这里使用的是HS256加密算法
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                //expTime是过期时间
                .setExpiration(exp)
                .claim("name", "wangtingjun")
                .claim("age", "18")
                //该方法是在JWT中加入值为vaule的key字段
                .claim("key", "vaule")
                .compact();
        return jwt;
    }


    public boolean isJwtValid(String jwt) {
        try {
            //解析JWT字符串中的数据，并进行最基础的验证
            Claims claims = Jwts.parser()
                    //SECRET_KEY是加密算法对应的密钥，jjwt可以自动判断机密算法
                    .setSigningKey(SECRET_KEY)
                    //jwt是JWT字符串
                    .parseClaimsJws(jwt)
                    .getBody();
            System.out.println(claims);
            //获取自定义字段key
            String vaule = claims.get("key", String.class);
            //判断自定义字段是否正确
            if ("vaule".equals(vaule)) {
                return true;
            } else {
                return false;
            }
            //在解析JWT字符串时，如果密钥不正确，将会解析失败，抛出SignatureException异常，说明该JWT字符串是伪造的
            //在解析JWT字符串时，如果‘过期时间字段’已经早于当前时间，将会抛出ExpiredJwtException异常，说明本次请求已经失效
        } catch (SignatureException | ExpiredJwtException e) {
            return false;
        }
    }
}
