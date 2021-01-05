package com.rsocket.experiment.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@AllArgsConstructor
public class EchoController {
    
    @MessageMapping("echo")
    public Mono<String> echo(String input) {
        return Mono.just(input);
    }
}
