package com.lyloou.practice.util;

import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lyloou
 * @date 2020/06/16
 * @desc
 */
public class CookieUtil {
    public static final String COOKIE_LOGIN_KEY = "SESSION";

    public static boolean isLogin(HttpServletRequest request, RedisTemplate redisTemplate) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return false;
        }
        for (Cookie cookie : cookies) {
            if (COOKIE_LOGIN_KEY.equals(cookie.getName())) {
                String session = (String) redisTemplate.opsForValue().get(COOKIE_LOGIN_KEY);
                return session != null;
            }
        }
        return false;
    }

}
