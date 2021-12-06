package jwt.test.rest;

import jwt.test.dto.request.AuthenticationRequest;
import jwt.test.dto.response.AuthenticationResponse;
import jwt.test.dto.request.RefreshTokenRequest;
import jwt.test.service.user.impl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthenticationRestController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationRestController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(HttpServletResponse response, @RequestBody AuthenticationRequest request) {
        return new ResponseEntity<>(authenticationService.authenticate(response, request), HttpStatus.OK);
    }


    @PostMapping("/refresh_token")
    public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request1, HttpServletResponse response, @RequestBody RefreshTokenRequest request) {
        Cookie[] cookies = request1.getCookies();
        if (cookies != null) {
            String cookiesss = Arrays.stream(cookies)
                    .map(c -> c.getName() + "=" + c.getValue())
                    .collect(Collectors.joining(", "));

            System.out.println(cookiesss);
        }
        return new ResponseEntity<>(authenticationService.refreshToken(response, request), HttpStatus.OK);
    }

//    @PostMapping("/logout")
//    public void logout(HttpServletRequest request, HttpServletResponse response) {
//        authenticationService.logout(request, response);
//    }
}
