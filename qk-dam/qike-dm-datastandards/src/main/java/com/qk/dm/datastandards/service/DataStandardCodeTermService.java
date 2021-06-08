package com.qk.dm.datastandards.service;

import com.qk.dm.datastandards.entity.DsdCodeTerm;
import com.qk.dm.datastandards.vo.DsdCodeTermVO;
import com.qk.dm.datastandards.vo.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 * 数据标准标准代码术语接口
 */
@Service
public interface DataStandardCodeTermService {

    Page<DsdCodeTerm> getDsdCodeTerm(Pagination pagination);

    void addDsdCodeTerm(DsdCodeTermVO dsdCodeTermVO);

    void updateDsdCodeTerm(DsdCodeTermVO dsdCodeTermVO);

    void deleteDsdCodeTerm(Integer id);


}
