package com.ssung.travelDiary.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpServletRequest request, HttpServletResponse response) {

//        Cookie cookie = new Cookie("userId", "1");
//        cookie.setMaxAge(3600);
//        cookie.setDomain("example.org");
//        cookie.setPath("/");
//        cookie.setSecure(true);
//        cookie.setHttpOnly(true);
//
//        response.addCookie(cookie);
//
//        Cookie[] cookies = request.getCookies();

        return "redirect:/login";
    }
}
