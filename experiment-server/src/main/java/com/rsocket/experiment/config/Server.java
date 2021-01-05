package com.rsocket.experiment.config;

import io.rsocket.core.RSocketServer;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.server.TcpServerTransport;

public class Server {

    public static void start() {
        RSocketServer.create()
        .acceptor(new SimpleRSocketAcceptor())
        .payloadDecoder(PayloadDecoder.ZERO_COPY)
        .bind(TcpServerTransport.create(6565))
                .subscribe();
    }
}

