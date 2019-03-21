package com.royasoft.component.cag.service.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class deded {

    public static void main(String[] args) throws Exception {
//        nativeFuture();
        guavaFuture();
//        guavaFuture2();
        System.out.println("================main====end=================");
    }

    public static void nativeFuture() throws Exception {
        // 原生的Future模式,实现异步
        ExecutorService exec = Executors.newSingleThreadExecutor();
        System.out.println("======1=====");
        Future<String> future = exec.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName());
                return "success";
            }
        });
        System.out.println("======2=====");
        // 阻塞获取结果，Future只实现了异步，没有回调，没有监听
        System.out.println(Thread.currentThread().getName() + "\t" + future.get());
        exec.shutdown();
    }

    // 线程的异步执行和监听不是同一个线程
    public static void guavaFuture() throws Exception {
        System.out.println("-------------------------------- 神秘的分割线one -----------------------------------");
        // 好的实现应该是提供回调,即异步调用完成后,可以直接回调.本例采用guava提供的异步回调接口,方便很多.
        final ListeningExecutorService guavaExecutor = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        final ListenableFuture<String> listenableFuture = guavaExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\taaa");
                return "aaa";
            }
        });

        // 注册监听器,即异步调用完成时会在指定的线程池中执行注册的监听器，可以添加两个监听
        listenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("one结果是：" + listenableFuture.get());
                    System.out.println(Thread.currentThread().getName() + "\tbbb");
                } catch (Exception e) {
                }
            }
        }, Executors.newSingleThreadExecutor());

        // 主线程可以继续执行,异步完成后会执行注册的监听器任务.
        System.out.println("[" + Thread.currentThread().getName() + "]: guavaFuture1执行结束");

    }

    // 线程的异步执行和回调是同一个线程
    public static void guavaFuture2() throws Exception {
        System.out.println("-------------------------------- 神秘的分割线two -----------------------------------");
        ListeningExecutorService guavaExecutor2 = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        final ListenableFuture<String> listenableFuture2 = guavaExecutor2.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\tccc");
                return "ccc";
            }
        });

        // 注意这里没用指定执行回调的线程池,从输出可以看出，默认是和执行异步操作的线程是同一个
        Futures.addCallback(listenableFuture2, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("two结果是：" + result);
                System.out.println(Thread.currentThread().getName() + "\tddd");
            }

            @Override
            public void onFailure(Throwable t) {}
        });
        // 主线程可以继续执行,异步完成后会执行注册的监听器任务.
        System.out.println("[" + Thread.currentThread().getName() + "]: guavaFuture2执行结束");
        guavaExecutor2.shutdown();
    }
}
