package com.mine.gublog.interceptor;

import com.mine.gublog.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // jwt 认证
        String token = request.getHeader("Authorization");
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    log.debug("Cookie : " + cookies[i].getName() + " = " + cookies[i].getValue());
                    if ("token".equals(cookies[i].getName())) {
                        token = cookies[i].getValue();
                        break;
                    }
                }
            }
        }
        boolean access = token != null && !jwtTokenUtil.isTokenExpired(token);
        log.debug(request.getRemoteAddr() + " " + request.getMethod() + " " + request.getRequestURI() + " " + access);
        return access;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
