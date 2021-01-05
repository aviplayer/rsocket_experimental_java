package com.rsocket.experiment;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@RequiredArgsConstructor
public class EchoServerTest extends AbstractTest {
    @Test
    @DisplayName("Test echo server")
    void testEcho() {
        RSocketRequester requester = createRSocketRequester();
        Flux<String> response = requester
                .route("stringSplit")
                .data("hello")
                .retrieveFlux(String.class)
                .map(test -> {
                    System.out.println(test);
                    return test;
                });
        StepVerifier.create(response)
                .expectNext("h", "e", "l", "l", "o")
                .expectComplete()
                .verify();

    }

    @Test
    @DisplayName("Test Tcp server")
    void testTcp() {
        RSocketRequester requester = createRSocketRequester();
        Mono<String> response = requester
                .route("stringSplit")
                .data("hello")
                .retrieveMono(String.class)
                .map(test -> {
                    System.out.println(test);
                    return test;
                });
        StepVerifier.create(response)
                .expectNext("test")
                .expectComplete()
                .verify();

    }
}
