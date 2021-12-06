package jwt.test.repository.text;

import jwt.test.model.token.RefreshToken;
import jwt.test.repository.EntityRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RefreshTokenRepository extends EntityRepository<RefreshToken> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    @Modifying
    @Query(value = "delete from RefreshToken r where (r.userId) = (?1)")
    void deleteAllByUserId(String userId);

}
