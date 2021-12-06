package jwt.test.security.jwt.provider;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public interface JwtTokenProvider {
    public String createToken(String userId, String username, String role);
    public boolean validateToken(String token);
    public Authentication getAuthentication(String token);
    public String getUsername(String token) ;
    public String resolveToken(HttpServletRequest request) ;
    public Date getExpiredDate(String token);
}
