package com.qk.dm.datastandards.rest;

import com.alibaba.excel.EasyExcel;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
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
 * 数据标准excel导入导出功能接口
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
     * 业务术语excel导出
     *
     * @Param: response
     * @return: void
     **/
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
     * 业务术语excel导入
     *
     * @Param: file
     * @return: java.lang.String
     **/
    @PostMapping("/term/upload")
    @ResponseBody
    public DefaultCommonResult termUpload(MultipartFile file) throws IOException {
        dsdExcelService.termUpload(file);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 数据标准基本信息excel导出
     *
     * @Param: response
     * @return: void
     **/
    @PostMapping("/basic/info/download")
    public void basicInfoDownload(HttpServletResponse response,@RequestParam(required = false) String dsdLevelId) throws IOException {
        List<DsdBasicinfoVO> dsdBasicinfoVOList = dsdExcelService.queryAllBasicInfo(dsdLevelId);

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("数据标准基本信息", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), DsdBasicinfoVO.class).sheet("模板").doWrite(dsdBasicinfoVOList);
    }

    /**
     * 数据标准基本信息excel 导入
     *
     * @Param: file
     * @return: java.lang.String
     **/
    @PostMapping("/basic/info/upload")
    @ResponseBody
    public DefaultCommonResult basicInfoUpload(MultipartFile file, @RequestParam(required = false) String dsdLevelId) throws IOException {
        dsdExcelService.basicInfoUpload(file, dsdLevelId);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 码表信息excel导出
     *
     * @Param: response
     * @return: void
     **/
    @PostMapping("/code/term/download")
    public void codeTermDownload(HttpServletResponse response) throws IOException {
        List<DsdCodeTermVO> codeTermVOList = dsdExcelService.queryAllCodeTerm();

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("数据标准码表信息", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), DsdCodeTermVO.class).sheet("模板").doWrite(codeTermVOList);
    }

    /**
     * 码表信息excel导入
     *
     * @Param: file
     * @return: java.lang.String
     **/
    @PostMapping("/code/term/upload")
    @ResponseBody
    public DefaultCommonResult codeTermUpload(MultipartFile file) throws IOException {
        dsdExcelService.codeTermUpload(file);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

}

