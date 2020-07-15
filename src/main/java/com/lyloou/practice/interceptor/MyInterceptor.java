package com.lyloou.practice.interceptor;

import com.lyloou.practice.util.CookieUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lyloou
 * @date 2020/06/16
 * @desc
 */
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return CookieUtil.isLogined(request);
    }
}
