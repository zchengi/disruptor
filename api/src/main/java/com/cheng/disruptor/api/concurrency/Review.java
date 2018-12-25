package com.cheng.disruptor.api.concurrency;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 并发编程回顾
 *
 * @author cheng
 *         2018/12/25 18:28
 */
@Slf4j
public class Review {
    public static void main(String[] args) throws InterruptedException {

        // ConcurrentHashMap
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>(16);

        // CopyOnWriteArrayList
        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add("aaa");

        // Atomic
        AtomicLong count = new AtomicLong(1);
        boolean flag = count.compareAndSet(0, 2);
        log.info("是否修改成功: {}, 当前值: {}", flag, count.get());


        // Object 锁
        Object lock = new Object();

        Thread A = new Thread(() -> {
            int sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += i;
            }

            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info("sum: {}", sum);
        });

        A.start();

        Thread.sleep(2000);
//        lock.notify(); // 不可以这样直接释放
        synchronized (lock) {
            lock.notify();
        }

        // LockSupport
        Thread B = new Thread(() -> {
            int sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += i;
            }

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 挂起（阻塞当前线程）
            // 如果后执行
            LockSupport.park();
            log.info("sum: {}", sum);
        });
        B.start();

        Thread.sleep(1000);
        // 恢复（唤醒指定线程）
        // 如果先执行
        LockSupport.unpark(B);


        // Executors
//        Executors.newCachedThreadPool();
//        Executors.newFixedThreadPool(10);

        // 参数:核心线程数，最大线程数量，线程池空闲回收时间，单位，有界队列/无界队列，线程工厂，拒绝策略
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                5,
                // JVM虚拟机运行时线程数
                Runtime.getRuntime().availableProcessors() * 2,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(200),
                r -> {

                    Thread t = new Thread(r);
                    t.setName("order-thread");

                    // 设置为非守护线程
                    if (t.isDaemon()) {
                        t.setDaemon(false);
                    }

                    // 线程优先级
                    if (Thread.NORM_PRIORITY == t.getPriority()) {
                        t.setPriority(Thread.NORM_PRIORITY);
                    }

                    return t;
                },
                (r, executor) -> log.info("拒绝策略: {}", r));
        pool.shutdown();

        // ReentrantLock
        ReentrantLock reentrantLock = new ReentrantLock(true);
    }
}
