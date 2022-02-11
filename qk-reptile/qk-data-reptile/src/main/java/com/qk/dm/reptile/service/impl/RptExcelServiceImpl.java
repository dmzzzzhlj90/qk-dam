package com.qk.dm.reptile.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.qk.dam.commons.exception.BizException;
import com.qk.dm.reptile.entity.RptBaseInfo;
import com.qk.dm.reptile.listener.RptBasicInfoUploadDataListener;
import com.qk.dm.reptile.params.vo.RptBaseInfoVO;
import com.qk.dm.reptile.service.RptExcelService;
import com.qk.dm.reptile.utils.MultipartFileUtil;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 导入导出excel
 * @author zys
 * @date 2021/12/21 15:19
 * @since 1.0.0
 */
@Service
public class RptExcelServiceImpl implements RptExcelService {
  private static final Log LOG = LogFactory.getLog("待配列表数据导入");
  private final RptExcelBatchService rptExcelBatchService;

  @Autowired
  public RptExcelServiceImpl(RptExcelBatchService rptExcelBatchService) {
    this.rptExcelBatchService = rptExcelBatchService;
  }

  /**
   * excel模板下载
   * @param response
   * @throws IOException
   */
  @Override
  public void basicInfoDownloadTemplate(HttpServletResponse response)
      throws IOException {
   List<RptBaseInfoVO> rptBaseInfoVOList = prtBaseInfoSampleData();
//    response.setContentType("application/vnd.ms-excel");
//    response.setCharacterEncoding("utf-8");
    response.setContentType("application/json;charset=utf-8");
    String fileName = URLEncoder.encode("待配列基本信息", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), RptBaseInfoVO.class)
        .sheet("待配列基本信息导入模板")
        .doWrite(rptBaseInfoVOList);
  }

  /**
   *excel数据导入
   * @param file
   */
  @Override
  public Boolean basicInfoUpload(MultipartFile file) {
    MultipartFileUtil.checkFile(file);
    List<RptBaseInfo> list = new ArrayList<>();
    LOG.info("======开始导入待配数据!======");
    try {
      RptBasicInfoUploadDataListener rptBasicInfoUploadDataListener = new RptBasicInfoUploadDataListener(rptExcelBatchService);
      EasyExcel.read(
          file.getInputStream(),
          RptBaseInfoVO.class,
          rptBasicInfoUploadDataListener)
          .sheet()
          .doRead();
      list = rptBasicInfoUploadDataListener.getList();
    } catch (Exception e) {
      LOG.info("======导入待配数据失败!======");
      throw new BizException("导入失败,请检验数据格式后再导入: " + e.getMessage());
    }
    LOG.info("======成功导入待配数据!======");

    if (CollectionUtils.isNotEmpty(list)){
      return true;
    }
    return false;
  }

  private List<RptBaseInfoVO> prtBaseInfoSampleData() {
    List<RptBaseInfoVO> list = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      RptBaseInfoVO rptBaseInfoVO =
          RptBaseInfoVO.builder()
              .createUsername("创建人"+i)
              .websiteName("网站名称"+i)
              .websiteUrl("连接"+i)
              .executor("执行人"+i)
              .distributionDate(new Date())
              .deliveryDate(new Date())
              .secondSiteType("二级站点类型"+i)
              .listPageAddress("列表页网址"+i)
              .differentTypeMixed("不同类型标讯混合"+i)
              .infoReleaseLevel("信息发布量级别"+i)
              .websiteNameCorrection("站点官网（修正）"+i)
              .websiteUrlCorrection("站点名称（修正）"+i)
              .regionCode("地区编码"+i)
              .provinceCode("省"+i)
              .cityCode("市"+i)
              .areaCode("地区编码"+i)
              .build();
      list.add(rptBaseInfoVO);
    }
    return list;
  }
}