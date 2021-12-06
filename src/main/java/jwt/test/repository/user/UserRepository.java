package jwt.test.repository.user;

import jwt.test.model.user.AppUser;
import jwt.test.repository.EntityRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface UserRepository<T extends AppUser> extends EntityRepository<T> {
    Optional<T> findByEmail(String email);

}
