package com.qk.dm.indicator.service.impl;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.indicator.entity.QIdcAtom;
import com.qk.dm.indicator.params.dto.IdcAtomDTO;
import com.qk.dm.indicator.params.dto.IdcAtomPageDTO;
import com.qk.dm.indicator.params.vo.IdcAtomVO;
import com.qk.dm.indicator.repositories.IdcAtomRepository;
import com.qk.dm.indicator.service.IdcAtomService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author shenpj
 * @date 2021/9/2 8:40 下午
 * @since 1.0.0
 */
@Service
public class IdcAtomServiceImpl implements IdcAtomService {
    private JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    private final QIdcAtom qIdcAtom = QIdcAtom.idcAtom;
    private final IdcAtomRepository idcAtomRepository;

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    public IdcAtomServiceImpl(EntityManager entityManager, IdcAtomRepository idcAtomRepository) {
        this.entityManager = entityManager;
        this.idcAtomRepository = idcAtomRepository;
    }

    @Override
    public void insert(IdcAtomDTO idcAtomDTO) {

    }

    @Override
    public void update(Long id, IdcAtomDTO idcAtomDTO) {

    }

    @Override
    public void delete(String ids) {

    }

    @Override
    public IdcAtomVO detail(Long id) {
        return null;
    }

    @Override
    public List<IdcAtomVO> getList() {
        return null;
    }

    @Override
    public PageResultVO<IdcAtomVO> listByPage(IdcAtomPageDTO idcAtomPageDTO) {
        return null;
    }


}
