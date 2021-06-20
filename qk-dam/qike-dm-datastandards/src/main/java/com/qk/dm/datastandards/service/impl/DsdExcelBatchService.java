package com.qk.dm.datastandards.service.impl;

import com.qk.dm.datastandards.entity.DsdBasicinfo;
import com.qk.dm.datastandards.entity.DsdCodeTerm;
import com.qk.dm.datastandards.repositories.DsdBasicinfoRepository;
import com.qk.dm.datastandards.repositories.DsdCodeTermRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
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

    public DsdExcelBatchService(DsdCodeTermRepository dsdCodeTermRepository, DsdBasicinfoRepository dsdBasicinfoRepository) {
        this.dsdCodeTermRepository = dsdCodeTermRepository;
        this.dsdBasicinfoRepository = dsdBasicinfoRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public void addDsdCodeTermBatch(List<DsdCodeTerm> codeTermList, Set<String> codeSet, Set<String> dirSet) {
        List<DsdCodeTerm> dsdCodeTermAll = dsdCodeTermRepository.findAllByDirAndCodeId(codeSet, dirSet);
        dsdCodeTermRepository.deleteInBatch(dsdCodeTermAll);

        for (DsdCodeTerm dsdCodeTerm : codeTermList) {
            entityManager.persist(dsdCodeTerm);//insert插入操作
        }
        entityManager.flush();
        entityManager.clear();

    }

    @Transactional(rollbackFor = Exception.class)
    public void addDsdBasicInfoBatch(List<DsdBasicinfo> dsdBasicInfoList, Set<String> codeSet, Set<String> nameSet) {
        List<DsdBasicinfo> dsdCodeTermAll = dsdBasicinfoRepository.findAllByCodeAndName(codeSet, nameSet);
        dsdBasicinfoRepository.deleteInBatch(dsdCodeTermAll);

        Map<String, List<DsdBasicinfo>> dsdLevelMap = dsdCodeTermAll.stream().collect(Collectors.groupingBy(DsdBasicinfo::getDsdLevel));

        for (DsdBasicinfo dsdBasicinfo : dsdBasicInfoList) {
            List<DsdBasicinfo> list = dsdLevelMap.get(dsdBasicinfo.getDsdLevel());
            dsdBasicinfo.setDsdLevelId(list.get(0).getDsdLevelId());
            entityManager.persist(dsdBasicinfo);//insert插入操作
        }
        entityManager.flush();
        entityManager.clear();
    }

//    @Transactional(rollbackFor = Exception.class)
//    public void updateBatch(List<DsdCodeTerm> codeTermList, Set<String> codeSet, Set<String> dirSet) {
//        List<DsdCodeTerm> dsdCodeTermAll = dsdCodeTermRepository.findAllByDirAndCodeId(codeSet, dirSet);
//        dsdCodeTermRepository.deleteInBatch(dsdCodeTermAll);
//
//        for (DsdCodeTerm dsdCodeTerm : codeTermList) {
//            entityManager.merge(dsdCodeTerm);//update插入操作
//        }
//        entityManager.flush();
//        entityManager.clear();
//    }
}
