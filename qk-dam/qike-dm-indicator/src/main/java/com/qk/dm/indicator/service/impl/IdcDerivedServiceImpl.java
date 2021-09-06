package com.qk.dm.indicator.service.impl;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.indicator.entity.IdcDerived;
import com.qk.dm.indicator.entity.QIdcDerived;
import com.qk.dm.indicator.mapstruct.mapper.IdcDerivedMapper;
import com.qk.dm.indicator.params.dto.IdcDerivedDTO;
import com.qk.dm.indicator.params.dto.IdcDerivedPageDTO;
import com.qk.dm.indicator.params.vo.IdcDerivedVO;
import com.qk.dm.indicator.repositories.IdcDerivedRepository;
import com.qk.dm.indicator.service.IdcDerivedService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Service
public class IdcDerivedServiceImpl implements IdcDerivedService {

    private JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    private final QIdcDerived qIdcDerived = QIdcDerived.idcDerived;
    private final IdcDerivedRepository idcDerivedRepository;
    @Autowired
    public IdcDerivedServiceImpl(EntityManager entityManager,IdcDerivedRepository idcDerivedRepository){
        this.entityManager = entityManager;
        this.idcDerivedRepository = idcDerivedRepository;
    }

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public void insert(IdcDerivedDTO idcDerivedDTO) {
        IdcDerived idcDerived = IdcDerivedMapper.INSTANCE.useIdcDerived(idcDerivedDTO);
        idcDerivedRepository.save(idcDerived);
    }

    @Override
    public void update(Long id, IdcDerivedDTO idcDerivedDTO) {

    }

    @Override
    public void delete(String ids) {

    }

    @Override
    public IdcDerivedVO detail(Long id) {
        return null;
    }

    @Override
    public PageResultVO<IdcDerivedVO> findListPage(IdcDerivedPageDTO idcDerivedPageDTO) {
        return null;
    }
}
