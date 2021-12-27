package com.qk.dm.reptile.rest;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.reptile.service.RptExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * execel数据导出导入接口
 * @author zys
 * @date 2021/12/21 15:14
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/excel")
public class RptExcelController {
  private final RptExcelService rptExcelService;
  @Autowired
  public RptExcelController(RptExcelService rptExcelService) {
    this.rptExcelService = rptExcelService;
  }
  /**
   * 待配列基本信息excel__模板下载
   *
   * @param response
   */
  @PostMapping("/basic/info/upload/template")
  public void basicInfoDownloadTemplate(HttpServletResponse response) throws
      IOException {
    rptExcelService.basicInfoDownloadTemplate(response);
  }

  /**
   * 待配列基本信息excel__导入数据
   *
   * @param file
   * @return DefaultCommonResult
   */
  @PostMapping("/basic/info/upload")
  @ResponseBody
  public DefaultCommonResult basicInfoUpload(MultipartFile file) {
    rptExcelService.basicInfoUpload(file);
    return DefaultCommonResult.success();
  }
}