package com.derevets.artem.config.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.URI;
import java.net.URISyntaxException;


@Slf4j
@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.url}")
    private String rabbitMqUrl;

    @Bean
    public ConnectionFactory connectionFactory() throws URISyntaxException {
        URI uri = new URI(rabbitMqUrl);
        return new CachingConnectionFactory(uri);
    }

    @Bean
    public AmqpAdmin amqpAdmin() throws URISyntaxException {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() throws URISyntaxException {
        return new RabbitTemplate(connectionFactory());
    }
}
