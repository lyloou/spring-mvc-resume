package com.lyloou.practice.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lyloou
 * @date 2020/06/16
 * @desc
 */
public class CookieUtil {
    public static final String COOKIE_LOGIN_KEY = "logined";
    public static final String COOKIE_LOGIN_VALUE = "true";


    public static boolean isLogined(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return false;
        }
        for (Cookie cookie : cookies) {
            if (COOKIE_LOGIN_KEY.equals(cookie.getName()) && COOKIE_LOGIN_VALUE.equals(cookie.getValue())) {
                return true;
            }
        }
        return false;
    }

}
