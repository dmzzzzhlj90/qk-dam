package com.qk.dm.datastandards.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datastandards.entity.*;
import com.qk.dm.datastandards.repositories.*;
import com.querydsl.core.types.Predicate;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据标准excel 批量导入导出
 *
 * @author wjq
 * @date 2021/6/20 16:10
 * @since 1.0.0
 */
@Service
public class DsdExcelBatchService {
    @PersistenceContext
    private EntityManager entityManager;

    private final DsdBasicinfoRepository dsdBasicinfoRepository;
    private final DsdDirRepository dsdDirRepository;
    private final DsdCodeInfoExtRepository dsdCodeInfoExtRepository;
    private final DsdCodeInfoRepository dsdCodeInfoRepository;
    private final DsdCodeDirRepository dsdCodeDirRepository;

    @Autowired
    public DsdExcelBatchService(DsdBasicinfoRepository dsdBasicinfoRepository, DsdDirRepository dsdDirRepository, DsdCodeInfoExtRepository dsdCodeInfoExtRepository, DsdCodeInfoRepository dsdCodeInfoRepository, DsdCodeDirRepository dsdCodeDirRepository) {
        this.dsdBasicinfoRepository = dsdBasicinfoRepository;
        this.dsdDirRepository = dsdDirRepository;
        this.dsdCodeInfoExtRepository = dsdCodeInfoExtRepository;
        this.dsdCodeInfoRepository = dsdCodeInfoRepository;
        this.dsdCodeDirRepository = dsdCodeDirRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public void addDsdBasicInfoBatch(
            List<DsdBasicinfo> dsdBasicInfoList,
            Set<String> dsdDirLevelSet,
            Map<String, String> codeDirLevelMap,
            String dirDsdId) {
        if (!StringUtils.isEmpty(dirDsdId)) {
            saveDsdBasicInfoByDirId(dsdBasicInfoList, codeDirLevelMap, dirDsdId);
        } else {
            saveDsdBasicInfo(dsdBasicInfoList, dsdDirLevelSet, codeDirLevelMap);
        }
        entityManager.flush();
        entityManager.clear();
    }

    private void saveDsdBasicInfo(
            List<DsdBasicinfo> dsdBasicInfoList,
            Set<String> dsdDirLevelSet,
            Map<String, String> codeDirLevelMap) {
        List<DsdDir> dsdDirAllList = dsdDirRepository.findAll();
        List<String> dsdDirLevels =
                dsdDirAllList.stream().map(dsdDir -> dsdDir.getDsdDirLevel()).collect(Collectors.toList());

        dsdDirLevelSet.forEach(
                dirDsdLevel -> {
                    if (!dsdDirLevels.contains(dirDsdLevel)) {
                        throw new BizException("Excel中输入的标准目录层级:" + dirDsdLevel + ",参数有误!!!");
                    }
                });

        Map<String, List<DsdDir>> dsdDirLevelMap =
                dsdDirAllList.stream().collect(Collectors.groupingBy(DsdDir::getDsdDirLevel));
        Predicate existDataPredicate =
                QDsdBasicinfo.dsdBasicinfo
                        .dsdLevel
                        .in(dsdDirLevelSet)
                        .and(QDsdBasicinfo.dsdBasicinfo.dsdCode.in(codeDirLevelMap.keySet()));
        Iterable<DsdBasicinfo> existList = dsdBasicinfoRepository.findAll(existDataPredicate);
        dsdBasicinfoRepository.deleteInBatch(existList);

        for (DsdBasicinfo dsdBasicinfo : dsdBasicInfoList) {
            List<DsdDir> dsdDirList = dsdDirLevelMap.get(dsdBasicinfo.getDsdLevel());
            dsdBasicinfo.setDsdLevelId(dsdDirList.get(0).getDirDsdId());
            dsdBasicinfo.setDsdLevel(dsdDirList.get(0).getDsdDirLevel());
            dsdBasicinfo.setGmtCreate(new Date());
            dsdBasicinfo.setGmtModified(new Date());
            entityManager.persist(dsdBasicinfo); // insert插入操作
        }
    }

    private void saveDsdBasicInfoByDirId(
            List<DsdBasicinfo> dsdBasicInfoList, Map<String, String> codeDirLevelMap, String dirDsdId) {
        Optional<DsdDir> dsdDir = dsdDirRepository.findOne(QDsdDir.dsdDir.dirDsdId.eq(dirDsdId));
        if (dsdDir.isPresent()) {
            Predicate existDataPredicate =
                    QDsdBasicinfo.dsdBasicinfo
                            .dsdLevelId
                            .eq(dirDsdId)
                            .and(QDsdBasicinfo.dsdBasicinfo.dsdCode.in(codeDirLevelMap.keySet()));
            Iterable<DsdBasicinfo> existList = dsdBasicinfoRepository.findAll(existDataPredicate);
            dsdBasicinfoRepository.deleteInBatch(existList);

            for (DsdBasicinfo dsdBasicinfo : dsdBasicInfoList) {
                dsdBasicinfo.setDsdLevelId(dsdDir.get().getDirDsdId());
                dsdBasicinfo.setDsdLevel(dsdDir.get().getDsdDirLevel());
                dsdBasicinfo.setGmtCreate(new Date());
                dsdBasicinfo.setGmtModified(new Date());
                entityManager.persist(dsdBasicinfo); // insert插入操作
            }
        } else {
            throw new BizException("码表目录,参数有误!!!");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void addDsdCodeValuesBatch(List<DsdCodeInfoExt> saveDataList, long dsdCodeInfoId, List<String> codeList) {
        Predicate predicate = QDsdCodeInfoExt.dsdCodeInfoExt.dsdCodeInfoId.eq(dsdCodeInfoId)
                .and(QDsdCodeInfoExt.dsdCodeInfoExt.tableConfCode.in(codeList));
        Iterable<DsdCodeInfoExt> existDataList = dsdCodeInfoExtRepository.findAll(predicate);

        dsdCodeInfoExtRepository.deleteInBatch(existDataList);
        dsdCodeInfoExtRepository.saveAll(saveDataList);

        entityManager.flush();
        entityManager.clear();
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveCodeInfos(List<DsdCodeInfo> dataList, Set<String> tableCodeSet, Set<String> codeDirLevelSet) {
        List<DsdCodeDir> codeDirAllList = dsdCodeDirRepository.findAll();

        List<String> codeDirLevels =
                codeDirAllList.stream().map(dsdCodeDir -> dsdCodeDir.getCodeDirLevel()).collect(Collectors.toList());

        codeDirLevelSet.forEach(
                codeDirLevel -> {
                    if (!codeDirLevels.contains(codeDirLevel))
                        throw new BizException("Excel中输入的码表目录层级:" + codeDirLevel + ",参数有误!!!");
                });

        Map<String, List<DsdCodeDir>> codeDirLevelMap =
                codeDirAllList.stream().collect(Collectors.groupingBy(DsdCodeDir::getCodeDirLevel));
        Predicate existDataPredicate =
                QDsdCodeInfo.dsdCodeInfo
                        .codeDirLevel
                        .in(codeDirLevelSet)
                        .and(QDsdCodeInfo.dsdCodeInfo.tableCode.in(tableCodeSet));
        Iterable<DsdCodeInfo> existList = dsdCodeInfoRepository.findAll(existDataPredicate);
        dsdCodeInfoRepository.deleteInBatch(existList);

        for (DsdCodeInfo dsdCodeInfo : dataList) {
            List<DsdCodeDir> codeDirList = codeDirLevelMap.get(dsdCodeInfo.getCodeDirLevel());
            dsdCodeInfo.setCodeDirId(codeDirList.get(0).getCodeDirId());
            dsdCodeInfo.setCodeDirLevel(codeDirList.get(0).getCodeDirLevel());
            dsdCodeInfo.setGmtCreate(new Date());
            dsdCodeInfo.setGmtModified(new Date());
            entityManager.persist(dsdCodeInfo); // insert插入操作
        }
        entityManager.flush();
        entityManager.clear();
    }


    //    @Transactional(rollbackFor = Exception.class)
    //    public void updateBatch(List<DsdCodeTerm> codeTermList, Set<String> codeSet, Set<String>
    // dirSet) {
    //        List<DsdCodeTerm> dsdCodeTermAll = dsdCodeTermRepository.findAllByDirAndCodeId(codeSet,
    // dirSet);
    //        dsdCodeTermRepository.deleteInBatch(dsdCodeTermAll);
    //
    //        for (DsdCodeTerm dsdCodeTerm : codeTermList) {
    //            entityManager.merge(dsdCodeTerm);//update插入操作
    //        }
    //        entityManager.flush();
    //        entityManager.clear();
    //    }
}
