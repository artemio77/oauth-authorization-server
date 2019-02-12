package com.derevets.artem.controller;


import com.derevets.artem.model.User;
import com.derevets.artem.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@RequestMapping({"/auth"})
@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final TokenStore tokenStore;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, TokenStore tokenStore, RabbitTemplate rabbitTemplate) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.tokenStore = tokenStore;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PutMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws IOException, JSONException {
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
        log.info("user {}", newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @GetMapping(path = "/{code}")
    public ResponseEntity<Long> checkCodeByEmail(@PathVariable("code") Long code) {
        log.info(code.toString());
        User user = userService.activateUser(code);
        return new ResponseEntity<>(user.getVerificationCode(), HttpStatus.OK);
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

    @GetMapping(value = "/user/{email:.+}")
    public ResponseEntity<User> user(@PathVariable("email") String email) {
        User user = userService.findUserByEmail(email);
        log.info("User,{}", user.toString());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/getToken/{email:.+}")
    public ResponseEntity<String> getUserToken(@PathVariable("email") String email) {
        Map<String, String> userTokenStore = new HashMap<>();
        String token = tokenStore.findTokensByClientIdAndUserName("fooClientIdPassword", email)
                .stream()
                .map(OAuth2AccessToken::getValue).collect(Collectors.toList()).get(0);
        log.info(token);
        return new ResponseEntity<String>(token, HttpStatus.OK);
    }

}
