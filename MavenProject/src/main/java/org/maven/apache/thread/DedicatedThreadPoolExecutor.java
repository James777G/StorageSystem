package org.maven.apache.thread;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;

/**
 * 线程池
 * ==============================================================
 * 在项目中所有的异步操作都应该由线程池来进行完成
 * <p>
 * 具体操作：
 * <p>
 * 1. 使用继承于 ThreadPoolExecutor 的 execute() 方法
 * 2. 传入想运行的实现了 Runnable 接口的异步操作函数
 * <p>
 * ===============================================================
 * 参数配置：
 * <p>
 * 1. 本线程池由 Spring 管理
 * 2. 本线程池核心线程数为 2
 * 3. 本线程池最大线程数为 4
 * 4. 本线程池线程最大存活时间为 60秒
 * 5. 任务队列上限数量为 10
 * <p>
 * ================================================================
 * 注意事项：
 * <p>
 * 1. 考虑到项目的需求， 本线程池并没有配置拒绝机制
 * 2. 同一时间所能执行的异步工作数量为 14
 * 3. 若超出 14 则会报错
 *
 */
public class DedicatedThreadPoolExecutor extends ThreadPoolExecutor {
    public DedicatedThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, @NotNull TimeUnit unit, @NotNull BlockingQueue<Runnable> workQueue, @NotNull RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }


}
