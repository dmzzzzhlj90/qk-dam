package com.qk.dm.datastandards.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.util.IoUtils;
import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
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
import com.qk.dm.datastandards.utils.GsonUtil;
import com.qk.dm.datastandards.vo.CodeTableFieldsVO;
import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import com.qk.dm.datastandards.vo.DsdCodeInfoVO;
import com.querydsl.core.types.Predicate;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据标准excel导入导出
 *
 * @author wjq
 * @date 2021/6/5 17:43
 * @since 1.0.0
 */
@Service
public class DsdExcelServiceImpl implements DsdExcelService {
    private final DsdBasicinfoRepository dsdBasicinfoRepository;

    private final DsdCodeInfoRepository dsdCodeInfoRepository;
    private final DsdCodeInfoExtRepository dsdCodeInfoExtRepository;

    private final DsdExcelBatchService dsdExcelBatchService;
    private final DataStandardDirService dataStandardDirService;
    private final DataStandardCodeDirService dataStandardCodeDirService;

    private final QDsdCodeInfoExt qDsdCodeInfoExt = QDsdCodeInfoExt.dsdCodeInfoExt;

    @Autowired
    public DsdExcelServiceImpl(DsdBasicinfoRepository dsdBasicinfoRepository, DsdCodeInfoRepository dsdCodeInfoRepository,
                               DsdCodeInfoExtRepository dsdCodeInfoExtRepository, DsdExcelBatchService dsdExcelBatchService, DataStandardDirService dataStandardDirService,
                               DataStandardCodeDirService dataStandardCodeDirService) {
        this.dsdBasicinfoRepository = dsdBasicinfoRepository;
        this.dsdCodeInfoRepository = dsdCodeInfoRepository;
        this.dsdCodeInfoExtRepository = dsdCodeInfoExtRepository;
        this.dsdExcelBatchService = dsdExcelBatchService;
        this.dataStandardDirService = dataStandardDirService;
        this.dataStandardCodeDirService = dataStandardCodeDirService;
    }

    @Override
    public List<DsdBasicinfoVO> queryBasicInfos(String dirDsdId) {
        List<DsdBasicinfoVO> dsdBasicInfoVOList = new ArrayList<>();
        if (!StringUtils.isEmpty(dirDsdId)) {
            Set<String> dsdDirSet = new HashSet<>();
            dataStandardDirService.getDsdId(dsdDirSet, dirDsdId);
            Predicate predicate = QDsdBasicinfo.dsdBasicinfo.dsdLevelId.in(dsdDirSet);
            Iterable<DsdBasicinfo> dsdTermList = dsdBasicinfoRepository.findAll(predicate);
            dsdTermList.forEach(
                    dsdBasicInfo -> {
                        DsdBasicinfoVO dsdBasicinfoVO = DsdBasicInfoMapper.INSTANCE.useDsdBasicInfoVO(dsdBasicInfo);
                        dsdBasicInfoVOList.add(dsdBasicinfoVO);
                    });
        } else {
            List<DsdBasicinfo> dsdTermList = dsdBasicinfoRepository.findAll();
            dsdTermList.forEach(
                    dsdBasicInfo -> {
                        DsdBasicinfoVO dsdBasicinfoVO = DsdBasicInfoMapper.INSTANCE.useDsdBasicInfoVO(dsdBasicInfo);
                        dsdBasicInfoVOList.add(dsdBasicinfoVO);
                    });
        }

        return dsdBasicInfoVOList;
    }

    @Override
    public void basicInfoUpload(MultipartFile file, String dirDsdId) throws IOException {
        EasyExcel.read(file.getInputStream(), DsdBasicinfoVO.class,
                new DsdBasicInfoUploadDataListener(dsdExcelBatchService, dirDsdId)).sheet().doRead();
    }

    @Override
    public List<String> findAllDsdDirLevel() {
        return dataStandardDirService.findAllDsdDirLevel();
    }

    @Override
    public List<String> findAllDsdCodeDirLevel() {
        return dataStandardCodeDirService.findAllDsdCodeDirLevel();
    }

