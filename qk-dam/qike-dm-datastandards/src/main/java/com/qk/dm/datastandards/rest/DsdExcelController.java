package com.qk.dm.datastandards.rest;

import com.alibaba.excel.EasyExcel;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.easyexcel.handler.DsdBasicInfoCustomSheetWriteHandler;
import com.qk.dm.datastandards.easyexcel.handler.DsdCodeInfoCustomSheetWriteHandler;
import com.qk.dm.datastandards.service.DsdExcelService;
import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import com.qk.dm.datastandards.vo.DsdCodeInfoVO;
import com.qk.dm.datastandards.vo.DsdTermVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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
     * 业务术语excel__全部导出数据
     *
     * @Param: response
     * @return: void
     */
    @PostMapping("/term/download")
    public void termDownload(HttpServletResponse response) throws IOException {
        List<DsdTermVO> dsdTermVOList = dsdExcelService.queryAllTerm();

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("数据标准业务术语", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), DsdTermVO.class).sheet("模板").doWrite(dsdTermVOList);
    }

    /**
     * 业务术语__excel导入数据
     *
     * @Param: file
     * @return: java.lang.String
     */
    @PostMapping("/term/upload")
    @ResponseBody
    public DefaultCommonResult termUpload(MultipartFile file) throws IOException {
        dsdExcelService.termUpload(file);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 标准基本信息excel__全部导出数据
     *
     * @Param: response
     * @return: void
     */
    @PostMapping("/basic/info/download")
    public void basicInfoDownload(HttpServletResponse response) throws IOException {
        List<DsdBasicinfoVO> dsdBasicinfoVOList = dsdExcelService.queryBasicInfos(null);

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("数据标准基本信息", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), DsdBasicinfoVO.class).sheet("模板").registerWriteHandler(new DsdBasicInfoCustomSheetWriteHandler(dsdExcelService)).doWrite(dsdBasicinfoVOList);
    }

    /**
     * 标准基本信息excel__导出数据 (根据标准分类目录Id)
     *
     * @Param: response
     * @return: void
     */
    @PostMapping("/basic/info/download/dirDsdId")
    public void basicInfoDownloadByDirDsdId(@RequestParam("dirDsdId") String dirDsdId, HttpServletResponse response) throws IOException {
        List<DsdBasicinfoVO> dsdBasicinfoVOList = dsdExcelService.queryBasicInfos(dirDsdId);

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("数据标准基本信息", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), DsdBasicinfoVO.class).sheet("模板").registerWriteHandler(new DsdBasicInfoCustomSheetWriteHandler(dsdExcelService)).doWrite(dsdBasicinfoVOList);
    }

    /**
     * 标准基本信息excel__导入数据(默认根据Excel中选择的层级进行导入)
     *
     * @Param: file
     * @return: java.lang.String
     */
    @PostMapping("/basic/info/upload")
    @ResponseBody
    public DefaultCommonResult basicInfoUploadBydirDsdId(MultipartFile file) throws IOException {
        dsdExcelService.basicInfoUpload(file, null);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 标准基本信息excel__导入数据 (根据标准分类目录Id)
     *
     * @Param: file
     * @return: java.lang.String
     */
    @PostMapping("/basic/info/upload/dirDsdId")
    @ResponseBody
    public DefaultCommonResult basicInfoUploadByDirDsdId(MultipartFile file, @RequestParam("dirDsdId") String dirDsdId) throws IOException {
        dsdExcelService.basicInfoUpload(file, dirDsdId);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 码表基本信息列表__导出excel数据(全量)
     *
     * @Param: response
     * @return: void
     */
    @PostMapping("/code/info/all/download")
    public void codeInfoAllDownload(HttpServletResponse response) throws IOException {
        List<DsdCodeInfoVO> dsdCodeInfoVOList = dsdExcelService.codeInfoAllDownload();

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("码表基本信息", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), DsdCodeInfoVO.class).sheet("码表基本信息")
                .registerWriteHandler(new DsdCodeInfoCustomSheetWriteHandler(dsdExcelService)).doWrite(dsdCodeInfoVOList);
    }


    /**
     * 码表基本信息列表__导入excel数据(全量)
     *
     * @Param: file
     * @return: java.lang.String
     */
    @PostMapping("/code/info/all/upload")
    @ResponseBody
    public DefaultCommonResult codeInfoAllUpload(MultipartFile file) {
        dsdExcelService.codeInfoAllUpload(file);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 码表数值信息列表__导出excel数据(根据dsdCodeInfoId表级数据)
     *
     * @Param: response
     * @return: void
     */
    @PostMapping("/code/values/dsdCodeInfoId/download")
    public void codeValuesDownloadByCodeInfoId(HttpServletResponse response, @RequestParam("dsdCodeInfoId") String dsdCodeInfoId) {
        dsdExcelService.codeValuesDownloadByCodeInfoId(response, Long.valueOf(dsdCodeInfoId).longValue());
    }

    /**
     * 码表数值信息列表__导入excel数据(根据dsdCodeInfoId表级数据)
     *
     * @Param: file
     * @return: java.lang.String
     */
    @PostMapping("/code/values/dsdCodeInfoId/upload")
    @ResponseBody
    public DefaultCommonResult codeValuesUploadByCodeInfoId(MultipartFile file, @RequestParam("dsdCodeInfoId") String dsdCodeInfoId) {
        dsdExcelService.codeValuesUploadByCodeInfoId(file, Long.valueOf(dsdCodeInfoId).longValue());
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }


    /**
     * 标准基本信息excel__模板下载
     *
     * @param response
     * @throws IOException
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
                .sheet("数据标准信息导入模板").doWrite(dsdBasicInfoSampleDataList);
    }


    /**
     * 码表基本信息excel__模板下载
     *
     * @param response
     * @throws IOException
     */
    @PostMapping("/code/info/upload/template")
    public void ccodeInfoDownloadTemplate(HttpServletResponse response) throws IOException {
//        List<DsdBasicinfoVO> dsdBasicInfoSampleDataList = dsdExcelService.dsdBasicInfoSampleData();

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("码表基本信息导入模板", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), DsdCodeInfoVO.class)
                .registerWriteHandler(new DsdCodeInfoCustomSheetWriteHandler(dsdExcelService)).sheet("码表基本信息导入模板").doWrite(new ArrayList());
    }

    /**
     * 码表数值信息excel__模板下载
     *
     * @Param: response
     * @return: void
     */
    @PostMapping("/code/values/download/template")
    public void codeValuesDownloadTemplate(HttpServletResponse response, @RequestParam("dsdCodeInfoId") String dsdCodeInfoId) {
        dsdExcelService.codeValuesDownloadTemplate(response, Long.valueOf(dsdCodeInfoId).longValue());
    }

}
