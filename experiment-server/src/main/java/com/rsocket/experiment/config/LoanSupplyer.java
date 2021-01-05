package com.rsocket.experiment.config;

import com.rsocket.experiment.loan.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

@Configuration
public class LoanSupplyer {
    private final List<String> names = Arrays.asList("Donald", "Theresa", "Vladimir", "Angela",
            "Emmanuel", "Shinzō", "Jacinda", "Kim");
    private final List<Long> amounts = Arrays.asList(10L, 100L, 1000L, 10000L, 100000L,
            1000000L, 10000000L, 100000000L, 100000000L);

    private static final Logger log = LoggerFactory.getLogger(LoanSupplyer.class);

    @Bean("supplyLoan")
    @PostConstruct
    public Supplier<Loan> supplyLoan() {
        Supplier<Loan> loanSupplier = () -> {
            Loan loan = new Loan(UUID.randomUUID().toString(),
                    names.get(new Random().nextInt(names.size())),
                    amounts.get(new Random().nextInt(amounts.size())));
            log.info("{} {} for ${} for {}", loan.getStatus(), loan.getUuid(), loan.getAmount(), loan.getName());
            return loan;
        };

        return loanSupplier;
    }
}
