package com.shtrade.tradeservice.util;

import com.shtrade.tradeservice.conf.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenServiceRedis tokenServiceRedis;

    @Autowired
    private Constant constant;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 方法未注明UserAuthorization，直接通过
        if (method.getAnnotation(UserAuthorization.class) == null) {
            return true;
        }
        //从header中得到token
        String authorization = request.getHeader(constant.getHeader_key_token());
        //验证token
        AuthToken authToken = tokenServiceRedis.getToken(authorization);
        if (tokenServiceRedis.checkToken(authToken)) {
            //如果token验证成功，将token对应的用户id存在request中，便于之后注入
            request.setAttribute(constant.getHeader_key_current_userid(), authToken.getUserId());
            return true;
        }
        //如果验证token失败，返回401错误
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        return false;
    }
}
