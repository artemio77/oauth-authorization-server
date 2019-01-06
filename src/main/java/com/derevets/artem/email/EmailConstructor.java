package com.derevets.artem.email;

import com.derevets.artem.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@PropertySource("classpath:application.properties")
@Component
@Transactional
public class EmailConstructor {

    @Autowired
    private Environment env;

    @Autowired
    private JavaMailSender javaMailSender;

    private SimpleMailMessage constructRegisterEmailMessage(final User user) {
        final String recipientAddress = user.getEmail();
        final String subject = "Registration Confirmation";
        final String message = "Registration successful! ";
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText( message + user.getEmail() + " Verification Code - " + user.getVerificationCode());
        email.setFrom(env.getProperty("mail.from"));
        return email;
    }


    public void registerEmailSender(User user) {
        final SimpleMailMessage email = constructRegisterEmailMessage(user);
        javaMailSender.send(email);
    }
}
