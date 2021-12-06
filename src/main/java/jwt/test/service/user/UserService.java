package jwt.test.service.user;

import jwt.test.dto.UserDTO;
import jwt.test.model.user.AppUser;

import java.util.List;
import java.util.UUID;

public interface UserService<T extends AppUser> {
    T save(T t);
    T update(UserDTO userDTO);
    T findById(UUID id);
    List<T> findAll();
    void deleteById(UUID id);
}