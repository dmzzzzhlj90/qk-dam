package com.qk.dm.datastandards.service;

import com.qk.dm.datastandards.entity.DsdCodeTerm;
import com.qk.dm.datastandards.entity.DsdTerm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wjq
 * @date 20210604
 * 数据标准__业务术语接口
 * @since 1.0.0
 */
@Service
public interface DataStandardTermService {

    Page<DsdTerm> getDsdTerm(Integer page,Integer size);

    void addDsdTerm(DsdTerm dsdTerm);

    void updateDsdTerm(DsdTerm dsdTerm);

    void deleteDsdTerm(Integer id);


}
