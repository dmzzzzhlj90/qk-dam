package com.qk.dam.sqlloader;

import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.http.HttpUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.sqlloader.constant.LongGovConstant;
import com.qk.dam.sqlloader.repo.PiciTaskAgg;
import com.qk.dam.sqlloader.util.TargzUtils;
import com.qk.dam.sqlloader.vo.PiciTaskVO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.qk.dam.sqlloader.util.BatchTask.batchTask;
import static com.qk.dam.sqlloader.util.DownloadFile.downFileInputStream;

public class DmSqlLoaderTest {
    private static final Log LOG = LogFactory.get("sql载入程序");

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        String fileUrl = "https://targz.oss-cn-beijing.aliyuncs.com/daily_qike/20210606/dataplus_vc_inv-210606142545-9uxdre1h.tar.gz";
//
//        LOG.info("开始尝试连接数据源【{}】", "qkdam");
//        LOG.info("连接成功！");
//        LOG.info("载入tar.gz包！");
//        Map<String, String> sqlMap = tarbgzContent(
//                downFileInputStream(fileUrl)
//        );
//        Objects.requireNonNull(sqlMap);
//        LOG.info("载入成功！");
//        sqlMap.forEach((key, sqlValue) -> {
//            LOG.info("获取执行的sql文件【{}】！", key);
//            qkUpdatedBatchSql(sqlValue);
//        });

        List<PiciTaskVO> piciTasks = PiciTaskAgg.longgovTaskrizhi(1);

        List<FutureTask<Integer>> futureTasks = batchTask(piciTasks, (piciTaskVO) -> {
            downFileInputStream(LongGovConstant.HOST_ALIYUN_OSS+piciTaskVO.getOssPath());
            return 1;
        });

        for (FutureTask<Integer> futureTask : futureTasks) {
            Integer state = futureTask.get();
            System.out.println(state);
        }

    }






}
