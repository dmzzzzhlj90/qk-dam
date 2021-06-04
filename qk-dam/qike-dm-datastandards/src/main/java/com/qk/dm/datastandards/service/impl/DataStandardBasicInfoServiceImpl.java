package com.qk.dm.datastandards.service.impl;

import com.qk.dm.datastandards.entity.DsdBasicinfo;
import com.qk.dm.datastandards.entity.DsdTerm;
import com.qk.dm.datastandards.repositories.DsdBasicinfoRepository;
import com.qk.dm.datastandards.service.DataStandardBasicInfoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 * 数据标准__标准信息接口实现类
 */
@Service
public class DataStandardBasicInfoServiceImpl implements DataStandardBasicInfoService {
    public static final String ID = "id";
    private final DsdBasicinfoRepository dsdBasicinfoRepository;

    public DataStandardBasicInfoServiceImpl(DsdBasicinfoRepository dsdBasicinfoRepository) {
        this.dsdBasicinfoRepository = dsdBasicinfoRepository;
    }

    @Override
    public Page<DsdBasicinfo> getDsdBasicInfo(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, ID);
        Pageable pageable = PageRequest.of(page,size,sort);
        return dsdBasicinfoRepository.findAll(pageable);
    }

    @Override
    public void addDsdBasicinfo(DsdBasicinfo dsdBasicinfo) {
        dsdBasicinfoRepository.save(dsdBasicinfo);
    }

    @Override
    public void updateDsdBasicinfo(DsdBasicinfo dsdBasicinfo) {
        dsdBasicinfoRepository.save(dsdBasicinfo);
    }

    @Override
    public void deleteDsdBasicinfo(Integer delId) {
        dsdBasicinfoRepository.deleteById(delId);
    }
    
}
