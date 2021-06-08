package com.qk.dam.sqlloader;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.lang.Console;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.http.HttpUtil;

import java.sql.SQLException;
import java.util.List;

public class DmSqlLoaderApplication {
    public static void main(String[] args) throws SQLException {
        List<Entity> user = Db.use().findAll("qk_dsd_basicinfo");
        System.out.println(user);

        String fileUrl = "https://targz.oss-cn-beijing.aliyuncs.com/daily_qike/20210606/dataplus_vc_inv-210606142545-9uxdre1h.tar.gz";

        //带进度显示的文件下载
        HttpUtil.downloadFile(fileUrl, FileUtil.file("/Users/daomingzhu"), new StreamProgress(){

            @Override
            public void start() {
                Console.log("开始下载。。。。");
            }

            @Override
            public void progress(long progressSize) {
                Console.log("已下载：{}", FileUtil.readableFileSize(progressSize));
            }

            @Override
            public void finish() {
                Console.log("下载完成！");
            }
        });
    }
}
