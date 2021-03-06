package org.linker.foundation.resolver;

import org.linker.foundation.authority.AuthorityContext;
import org.linker.foundation.authority.AuthoritySession;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthorityArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return !parameter.hasParameterAnnotations() && parameter.getParameterType().equals(AuthoritySession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest request, WebDataBinderFactory binder) {
        return AuthorityContext.get().getSession();
    }
}
