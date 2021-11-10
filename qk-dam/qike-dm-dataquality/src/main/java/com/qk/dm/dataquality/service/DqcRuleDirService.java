package com.qk.dm.dataquality.service;

import com.qk.dm.dataquality.vo.DqcRuleDirTreeVO;
import com.qk.dm.dataquality.vo.DqcRuleDirVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据质量_规则分类目录
 *
 * @author wjq
 * @date 2021/11/08
 * @since 1.0.0
 */
@Service
public interface DqcRuleDirService {

    List<DqcRuleDirTreeVO> searchList();

    void insert(DqcRuleDirVO dqcRuleDirVO);

    void update(DqcRuleDirVO dqcRuleDirVO);

    void delete(Long delId);

    void deleteBulk(Long delId);

}
