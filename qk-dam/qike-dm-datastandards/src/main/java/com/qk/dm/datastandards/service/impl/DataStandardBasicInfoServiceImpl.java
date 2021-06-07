package com.qk.dm.datastandards.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datastandards.entity.DsdBasicinfo;
import com.qk.dm.datastandards.entity.QDsdBasicinfo;
import com.qk.dm.datastandards.mapstruct.mapper.DsdBasicInfoMapper;
import com.qk.dm.datastandards.repositories.DsdBasicinfoRepository;
import com.qk.dm.datastandards.service.DataStandardBasicInfoService;
import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 * 数据标准标准信息接口实现类
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
        Pageable pageable = PageRequest.of(page-1, size, sort);
        return dsdBasicinfoRepository.findAll(pageable);
    }

    @Override
    public void addDsdBasicinfo(DsdBasicinfoVO dsdBasicinfoVO) {
        DsdBasicinfo dsdBasicinfo = DsdBasicInfoMapper.INSTANCE.useDsdBasicInfo(dsdBasicinfoVO);
        Predicate predicate = QDsdBasicinfo.dsdBasicinfo.dsdId.eq(dsdBasicinfo.getDsdId());
        boolean exists = dsdBasicinfoRepository.exists(predicate);
        if (exists) {
            throw new BizException("当前要新增的标准代码ID为：" + dsdBasicinfo.getDsdId()
                    + "标准名称为:" + dsdBasicinfo.getDsdName() + " 的数据，已存在！！！");
        }
        dsdBasicinfoRepository.save(dsdBasicinfo);
    }

    @Override
    public void updateDsdBasicinfo(DsdBasicinfoVO dsdBasicinfoVO) {
        DsdBasicinfo dsdBasicinfo = DsdBasicInfoMapper.INSTANCE.useDsdBasicInfo(dsdBasicinfoVO);
        Predicate predicate = QDsdBasicinfo.dsdBasicinfo.dsdId.eq(dsdBasicinfo.getDsdId());
        boolean exists = dsdBasicinfoRepository.exists(predicate);
        if (exists) {
            dsdBasicinfoRepository.saveAndFlush(dsdBasicinfo);
        } else {
            throw new BizException("当前要更新的标准代码ID为：" + dsdBasicinfo.getDsdId()
                    + "标准名称为:" + dsdBasicinfo.getDsdName() + " 的数据，不存在！！！");
        }
    }

    @Override
    public void deleteDsdBasicinfo(Integer delId) {
        boolean exists = dsdBasicinfoRepository.exists(QDsdBasicinfo.dsdBasicinfo.id.eq(delId));
        if (exists) {
            dsdBasicinfoRepository.deleteById(delId);
        }
    }

}
