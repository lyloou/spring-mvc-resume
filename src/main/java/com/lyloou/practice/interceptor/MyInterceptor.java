package com.lyloou.practice.interceptor;

import com.lyloou.practice.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lyloou
 * @date 2020/06/16
 * @desc
 */
public class MyInterceptor implements HandlerInterceptor {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (CookieUtil.isLogin(request, redisTemplate)) {
            return true;
        } else {
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }
    }
}
