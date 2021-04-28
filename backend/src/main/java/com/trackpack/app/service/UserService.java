package com.trackpack.app.service;

import com.trackpack.app.model.user.User;
import com.trackpack.app.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void add(User user) {
        user.setPassword(user.getPassword());
        repository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public void updatePassword(String email, String password) {
        User user = findByEmail(email).get();
        user.setPassword(passwordEncoder.encode(password));
    }

}
