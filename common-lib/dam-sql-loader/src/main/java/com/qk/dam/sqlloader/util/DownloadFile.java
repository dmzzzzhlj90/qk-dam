package com.qk.dam.sqlloader.util;

import cn.hutool.core.io.StreamProgress;
import cn.hutool.http.HttpUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.function.Consumer;

public class DownloadFile {
    private static final Log LOG = LogFactory.get("文件下载程序");

    public static ByteArrayInputStream downloadFileFromUrl(String fileUrl, String destFile) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //带进度显示的文件下载
        HttpUtil.downloadFileFromUrl(fileUrl, new File(destFile), -1, new StreamProgress() {
            @Override
            public void start() {
                LOG.info("【{}】文件开始下载...", fileUrl);
            }

            @Override
            public void progress(long progressSize) {
            }

            @Override
            public void finish() {
                LOG.info("【{}】文件下载完成！", fileUrl);
            }
        });
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
    public static ByteArrayInputStream downloadFileFromUrl(String fileUrl, String destFile, Consumer<String> consumer) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //带进度显示的文件下载
        HttpUtil.downloadFileFromUrl(fileUrl, new File(destFile), -1, new StreamProgress() {
            @Override
            public void start() {
                LOG.info("【{}】文件开始下载...", fileUrl);
            }

            @Override
            public void progress(long progressSize) {
            }

            @Override
            public void finish() {
                LOG.info("【{}】文件下载完成！", fileUrl);
                consumer.accept(fileUrl);
            }
        });
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    public static ByteArrayInputStream downFileInputStream(String fileUrl) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //带进度显示的文件下载
        HttpUtil.download(fileUrl, byteArrayOutputStream, true, new StreamProgress() {
            @Override
            public void start() {
                LOG.info("【{}】文件开始下载...", fileUrl);
            }

            @Override
            public void progress(long progressSize) {
            }

            @Override
            public void finish() {
                LOG.info("【{}】文件下载完成！", fileUrl);
            }
        });
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
}
