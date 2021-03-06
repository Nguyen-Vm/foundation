package org.linker.foundation.authority;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author RWM
 * @date 2018/9/20
 */
public interface IAuthenticationFilter extends Filter {

    /**
     * override to implements your own web AuthoritySession logic
     */
    default void authentication(AuthoritySession session,
                                HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain chain) throws Exception {
        AuthorityContext.get().setSession(session);
        chain.doFilter(request, response);
    }

    @Override
    default void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    default void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            AuthoritySession session = AuthoritySession.create(request);
            authentication(session, request, response, filterChain);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    default void destroy() {

    }
}
