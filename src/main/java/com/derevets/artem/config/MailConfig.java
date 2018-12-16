package com.derevets.artem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
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




    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", auth);
        mailProperties.put("mail.smtp.starttls.enable", starttls);
        mailProperties.put("mail.smtp.starttls.required", startlls_required);
        mailProperties.put("mail.smtp.socketFactory.port", socketPort);
        mailProperties.put("mail.smtp.debug", debug);
        mailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailProperties.put("mail.smtp.socketFactory.fallback", fallback);

        mailSender.setJavaMailProperties(mailProperties);
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setProtocol(protocol);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        return mailSender;
    }

}