package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.authority.service.EmpExcelService;
import com.qk.dm.authority.vo.user.AtyUserDownVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * 资源导入导出
 * @author zys
 * @date 2022/3/1 15:06
 * @since 1.0.0
 */
@RestController
@RequestMapping("/excel")
public class EmpExcelController {
  private final EmpExcelService empExcelService;

  public EmpExcelController(EmpExcelService empExcelService) {
    this.empExcelService = empExcelService;
  }

  /**
   * 资源信息excel__模板下载
   *
   * @param response
   */
  @PostMapping("/resource/template")
  public void resourceDownloadTemplate(HttpServletResponse response) throws
      IOException {
    empExcelService.resourceDownloadTemplate(response);
  }

  /**
   * API信息excel__模板下载
   *
   * @param response
   */
  @PostMapping("/api/template")
  public void apiDownloadTemplate(HttpServletResponse response) throws
      IOException {
    empExcelService.apiDownloadTemplate(response);
  }

  /**
   * 用户信息excel__模板下载
   *
   * @param response
   */
  @PostMapping("/user/template")
  public void userDownloadTemplate(HttpServletResponse response) throws
      IOException {
    empExcelService.userDownloadTemplate(response);
  }

  /**
   * 资源信息excel__导入数据
   *
   * @param file 导入文件
   * @return DefaultCommonResult
   */
  @PostMapping("/resource/upload")
  @ResponseBody
  public DefaultCommonResult resourceInfoUpload(MultipartFile file) {
    return DefaultCommonResult.success(ResultCodeEnum.OK,empExcelService.resourceInfoUpload(file));
  }

  /**
   * api_资源信息excel__导入数据
   *
   * @param file 导入文件
   * @return DefaultCommonResult
   */
  @PostMapping("/api/upload")
  @ResponseBody
  public DefaultCommonResult apiResourceInfoUpload(MultipartFile file) {
    return DefaultCommonResult.success(ResultCodeEnum.OK,empExcelService.apiResourceInfoUpload(file));
  }

  /**
   *  用户信息excel__导入数据
   * @param realm 域名称
   * @param file  导入文件
   * @return
   */
  @PostMapping("/user/upload")
  @ResponseBody
  public DefaultCommonResult userResourceInfoUpload(@RequestParam("realm")String realm , MultipartFile file) {
    return DefaultCommonResult.success(ResultCodeEnum.OK,empExcelService.userResourceInfoUpload(file,realm));
  }

  /**
   * 资源信息excel__导出数据
   * @param serviceId 服务UUID
   * @param response
   * @return
   * @throws IOException
   */
  @PostMapping("/resource/download")
  public DefaultCommonResult resourcesAllDownload(@RequestParam("serviceId") String serviceId,HttpServletResponse response)
      throws IOException {
    empExcelService.resourcesAllDownload(serviceId,response);
    return DefaultCommonResult.success();
  }

  /**
   * api资源信息excel__导出数据
   * @param serviceId 服务UUID
   * @param response
   * @return
   * @throws IOException
   */
  @PostMapping("/api/download")
  public DefaultCommonResult apiAllDownload(@RequestParam("serviceId") String serviceId,HttpServletResponse response)
      throws IOException {
    empExcelService.apiAllDownload(serviceId,response);
    return DefaultCommonResult.success();
  }

  /**
   * 用戶信息excel__导出数据
   * @param realm  域名称
   * @param search 筛选条件
   * @return
   * @throws IOException
   */
  @PostMapping("/user/download")
  public DefaultCommonResult UserAllDownload(@RequestParam("realm")String realm,@RequestParam("search") String search,HttpServletResponse response)
      throws IOException {
    empExcelService.UserAllDownload(realm,search,response);
    return DefaultCommonResult.success();
  }

  /**
   * 用戶信息excel__指定导出数据
   * @param atyUserDownVO
   * @param response
   * @return
   * @throws IOException
   */
  @PostMapping("/user/download/ids")
  public DefaultCommonResult UserDownloadByIds(@RequestBody @Valid AtyUserDownVO atyUserDownVO, HttpServletResponse response)
          throws IOException {
    empExcelService.UserAllDownload(atyUserDownVO,response);
    return DefaultCommonResult.success();
  }
}