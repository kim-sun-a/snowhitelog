package com.snowhitelog.config;

import com.snowhitelog.config.data.UserSection;
import com.snowhitelog.domain.Session;
import com.snowhitelog.exception.Unauthorized;
import com.snowhitelog.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSection.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String accessToken = webRequest.getHeader("Authorization");
        if (accessToken == null  || accessToken.equals("")) {
            throw new Unauthorized();
        }

        Session session = sessionRepository.findByAccessToken(accessToken).orElseThrow(Unauthorized::new);

        return new UserSection(session.getUser().getId());
    }
}
