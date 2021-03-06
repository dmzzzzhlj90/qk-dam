package com.qk.dm.datastandards.service.impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.util.IoUtils;
import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.datastandards.constant.DsdConstant;
import com.qk.dm.datastandards.easyexcel.handler.DsdBasicInfoCustomSheetWriteHandler;
import com.qk.dm.datastandards.easyexcel.handler.DsdCodeInfoCustomSheetWriteHandler;
import com.qk.dm.datastandards.easyexcel.listener.DsdBasicInfoUploadDataListener;
import com.qk.dm.datastandards.easyexcel.listener.DsdCodeInfoUploadDataListener;
import com.qk.dm.datastandards.easyexcel.listener.DsdCodeValuesUploadDataListener;
import com.qk.dm.datastandards.easyexcel.utils.DynamicEasyExcelExportUtils;
import com.qk.dm.datastandards.entity.*;
import com.qk.dm.datastandards.mapstruct.mapper.DsdBasicInfoMapper;
import com.qk.dm.datastandards.repositories.DsdBasicinfoRepository;
import com.qk.dm.datastandards.repositories.DsdCodeInfoExtRepository;
import com.qk.dm.datastandards.repositories.DsdCodeInfoRepository;
import com.qk.dm.datastandards.service.DataStandardCodeDirService;
import com.qk.dm.datastandards.service.DataStandardDirService;
import com.qk.dm.datastandards.service.DsdExcelService;
import com.qk.dm.datastandards.vo.CodeTableFieldsVO;
import com.qk.dm.datastandards.vo.DsdBasicInfoVO;
import com.qk.dm.datastandards.vo.DsdCodeInfoVO;
import com.querydsl.core.types.Predicate;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * ????????????excel????????????
 *
 * @author wjq
 * @date 2021/6/5 17:43
 * @since 1.0.0
 */
@Service
public class DsdExcelServiceImpl implements DsdExcelService {
  private static final Log LOG = LogFactory.get("????????????excel????????????");

  private final DsdBasicinfoRepository dsdBasicinfoRepository;
  private final DsdCodeInfoRepository dsdCodeInfoRepository;
  private final DsdCodeInfoExtRepository dsdCodeInfoExtRepository;

  private final DsdExcelBatchService dsdExcelBatchService;
  private final DataStandardDirService dataStandardDirService;
  private final DataStandardCodeDirService dataStandardCodeDirService;

  private final QDsdCodeInfoExt qDsdCodeInfoExt = QDsdCodeInfoExt.dsdCodeInfoExt;

  @Autowired
  public DsdExcelServiceImpl(
      DsdBasicinfoRepository dsdBasicinfoRepository,
      DsdCodeInfoRepository dsdCodeInfoRepository,
      DsdCodeInfoExtRepository dsdCodeInfoExtRepository,
      DsdExcelBatchService dsdExcelBatchService,
      DataStandardDirService dataStandardDirService,
      DataStandardCodeDirService dataStandardCodeDirService) {
    this.dsdBasicinfoRepository = dsdBasicinfoRepository;
    this.dsdCodeInfoRepository = dsdCodeInfoRepository;
    this.dsdCodeInfoExtRepository = dsdCodeInfoExtRepository;
    this.dsdExcelBatchService = dsdExcelBatchService;
    this.dataStandardDirService = dataStandardDirService;
    this.dataStandardCodeDirService = dataStandardCodeDirService;
  }

  //  ================================basicInfo===============================================
  @Override
  public void basicInfoUpload(MultipartFile file, String dirDsdId) {
    LOG.info("======????????????????????????!======");
    try {
      EasyExcel.read(
              file.getInputStream(),
              DsdBasicInfoVO.class,
              new DsdBasicInfoUploadDataListener(dsdExcelBatchService, dirDsdId))
          .sheet()
          .doRead();
    } catch (Exception e) {
      LOG.info("======????????????????????????!======");
      throw new BizException("????????????: " + e.getMessage());
    }
    LOG.info("======????????????????????????!======");
  }

