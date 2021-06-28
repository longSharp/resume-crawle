package com.npp.resumec.resumepro.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author 龙朝敏
 * @describe
 * @create 2021-06-26
 */
public class ThreadPoolUtil {

    /**

     * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。

     * 即：相当于FixedThreadPool(1)

     * 注:创建一个单线程的线程池。这个线程池只有一个线程在工作，也就是相当于单线程串行执行所有任务。

     * 如果这个唯一的线程因为异常结束，那么会有一个新的线程来替代它。

     * 此线程池保证所有任务的执行顺序按照任务的提交顺序执行。

     */

    public static ExecutorService getSingleThread(){
        ExecutorService exec = Executors.newSingleThreadExecutor();

        return exec;

    }

    /**

     * 创建固定大小的线程池。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。

     * 线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程。

     */

    public static ExecutorService getFixedThreadPool(int count){
        ExecutorService exec = Executors.newFixedThreadPool(count);
        return exec;

    }

    /**

     * 创建一个定长线程池，支持定时及周期性任务执行

     */

    public static ExecutorService getScheduledThreadPool(int count){
        ExecutorService exec = Executors.newScheduledThreadPool(count);

        return exec;

    }

    /**

     * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。

     * 即：可变大小线程池，按照任务数来分配线程

     */

    public static ExecutorService getCachedThreadPool(){
        ExecutorService exec = Executors.newCachedThreadPool();

        return exec;

    }

    /**

     * 结束线程时调用，一定要调用这个方法，不然executorService.isTerminated()永远不为true

     * @param executor

     * @throws Exception

     */

    public static void endThread(ExecutorService executor) throws Exception {
        TimeUnit.MILLISECONDS.sleep(1000);

        executor.shutdown();//关闭线程池

        while (true) {
            if (executor.isTerminated()) {
                System.out.println(getClassName()+"******************所有的子线程都结束了********************");

                break;

            }

            TimeUnit.MILLISECONDS.sleep(200);

        }

    }

    /**

     * 获取调用静态类的类名

     */

    private static String getClassName() {
        return new SecurityManager() {
            public String getClassName() {
                return getClassContext()[3].getName();

            }

        }.getClassName();

    }
}
