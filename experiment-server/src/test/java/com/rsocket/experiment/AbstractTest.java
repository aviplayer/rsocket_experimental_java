package com.rsocket.experiment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeTypeUtils;

import java.net.URI;

abstract class AbstractTest {

    @Value("${spring.rsocket.server.port}")
    private int serverPort;
    @Autowired
    private RSocketRequester.Builder builder;

    RSocketRequester createRSocketRequester() {
        return builder.dataMimeType(MimeTypeUtils.TEXT_PLAIN)
               .connectWebSocket(URI.create("ws://localhost:7100/route"))
                
                //.connect(TcpClientTransport.create(6565))
                .block();
    }
}
