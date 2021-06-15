package com.qk.dm.dataingestion.scheduled;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.sqlloader.SqlLoaderMain;
import com.qk.dam.sqlloader.constant.LongGovConstant;
import com.qk.dm.dataingestion.service.PiciTaskDataFileSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 定时同步数据文件
 *
 * @author wjq
 * @date 20210615
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/timer/sync/")
@RefreshScope
public class TaskScheduledPiciDataFiles {
    private static final Log LOG = LogFactory.get("定时同步数据文件");

    @Value("${read.timer.param}")
    private String timerParam;

    private final PiciTaskDataFileSyncService piciTaskDataFileSyncService;

    @Autowired
    public TaskScheduledPiciDataFiles(PiciTaskDataFileSyncService piciTaskDataFileSyncService) {
        this.piciTaskDataFileSyncService = piciTaskDataFileSyncService;
    }

    /**
     * 同步阿里云数据到腾讯云
     *
     * @return: com.qk.dam.commons.http.result.DefaultCommonResult
     **/
    @Scheduled(cron = "${read.timer.param}")
    @GetMapping("/files/data")
    public void syncRiZhiFilesData() {
        LOG.info("定时同步开始!");
        LOG.info("定时器时间cron为:【{}】!", timerParam);
        try {
            int rtState = piciTaskDataFileSyncService.syncPiciTaskFilesData("", "", LongGovConstant.BUCKETNAME);
            if (rtState==2){
                String dataDay = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now());
                SqlLoaderMain.executeTarSqlUpdate(dataDay);
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOG.info("定时同步出错!" + e.getMessage());
        }
        LOG.info("定时同步结束!");
    }

//    /**
//     * 同步阿里云数据到腾讯云
//     *
//     * @return: com.qk.dam.commons.http.result.DefaultCommonResult
//     **/
//    @GetMapping("/files/data")
//    public DefaultCommonResult syncRiZhiFilesData(@RequestParam(required = false) String frontTabNamePatter,
//                                                  @RequestParam(required = false) String batchNum) throws ExecutionException, InterruptedException {
//        return new DefaultCommonResult(ResultCodeEnum.OK,
//                piciTaskDataFileSyncService.syncPiciTaskFilesData(frontTabNamePatter, batchNum, LongGovConstant.BUCKETNAME));
//    }

}