  @Override
  public void basicInfoDownloadAll(HttpServletResponse response) throws IOException {
    List<DsdBasicInfoVO> dsdBasicInfoVOList = queryBasicInfos(null);
    // ????????????????????????
    Map<Integer, String[]> spinnerMap = getBasicInfoSpinnerMap();

    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    String fileName = URLEncoder.encode("????????????????????????", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), DsdBasicInfoVO.class)
        .sheet("??????")
        .registerWriteHandler(
            new DsdBasicInfoCustomSheetWriteHandler(spinnerMap, dsdBasicInfoVOList.size()))
        .doWrite(dsdBasicInfoVOList);
  }

  @Override
  public void basicInfoDownloadByDirDsdId(String dirDsdId, HttpServletResponse response)
      throws IOException {
    List<DsdBasicInfoVO> dsdBasicInfoVOList = queryBasicInfos(dirDsdId);
    Map<Integer, String[]> spinnerMap = getBasicInfoSpinnerMap();

    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    String fileName = URLEncoder.encode("????????????????????????", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), DsdBasicInfoVO.class)
        .sheet("??????")
        .registerWriteHandler(
            new DsdBasicInfoCustomSheetWriteHandler(spinnerMap, dsdBasicInfoVOList.size()))
        .doWrite(dsdBasicInfoVOList);
  }

  @Override
  public void basicInfoDownloadTemplate(HttpServletResponse response) throws IOException {
    List<DsdBasicInfoVO> dsdBasicInfoSampleDataList = dsdBasicInfoSampleData();
    // ????????????????????????????????????
    Map<Integer, String[]> spinnerMap = getBasicInfoSpinnerMap();

    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    String fileName = URLEncoder.encode("????????????????????????", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), DsdBasicInfoVO.class)
        .registerWriteHandler(
            new DsdBasicInfoCustomSheetWriteHandler(spinnerMap, dsdBasicInfoSampleDataList.size()))
        .sheet("??????????????????????????????")
        .doWrite(dsdBasicInfoSampleDataList);
  }

  public List<DsdBasicInfoVO> queryBasicInfos(String dirDsdId) {
    List<DsdBasicInfoVO> dsdBasicInfoVOList = new ArrayList<>();
    if (!StringUtils.isEmpty(dirDsdId)) {
      Set<String> dsdDirSet = new HashSet<>();
      dataStandardDirService.getDsdId(dsdDirSet, dirDsdId);
      Predicate predicate = QDsdBasicinfo.dsdBasicinfo.dsdLevelId.in(dsdDirSet);
      Iterable<DsdBasicinfo> dsdTermList = dsdBasicinfoRepository.findAll(predicate);
      dsdTermList.forEach(
          dsdBasicInfo -> {
            DsdBasicInfoVO dsdBasicinfoVO =
                DsdBasicInfoMapper.INSTANCE.useDsdBasicInfoVO(dsdBasicInfo);
            dsdBasicInfoVOList.add(dsdBasicinfoVO);
          });
    } else {
      List<DsdBasicinfo> dsdTermList = dsdBasicinfoRepository.findAll();
      dsdTermList.forEach(
          dsdBasicInfo -> {
            DsdBasicInfoVO dsdBasicinfoVO =
                DsdBasicInfoMapper.INSTANCE.useDsdBasicInfoVO(dsdBasicInfo);
            dsdBasicInfoVOList.add(dsdBasicinfoVO);
          });
    }
    return dsdBasicInfoVOList;
  }

  public List<String> findAllDsdDirLevel() {
    return dataStandardDirService.findAllDsdDirLevel();
  }

  //  ================================codeInfo===============================================

  @Override
  public void codeInfoAllUpload(MultipartFile file, String codeDirId) {
    try {
      EasyExcel.read(
              file.getInputStream(),
              DsdCodeInfoVO.class,
              new DsdCodeInfoUploadDataListener(dsdExcelBatchService, codeDirId))
          .sheet()
          .doRead();
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("????????????: " + e.getMessage());
    }
    LOG.info("======????????????????????????!======");
  }

  @Override
  public void codeInfoAllDownload(String codeDirId, HttpServletResponse response)
      throws IOException {
    List<DsdCodeInfoVO> dsdCodeInfoVOList = getCodeInfoAll(codeDirId);
    Map<Integer, String[]> spinnerMap = getCodeInoSpinnerMap(findAllDsdCodeDirLevel(), 0);

    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    String fileName = URLEncoder.encode("??????????????????", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), DsdCodeInfoVO.class)
        .sheet("??????????????????")
        .registerWriteHandler(
            new DsdCodeInfoCustomSheetWriteHandler(spinnerMap, dsdCodeInfoVOList.size()))
        .doWrite(dsdCodeInfoVOList);
  }

  @Override
  public void codeInfoDownloadTemplate(HttpServletResponse response) throws IOException {
    Map<Integer, String[]> spinnerMap = getCodeInoSpinnerMap(findAllDsdCodeDirLevel(), 0);

    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    String fileName = URLEncoder.encode("??????????????????????????????", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), DsdCodeInfoVO.class)
        .registerWriteHandler(new DsdCodeInfoCustomSheetWriteHandler(spinnerMap, 0))
        .sheet("??????????????????????????????")
        .doWrite(new ArrayList());
  }

  public List<String> findAllDsdCodeDirLevel() {
    return dataStandardCodeDirService.findAllDsdCodeDirLevel();
  }

  public List<DsdCodeInfoVO> getCodeInfoAll(String codeDirId) {
    List<DsdCodeInfoVO> dsdBasicInfoVOList = new ArrayList<>();

    try {
      if (StringUtils.isEmpty(codeDirId)) {
        getCodeInfoAllDownloadValues(dsdBasicInfoVOList);
      } else {
        getCodeInfoDownloadValuesByCodeDirId(codeDirId, dsdBasicInfoVOList);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("???????????? : " + e.getMessage());
    }
    return dsdBasicInfoVOList;
  }

  private void getCodeInfoDownloadValuesByCodeDirId(
      String codeDirId, List<DsdCodeInfoVO> dsdBasicInfoVOList) {
    Predicate predicate = QDsdCodeInfo.dsdCodeInfo.codeDirId.eq(codeDirId);
    Iterable<DsdCodeInfo> dsdCodeInfoList = dsdCodeInfoRepository.findAll(predicate);
    for (DsdCodeInfo dsdCodeInfo : dsdCodeInfoList) {
      String tableConfFieldsStr = dsdCodeInfo.getTableConfFields();
      List<CodeTableFieldsVO> codeTableFieldsVOList =
          GsonUtil.fromJsonString(
              tableConfFieldsStr, new TypeToken<List<CodeTableFieldsVO>>() {}.getType());
      for (CodeTableFieldsVO codeTableFieldsVO : codeTableFieldsVOList) {
        DsdCodeInfoVO dsdCodeInfoVO =
            DsdCodeInfoVO.builder()
                .codeDirId(dsdCodeInfo.getCodeDirId())
                .codeDirLevel(dsdCodeInfo.getCodeDirLevel())
                .tableName(dsdCodeInfo.getTableName())
                .tableCode(dsdCodeInfo.getTableCode())
                .tableDesc(dsdCodeInfo.getTableDesc())
                .codeTableId(codeTableFieldsVO.getCode_table_id())
                .nameCh(codeTableFieldsVO.getName_ch())
                .nameEn(codeTableFieldsVO.getName_en())
                .dataType(codeTableFieldsVO.getData_type())
                .build();
        dsdBasicInfoVOList.add(dsdCodeInfoVO);
      }
    }
  }

  private void getCodeInfoAllDownloadValues(List<DsdCodeInfoVO> dsdBasicInfoVOList) {
    List<DsdCodeInfo> dsdCodeInfoList = dsdCodeInfoRepository.findAll();
    for (DsdCodeInfo dsdCodeInfo : dsdCodeInfoList) {
      String tableConfFieldsStr = dsdCodeInfo.getTableConfFields();
      List<CodeTableFieldsVO> codeTableFieldsVOList =
          GsonUtil.fromJsonString(
              tableConfFieldsStr, new TypeToken<List<CodeTableFieldsVO>>() {}.getType());
      for (CodeTableFieldsVO codeTableFieldsVO : codeTableFieldsVOList) {
        DsdCodeInfoVO dsdCodeInfoVO =
            DsdCodeInfoVO.builder()
                .codeDirId(dsdCodeInfo.getCodeDirId())
                .codeDirLevel(dsdCodeInfo.getCodeDirLevel())
                .tableName(dsdCodeInfo.getTableName())
                .tableCode(dsdCodeInfo.getTableCode())
                .tableDesc(dsdCodeInfo.getTableDesc())
                .codeTableId(codeTableFieldsVO.getCode_table_id())
                .nameCh(codeTableFieldsVO.getName_ch())
                .nameEn(codeTableFieldsVO.getName_en())
                .dataType(codeTableFieldsVO.getData_type())
                .build();
        dsdBasicInfoVOList.add(dsdCodeInfoVO);
      }
    }
  }

  //  ================================codeValues===============================================

  @Override
  public void codeValuesUploadByCodeInfoId(MultipartFile file, long dsdCodeInfoId) {
    try {
      InputStream inputStream = file.getInputStream();
      byte[] stream = IoUtils.toByteArray(inputStream);
      // ??????excel??????
      DsdCodeValuesUploadDataListener readListener = new DsdCodeValuesUploadDataListener();
      EasyExcelFactory.read(new ByteArrayInputStream(stream))
          .registerReadListener(readListener)
          .headRowNumber(1)
          .sheet(0)
          .doRead();
      // ??????????????????
      Optional<DsdCodeInfo> dsdCodeInfo = dsdCodeInfoRepository.findById(dsdCodeInfoId);
      // ??????????????????
      String tableConfFieldsStr = dsdCodeInfo.get().getTableConfFields();
      List<CodeTableFieldsVO> codeTableFieldsVOList =
          GsonUtil.fromJsonString(
              tableConfFieldsStr, new TypeToken<List<CodeTableFieldsVO>>() {}.getType());
      // ????????????
      List<Map<Integer, String>> headList =
          checkCodeInfoHeadList(readListener, codeTableFieldsVOList);
      // ??????????????????
      List<Map<Integer, String>> dataList = checkCodeInfoDataList(readListener);

      Map<String, String> fieldMap =
          codeTableFieldsVOList.stream()
              .collect(
                  Collectors.toMap(
                      CodeTableFieldsVO::getName_ch, CodeTableFieldsVO::getCode_table_id));
      Map<Integer, String> excelHeadIdxNameMap = headList.get(headList.size() - 1);
      List<DsdCodeInfoExt> saveDataList = Lists.newArrayList();
      // ????????????
      getCodeValues(dsdCodeInfoId, dataList, fieldMap, excelHeadIdxNameMap, saveDataList);

      dsdExcelBatchService.addDsdCodeValuesBatch(saveDataList, dsdCodeInfoId);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("????????????: " + e.getMessage());
    }
  }

  @Override
  public void codeValuesDownloadByCodeInfoId(HttpServletResponse response, Long dsdCodeInfoId) {
    try {
      Map<String, Object> headInfoMap = getCodeExcelHeadList(dsdCodeInfoId);
      List<List<Object>> excelRows =
          getCodeExcelValues(dsdCodeInfoId, (LinkedList<String>) headInfoMap.get("headKeyList"));
      DynamicEasyExcelExportUtils.exportWebExcelFile(
          response, (List<List<String>>) headInfoMap.get("excelHead"), excelRows, "??????????????????");
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("??????????????????!");
    }
  }

  @Override
  public void codeValuesDownloadTemplate(HttpServletResponse response, long dsdCodeInfoId) {
    try {
      final Map<String, Object> headInfoMap = getCodeExcelHeadList(dsdCodeInfoId);
      List<List<Object>> excelRows =
          getCodeTemplateSampleData((List<List<String>>) headInfoMap.get("excelHead"));
      DynamicEasyExcelExportUtils.exportWebExcelFile(
          response, (List<List<String>>) headInfoMap.get("excelHead"), excelRows, "????????????????????????_????????????");
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("??????????????????!");
    }
  }

  private Map<String, Object> getCodeExcelHeadList(Long dsdCodeInfoId) {
    Map<String, Object> headInfoMap = new HashMap<>();
    List<List<String>> excelHead = new ArrayList<>();
    LinkedList<String> headKeyList = new LinkedList<>();

    Optional<DsdCodeInfo> dsdCodeInfo = dsdCodeInfoRepository.findById(dsdCodeInfoId);
    String tableConfFields = dsdCodeInfo.get().getTableConfFields();
    List<CodeTableFieldsVO> codeTableFieldsVOList =
        GsonUtil.fromJsonString(
            tableConfFields, new TypeToken<List<CodeTableFieldsVO>>() {}.getType());

    codeTableFieldsVOList.forEach(
        codeTableFieldsVO -> {
          excelHead.add(Lists.newArrayList(codeTableFieldsVO.getName_ch().split(",")));
          headKeyList.add(codeTableFieldsVO.getCode_table_id());
        });
    headInfoMap.put("excelHead", excelHead);
    headInfoMap.put("headKeyList", headKeyList);
    return headInfoMap;
  }

  private List<List<Object>> getCodeExcelValues(
      Long dsdCodeInfoId, LinkedList<String> headKeyList) {
    List<List<Object>> excelRows = new ArrayList<>();
    Predicate predicate = qDsdCodeInfoExt.dsdCodeInfoId.eq(dsdCodeInfoId);
    Iterable<DsdCodeInfoExt> dsdCodeInfoExtIter = dsdCodeInfoExtRepository.findAll(predicate);

    for (DsdCodeInfoExt dsdCodeInfoExt : dsdCodeInfoExtIter) {
      List<Object> rows = new ArrayList<>();
      //      String code = dsdCodeInfoExt.getTableConfCode();
      //      String value = dsdCodeInfoExt.getTableConfValue();
      //      rows.add(code);
      //      rows.add(value);
      String extValuesStr = dsdCodeInfoExt.getTableConfExtValues();
      if (!StringUtils.isEmpty(extValuesStr)) {
        LinkedHashMap<String, String> extDataMap =
            GsonUtil.fromJsonString(
                extValuesStr, new TypeToken<LinkedHashMap<String, String>>() {}.getType());
        for (String headKey : headKeyList) {
          rows.add(extDataMap.get(headKey));
        }
      }
      excelRows.add(rows);
    }
    return excelRows;
  }

  private Map<Integer, String[]> getCodeInoSpinnerMap(List<String> allDsdCodeDirLevel, int i) {
    // ??????????????????????????????
    List<String> dsdCodeDirLevelList = allDsdCodeDirLevel;
    Map<Integer, String[]> spinnerMap = new HashMap<>();
    String[] strings = new String[dsdCodeDirLevelList.size()];
    dsdCodeDirLevelList.toArray(strings);
    spinnerMap.put(i, strings);
    return spinnerMap;
  }

  private Map<Integer, String[]> getBasicInfoSpinnerMap() {
    // ????????????????????????
    List<String> dsdDirLevelList = findAllDsdDirLevel();
    Map<Integer, String[]> spinnerMap = new HashMap<>();
    String[] strings = new String[dsdDirLevelList.size()];
    dsdDirLevelList.toArray(strings);
    spinnerMap.put(7, strings);
    return spinnerMap;
  }

  private void getCodeValues(
      long dsdCodeInfoId,
      List<Map<Integer, String>> dataList,
      Map<String, String> fieldMap,
      Map<Integer, String> excelHeadIdxNameMap,
      List<DsdCodeInfoExt> saveDataList) {
    for (Map<Integer, String> dataRow : dataList) {
      DsdCodeInfoExt dsdCodeInfoExt = new DsdCodeInfoExt();
      dsdCodeInfoExt.setDsdCodeInfoId(dsdCodeInfoId);
      dsdCodeInfoExt.setGmtCreate(new Date());
      dsdCodeInfoExt.setGmtModified(new Date());
      dsdCodeInfoExt.setGmtModified(new Date());
      dsdCodeInfoExt.setDelFlag(0);
      // ??????rowExtData
      Map<String, String> rowExtData = new LinkedHashMap<>();
      setRowExtData(fieldMap, excelHeadIdxNameMap, dataRow, dsdCodeInfoExt, rowExtData);
      dsdCodeInfoExt.setTableConfExtValues(GsonUtil.toJsonString(rowExtData));
      saveDataList.add(dsdCodeInfoExt);
    }
  }

  private void setRowExtData(
      Map<String, String> fieldMap,
      Map<Integer, String> excelHeadIdxNameMap,
      Map<Integer, String> dataRow,
      DsdCodeInfoExt dsdCodeInfoExt,
      Map<String, String> rowExtData) {
    excelHeadIdxNameMap
        .entrySet()
        .forEach(
            columnHead -> {
              String value = dataRow.get(columnHead.getKey());
              rowExtData.put(fieldMap.get(columnHead.getValue()), value);
              // TODO ???????????????????????????????????????code||name, 20210910
              if (columnHead.getValue().equals(DsdConstant.CODE_INFO_CODE_CH_NAME)
                  || columnHead.getValue().equalsIgnoreCase(DsdConstant.CODE_INFO_CODE_EN_NAME)) {
                dsdCodeInfoExt.setSearchCode(value);
              } else if (columnHead.getValue().equals(DsdConstant.CODE_INFO_NAME_CH_NAME)
                  || columnHead.getValue().equalsIgnoreCase(DsdConstant.CODE_INFO_NAME_EN_NAME)) {
                dsdCodeInfoExt.setSearchValue(value);
              }
            });
  }

  private List<Map<Integer, String>> checkCodeInfoDataList(
      DsdCodeValuesUploadDataListener readListener) {
    List<Map<Integer, String>> dataList = readListener.getDataList();
    //        if (CollectionUtils.isEmpty(dataList)) {
    //            throw new BizException("Excel??????????????????!!!");
    //        }
    return dataList;
  }

  private List<Map<Integer, String>> checkCodeInfoHeadList(
      DsdCodeValuesUploadDataListener readListener, List<CodeTableFieldsVO> codeTableFieldsVOList) {
    List<Map<Integer, String>> headList = readListener.getHeadList();
    if (CollectionUtils.isEmpty(headList)) {
      throw new BizException("Excel???????????????!");
    }
    if (headList.get(headList.size() - 1).size() != codeTableFieldsVOList.size()) {
      throw new BizException("Excel??????????????????!?????????????????????,??????????????????????????????????????????!!!");
    }
    return headList;
  }

  //  ================================SampleData===============================================
  public List<DsdBasicInfoVO> dsdBasicInfoSampleData() {
    List<DsdBasicInfoVO> list = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      DsdBasicInfoVO dsdBasicinfoVO =
          DsdBasicInfoVO.builder()
              .dsdName("??????????????????" + i)
              .dsdCode("??????????????????" + i)
              .colName("column" + i)
              .dataType("varchar")
              .dataCapacity("50")
              .useCodeLevel("/code_table" + i)
              .dsdLevel("/????????????")
              .description("????????????????????????,excel??????????????????!")
              .build();
      list.add(dsdBasicinfoVO);
    }
    return list;
  }

  public List<List<Object>> getCodeTemplateSampleData(List<List<String>> excelHead) {
    List<List<Object>> list = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      List rowData = new ArrayList();
      for (List<String> headDatas : excelHead) {
        for (String head : headDatas) {
          rowData.add("   " + head + (i + 1) + "   ");
        }
      }
      list.add(rowData);
    }
    return list;
  }
}
