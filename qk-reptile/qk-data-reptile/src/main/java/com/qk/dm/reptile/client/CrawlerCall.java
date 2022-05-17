package com.qk.dm.reptile.client;

import lombok.Data;

/**
 * 爬虫调用接口地址
 */
@Data
public class CrawlerCall {
    /**
     * 定时调用爬虫接口地址
     */
    private String timingUrl;
    /**
     * 手动抓取爬虫接口地址
     */
    private String manualUrl;
    /**
     * 数据找源接口检测是否存在数据
     */
    private String dataCheckUrl;

}
