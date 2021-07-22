package com.qk.dm.datastandards.rest;

import com.alibaba.excel.EasyExcel;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.easyexcel.handler.DsdBasicInfoCustomSheetWriteHandler;
import com.qk.dm.datastandards.easyexcel.handler.DsdCodeTermCustomSheetWriteHandler;
import com.qk.dm.datastandards.service.DsdExcelService;
import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import com.qk.dm.datastandards.vo.DsdCodeTermVO;
import com.qk.dm.datastandards.vo.DsdTermVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
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
     * 码表信息excel__全部导出数据
     *
     * @Param: response
     * @return: void
     */
    @PostMapping("/code/term/download")
    public void codeTermDownload(HttpServletResponse response) throws IOException {
        List<DsdCodeTermVO> codeTermVOList = dsdExcelService.queryCodeTerms(null);

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("数据标准码表信息", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), DsdCodeTermVO.class).registerWriteHandler(new DsdCodeTermCustomSheetWriteHandler(dsdExcelService)).sheet("模板").doWrite(codeTermVOList);
    }

    /**
     * 码表信息excel__导出数据 (根据码表分类目录Id)
     *
     * @Param: response
     * @return: void
     */
    @PostMapping("/code/term/download/codeDirId")
    public void codeTermDownloadByCodeDir(HttpServletResponse response,@RequestParam("codeDirId") String codeDirId) throws IOException {
        List<DsdCodeTermVO> codeTermVOList = dsdExcelService.queryCodeTerms(codeDirId);

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("数据标准码表信息", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), DsdCodeTermVO.class).registerWriteHandler(new DsdCodeTermCustomSheetWriteHandler(dsdExcelService)).sheet("模板").doWrite(codeTermVOList);
    }

    /**
     * 码表信息excel_导入数据(根据码表分类目录Id)
     *
     * @Param: file
     * @return: java.lang.String
     */
    @PostMapping("/code/term/upload/codeDirId")
    @ResponseBody
    public DefaultCommonResult codeTermUploadByCodeDir(MultipartFile file, @RequestParam("codeDirId") String codeDirId) throws IOException {
        dsdExcelService.codeTermUpload(file, codeDirId);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }


    /**
     * 码表信息excel__导入数据(默认根据Excel中选择的码表层级进行导入)
     *
     * @Param: file
     * @return: java.lang.String
     */
    @PostMapping("/code/term/upload")
    @ResponseBody
    public DefaultCommonResult codeTermUpload(MultipartFile file) throws IOException {
        dsdExcelService.codeTermUpload(file, null);
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
     * 码表信息excel__模板下载
     *
     * @Param: response
     * @return: void
     */
    @PostMapping("/code/term/download/template")
    public void codeTermDownloadTemplate(HttpServletResponse response) throws IOException {
        List<DsdCodeTermVO> codeTermSampleDataList = dsdExcelService.dsdCodeTermSampleData();

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("数据标准码表信息", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), DsdCodeTermVO.class)
                .registerWriteHandler(new DsdCodeTermCustomSheetWriteHandler(dsdExcelService))
                .sheet("模板").doWrite(codeTermSampleDataList);
    }

}
