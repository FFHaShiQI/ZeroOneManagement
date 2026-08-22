package com.hajimi.backend.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 */
public class JwtUtils {

    // 密钥 和测试代码保持一致，密钥我设置为“01星球赋能驾驶舱学员管理系统”的base64编码
    private static final String SECRET_KEY = "MDHmmJ/nkIPotYvog73pqb7pqbboiLHlrablkZjnrqHnkIbns7vnu58=";
    // 过期时间：12小时，单位毫秒
    private static final long EXPIRE_TIME = 12 * 3600 * 1000L;


    /**
     * 生成JWT令牌
     * claimsMap 自定义载荷数据
     * jwt字符串
     */
    public static String generateJwt(Map<String, Object> claimsMap){
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .addClaims(claimsMap)
                // 设置过期时间 当前时间 +12小时
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .compact();
    }


    /**
     * 解析JWT令牌
     * @param jwtToken jwt令牌字符串
     * @return 载荷Claims对象
     */
    public static Claims parseJwt(String jwtToken){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(jwtToken)
                .getBody();
    }

}
