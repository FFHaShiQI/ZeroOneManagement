package com.hajimi.backend.interceptor;

import com.hajimi.backend.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/login")||requestURI.contains("/register")||requestURI.contains("/senior/groups/All")) {
            log.info("登录请求");
            return true;
        }
        String token = request.getHeader("token");
        if(token==null) {
            log.info("令牌为空,未登录");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        try {
            JwtUtils.parseJwt(token);
        }catch (Exception e){
            log.info("令牌非法");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        log.info("放行");
        return true;
    }
}
