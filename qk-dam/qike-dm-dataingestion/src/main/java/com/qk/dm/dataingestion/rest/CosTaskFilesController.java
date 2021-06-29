package com.qk.dm.dataingestion.rest;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.sqlloader.constant.LongGovConstant;
import com.qk.dm.dataingestion.service.CosTaskFilesService;
import com.qk.dm.dataingestion.vo.CosTaskFileInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 同步获取COS客户端文件信息
 *
 * @author wjq
 * @date 2021/06/28
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/cos/task")
public class CosTaskFilesController {
    private static final Log LOG = LogFactory.get("同步获取COS客户端文件信息");

    private final CosTaskFilesService cosTaskFilesService;

    @Autowired
    public CosTaskFilesController(CosTaskFilesService cosTaskFilesService) {
        this.cosTaskFilesService = cosTaskFilesService;
    }


    /**
     * 获取COS任务文件信息
     *
     * @param: frontTabNamePatter, batchNum
     * @return: DefaultCommonResult
     */
    @GetMapping("/files/info")
    public DefaultCommonResult<CosTaskFileInfoVO> getCosTaskFilesInfo() {
        String dataDay = DateTimeFormatter.ofPattern(LongGovConstant.DATE_TIME_PATTERN).format(LocalDateTime.now());
        return new DefaultCommonResult(ResultCodeEnum.OK, cosTaskFilesService.getCosTaskFilesInfo(dataDay));
    }
}
