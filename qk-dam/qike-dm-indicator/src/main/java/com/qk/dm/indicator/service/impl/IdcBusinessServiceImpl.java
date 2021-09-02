package com.qk.dm.indicator.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.indicator.entity.IdcBusiness;
import com.qk.dm.indicator.entity.QIdcBusiness;
import com.qk.dm.indicator.mapstruct.mapper.IdcBusinessMapper;
import com.qk.dm.indicator.params.dto.IdcBusinessDTO;
import com.qk.dm.indicator.params.dto.IdcBusinessPageDTO;
import com.qk.dm.indicator.params.vo.IdcBusinessVO;
import com.qk.dm.indicator.repositories.IdcBusinessRepository;
import com.qk.dm.indicator.service.IdcBusinessService;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author wangzp
 * @date 2021/9/2 14:45
 * @since 1.0.0
 */
@Service
public class IdcBusinessServiceImpl implements IdcBusinessService {

    private JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    private final QIdcBusiness qIdcBusiness = QIdcBusiness.idcBusiness;
    private final IdcBusinessRepository idcBusinessRepository;

    @Autowired
    public IdcBusinessServiceImpl(EntityManager entityManager, IdcBusinessRepository idcBusinessRepository) {
        this.entityManager = entityManager;
        this.idcBusinessRepository = idcBusinessRepository;
    }

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public void insert(IdcBusinessDTO idcBusinessDTO) {
        IdcBusiness idcBusiness = IdcBusinessMapper.INSTANCE.useIdcBusiness(idcBusinessDTO);
        idcBusinessRepository.save(idcBusiness);
    }

    @Override
    public void update(Long id, IdcBusinessDTO idcBusinessDTO) {
        Predicate predicate =
                qIdcBusiness
                        .id
                        .eq(id);
        IdcBusiness idcBusiness = idcBusinessRepository.findOne(predicate).orElse(null);
        if (Objects.isNull(idcBusiness)) {
            throw new BizException("当前业务指标：" + id + " 的数据不存在！！！");
        }
        IdcBusinessMapper.INSTANCE.useIdcBusiness(idcBusinessDTO,idcBusiness);
        idcBusinessRepository.saveAndFlush(idcBusiness);
    }

    @Override
    public void delete(String ids) {
        Iterable<Long> idList =
                Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<IdcBusiness> idcBusinessList = idcBusinessRepository.findAllById(idList);
        if (idcBusinessList.isEmpty()) {
            throw new BizException("当前要删除的业务指标id为：" + ids + " 的数据，不存在！！！");
        }
        idcBusinessRepository.deleteAll(idcBusinessList);
    }

    @Override
    public IdcBusinessVO detail(Long id) {
        Optional<IdcBusiness> idcBusiness = idcBusinessRepository.findById(id);
        if (idcBusiness.isEmpty()) {
            throw new BizException("当前要查询的业务指标id为：" + id + " 的数据，不存在！！！");
        }
        return IdcBusinessMapper.INSTANCE.useIdcBusinessVO(idcBusiness.get());
    }

    @Override
    public PageResultVO<IdcBusinessVO> findListPage(IdcBusinessPageDTO idcBusinessPageDTO) {
        return null;
    }
}
