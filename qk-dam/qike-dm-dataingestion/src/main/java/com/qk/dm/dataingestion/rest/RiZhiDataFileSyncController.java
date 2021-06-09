package com.qk.dm.dataingestion.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataingestion.service.RiZhiDataFileSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * 原始层数据同步
 *
 * @author wjq
 * @date 20210608
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/sync/rizhi")
public class RiZhiDataFileSyncController {
    private final RiZhiDataFileSyncService riZhiDataFileSyncService;

    @Autowired
    public RiZhiDataFileSyncController(RiZhiDataFileSyncService riZhiDataFileSyncService) {
        this.riZhiDataFileSyncService = riZhiDataFileSyncService;
    }

    /**
     * 同步阿里云数据到腾讯云
     *
     * @return: com.qk.dam.commons.http.result.DefaultCommonResult
     **/
    @GetMapping("/files/data")
    public DefaultCommonResult syncRiZhiFilesData(String frontTabNamePatter, String batchNum, String bucketName) throws ExecutionException, InterruptedException {
        riZhiDataFileSyncService.syncRiZhiFilesData(frontTabNamePatter, batchNum, bucketName);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

}

