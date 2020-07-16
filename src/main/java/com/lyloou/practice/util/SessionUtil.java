package com.lyloou.practice.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author lyloou
 * @date 2020/06/16
 * @desc
 */
public class SessionUtil {

    public static boolean isLogined(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object username = session.getAttribute("username");
        System.out.println("username==>:" + username);
        return username != null;
    }

}
