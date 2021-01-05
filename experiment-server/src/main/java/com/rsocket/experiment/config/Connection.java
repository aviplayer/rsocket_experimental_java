package com.rsocket.experiment.config;

import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Connection {

    @Bean
    void createRsocket() {
        Server.start();
        RSocketConnector.connectWith(TcpClientTransport.create(6565))
                .block();
    }

}
