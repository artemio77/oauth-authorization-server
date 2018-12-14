package com.derevets.artem.controller;


import com.derevets.artem.model.User;
import com.derevets.artem.service.UserService;
import com.google.common.io.BaseEncoding;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;




@RequestMapping({"/auth"})
@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final TokenStore tokenStore;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, AuthenticationManager authenticationManager, TokenStore tokenStore) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.tokenStore = tokenStore;
    }

    @PutMapping("/register")
    @Transactional
    public ResponseEntity<User> create(@RequestBody User user) {
        log.info(user.toString());
        User newUser = User.getBuilder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(passwordEncoder.encode(user.getPassword()))
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonLocked(true)
                .isEnabled(false)
                .build();
        userService.registerNewAccount(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @GetMapping(path = "/{code}")
    public ResponseEntity<Long> checkCodeByEmail(@PathVariable("code") Long code) {
        log.info(code.toString());
        User user = userService.findUserByEmail(userService.checkUserVerificationCode(code));
        if (!user.getIsEnabled()) {
            user.setIsEnabled(true);
            user.setVerificationCode(null);
            userService.updateUser(user);
        }
        return new ResponseEntity<Long>(user.getVerificationCode(), HttpStatus.OK);
    }

    @GetMapping("/exist/{email:.+}")
    public Boolean existUserByEmail(@PathVariable String email) {
        if (userService.findUserByEmail(email) != null) {
            log.info("true");
            return true;
        }
        log.info("false");
        return false;
    }

    @GetMapping("/auth/checkMongoReplicate/{email:.+}")
    public Boolean isUserAlreadyActivated(@PathVariable String email) {
        User user = userService.findUserByEmail(email);
        if (user != null && user.getIsEnabled()) {
            log.info("true");
            return true;
        }
        log.info("false");
        return false;
    }

    @RequestMapping(value = "/user/{email:.+}", method = RequestMethod.GET)
    public ResponseEntity<User> user(@PathVariable("email") String email) {
        User user = userService.findUserByEmail(email);
        log.info("User,{}", user.toString());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/getToken/{email:.+}", method = RequestMethod.GET)
    public ResponseEntity<String> getUserToken(@PathVariable("email") String email) {
        Map<String, String> userTokenStore = new HashMap<>();
        String token = tokenStore.findTokensByClientIdAndUserName("fooClientIdPassword", email)
                .stream()
                .map(OAuth2AccessToken::getValue).collect(Collectors.toList()).get(0);
        log.info(token);
        return new ResponseEntity<String>(token, HttpStatus.OK);
    }

}
