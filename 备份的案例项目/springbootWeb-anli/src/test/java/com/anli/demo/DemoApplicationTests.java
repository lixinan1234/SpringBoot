package com.anli.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {

    /*生成JWT令牌*/
    @Test
    public void testGenJWt(){
        Map<String, Object> craims = new HashMap<>();
        craims.put("id",1);
        craims.put("name","tom");

        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,"lixinan")//签名算法
                .setClaims(craims)//自定义内容（载荷）
                .setExpiration(new Date(System.currentTimeMillis() + 24*3600*1000)) //有效期
                .compact();

        System.out.println(jwt);
    }


    //解析JMT令牌
    @Test
    public void parseJwt(){
        Claims claims = Jwts.parser()
                .setSigningKey("lixinan")//指定签名密钥（必须保证和生成令牌时使用相同的签名密钥）
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoidG9tIiwiaWQiOjEsImV4cCI6MTcyMDU5Mzc0MX0.6pFBL-rpCj16yvtgdpSSsfAqTfFRc3veEU9ALtR5Yws")
                .getBody();

        System.out.println(claims);
    }

}
