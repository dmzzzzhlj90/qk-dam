package com.qk.dm.datastandards.service.impl;

import com.qk.dm.datastandards.entity.DsdBasicinfo;
import com.qk.dm.datastandards.entity.DsdCodeTerm;
import com.qk.dm.datastandards.repositories.DsdBasicinfoRepository;
import com.qk.dm.datastandards.repositories.DsdCodeTermRepository;
import com.qk.dm.datastandards.service.DataStandardBasicInfoService;
import com.qk.dm.datastandards.service.DataStandardCodeTermService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 * 数据标准标准代码术语接口实现类
 */
@Service
public class DataStandardCodeTermServiceImpl implements DataStandardCodeTermService {
    public static final String ID = "id";
    private final DsdCodeTermRepository dsdCodeTermRepository;

    public DataStandardCodeTermServiceImpl(DsdCodeTermRepository dsdCodeTermRepository) {
        this.dsdCodeTermRepository = dsdCodeTermRepository;
    }

    @Override
    public Page<DsdCodeTerm> getDsdCodeTerm(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, ID);
        Pageable pageable = PageRequest.of(page,size,sort);
        return dsdCodeTermRepository.findAll(pageable);
    }

    @Override
    public void addDsdCodeTerm(DsdCodeTerm dsdCodeTerm) {
        dsdCodeTermRepository.save(dsdCodeTerm);
    }

    @Override
    public void updateDsdCodeTerm(DsdCodeTerm dsdCodeTerm) {
        dsdCodeTermRepository.save(dsdCodeTerm);
    }

    @Override
    public void deleteDsdCodeTerm(Integer delId) {
        dsdCodeTermRepository.deleteById(delId);
    }
    
}
