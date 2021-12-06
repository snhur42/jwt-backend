package jwt.test.service.user.impl;

import jwt.test.dto.UserDTO;
import jwt.test.model.user.AppUser;
import jwt.test.repository.user.UserRepository;
import jwt.test.service.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class UserServiceImpl<T extends AppUser, R extends UserRepository<T>> implements UserService<T> {
    @Value("${auth.jwt.noJwtTokenName}")
    private String noJwtTokenName;
    protected final R repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(R r, PasswordEncoder passwordEncoder) {
        super();
        this.repository = r;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public T save(T t) {
        t.setAccountNonExpired(true);
        t.setCredentialsNonExpired(true);
        t.setAccountNonLocked(true);
        t.setEnabled(true);
        t.setPassword(passwordEncoder.encode(t.getPassword()));
        return repository.save(t);
    }

    @Override
    public T update(UserDTO userDTO) {
        T t = repository.getById(UUID.fromString(userDTO.getId()));
        t.setFirstName(userDTO.getFirstName());
        t.setLastName(userDTO.getLastName());
        t.setEmail(userDTO.getEmail());
        t.setPhone(userDTO.getPhone());
        return repository.save(t);
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(repository.findAll());
    }

    @Override
    public T findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User was not found by this id: " + id));
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }


}
