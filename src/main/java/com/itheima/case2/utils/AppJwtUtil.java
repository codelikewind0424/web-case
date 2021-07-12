package com.itheima.case2.utils;

import io.jsonwebtoken.*;
import sun.misc.BASE64Encoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

public class AppJwtUtil {

    // TOKEN的有效期一小时（S）
    private static final int TOKEN_TIME_OUT = 3600;
    // 密钥,不能泄露
    private static final String TOKEN_ENCRY_KEY = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY";

    /*
        获取token方法 :
            userId 是要存到token的用户信息， 如有需要可以添加更多
      */
    public static String getToken(Integer userId){
        Map<String, Object> claimMaps = new HashMap<>();
        claimMaps.put("userId",userId);
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString()) //jwt编号:随机产生
                .setIssuedAt(new Date(currentTime))  //签发时间
                .setIssuer("heima") //签发者信息
                .setExpiration(new Date(currentTime + TOKEN_TIME_OUT * 1000))  //过期时间戳
                .addClaims(claimMaps) //自定义
                .signWith(SignatureAlgorithm.HS256, generalKey()) //加密方式
                .compact();
    }
    /**
     * 获取token中的claims信息
     *
     * @param token
     * @return
     */
    private static Jws<Claims> getJws(String token) {
            return Jwts.parser()
                    .setSigningKey(generalKey())
                    .parseClaimsJws(token);
    }
    /**
     * 获取payload body信息(指的是tocken中Payload部分)
     * @param token
     * @return Claims 是Map
     */
    public static Claims getClaimsBody(String token) {
        try {
            return getJws(token).getBody();
        }catch (ExpiredJwtException e){
            return null;
        }
    }
    public static JwsHeader getClaimsHeader(String token) {
        try {
            return getJws(token).getHeader();
        }catch (ExpiredJwtException e){
            return null;
        }
    }

    public static String getClaimsSignature(String token) {
        try {
            return getJws(token).getSignature();
        }catch (ExpiredJwtException e){
            return null;
        }
    }


    /**
     *
     * 检查token
     *      1. 检查tocken的完整性和有效期
     *      2. 检查失败会报错
     *      3. 检查成功返回tocken的playload内容
     */
    public static Claims checkToken(String token) {
        try {
            Claims claims = getClaimsBody(token);
            if(claims==null){
               throw new RuntimeException("token解析失败");
            }
            return claims;
        } catch (ExpiredJwtException ex) {
            throw new RuntimeException("token已经失效");
        }catch (Exception e){
            throw new RuntimeException("token解析失败");
        }
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getEncoder().encode(TOKEN_ENCRY_KEY.getBytes());
        /*
        使用len的第一个len字节构造来自给定字节数组的key ，从offset开始。
        构成密钥的字节是key[offset]和key[offset+len-1]之间的字节。

        参数
            key - 密钥的密钥材料。 将复制以offset开头的数组的第一个len字节，以防止后续修改。
            offset - 密钥材料开始的 key中的偏移量。
            len - 密钥材料的长度。
            algorithm - 与给定密钥材料关联的密钥算法的名称。 AES是一种对称加密算法
         */
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    //测试
    public static void main(String[] args) {
        String token = getToken(1);
        /*

            header: eyJhbGciOiJIUzI1NiJ9.
            Payload:eyJqdGkiOiJlNzMxNjk5Mi0yYTk5LTQwOTAtYWMwOS01MGMzZjU5MGUyNGIiLCJpYXQiOjE2MjMyODU2ODYsImlzcyI6ImhlaW1hIiwiZXhwIjoxNjIzMjg5Mjg2LCJ1c2VySWQiOjF9.
            Signature:+oIJ-xzz67xGG5Ft5D8XxRjlwIB37RYcsb-I-V6cC-7Y
         */
        System.out.println(token);
        Claims claims = checkToken(token);

        byte[] xx = Base64.getUrlDecoder().decode("eyJhbGciOiJIUzI1NiJ9");
        String s = new String(xx);
        //{"alg":"HS256"}
        System.out.println(s);

        //对Payload进行解密
        byte[] yy = Base64.getUrlDecoder().decode("eyJqdGkiOiJlNzMxNjk5Mi0yYTk5LTQwOTAtYWMwOS01MGMzZjU5MGUyNGIiLCJpYXQiOjE2MjMyODU2ODYsImlzcyI6ImhlaW1hIiwiZXhwIjoxNjIzMjg5Mjg2LCJ1c2VySWQiOjF9");

        /*
            {
                "jti":"e7316992-2a99-4090-ac09-50c3f590e24b", //表示token的编号，这里使用UUID.randomUUID().toString()生成的
                "iat":1623285686, //签发时间,new Date(currentTime) 表示当前系统时间
                "iss":"heima", 签发人
                "exp":1623289286, 过期时间
                "userId":1 自定义的字段
           }
         */
        String s2 = new String(yy);
        System.out.println(s2);


        /* Object userId = claims.get("userId");
        Object iat = claims.get("iat");
        Object exp  = claims.get("exp");
        Object jti  = claims.get("jti");
        Object sub   = claims.get("sub"); //没有定义的信息是获取不到的
        System.out.println(jti);
        System.out.println(userId);
        System.out.println(iat);
        System.out.println(exp);
        System.out.println(sub);

        JwsHeader header = getClaimsHeader(token);
        String algorithm = header.getAlgorithm();
        System.out.println(algorithm);  // HS256

        String claimsSignature = getClaimsSignature(token);
        System.out.println(claimsSignature);*/

    }
}
