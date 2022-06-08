package com.ssung.travelDiary.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Iterator;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

//        log.info("인터셉터 요청 = {}", requestURI);

        HttpSession session = request.getSession(false);

        if (session == null) {
//            log.info("미인증 사용자 요청");
            response.sendRedirect("/login?redirectURL=" + requestURI);
            return false;
        } else {
//            Iterator<String> stringIterator = session.getAttributeNames().asIterator();
//            while (stringIterator.hasNext()) {
//                String sessionName = stringIterator.next();
//                log.info("name = {} | sessionValue = {}", sessionName, session.getAttribute(sessionName));
//            }
        }

        return true;
    }

//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        throw ex;
//    }
}
