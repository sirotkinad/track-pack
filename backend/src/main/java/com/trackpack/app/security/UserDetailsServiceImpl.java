package com.trackpack.app.security;

import com.trackpack.app.model.user.User;
import com.trackpack.app.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService service;

    public UserDetailsServiceImpl(UserService service) {
        this.service = service;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
       User user = service.findByEmail(email).
               orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " is not found"));
       return UserDetailsImpl.buildFromUser(user);
    }

}
