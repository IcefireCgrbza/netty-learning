package com.feimao.netty.learning.rxjava;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author: feimao
 * @Date: 20-2-26 下午7:40
 */
public class RxjavaExample {

    public static void main(String[] args) throws InterruptedException {
        Flowable.fromCallable(() -> {
            Thread.sleep(1000); //  imitate expensive computation
            return "Done";
        })
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single())
            .subscribe(System.out::println, Throwable::printStackTrace);

        Thread.sleep(2000); // <--- wait for the flow to finish
    }
}
