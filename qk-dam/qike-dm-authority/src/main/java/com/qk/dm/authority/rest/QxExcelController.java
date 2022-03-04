package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.authority.service.QxExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 资源导入导出
 * @author zys
 * @date 2022/3/1 15:06
 * @since 1.0.0
 */
@RestController
@RequestMapping("/excel")
public class QxExcelController {
  private final QxExcelService qxExcelService;

  @Autowired
  public QxExcelController(QxExcelService qxExcelService) {
    this.qxExcelService = qxExcelService;
  }

  /**
   * 资源信息excel__模板下载
   *
   * @param response
   */
  @PostMapping("/resource/template")
  public void resourceDownloadTemplate(HttpServletResponse response) throws
      IOException {
    qxExcelService.resourceDownloadTemplate(response);
  }

  /**
   * API信息excel__模板下载
   *
   * @param response
   */
  @PostMapping("/api/template")
  public void apiDownloadTemplate(HttpServletResponse response) throws
      IOException {
    qxExcelService.apiDownloadTemplate(response);
  }

  /**
   * 用户信息excel__模板下载
   *
   * @param response
   */
  @PostMapping("/user/template")
  public void userDownloadTemplate(HttpServletResponse response) throws
      IOException {
    qxExcelService.userDownloadTemplate(response);
  }

  /**
   * 资源信息excel__导入数据
   *
   * @param file
   * @return DefaultCommonResult
   */
  @PostMapping("/resource/upload")
  @ResponseBody
  public DefaultCommonResult resourceInfoUpload(MultipartFile file) {
    return DefaultCommonResult.success(ResultCodeEnum.OK,qxExcelService.resourceInfoUpload(file));
  }

  /**
   * api_资源信息excel__导入数据
   *
   * @param file
   * @return DefaultCommonResult
   */
  @PostMapping("/api/upload")
  @ResponseBody
  public DefaultCommonResult apiResourceInfoUpload(MultipartFile file) {
    return DefaultCommonResult.success(ResultCodeEnum.OK,qxExcelService.apiResourceInfoUpload(file));
  }

  /**
   *  用户信息excel__导入数据
   * @param realm
   * @param file
   * @return
   */
  @PostMapping("/user/upload")
  @ResponseBody
  public DefaultCommonResult userResourceInfoUpload(String realm , MultipartFile file) {
    return DefaultCommonResult.success(ResultCodeEnum.OK,qxExcelService.userResourceInfoUpload(file,realm));
  }

  /**
   * 资源信息excel__导出数据
   *
   * @param response
   */
  @PostMapping("/resource/download")
  public DefaultCommonResult resourcesAllDownload(@RequestParam("serviceId") Long serviceId,HttpServletResponse response)
      throws IOException {
    qxExcelService.resourcesAllDownload(serviceId,response);
    return DefaultCommonResult.success();
  }

  /**
   * api资源信息excel__导出数据
   *
   * @param response
   */
  @PostMapping("/api/download")
  public DefaultCommonResult apiAllDownload(@RequestParam("serviceId") Long serviceId,HttpServletResponse response)
      throws IOException {
    qxExcelService.apiAllDownload(serviceId,response);
    return DefaultCommonResult.success();
  }

  /**
   * 用戶信息excel__导出数据
   * @param realm
   * @param search
   * @return
   * @throws IOException
   */
  @PostMapping("/user/download")
  public DefaultCommonResult UserAllDownload(String realm, String search,HttpServletResponse response)
      throws IOException {
    qxExcelService.UserAllDownload(realm,search,response);
    return DefaultCommonResult.success();
  }
}