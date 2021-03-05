package com.feimao.netty.learning.reactor;

import reactor.core.publisher.Flux;
import reactor.netty.http.server.HttpServer;

/**
 * @Author: feimao
 * @Date: 20-2-26 下午7:58
 */
public class ReactorNetty {

    public static void main(String[] args) {
        HttpServer.create()
            .host("0.0.0.0")
            .handle((req, res) -> res.sendString(Flux.just("hello")))
            .bind()
            .block();
    }
}
