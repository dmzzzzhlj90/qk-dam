package com.qk.dm.datastandards.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datastandards.entity.DsdTerm;
import com.qk.dm.datastandards.entity.QDsdTerm;
import com.qk.dm.datastandards.mapstruct.mapper.DsdTermMapper;
import com.qk.dm.datastandards.repositories.DsdTermRepository;
import com.qk.dm.datastandards.service.DataStandardTermService;
import com.qk.dm.datastandards.vo.DsdTermVO;
import com.qk.dm.datastandards.vo.Pagination;
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
 * 数据标准业务术语接口实现类
 */
@Service
public class DataStandardTermServiceImpl implements DataStandardTermService {
    public static final String ID = "id";
    private final DsdTermRepository dsdTermRepository;

    public DataStandardTermServiceImpl(DsdTermRepository dsdTermRepository) {
        this.dsdTermRepository = dsdTermRepository;
    }


    @Override
    public Page<DsdTerm> getDsdTerm(Pagination pagination) {
        return dsdTermRepository.findAll(pagination.getPageable());
    }

    @Override
    public void addDsdTerm(DsdTermVO dsdTermVO) {
        DsdTerm dsdTerm = DsdTermMapper.INSTANCE.useDsdTerm(dsdTermVO);
        Predicate predicate = QDsdTerm.dsdTerm.id.eq(dsdTerm.getId());
        boolean exists = dsdTermRepository.exists(predicate);
        if (exists) {
            throw new BizException("当前要新增的术语中文简称为：" + dsdTerm.getChineseName()
                    + ",英文简称为:" + dsdTerm.getEnglishName() + " 的数据，已存在！！！");
        }
        dsdTermRepository.save(dsdTerm);
    }

    @Override
    public void updateDsdTerm(DsdTermVO dsdTermVO) {
        DsdTerm dsdTerm = DsdTermMapper.INSTANCE.useDsdTerm(dsdTermVO);
        Predicate predicate = QDsdTerm.dsdTerm.id.eq(dsdTerm.getId());
        boolean exists = dsdTermRepository.exists(predicate);
        if (exists) {
            dsdTermRepository.saveAndFlush(dsdTerm);
        } else {
            throw new BizException("当前要新增的术语中文简称为：" + dsdTerm.getChineseName()
                    + ",英文简称为:" + dsdTerm.getEnglishName() + " 的数据，不存在！！！");
        }
    }

    @Override
    public void deleteDsdTerm(Integer delId) {
        boolean exists = dsdTermRepository.exists(QDsdTerm.dsdTerm.id.eq(delId));
        if (exists) {
            dsdTermRepository.deleteById(delId);
        }
    }
}
