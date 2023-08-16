package com.snowhitelog.config;

import com.snowhitelog.config.data.UserSection;
import com.snowhitelog.exception.Unauthorized;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSection.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String accessToken = webRequest.getParameter("accessToken");
        if (accessToken == null  || accessToken.equals("")) {
            throw new Unauthorized();
        }
        UserSection userSection = new UserSection();
        userSection.name = accessToken;
        return userSection;
    }
}
