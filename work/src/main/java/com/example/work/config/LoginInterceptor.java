package com.example.work.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Object user = request.getSession().getAttribute("user");
        // 此处获取浏览器内存储的cookie，并判断是否存在user属性
        if (null == user) {
            response.sendRedirect("/login");
        }
        return true;
    }
}
