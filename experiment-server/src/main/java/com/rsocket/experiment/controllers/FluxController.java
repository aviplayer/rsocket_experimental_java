package com.rsocket.experiment.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
public class FluxController {
    @MessageMapping("stringSplit")
    public Flux<String> stringSplit(String input) {
        return Flux.fromStream(input.codePoints().mapToObj(c ->
                String.valueOf((char) c))
        );
    }
}
