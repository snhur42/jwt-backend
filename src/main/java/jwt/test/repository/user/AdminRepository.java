package jwt.test.repository.user;

import jwt.test.model.user.roles.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends UserRepository<Admin> {
}