package com.feimao.netty.learning.reactor;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @Author: feimao
 * @Date: 20-2-26 下午7:54
 */
public class MonoSample {

    public static void main(String[] args) throws InterruptedException {
        Mono.just("foo")
            .publishOn(Schedulers.single())
            .subscribeOn(Schedulers.single())
            .subscribe(System.out::println, Throwable::printStackTrace);

        Thread.sleep(2000); // <--- wait for the flow to finish
    }
}
