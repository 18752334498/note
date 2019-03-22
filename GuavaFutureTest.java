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

public class GuavaFutureTest {

    /**
     *  原生的future和guava的future都可以阻塞获取结果
     */
    public static void main(String[] args) throws Exception {
        nativeFuture();
        guavaFuture();
    }

    public static void nativeFuture() throws Exception {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        Future<String> future = exec.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("exec: " + Thread.currentThread().getName());
                return "success";
            }
        });
        
        // Future只实现了异步，没有回调，没有监听
        // 阻塞等待结果
        System.out.println(Thread.currentThread().getName() + "\t" + future.get());
    }


    public static void guavaFuture() throws Exception {
        ListeningExecutorService exec = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        final ListenableFuture<String> future = exec.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("exec: " + Thread.currentThread().getName());
                return "success";
            }
        });
        
//        如果需要对结果处理，就get()阻塞等待结果，回调就没有意义，那么就可以用原生的future阻塞等待
//        System.out.println("future: " + future.get());

        // 如果对主业务没有影响，就可以用回调方法等待结果，主业务继续执行，等回调执行完再处理结果返回给用户，适用于springmvc异步
        Futures.addCallback(future, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("结果是：" + result);
                System.out.println("callback: " + Thread.currentThread().getName());
            }
            @Override
            public void onFailure(Throwable t) {}
        });
        System.out.println(exec.isShutdown());
    }
}
