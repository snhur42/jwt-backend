package jwt.test.security.jwt.provider.impl;

import jwt.test.service.user.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtRefreshTokenProvider extends JwtTokenProviderImpl  {

    public JwtRefreshTokenProvider(@Qualifier("userDetailsServiceImpl") UserDetailsServiceImpl userDetailsService,
                                   @Value("${auth.jwt.secret.refresh.token}") String secretKey,
                                   @Value("${auth.jwt.expiration.refresh.token}") long validityInMilliseconds) {
        super(userDetailsService, secretKey, validityInMilliseconds);
    }
}
