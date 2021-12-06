package jwt.test.security.jwt.provider.impl;


import jwt.test.service.user.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtAccessTokenProvider extends JwtTokenProviderImpl {

    public JwtAccessTokenProvider(@Qualifier("userDetailsServiceImpl") UserDetailsServiceImpl userDetailsService,
                                  @Value("${auth.jwt.secret.access.token}") String secretKey,
                                  @Value("${auth.jwt.expiration.access.token}") long validityInMilliseconds) {
        super(userDetailsService, secretKey, validityInMilliseconds);
    }
}
