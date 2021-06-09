package com.qk.dam.sqlloader;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.sqlloader.repo.PiciTaskAgg;
import com.qk.dam.sqlloader.util.TargzUtils;

import java.io.*;
import java.util.Map;
import java.util.Objects;

import static com.qk.dam.sqlloader.repo.qkUpdatedAgg.qkUpdatedBatchSql;

public class DmSqlLoaderApplication {
    private static final Log LOG = LogFactory.get("sql载入程序");
    public static void main(String[] args)  {
        String fileUrl = "https://targz.oss-cn-beijing.aliyuncs.com/daily_qike/20210606/dataplus_vc_inv-210606142545-9uxdre1h.tar.gz";

        LOG.info("开始尝试连接数据源【{}】","qkdam");
        LOG.info("连接成功！");
        LOG.info("载入tar.gz包！");
        Map<String, String> sqlMap = tarbgzContent(
                downFileInputStream(fileUrl)
        );
        Objects.requireNonNull(sqlMap);
        LOG.info("载入成功！");
        sqlMap.forEach((key, sqlValue) -> {
            LOG.info("获取执行的sql文件【{}】！",key);
            qkUpdatedBatchSql(sqlValue);
        });

        PiciTaskAgg.longgovTaskrizhi(1);
    }

    private static Map<String, String> tarbgzContent(InputStream inputStream){
        try {
            return TargzUtils.readTarbgzContent(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ByteArrayInputStream downFileInputStream(String fileUrl){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //带进度显示的文件下载
        HttpUtil.download(fileUrl, byteArrayOutputStream,true, new StreamProgress(){
            @Override
            public void start() {
                LOG.info("【{}】文件开始下载...",fileUrl);
            }
            @Override
            public void progress(long progressSize) {
            }
            @Override
            public void finish() {
                LOG.info("【{}】文件下载完成！",fileUrl);
            }
        });
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }



}
