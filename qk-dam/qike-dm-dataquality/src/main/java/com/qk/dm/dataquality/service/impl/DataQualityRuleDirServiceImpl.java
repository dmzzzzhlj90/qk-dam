package com.qk.dm.dataquality.service.impl;

import com.qk.dm.dataquality.repositories.DqcRuleDirRepository;
import com.qk.dm.dataquality.service.DataQualityRuleDirService;
import com.qk.dm.dataquality.vo.DataQualityRuleDirTreeVO;
import com.qk.dm.dataquality.vo.DataQualityRuleDirVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * 数据质量_规则模板目录
 *
 * @author wjq
 * @date 2021/11/8
 * @since 1.0.0
 */
@Service
public class DataQualityRuleDirServiceImpl implements DataQualityRuleDirService {
    private final DqcRuleDirRepository dqcRuleDirRepository;

    @Autowired
    public DataQualityRuleDirServiceImpl(DqcRuleDirRepository dqcRuleDirRepository) {
        this.dqcRuleDirRepository = dqcRuleDirRepository;
    }


    @Override
    public List<DataQualityRuleDirTreeVO> searchList() {

//        Predicate predicate = QData.dsdDir.delFlag.eq(0);
//        List<DsdDir> dsdDirList = (List<DsdDir>) dsdDirRepository.findAll(predicate);
//        List<DataStandardTreeVO> respList = new ArrayList<>();
//        for (DsdDir dsdDir : dsdDirList) {
//            DataStandardTreeVO dirTreeVO = DsdDirTreeMapper.INSTANCE.useDirTreeVO(dsdDir);
//            respList.add(dirTreeVO);
//        }
//        return buildByRecursive(respList);
        return null;
    }

    @Override
    public void insert(DataQualityRuleDirVO dataQualityRuleDirVO) {

    }

    @Override
    public void update(DataQualityRuleDirVO dataQualityRuleDirVO) {

    }

    @Override
    public void delete(Integer delId) {

    }

    @Override
    public void deleteBulk(Integer delId) {

    }
}
