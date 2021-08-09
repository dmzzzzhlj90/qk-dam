package com.qk.dm.datastandards.rest;

import com.alibaba.excel.EasyExcel;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.easyexcel.handler.DsdBasicInfoCustomSheetWriteHandler;
import com.qk.dm.datastandards.easyexcel.handler.DsdCodeInfoCustomSheetWriteHandler;
import com.qk.dm.datastandards.service.DsdExcelService;
import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import com.qk.dm.datastandards.vo.DsdCodeInfoVO;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 数据标准__excel导入导出功能接口
 *
 * @author wjq
 * @date 20210605
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/excel")
public class DsdExcelController {
  private final DsdExcelService dsdExcelService;

  @Autowired
  public DsdExcelController(DsdExcelService dsdExcelService) {
    this.dsdExcelService = dsdExcelService;
  }

  /**
   * 标准基本信息excel__全部导出数据
   *
   * @param response
   */
  @PostMapping("/basic/info/download")
  public void basicInfoDownload(HttpServletResponse response) throws IOException {
    List<DsdBasicinfoVO> dsdBasicinfoVOList = dsdExcelService.queryBasicInfos(null);

    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    String fileName = URLEncoder.encode("数据标准基本信息", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), DsdBasicinfoVO.class)
        .sheet("模板")
        .registerWriteHandler(new DsdBasicInfoCustomSheetWriteHandler(dsdExcelService))
        .doWrite(dsdBasicinfoVOList);
  }

  /**
   * 标准基本信息excel__导出数据 (根据标准分类目录Id)
   *
   * @param response
   */
  @PostMapping("/basic/info/download/dirDsdId")
  public void basicInfoDownloadByDirDsdId(
      @RequestParam("dirDsdId") String dirDsdId, HttpServletResponse response) throws IOException {
    List<DsdBasicinfoVO> dsdBasicinfoVOList = dsdExcelService.queryBasicInfos(dirDsdId);

    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    String fileName = URLEncoder.encode("数据标准基本信息", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), DsdBasicinfoVO.class)
        .sheet("模板")
        .registerWriteHandler(new DsdBasicInfoCustomSheetWriteHandler(dsdExcelService))
        .doWrite(dsdBasicinfoVOList);
  }

  /**
   * 标准基本信息excel__导入数据(默认根据Excel中选择的层级进行导入)
   *
   * @param file
   * @return DefaultCommonResult
   */
  @PostMapping("/basic/info/upload")
  @ResponseBody
  public DefaultCommonResult basicInfoUploadBydirDsdId(MultipartFile file) {
    dsdExcelService.basicInfoUpload(file, null);
    return DefaultCommonResult.success();
  }

  /**
   * 标准基本信息excel__导入数据 (根据标准分类目录Id)
   *
   * @param file
   * @return DefaultCommonResult
   */
  @PostMapping("/basic/info/upload/dirDsdId")
  @ResponseBody
  public DefaultCommonResult basicInfoUploadByDirDsdId(
      MultipartFile file, @RequestParam("dirDsdId") String dirDsdId) {
    dsdExcelService.basicInfoUpload(file, dirDsdId);
    return DefaultCommonResult.success();
  }

  /**
   * 码表基本信息列表__导出excel数据(全量)
   *
   * @param response
   */
  @PostMapping("/code/info/all/download")
  public void codeInfoAllDownload(
      @RequestParam("codeDirId") String codeDirId, HttpServletResponse response)
      throws IOException {
    List<DsdCodeInfoVO> dsdCodeInfoVOList = dsdExcelService.codeInfoAllDownload(codeDirId);

    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    String fileName = URLEncoder.encode("码表基本信息", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), DsdCodeInfoVO.class)
        .sheet("码表基本信息")
        .registerWriteHandler(new DsdCodeInfoCustomSheetWriteHandler(dsdExcelService))
        .doWrite(dsdCodeInfoVOList);
  }

  /**
   * 码表基本信息列表__导入excel数据(全量)
   *
   * @param file
   * @return DefaultCommonResult
   */
  @PostMapping("/code/info/all/upload")
  @ResponseBody
  public DefaultCommonResult codeInfoAllUpload(
      MultipartFile file, @RequestParam("codeDirId") String codeDirId) {
    dsdExcelService.codeInfoAllUpload(file, codeDirId);
    return DefaultCommonResult.success();
  }

  /**
   * 码表数值信息列表__导出excel数据(根据dsdCodeInfoId表级数据)
   *
   * @param response
   */
  @PostMapping("/code/values/dsdCodeInfoId/download")
  public void codeValuesDownloadByCodeInfoId(
      HttpServletResponse response, @RequestParam("dsdCodeInfoId") String dsdCodeInfoId) {
    dsdExcelService.codeValuesDownloadByCodeInfoId(
        response, Long.valueOf(dsdCodeInfoId).longValue());
  }

  /**
   * 码表数值信息列表__导入excel数据(根据dsdCodeInfoId表级数据)
   *
   * @param file
   * @return DefaultCommonResult
   */
  @PostMapping("/code/values/dsdCodeInfoId/upload")
  @ResponseBody
  public DefaultCommonResult codeValuesUploadByCodeInfoId(
      MultipartFile file, @RequestParam("dsdCodeInfoId") String dsdCodeInfoId) {
    dsdExcelService.codeValuesUploadByCodeInfoId(file, Long.valueOf(dsdCodeInfoId).longValue());
    return DefaultCommonResult.success();
  }

  /**
   * 标准基本信息excel__模板下载
   *
   * @param response
   */
  @PostMapping("/basic/info/upload/template")
  public void basicInfoDownloadTemplate(HttpServletResponse response) throws IOException {
    List<DsdBasicinfoVO> dsdBasicInfoSampleDataList = dsdExcelService.dsdBasicInfoSampleData();

    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    String fileName = URLEncoder.encode("数据标准基本信息", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), DsdBasicinfoVO.class)
        .registerWriteHandler(new DsdBasicInfoCustomSheetWriteHandler(dsdExcelService))
        .sheet("数据标准信息导入模板")
        .doWrite(dsdBasicInfoSampleDataList);
  }

  /**
   * 码表基本信息excel__模板下载
   *
   * @param response
   */
  @PostMapping("/code/info/upload/template")
  public void ccodeInfoDownloadTemplate(HttpServletResponse response) throws IOException {
    //        List<DsdBasicinfoVO> dsdBasicInfoSampleDataList =
    // dsdExcelService.dsdBasicInfoSampleData();

    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    String fileName = URLEncoder.encode("码表基本信息导入模板", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), DsdCodeInfoVO.class)
        .registerWriteHandler(new DsdCodeInfoCustomSheetWriteHandler(dsdExcelService))
        .sheet("码表基本信息导入模板")
        .doWrite(new ArrayList());
  }

  /**
   * 码表数值信息excel__模板下载
   *
   * @param response
   */
  @PostMapping("/code/values/download/template")
  public void codeValuesDownloadTemplate(
      HttpServletResponse response, @RequestParam("dsdCodeInfoId") String dsdCodeInfoId) {
    dsdExcelService.codeValuesDownloadTemplate(response, Long.valueOf(dsdCodeInfoId).longValue());
  }
}
