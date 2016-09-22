package com.ancx.ancxutil.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 管理线程的工具类(线程池)
 * Created by Ancx.
 */
public class ThreadUtil {

    /**
     * 线程池
     */
    private final static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 2);

    /**
     * 从线程池里开启一个线程
     *
     * @param runnable
     */
    public static void startThread(Runnable runnable) {
        executorService.execute(runnable);
    }

}
