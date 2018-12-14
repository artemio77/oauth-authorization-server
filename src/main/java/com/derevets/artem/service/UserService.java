package com.derevets.artem.service;

import com.derevets.artem.email.EmailConstructor;
import com.derevets.artem.model.User;
import com.derevets.artem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailConstructor emailConstructor;


    public void registerNewAccount(final User user) {
        user.setVerificationCode(generateUniqueVerificationCode());
        userRepository.save(user);
        emailConstructor.registerEmailSender(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public String checkUserVerificationCode(Long code) {
        return userRepository.checkUniqueVerificationCode(code);
    }

    private Long generateUniqueVerificationCode() {
        for (; ; ) {
            Random r = new Random();
            Long code = r.longs(1, 10000, 99999).findFirst().getAsLong();
            log.info(code.toString());
            if (userRepository.checkUniqueVerificationCode(new Random()
                    .longs(1, 10000, 99999).findAny().getAsLong()) == null) {
                return code;
            }
        }
    }


}
