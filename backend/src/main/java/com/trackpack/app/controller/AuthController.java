package com.trackpack.app.controller;

import com.trackpack.app.exceptions.ResourceAlreadyExistsException;
import com.trackpack.app.model.user.NewUser;
import com.trackpack.app.model.user.RegisteredUser;
import com.trackpack.app.model.user.User;
import com.trackpack.app.security.JWTProvider;
import com.trackpack.app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JWTProvider jwtProvider;

    public AuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserService userService, JWTProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }


    @PostMapping("/signIn")
    public ResponseEntity<RegisteredUser> signIn(@Valid @RequestBody RegisteredUser registeredUser) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registeredUser.getEmail(), registeredUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userService.findByEmail(registeredUser.getEmail()).get();
        registeredUser.setId(user.getId());
        registeredUser.setToken(jwtProvider.generateToken(user.getEmail()));
        return ResponseEntity.ok().body(registeredUser);
    }

    @PostMapping("/signUp")
    public ResponseEntity<User> signUp(@Valid @RequestBody NewUser newUser) {
        if(userService.findByEmail(newUser.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("User with email " + newUser.getEmail() + " is already exists");
        }
        User user = new User(newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), passwordEncoder.encode(newUser.getPassword()));
        userService.add(user);
        return ResponseEntity.ok().body(user);
    }

}
