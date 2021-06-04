package com.qk.dm.datastandards.service.impl;

import com.qk.dm.datastandards.entity.DsdTerm;
import com.qk.dm.datastandards.repositories.DsdTermRepository;
import com.qk.dm.datastandards.service.DataStandardTermService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wjq
 * @date 20210604
 * 数据标准__业务术语接口实现类
 * @since 1.0.0
 */
@Service
public class DataStandardTermServiceImpl implements DataStandardTermService {
    public static final String ID = "id";
    private final DsdTermRepository dsdTermRepository;

    public DataStandardTermServiceImpl(DsdTermRepository dsdTermRepository) {
        this.dsdTermRepository = dsdTermRepository;
    }


    @Override
    public Page<DsdTerm> getDsdTerm(Integer page,Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, ID);
        Pageable pageable = PageRequest.of(page,size,sort);
        return dsdTermRepository.findAll(pageable);
    }

    @Override
    public void addDsdTerm(DsdTerm dsdTerm) {
        dsdTermRepository.save(dsdTerm);
    }

    @Override
    public void updateDsdTerm(DsdTerm dsdTerm) {
        dsdTermRepository.save(dsdTerm);
    }

    @Override
    public void deleteDsdTerm(Integer delId) {
        dsdTermRepository.deleteById(delId);
    }
    
}
