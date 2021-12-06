package jwt.test.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class RefreshTokenRequest {
    private UUID userId;
    private UUID refreshTokenId;
    private String fingerprint;
}
