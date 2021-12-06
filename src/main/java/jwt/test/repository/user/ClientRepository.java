package jwt.test.repository.user;

import jwt.test.model.user.roles.Client;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends UserRepository<Client> {
}