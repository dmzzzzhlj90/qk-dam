package com.qk.dm.dataservice.controller;

//import com.qk.dam.authorization.Auth;
//import com.qk.dam.authorization.BizResource;
//import com.qk.dam.authorization.RestActionType;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasApiExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 数据服务__Excel数据迁移功能
 *
 * @author wjq
 * @date 2021/04/18
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/excel")
public class DasApiExcelController {
    private final DasApiExcelService dasApiExcelService;

    @Autowired
    public DasApiExcelController(DasApiExcelService dasApiExcelService) {
        this.dasApiExcelService = dasApiExcelService;
    }

    /**
     * API导入
     *
     * @param file
     * @return DefaultCommonResult
     */
    @PostMapping("/api/data/upload/{dirId}")
    @ResponseBody
//  @Auth(bizType = BizResource.DSD_EXCEL_UPLOAD, actionType = RestActionType.IMPORT)
    public DefaultCommonResult apiDataUpload(MultipartFile file, @PathVariable("dirId") String dirId) {
        dasApiExcelService.apiDataUpload(file, dirId);
        return DefaultCommonResult.success();
    }

    /**
     * API导出
     *
     * @param response
     */
    @PostMapping("/api/data/download/{dirId}")
//  @Auth(bizType = BizResource.DSD_EXCEL_DOWNLOAD, actionType = RestActionType.EXPORT)
    public void apiDataDownload(@PathVariable("dirId") String dirId, HttpServletResponse response) throws IOException {
        dasApiExcelService.apiDataDownload(dirId, response);
    }

}
