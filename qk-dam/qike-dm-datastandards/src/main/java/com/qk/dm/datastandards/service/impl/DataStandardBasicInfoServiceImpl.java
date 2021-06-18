package com.qk.dm.datastandards.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datastandards.entity.DsdBasicinfo;
import com.qk.dm.datastandards.entity.QDsdBasicinfo;
import com.qk.dm.datastandards.mapstruct.mapper.DsdBasicInfoMapper;
import com.qk.dm.datastandards.repositories.DsdBasicinfoRepository;
import com.qk.dm.datastandards.service.DataStandardBasicInfoService;
import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import com.qk.dm.datastandards.vo.PageResultVO;
import com.qk.dm.datastandards.vo.Pagination;
import com.querydsl.core.types.Predicate;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public PageResultVO<DsdBasicinfoVO> getDsdBasicInfo(Pagination pagination, String dirDsdId) {
        Page<DsdBasicinfo> basicinfoPage = null;
        List<DsdBasicinfoVO> dsdBasicinfoVOList = new ArrayList<DsdBasicinfoVO>();

        if (StringUtils.isEmpty(dirDsdId)) {
            basicinfoPage = dsdBasicinfoRepository.findAll(pagination.getPageable());
        } else {
            DsdBasicinfo dsdBasicinfo = new DsdBasicinfo();
            dsdBasicinfo.setDsdLevel(dirDsdId);
            Example<DsdBasicinfo> example = Example.of(dsdBasicinfo);
            basicinfoPage = dsdBasicinfoRepository.findAll(example, pagination.getPageable());
        }

        basicinfoPage.getContent().forEach(dsdBasicinfo -> {
            DsdBasicinfoVO dsdBasicinfoVO = DsdBasicInfoMapper.INSTANCE.useDsdBasicInfoVO(dsdBasicinfo);
            dsdBasicinfoVOList.add(dsdBasicinfoVO);
        });

        return new PageResultVO<>(basicinfoPage.getTotalElements(), basicinfoPage.getNumber(), basicinfoPage.getSize(), dsdBasicinfoVOList);

    }

    @Override
    public void addDsdBasicinfo(DsdBasicinfoVO dsdBasicinfoVO) {
        DsdBasicinfo dsdBasicinfo = DsdBasicInfoMapper.INSTANCE.useDsdBasicInfo(dsdBasicinfoVO);
        Predicate predicate = QDsdBasicinfo.dsdBasicinfo.dsdCode.eq(dsdBasicinfo.getDsdCode());
        boolean exists = dsdBasicinfoRepository.exists(predicate);
        if (exists) {
            throw new BizException("当前要新增的标准代码ID为：" + dsdBasicinfo.getDsdCode()
                    + "标准名称为:" + dsdBasicinfo.getDsdName() + " 的数据，已存在！！！");
        }
        dsdBasicinfoRepository.save(dsdBasicinfo);
    }

    @Override
    public void updateDsdBasicinfo(DsdBasicinfoVO dsdBasicinfoVO) {
        DsdBasicinfo dsdBasicinfo = DsdBasicInfoMapper.INSTANCE.useDsdBasicInfo(dsdBasicinfoVO);
        Predicate predicate = QDsdBasicinfo.dsdBasicinfo.dsdCode.eq(dsdBasicinfo.getDsdCode());
        boolean exists = dsdBasicinfoRepository.exists(predicate);
        if (exists) {
            dsdBasicinfoRepository.saveAndFlush(dsdBasicinfo);
        } else {
            throw new BizException("当前要更新的标准代码ID为：" + dsdBasicinfo.getDsdCode()
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
