package com.qk.dm.reptile.service.impl;

import com.qk.dm.reptile.mapstruct.mapper.RptDictMapper;
import com.qk.dm.reptile.params.vo.RptDictVO;
import com.qk.dm.reptile.repositories.RptDictRepository;
import com.qk.dm.reptile.service.RptDictService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典信息（省市区）
 * @author wangzp
 * @date 2021/12/24 10:16
 * @since 1.0.0
 */
@Service
public class RptDictServiceImpl implements RptDictService {

    private final RptDictRepository rptDictRepository;

    public RptDictServiceImpl(RptDictRepository rptDictRepository){
        this.rptDictRepository = rptDictRepository;
    }
    @Override
    public List<RptDictVO> getDictList(Long pid) {
       return RptDictMapper.INSTANCE.of(rptDictRepository.findAllByPid(pid));
    }
}
