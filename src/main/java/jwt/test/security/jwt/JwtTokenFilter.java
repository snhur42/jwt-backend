package jwt.test.security.jwt;

import jwt.test.security.jwt.provider.impl.JwtAccessTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends GenericFilterBean {
    @Value("${auth.header.string}")
    String authHeader;
    private final JwtAccessTokenProvider jwtAccessTokenProvider;
    @Autowired
    public JwtTokenFilter(JwtAccessTokenProvider jwtAccessTokenProvider) {
        this.jwtAccessTokenProvider = jwtAccessTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String token = jwtAccessTokenProvider.resolveToken((HttpServletRequest) servletRequest);
        try {
            if (token != null && jwtAccessTokenProvider.validateToken(token)) {
                Authentication authentication = jwtAccessTokenProvider.getAuthentication(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtAuthenticationException e) {
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) servletResponse).sendError(e.getHttpStatus().value());
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
