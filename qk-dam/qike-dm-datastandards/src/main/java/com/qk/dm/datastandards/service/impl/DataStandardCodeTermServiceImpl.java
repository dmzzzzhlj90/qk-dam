package com.qk.dm.datastandards.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datastandards.entity.DsdCodeTerm;
import com.qk.dm.datastandards.entity.QDsdCodeTerm;
import com.qk.dm.datastandards.mapstruct.mapper.DsdCodeTermMapper;
import com.qk.dm.datastandards.repositories.DsdCodeTermRepository;
import com.qk.dm.datastandards.service.DataStandardCodeTermService;
import com.qk.dm.datastandards.vo.DsdCodeTermVO;
import com.qk.dm.datastandards.vo.PageResultVO;
import com.qk.dm.datastandards.vo.Pagination;
import com.querydsl.core.types.Predicate;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public PageResultVO<DsdCodeTermVO> getDsdCodeTerm(Pagination pagination, String codeDirId) {
        Page<DsdCodeTerm> codeTermPage = null;
        List<DsdCodeTermVO> dsdCodeTermVOList = new ArrayList<DsdCodeTermVO>();

        if (StringUtils.isEmpty(codeDirId)) {
            codeTermPage = dsdCodeTermRepository.findAll(pagination.getPageable());
        } else {
            DsdCodeTerm dsdCodeTerm = new DsdCodeTerm();
            dsdCodeTerm.setCodeDirId(codeDirId);
            Example<DsdCodeTerm> example = Example.of(dsdCodeTerm);
            codeTermPage = dsdCodeTermRepository.findAll(example, pagination.getPageable());
        }

        codeTermPage.getContent().forEach(dsdCodeTerm -> {
            DsdCodeTermVO dsdCodeTermVO = DsdCodeTermMapper.INSTANCE.usDsdCodeTermVO(dsdCodeTerm);
            dsdCodeTermVOList.add(dsdCodeTermVO);
        });
        return new PageResultVO<>(codeTermPage.getTotalElements(), codeTermPage.getNumber(), codeTermPage.getSize(), dsdCodeTermVOList);
    }

    @Override
    public void addDsdCodeTerm(DsdCodeTermVO dsdCodeTermVO) {
        DsdCodeTerm dsdCodeTerm = DsdCodeTermMapper.INSTANCE.useDsdCodeTerm(dsdCodeTermVO);
        Predicate predicate = QDsdCodeTerm.dsdCodeTerm.codeId.eq(dsdCodeTerm.getCodeId());
        boolean exists = dsdCodeTermRepository.exists(predicate);
        if (exists) {
            throw new BizException("当前要新增的码表编码ID为：" + dsdCodeTerm.getCodeId()
                    + "码表名称名称为:" + dsdCodeTerm.getCodeName() + " 的数据，已存在！！！");
        }
        dsdCodeTermRepository.save(dsdCodeTerm);
    }

    @Override
    public void updateDsdCodeTerm(DsdCodeTermVO dsdCodeTermVO) {
        DsdCodeTerm dsdCodeTerm = DsdCodeTermMapper.INSTANCE.useDsdCodeTerm(dsdCodeTermVO);
        Predicate predicate = QDsdCodeTerm.dsdCodeTerm.codeId.eq(dsdCodeTerm.getCodeId());
        boolean exists = dsdCodeTermRepository.exists(predicate);
        if (exists) {
            dsdCodeTermRepository.saveAndFlush(dsdCodeTerm);
        } else {
            throw new BizException("当前要新增的码表编码ID为：" + dsdCodeTerm.getCodeId()
                    + "码表名称名称为:" + dsdCodeTerm.getCodeName() + " 的数据，不存在！！！");
        }

    }

    @Override
    public void deleteDsdCodeTerm(Integer delId) {
        boolean exists = dsdCodeTermRepository.exists(QDsdCodeTerm.dsdCodeTerm.id.eq(delId));
        if (exists) {
            dsdCodeTermRepository.deleteById(delId);
        }
    }
}
