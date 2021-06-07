package com.qk.dm.datastandards.service;

import com.qk.dm.datastandards.entity.DsdTerm;
import com.qk.dm.datastandards.vo.DsdTermVO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 * 数据标准业务术语接口
 */
@Service
public interface DataStandardTermService {

    Page<DsdTerm> getDsdTerm(Integer page,Integer size);

    void addDsdTerm(DsdTermVO dsdTermVO);

    void updateDsdTerm(DsdTermVO dsdTermVO);

    void deleteDsdTerm(Integer id);


}
