package com.rsocket.experiment.config;

import com.rsocket.experiment.loan.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class LoanConsumer {
    private static final Logger log = LoggerFactory.getLogger(LoanConsumer.class);

    @Bean
    @DependsOn({"supplyLoan"})
    public Function<Flux<Message<Loan>>, Mono<Void>> consumeLoan() {
        return this::performMessage;
    }

    private Mono<Void> performMessage(Flux<Message<Loan>> flux) {
        return flux
                .map(val -> {
                    var loaninfo = val.getPayload();
                    return loaninfo;
                })
                .doOnNext(loanData -> {
                    log.info("=== Full loan info: {} ===", loanData);
                })
                .map(one -> Mono.empty())
                .doOnError(error -> {
                    log.error(error.getMessage());
                })
                .collectList()
                .flatMap(any -> Mono.empty());
    }
}
