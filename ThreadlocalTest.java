package com.royasoft.component.cag.service.impl;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class ThreadlocalTest {
    
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    private static CyclicBarrier barrier = new CyclicBarrier(3);

    public static void main(String[] args) throws Exception {
        Thread man1 = new Thread(new Man(threadLocal, barrier), "man1");
        Thread man2 = new Thread(new Man(threadLocal, barrier), "man2");
        Thread man3 = new Thread(new Man(threadLocal, barrier), "man3");
        man1.start();
        man2.start();
        man3.start();
    }
}

class Man implements Runnable{
    private ThreadLocal<Integer> threadLocal = null;
    private CyclicBarrier barrier = null;
    
    public Man(ThreadLocal<Integer> threadLocal, CyclicBarrier barrier) {
        this.threadLocal = threadLocal;
        this.barrier = barrier;
    }
    
    @Override
    public void run() {
        try {
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int a = new Random().nextInt(1000);
        for (int i = 0; i < a; i++) {
            if (threadLocal.get() == null) {
                threadLocal.set(1);
            } else {
                threadLocal.set(threadLocal.get() + 1);
            }
        }
        
        System.out.println(Thread.currentThread().getName() + "，累加次数： " + a + "\t" + threadLocal.get());
    }
}

