package com.lyloou.practice.controller;

import com.lyloou.practice.dao.ResumeDao;
import com.lyloou.practice.model.Resume;
import com.lyloou.practice.util.CookieUtil;
import com.lyloou.practice.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ResumeController {

    @Autowired
    private ResumeDao resumeDao;


    @RequestMapping("/")
    public String login(HttpServletRequest request) {
        if (SessionUtil.isLogined(request)) {
            return "redirect:list";
        }
        return "index";
    }

    @RequestMapping("/login")
    public String loginSystem(String username, String password, HttpSession session) {
        // 合法用户，信息写入session，同时跳转到系统主页面
        if ("admin".equals(username) && "admin".equals(password)) {
            System.out.println("合法用户");
            session.setAttribute("username", username);
            return "redirect:list";
        } else {
            // 非法用户返回登录页面
            System.out.println("非法，跳转");
            return "redirect:/";
        }
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
