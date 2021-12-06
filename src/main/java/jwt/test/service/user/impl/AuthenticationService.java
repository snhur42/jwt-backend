package jwt.test.service.user.impl;

import jwt.test.dto.request.AuthenticationRequest;
import jwt.test.dto.request.RefreshTokenRequest;
import jwt.test.dto.response.AuthenticationResponse;
import jwt.test.model.token.RefreshToken;
import jwt.test.model.user.AppUser;
import jwt.test.security.jwt.provider.impl.JwtAccessTokenProvider;
import jwt.test.security.jwt.provider.impl.JwtRefreshTokenProvider;
import jwt.test.service.text.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

@Service
@Qualifier("authenticationService")
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenService refreshTokenService;
    private final JwtAccessTokenProvider jwtAccessTokenProvider;
    private final JwtRefreshTokenProvider jwtRefreshTokenProvider;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager,
                                 UserDetailsServiceImpl userDetailsService,
                                 RefreshTokenService refreshTokenService,
                                 JwtAccessTokenProvider jwtAccessTokenProvider,
                                 JwtRefreshTokenProvider jwtRefreshTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.refreshTokenService = refreshTokenService;
        this.jwtAccessTokenProvider = jwtAccessTokenProvider;
        this.jwtRefreshTokenProvider = jwtRefreshTokenProvider;
    }

    public AuthenticationResponse authenticate(HttpServletResponse response, AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            AppUser user = (AppUser) userDetailsService.loadUserByUsername(request.getUsername());

            String accessTokenString = jwtAccessTokenProvider.createToken(user.getId().toString(), request.getUsername(), user.getRole().name());
            String refreshTokenString = jwtRefreshTokenProvider.createToken(user.getId().toString(), request.getUsername(), user.getRole().name());

            RefreshToken refreshTokenObj = refreshTokenService.save(new RefreshToken(
                    user.getId().toString(),
                    refreshTokenString,
                    request.getFingerprint(),
                    jwtRefreshTokenProvider.getExpiredDate(refreshTokenString)
            ));

            setCookieToResponse(response, refreshTokenObj.getId().toString());

            return new AuthenticationResponse(true, accessTokenString);

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid email/password combination");
        }
    }



    public AuthenticationResponse refreshToken(HttpServletResponse response, RefreshTokenRequest refreshTokenRequest) {
        try {
            RefreshToken refreshToken = refreshTokenService.findById(refreshTokenRequest.getRefreshTokenId());

            boolean isRefreshTokenValid = jwtRefreshTokenProvider.validateToken(refreshToken.getRefreshToken());
            boolean isRefreshTokenHasTheSameUserId = refreshTokenRequest.getUserId().equals(UUID.fromString(refreshToken.getUserId()));
            boolean isExpired = refreshToken.getExpiresIn().after(new Date());
            boolean isFingerprintValid = refreshToken.getFingerPrint().equals(refreshTokenRequest.getFingerprint());

            if (isRefreshTokenValid && isExpired && isFingerprintValid && isRefreshTokenHasTheSameUserId) {

                AppUser user = (AppUser) userDetailsService.loadUserByUserId(UUID.fromString(refreshToken.getUserId()));

                String accessTokenString = jwtAccessTokenProvider.createToken(user.getId().toString(), user.getUsername(), user.getRole().name());
                String refreshTokenString = jwtRefreshTokenProvider.createToken(user.getId().toString(), user.getUsername(), user.getRole().name());

                long countRefreshToken = refreshTokenService.findAll().stream().filter(token -> token.getUserId().equals(refreshToken.getUserId())).count();

                if(countRefreshToken > 5) {
                    refreshTokenService.deleteAllByUserId(UUID.fromString(refreshToken.getUserId()));
                } else {
                    refreshTokenService.delete(refreshToken);
                }

                RefreshToken refreshTokenObj = refreshTokenService.save(new RefreshToken(
                        user.getId().toString(),
                        refreshTokenString,
                        refreshTokenRequest.getFingerprint(),
                        jwtRefreshTokenProvider.getExpiredDate(refreshTokenString)
                ));

                setCookieToResponse(response, refreshTokenObj.getId().toString());

                return new AuthenticationResponse(true, accessTokenString);
            } else  {
                refreshTokenService.delete(refreshToken);
            }
            return  new AuthenticationResponse(false, null);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid refreshToken");
        }
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    private void setCookieToResponse(HttpServletResponse response, String userId) {
        Cookie cookie = new Cookie("refreshToken", userId);
        cookie.setMaxAge(30 * 24 * 60 * 60);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
