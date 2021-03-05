package com.feimao.netty.learning.reactor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * @Author: feimao
 * @Date: 20-2-26 下午7:53
 */
public class FluxSample {

    public static void main(String[] args) throws InterruptedException {
        Flux.just("foo", "bar")
            .publishOn(Schedulers.single())
            .subscribeOn(Schedulers.single())
            .subscribe(System.out::println, Throwable::printStackTrace);

        Thread.sleep(2000); // <--- wait for the flow to finish
    }
}
