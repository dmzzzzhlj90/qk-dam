package com.qk.dm.datastandards.service.impl;

import com.alibaba.excel.EasyExcel;
import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datastandards.easyexcel.listener.DsdBasicInfoUploadDataListener;
import com.qk.dm.datastandards.easyexcel.listener.DsdCodeTermUploadDataListener;
import com.qk.dm.datastandards.easyexcel.listener.DsdTermUploadDataListener;
import com.qk.dm.datastandards.easyexcel.utils.DynamicEasyExcelExportUtils;
import com.qk.dm.datastandards.entity.*;
import com.qk.dm.datastandards.mapstruct.mapper.DsdBasicInfoMapper;
import com.qk.dm.datastandards.mapstruct.mapper.DsdCodeTermMapper;
import com.qk.dm.datastandards.mapstruct.mapper.DsdTermMapper;
import com.qk.dm.datastandards.repositories.*;
import com.qk.dm.datastandards.service.DataStandardCodeDirService;
import com.qk.dm.datastandards.service.DataStandardDirService;
import com.qk.dm.datastandards.service.DsdExcelService;
import com.qk.dm.datastandards.utils.GsonUtil;
import com.qk.dm.datastandards.vo.CodeTableFieldsVO;
import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import com.qk.dm.datastandards.vo.DsdCodeTermVO;
import com.qk.dm.datastandards.vo.DsdTermVO;
import com.querydsl.core.types.Predicate;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 数据标准excel导入导出
 *
 * @author wjq
 * @date 2021/6/5 17:43
 * @since 1.0.0
 */
@Service
public class DsdExcelServiceImpl implements DsdExcelService {
    @PersistenceContext
    private EntityManager entityManager;

    private final DsdTermRepository dsdTermRepository;
    private final DsdBasicinfoRepository dsdBasicinfoRepository;
    private final DsdCodeTermRepository dsdCodeTermRepository;

    private final DsdCodeInfoRepository dsdCodeInfoRepository;
    private final DsdCodeInfoExtRepository dsdCodeInfoExtRepository;

    private final DsdExcelBatchService dsdExcelBatchService;
    private final DataStandardDirService dataStandardDirService;
    private final DataStandardCodeDirService dataStandardCodeDirService;

    private final QDsdCodeInfoExt qDsdCodeInfoExt = QDsdCodeInfoExt.dsdCodeInfoExt;

    @Autowired
    public DsdExcelServiceImpl(
            DsdTermRepository dsdTermRepository,
            DsdBasicinfoRepository dsdBasicinfoRepository,
            DsdCodeTermRepository dsdCodeTermRepository,
            EntityManager entityManager,
            DsdCodeInfoRepository dsdCodeInfoRepository, DsdCodeInfoExtRepository dsdCodeInfoExtRepository, DsdExcelBatchService dsdExcelBatchService, DataStandardDirService dataStandardDirService, DataStandardCodeDirService dataStandardCodeDirService) {
        this.dsdTermRepository = dsdTermRepository;
        this.dsdBasicinfoRepository = dsdBasicinfoRepository;
        this.dsdCodeTermRepository = dsdCodeTermRepository;
        this.entityManager = entityManager;
        this.dsdCodeInfoRepository = dsdCodeInfoRepository;
        this.dsdCodeInfoExtRepository = dsdCodeInfoExtRepository;
        this.dsdExcelBatchService = dsdExcelBatchService;
        this.dataStandardDirService = dataStandardDirService;
        this.dataStandardCodeDirService = dataStandardCodeDirService;
    }


    @Override
    public List<DsdTermVO> queryAllTerm() {
        List<DsdTermVO> dsdTermVOList = new ArrayList<>();

        List<DsdTerm> dsdTermList = dsdTermRepository.findAll();
        dsdTermList.forEach(
                dsdTerm -> {
                    DsdTermVO dsdTermVO = DsdTermMapper.INSTANCE.useDsdTermVO(dsdTerm);
                    dsdTermVOList.add(dsdTermVO);
                });
        return dsdTermVOList;
    }

    @Override
    public void termUpload(MultipartFile file) throws IOException {
        EasyExcel.read(
                file.getInputStream(),
                DsdTermVO.class,
                new DsdTermUploadDataListener(dsdTermRepository))
                .sheet()
                .doRead();
    }


    @Override
    public List<DsdCodeTermVO> queryCodeTerms(String codeDirId) {
        List<DsdCodeTermVO> codeTermVOList = new ArrayList<>();
        if (!StringUtils.isEmpty(codeDirId)) {
            Set<String> codeDirSet = new HashSet<>();
            dataStandardCodeDirService.getCodeDirId(codeDirSet, codeDirId);
            Predicate predicate = QDsdCodeTerm.dsdCodeTerm.codeDirId.in(codeDirSet);
            Iterable<DsdCodeTerm> dsdTermList = dsdCodeTermRepository.findAll(predicate);

            dsdTermList.forEach(
                    dsdCodeTerm -> {
                        DsdCodeTermVO dsdCodeTermVO = DsdCodeTermMapper.INSTANCE.usDsdCodeTermVO(dsdCodeTerm);
                        codeTermVOList.add(dsdCodeTermVO);
                    });
        } else {
            List<DsdCodeTerm> dsdTermList = dsdCodeTermRepository.findAll();
            dsdTermList.forEach(
                    dsdCodeTerm -> {
                        DsdCodeTermVO dsdCodeTermVO = DsdCodeTermMapper.INSTANCE.usDsdCodeTermVO(dsdCodeTerm);
                        codeTermVOList.add(dsdCodeTermVO);
                    });
        }
        return codeTermVOList;
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
    public void codeTermUpload(MultipartFile file, String codeDirId) throws IOException {
        EasyExcel.read(file.getInputStream(), DsdCodeTermVO.class,
                new DsdCodeTermUploadDataListener(dsdExcelBatchService, codeDirId)).sheet().doRead();
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
    public List<DsdCodeTermVO> dsdCodeTermSampleData() {
        List<DsdCodeTermVO> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            DsdCodeTermVO dsdCodeTermVO = DsdCodeTermVO.builder()
                    .codeTableChnName("中文表名称" + i)
                    .codeTableEnName("英文表名称" + i)

                    .codeDirLevel("/全部标准")
                    .termId(0)
                    .description("数据标准码表,excel模板样例数据!")
                    .build();
            list.add(dsdCodeTermVO);
        }
        return list;
    }

    @Override
    public void codeValuesDownloadByCodeInfoId(HttpServletResponse response, Long dsdCodeInfoId) {
        try {
            List<List<String>> excelHead = getCodeExcelHeadList(dsdCodeInfoId);
            List<List<Object>> excelRows = getCodeExcelValues(dsdCodeInfoId);
            DynamicEasyExcelExportUtils.exportWebExcelFile(response, excelHead, excelRows, "数据标准码表信息");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("导出文件失败!");
        }
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


}
