package com.qk.dm.dataquality.service;

import com.qk.dm.dataquality.vo.DataQualityRuleDirTreeVO;
import com.qk.dm.dataquality.vo.DataQualityRuleDirVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 数据质量_规则模板目录
 *
 * @author wjq
 * @date 20211108
 * @since 1.0.0
 */
@Service
public interface DataQualityRuleDirService {

    List<DataQualityRuleDirTreeVO> searchList();

    void insert(DataQualityRuleDirVO dataQualityRuleDirVO);

    void update(DataQualityRuleDirVO dataQualityRuleDirVO);

    void delete(Integer delId);

    void deleteBulk(Integer delId);

}
