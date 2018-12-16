package com.derevets.artem.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;


public class MailConfig {

    @Value("${mail.protocol}")
    protected String protocol;
    @Value("${mail.host}")
    protected String host;
    @Value("${mail.port}")
    protected Integer port;
    @Value("${mail.smtp.socketFactory.port}")
    protected Integer socketPort;
    @Value("${mail.smtp.auth}")
    protected Boolean auth;
    @Value("${mail.smtp.starttls.enable}")
    protected Boolean starttls;
    @Value("${mail.smtp.starttls.required}")
    protected Boolean startlls_required;
    @Value("${mail.smtp.debug}")
    protected Boolean debug;
    @Value("${mail.smtp.socketFactory.fallback}")
    protected Boolean fallback;
    @Value("${mail.from}")
    protected String from;
    @Value("${mail.username}")
    protected String username;
    @Value("${mail.password}")
    protected String password;
    @Value("email.server")
    protected String applicationServer;

}