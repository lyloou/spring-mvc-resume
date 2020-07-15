package com.lyloou.practice.controller;

import com.lyloou.practice.dao.ResumeDao;
import com.lyloou.practice.model.Resume;
import com.lyloou.practice.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class ResumeController {

    @Autowired
    private ResumeDao resumeDao;


    @RequestMapping("/")
    public String login(HttpServletRequest request) {
        if (CookieUtil.isLogined(request)) {
            return "redirect:list";
        }
        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, String username, String password) {
        // 已经登录直接跳转
        if (CookieUtil.isLogined(request)) {
            return "redirect:list";
        }

        // 验证用户名和密码
        if (!"admin".equals(username) || !"admin".equals(password)) {
            return "failed";
        }

        // 设置 cookie
        Cookie cookie = new Cookie(CookieUtil.COOKIE_LOGIN_KEY, CookieUtil.COOKIE_LOGIN_VALUE);
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);
        return "redirect:list";
    }

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        List<Resume> resumeList = resumeDao.findAll();
        modelAndView.setViewName("list");
        modelAndView.addObject("resumeList", resumeList);
        return modelAndView;
    }

    @RequestMapping("/addResume")
    @ResponseBody
    public int addResume(HttpServletRequest request, HttpServletResponse response, @RequestBody Resume resume) throws IllegalAccessException {
        Resume save = resumeDao.save(resume);
        if (save.getId() > 0) {
            return 0;
        }

        return -1;
    }

    @RequestMapping("/updateResume")
    @ResponseBody
    public int updateResume(HttpServletRequest request, HttpServletResponse response, @RequestBody Resume resume) throws IllegalAccessException {
        // 已经登录直接跳转
        if (resume.getId() == null) {
            return -1;
        }
        Resume save = resumeDao.save(resume);
        if (save.getId() > 0) {
            return 0;
        }

        return -1;
    }


    @RequestMapping("/deleteResume")
    @ResponseBody
    public int deleteResume(HttpServletRequest request, HttpServletResponse response, @RequestBody Resume resume) throws IllegalAccessException {
        // 已经登录直接跳转
        if (resume.getId() == null) {
            return -1;
        }
        resumeDao.deleteById(resume.getId());
        return 0;
    }
}
