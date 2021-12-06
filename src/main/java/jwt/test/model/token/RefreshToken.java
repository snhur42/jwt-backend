package jwt.test.model.token;

import jwt.test.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "RefreshToken")
@Table(name = "refresh_token",
        uniqueConstraints = {
                @UniqueConstraint(name = "refresh_token_unique", columnNames = "refresh_token"),
        }
)
public class RefreshToken extends AbstractEntity {
    @Column(name = "user_id", nullable = false, columnDefinition = "TEXT")
    private String userId;
    @Column(name = "refresh_token", nullable = false, columnDefinition = "TEXT")
    private String refreshToken;
    @Column(name = "finger_print", nullable = false, columnDefinition = "TEXT")
    private String fingerPrint;
    @Column(name = "expires_in", nullable = false, columnDefinition = "TIMESTAMP")
    private Date expiresIn;
}