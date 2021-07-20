package com.qk.dm.datastandards.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datastandards.entity.*;
import com.qk.dm.datastandards.repositories.DsdBasicinfoRepository;
import com.qk.dm.datastandards.repositories.DsdCodeTermRepository;
import com.qk.dm.datastandards.repositories.DsdDirRepository;
import com.qk.dm.datastandards.service.DataStandardDirService;
import com.querydsl.core.types.Predicate;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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

    private final DsdCodeTermRepository dsdCodeTermRepository;
    private final DsdBasicinfoRepository dsdBasicinfoRepository;
    private final DsdDirRepository dsdDirRepository;
    private final DataStandardDirService dataStandardDirService;

    @Autowired
    public DsdExcelBatchService(
            DsdCodeTermRepository dsdCodeTermRepository, DsdBasicinfoRepository dsdBasicinfoRepository, DsdDirRepository dsdDirRepository, DataStandardDirService dataStandardDirService) {
        this.dsdCodeTermRepository = dsdCodeTermRepository;
        this.dsdBasicinfoRepository = dsdBasicinfoRepository;
        this.dsdDirRepository = dsdDirRepository;
        this.dataStandardDirService = dataStandardDirService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void addDsdCodeTermBatch(
            List<DsdCodeTerm> codeTermList, Set<String> codeSet, Set<String> dirSet) {
        List<DsdCodeTerm> dsdCodeTermAll = dsdCodeTermRepository.findAllByDirAndCodeId(codeSet, dirSet);
        dsdCodeTermRepository.deleteInBatch(dsdCodeTermAll);

        for (DsdCodeTerm dsdCodeTerm : codeTermList) {
            entityManager.persist(dsdCodeTerm); // insert插入操作
        }
        entityManager.flush();
        entityManager.clear();
    }

    @Transactional(rollbackFor = Exception.class)
    public void addDsdBasicInfoBatch(List<DsdBasicinfo> dsdBasicInfoList, Set<String> dsdDirLevelSet, Map<String, String> codeDirLevelMap, String dirDsdId) {
        if (!StringUtils.isEmpty(dirDsdId)) {
            saveDsdBasicInfoByDirId(dsdBasicInfoList, codeDirLevelMap, dirDsdId);
        } else {
            saveDsdBasicInfo(dsdBasicInfoList, dsdDirLevelSet, codeDirLevelMap);
        }
        entityManager.flush();
        entityManager.clear();
    }

    private void saveDsdBasicInfo(List<DsdBasicinfo> dsdBasicInfoList, Set<String> dsdDirLevelSet, Map<String, String> codeDirLevelMap) {
        List<DsdDir> dsdDirAllList = dsdDirRepository.findAll();
        List<String> dsdDirLevels = dsdDirAllList.stream().map(dsdDir -> dsdDir.getDsdDirLevel()).collect(Collectors.toList());

        dsdDirLevelSet.forEach(dirDsdLevel -> {
            if (!dsdDirLevels.contains(dirDsdLevel)) {
                throw new BizException("Excel中输入的标准目录层级:" + dirDsdLevel + ",参数有误!!!");
            }
        });

        Map<String, List<DsdDir>> dsdDirLevelMap = dsdDirAllList.stream().collect(Collectors.groupingBy(DsdDir::getDsdDirLevel));
        Predicate existDataPredicate = QDsdBasicinfo.dsdBasicinfo.dsdLevel.in(dsdDirLevelSet)
                .and(QDsdBasicinfo.dsdBasicinfo.dsdCode.in(codeDirLevelMap.keySet()));
        Iterable<DsdBasicinfo> existList = dsdBasicinfoRepository.findAll(existDataPredicate);
        dsdBasicinfoRepository.deleteInBatch(existList);

        for (DsdBasicinfo dsdBasicinfo : dsdBasicInfoList) {
            List<DsdDir> dsdDirList = dsdDirLevelMap.get(dsdBasicinfo.getDsdLevel());
            dsdBasicinfo.setDsdLevelId(dsdDirList.get(0).getDirDsdId());
            dsdBasicinfo.setDsdLevel(dsdDirList.get(0).getDsdDirLevel());
            entityManager.persist(dsdBasicinfo); // insert插入操作
        }
    }

    private void saveDsdBasicInfoByDirId(List<DsdBasicinfo> dsdBasicInfoList, Map<String, String> codeDirLevelMap, String dirDsdId) {
        Optional<DsdDir> dsdDir = dsdDirRepository.findOne(QDsdDir.dsdDir.dirDsdId.eq(dirDsdId));
        if (dsdDir.isPresent()) {
            Predicate existDataPredicate = QDsdBasicinfo.dsdBasicinfo.dsdCode.eq(dirDsdId)
                    .and(QDsdBasicinfo.dsdBasicinfo.dsdCode.in(codeDirLevelMap.keySet()));
            Iterable<DsdBasicinfo> existList = dsdBasicinfoRepository.findAll(existDataPredicate);
            dsdBasicinfoRepository.deleteInBatch(existList);

            for (DsdBasicinfo dsdBasicinfo : dsdBasicInfoList) {
                dsdBasicinfo.setDsdLevelId(dsdDir.get().getDirDsdId());
                dsdBasicinfo.setDsdLevel(dsdDir.get().getDsdDirLevel());
                entityManager.persist(dsdBasicinfo); // insert插入操作
            }
        } else {
            throw new BizException("数据标准目录,参数有误!!!");
        }
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
