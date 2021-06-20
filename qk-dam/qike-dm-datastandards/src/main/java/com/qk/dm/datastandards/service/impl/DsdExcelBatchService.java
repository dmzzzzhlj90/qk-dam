package com.qk.dm.datastandards.service.impl;

import com.qk.dm.datastandards.entity.DsdCodeTerm;
import com.qk.dm.datastandards.repositories.DsdCodeTermRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

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

    public DsdExcelBatchService(DsdCodeTermRepository dsdCodeTermRepository) {
        this.dsdCodeTermRepository = dsdCodeTermRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public void addBatch(List<DsdCodeTerm> codeTermList, Set<String> codeSet, Set<String> dirSet) {
        List<DsdCodeTerm> dsdCodeTermAll = dsdCodeTermRepository.findAllByDirAndCodeId(codeSet, dirSet);
        dsdCodeTermRepository.deleteInBatch(dsdCodeTermAll);

        for (DsdCodeTerm dsdCodeTerm : codeTermList) {
            entityManager.persist(dsdCodeTerm);//insert插入操作
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
