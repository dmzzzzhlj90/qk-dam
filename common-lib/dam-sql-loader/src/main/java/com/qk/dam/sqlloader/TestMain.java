package com.qk.dam.sqlloader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class TestMain {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        new TestMain().exec();
    }

    void exec() throws InterruptedException, ExecutionException {
        //进行异步任务列表
        List<FutureTask<Integer>> futureTasks = new ArrayList<FutureTask<Integer>>();
        //线程池 初始化十个线程 和JDBC连接池是一个意思 实现重用
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        long start = System.currentTimeMillis();
        //类似与run方法的实现 Callable是一个接口，在call中手写逻辑代码
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Integer res = new Random().nextInt(100);
                Thread.sleep(1000);
                System.out.println("任务执行:获取到结果 :" + res);
                return res;
            }
        };

        for (int i = 0; i < 10; i++) {
            //创建一个异步任务
            FutureTask<Integer> futureTask = new FutureTask<Integer>(callable);
            futureTasks.add(futureTask);
            //提交异步任务到线程池，让线程池管理任务 特爽把。
            //由于是异步并行任务，所以这里并不会阻塞
            executorService.submit(futureTask);
        }

        int count = 0;
        for (FutureTask<Integer> futureTask : futureTasks) {
            //futureTask.get() 得到我们想要的结果
            //该方法有一个重载get(long timeout, TimeUnit unit) 第一个参数为最大等待时间，第二个为时间的单位
            count += futureTask.get();
        }
        long end = System.currentTimeMillis();
        System.out.println("线程池的任务全部完成:结果为:" + count + "，main线程关闭，进行线程的清理");
        System.out.println("使用时间：" + (end - start) + "ms");
        //清理线程池
        executorService.shutdown();

    }
}