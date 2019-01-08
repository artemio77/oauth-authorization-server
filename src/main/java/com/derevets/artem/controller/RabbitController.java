package com.derevets.artem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/auth")
public class RabbitController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpTemplate template;

    @RequestMapping("/emit")
    @ResponseBody
    public String queue1() {
        log.info("Emit to queue1");
        template.convertAndSend("queue1", "Message to queue");
        return "Emit to queue";
    }

    @RequestMapping("/queue")
    @ResponseBody
    public String sendIntoQueue() {
        log.info("Emit to queue");
        for (int i = 0; i < 10; i++) {
            log.info("i, {}", i);
            template.convertAndSend("query-example-2", "Message " + i);
        }
        return "Emit to queue";
    }

    @RequestMapping("/subscribe/queue")
    public String sendSubscribe() {
        log.info("Emit to exchange-example-3");
        rabbitTemplate.setExchange("exchange-example-subscribe");
        rabbitTemplate.convertAndSend("Fanout message");
        return "Emit to subscribe queue";
    }

    @RequestMapping("/route/error")
    public String sendRouteError() {
        log.info("Emit as error");
        rabbitTemplate.setExchange("route");
        rabbitTemplate.convertAndSend("error", "Error");
        return "Emit as error";
    }

    @RequestMapping("/route/info")
    public String sendRouteInfo() {
        log.info("Emit as info");
        rabbitTemplate.setExchange("route");
        rabbitTemplate.convertAndSend("info", "Info");
        return "Emit as info";
    }

    @RequestMapping("/route/warning")
    public String sendRouteWarning() {
        log.info("Emit as warning");
        rabbitTemplate.setExchange("route");
        rabbitTemplate.convertAndSend("warning", "Warning");
        return "Emit as warning";
    }

}