    @Override
    public List<DsdBasicinfoVO> dsdBasicInfoSampleData() {
        List<DsdBasicinfoVO> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            DsdBasicinfoVO dsdBasicinfoVO = DsdBasicinfoVO.builder()
                    .dsdName("数据标准名称" + i)
                    .dsdCode("数据标准代码" + i)
                    .colName("column" + i)
                    .dataType("varchar")
                    .dataCapacity("50")
                    .useCodeLevel("/code_table" + i)
                    .dsdLevel("/全部标准")
                    .description("数据标准基本信息,excel模板样例数据!")
                    .build();
            list.add(dsdBasicinfoVO);
        }
        return list;
    }

    @Override
    public void codeValuesDownloadByCodeInfoId(HttpServletResponse response, Long dsdCodeInfoId) {
        try {
            List<List<String>> excelHead = getCodeExcelHeadList(dsdCodeInfoId);
            List<List<Object>> excelRows = getCodeExcelValues(dsdCodeInfoId);
            DynamicEasyExcelExportUtils.exportWebExcelFile(response, excelHead, excelRows, "码表数值信息");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("导出文件失败!");
        }
    }

    private List<List<String>> getCodeExcelHeadList(Long dsdCodeInfoId) {
        Optional<DsdCodeInfo> dsdCodeInfo = dsdCodeInfoRepository.findById(dsdCodeInfoId);
        String tableConfFields = dsdCodeInfo.get().getTableConfFields();
        List<CodeTableFieldsVO> codeTableFieldsVOList = GsonUtil.fromJsonString(tableConfFields,
                new TypeToken<List<CodeTableFieldsVO>>() {
                }.getType());
        List<List<String>> excelHead = new ArrayList<>();
        codeTableFieldsVOList.forEach(codeTableFieldsVO -> {
            excelHead.add(Lists.newArrayList(codeTableFieldsVO.getName_ch().split(",")));
        });
        return excelHead;
    }

    private List<List<Object>> getCodeExcelValues(Long dsdCodeInfoId) {
        List<List<Object>> excelRows = new ArrayList<>();
        Predicate predicate = qDsdCodeInfoExt.dsdCodeInfoId.eq(dsdCodeInfoId);
        Iterable<DsdCodeInfoExt> dsdCodeInfoExtIter = dsdCodeInfoExtRepository.findAll(predicate);

        for (DsdCodeInfoExt dsdCodeInfoExt : dsdCodeInfoExtIter) {
            List<Object> rows = new ArrayList<>();
            String code = dsdCodeInfoExt.getTableConfCode();
            String value = dsdCodeInfoExt.getTableConfValue();
            rows.add(code);
            rows.add(value);
            String extValuesStr = dsdCodeInfoExt.getTableConfExtValues();
            if (!StringUtils.isEmpty(extValuesStr)) {
                LinkedHashMap<String, String> extDataMap = GsonUtil.fromJsonString(extValuesStr, new TypeToken<LinkedHashMap<String, String>>() {
                }.getType());
                for (String key : extDataMap.keySet()) {
                    rows.add(extDataMap.get(key));
                }
            }
            excelRows.add(rows);
        }
        return excelRows;
    }

    @Override
    public void codeValuesUploadByCodeInfoId(MultipartFile file, long dsdCodeInfoId) {
        try {
            InputStream inputStream = file.getInputStream();
            byte[] stream = IoUtils.toByteArray(inputStream);
            DsdCodeValuesUploadDataListener readListener = new DsdCodeValuesUploadDataListener();
            EasyExcelFactory.read(new ByteArrayInputStream(stream)).registerReadListener(readListener).headRowNumber(1).sheet(0).doRead();

            Optional<DsdCodeInfo> dsdCodeInfo = dsdCodeInfoRepository.findById(dsdCodeInfoId);
            String tableConfFieldsStr = dsdCodeInfo.get().getTableConfFields();
            List<CodeTableFieldsVO> codeTableFieldsVOList = GsonUtil.fromJsonString(tableConfFieldsStr, new TypeToken<List<CodeTableFieldsVO>>() {
            }.getType());

            List<Map<Integer, String>> headList = checkCodeInfoHeadList(readListener, codeTableFieldsVOList);
            List<Map<Integer, String>> dataList = checkCodeInfoDataList(readListener);

            Map<String, String> fieldMap = codeTableFieldsVOList.stream().collect(Collectors.toMap(CodeTableFieldsVO::getName_ch, CodeTableFieldsVO::getCode_table_id));
            Map<Integer, String> excelHeadIdxNameMap = headList.get(headList.size() - 1);
            List<DsdCodeInfoExt> saveDataList = Lists.newArrayList();
            List<String> codeList = Lists.newArrayList();
            getCodeValues(dsdCodeInfoId, dataList, fieldMap, excelHeadIdxNameMap, saveDataList, codeList);

            dsdExcelBatchService.addDsdCodeValuesBatch(saveDataList, dsdCodeInfoId, codeList);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BizException("导入异常!");
        }
    }

    @Override
    public void codeValuesDownloadTemplate(HttpServletResponse response, long dsdCodeInfoId) {
        try {
            List<List<String>> excelHead = getCodeExcelHeadList(dsdCodeInfoId);
            List<List<Object>> excelRows = getCodeTemplateSampleData(excelHead);
            DynamicEasyExcelExportUtils.exportWebExcelFile(response, excelHead, excelRows, "数据标准码表信息_模板下载");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("导出文件失败!");
        }
    }

    @Override
    public List<DsdCodeInfoVO> codeInfoAllDownload() {
        List<DsdCodeInfoVO> dsdBasicInfoVOList = new ArrayList<>();
        List<DsdCodeInfo> dsdCodeInfoList = dsdCodeInfoRepository.findAll();

        for (DsdCodeInfo dsdCodeInfo : dsdCodeInfoList) {
            String tableConfFieldsStr = dsdCodeInfo.getTableConfFields();
            List<CodeTableFieldsVO> codeTableFieldsVOList = GsonUtil.fromJsonString(tableConfFieldsStr, new TypeToken<List<CodeTableFieldsVO>>() {
            }.getType());
            for (CodeTableFieldsVO codeTableFieldsVO : codeTableFieldsVOList) {
                DsdCodeInfoVO dsdCodeInfoVO = DsdCodeInfoVO.builder().codeDirId(dsdCodeInfo.getCodeDirId())
                        .codeDirLevel(dsdCodeInfo.getCodeDirLevel())
                        .tableName(dsdCodeInfo.getTableName())
                        .tableCode(dsdCodeInfo.getTableCode())
                        .tableDesc(dsdCodeInfo.getTableDesc())
                        .codeTableId(codeTableFieldsVO.getCode_table_id())
                        .nameCh(codeTableFieldsVO.getName_ch())
                        .nameEn(codeTableFieldsVO.getName_en())
                        .dataType(codeTableFieldsVO.getData_type()).build();
                dsdBasicInfoVOList.add(dsdCodeInfoVO);
            }
        }
        return dsdBasicInfoVOList;
    }

    @Override
    public void codeInfoAllUpload(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), DsdCodeInfoVO.class,
                    new DsdCodeInfoUploadDataListener(dsdExcelBatchService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BizException("导入失败!");
        }
    }

    private void getCodeValues(long dsdCodeInfoId, List<Map<Integer, String>> dataList, Map<String, String> fieldMap, Map<Integer, String> excelHeadIdxNameMap, List<DsdCodeInfoExt> saveDataList, List<String> codeList) {
        for (Map<Integer, String> dataRow : dataList) {
            DsdCodeInfoExt dsdCodeInfoExt = new DsdCodeInfoExt();
            dsdCodeInfoExt.setDsdCodeInfoId(dsdCodeInfoId);
            dsdCodeInfoExt.setGmtCreate(new Date());
            dsdCodeInfoExt.setGmtModified(new Date());
            Map<String, String> rowExtData = new LinkedHashMap<>();
            excelHeadIdxNameMap.entrySet().forEach(columnHead -> {
                final String value = dataRow.get(columnHead.getKey());
                if (columnHead.getValue().equals("编码")) {
                    dsdCodeInfoExt.setTableConfCode(value);
                    codeList.add(value);
                } else if (columnHead.getValue().equals("值")) {
                    dsdCodeInfoExt.setTableConfValue(value);
                } else {
                    rowExtData.put(fieldMap.get(columnHead.getValue()), value);
                }
            });
            dsdCodeInfoExt.setTableConfExtValues(GsonUtil.toJsonString(rowExtData));
            saveDataList.add(dsdCodeInfoExt);
        }
    }

    private List<Map<Integer, String>> checkCodeInfoDataList(DsdCodeValuesUploadDataListener readListener) {
        List<Map<Integer, String>> dataList = readListener.getDataList();
        if (CollectionUtils.isEmpty(dataList)) {
            throw new BizException("Excel导入数据为空!!!");
        }
        return dataList;
    }

    private List<Map<Integer, String>> checkCodeInfoHeadList(DsdCodeValuesUploadDataListener readListener, List<CodeTableFieldsVO> codeTableFieldsVOList) {
        List<Map<Integer, String>> headList = readListener.getHeadList();
        if (CollectionUtils.isEmpty(headList)) {
            throw new BizException("Excel未包含表头!");
        }
        if (headList.get(headList.size() - 1).size() != codeTableFieldsVOList.size()) {
            throw new BizException("Excel表头信息有误!请核对配置信息,下载新的模板进行导入维护数据!!!");
        }
        return headList;
    }

    public List<List<Object>> getCodeTemplateSampleData(List<List<String>> excelHead) {
        List<List<Object>> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            List rowData = new ArrayList();
            for (List<String> headDatas : excelHead) {
                for (String head : headDatas) {
                    rowData.add(head + (i + 1));
                }
            }
            list.add(rowData);
        }
        return list;
    }
}
