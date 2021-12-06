package jwt.test.service.text;

import jwt.test.model.token.RefreshToken;
import jwt.test.repository.text.RefreshTokenRepository;
import jwt.test.service.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
@Qualifier("refreshToken")
public class RefreshTokenService extends EntityServiceImpl<RefreshToken, RefreshTokenRepository> {
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        super(refreshTokenRepository);
    }

    public boolean isPresent(String refreshToken){
        return repository.findByRefreshToken(refreshToken).isPresent();
    }

    public RefreshToken findByRefreshToken(String refreshToken){
        return repository.findByRefreshToken(refreshToken).get();
    }

    public void deleteAllByUserId(UUID userId) {
        repository.deleteAllByUserId(userId.toString());
    }
}




