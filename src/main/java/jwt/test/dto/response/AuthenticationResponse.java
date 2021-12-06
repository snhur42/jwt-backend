package jwt.test.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private boolean success ;
    private String accessToken;
}