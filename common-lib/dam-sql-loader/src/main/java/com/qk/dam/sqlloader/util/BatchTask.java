package com.qk.dam.sqlloader.util;

import cn.hutool.core.thread.ExecutorBuilder;
import com.qk.dam.sqlloader.vo.PiciTaskVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class BatchTask {
  public static List<FutureTask<Integer>> batchTask(
      List<PiciTaskVO> piciTask, Function<PiciTaskVO, Integer> function) {
    ExecutorService executor =
        ExecutorBuilder.create()
            .setCorePoolSize(5)
            .setMaxPoolSize(10)
            .setWorkQueue(new LinkedBlockingQueue<>(50))
            .setKeepAliveTime(60, TimeUnit.MINUTES)
            .build();

    List<FutureTask<Integer>> futureTasks = new ArrayList<>();
    Objects.requireNonNull(piciTask)
        .forEach(
            piciTaskVO -> {
              FutureTask<Integer> futureTask = new FutureTask<>(() -> function.apply(piciTaskVO));
              futureTasks.add(futureTask);
              executor.submit(futureTask);
            });
    executor.shutdown();

    return futureTasks;
  }
}
