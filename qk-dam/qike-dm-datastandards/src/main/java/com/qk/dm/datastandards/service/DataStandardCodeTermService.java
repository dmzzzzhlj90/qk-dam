package com.qk.dm.datastandards.service;

import com.qk.dm.datastandards.entity.DsdBasicinfo;
import com.qk.dm.datastandards.entity.DsdCodeTerm;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 * 数据标准__标准代码术语接口
 */
@Service
public interface DataStandardCodeTermService {

    Page<DsdCodeTerm> getDsdCodeTerm(Integer page, Integer size);

    void addDsdCodeTerm(DsdCodeTerm dsdCodeTerm);

    void updateDsdCodeTerm(DsdCodeTerm dsdCodeTerm);

    void deleteDsdCodeTerm(Integer id);


}
