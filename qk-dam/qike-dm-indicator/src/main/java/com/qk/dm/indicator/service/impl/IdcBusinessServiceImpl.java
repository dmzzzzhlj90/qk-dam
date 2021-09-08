package com.qk.dm.indicator.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.indicator.common.property.IdcState;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.indicator.entity.IdcBusiness;
import com.qk.dm.indicator.entity.QIdcBusiness;
import com.qk.dm.indicator.mapstruct.mapper.IdcBusinessMapper;
import com.qk.dm.indicator.params.dto.IdcBusinessDTO;
import com.qk.dm.indicator.params.dto.IdcBusinessPageDTO;
import com.qk.dm.indicator.params.vo.IdcBusinessVO;
import com.qk.dm.indicator.repositories.IdcBusinessRepository;
import com.qk.dm.indicator.service.IdcBusinessService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
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
    public void publish(Long id) {
        IdcBusiness idcBusiness = idcBusinessRepository.findById(id).orElse(null);
        if (idcBusiness == null) {
            throw new BizException("当前要发布的业务指标id为：" + id + " 的数据不存在！！！");
        }
        idcBusiness.setIndicatorStatus(IdcState.ONLINE);
        idcBusinessRepository.saveAndFlush(idcBusiness);
    }

    @Override
    public void offline(Long id) {
        IdcBusiness idcBusiness = idcBusinessRepository.findById(id).orElse(null);
        if (idcBusiness == null) {
            throw new BizException("当前要下线的业务指标id为：" + id + " 的数据不存在！！！");
        }
        idcBusiness.setIndicatorStatus(IdcState.OFFLINE);
        idcBusinessRepository.saveAndFlush(idcBusiness);
    }

    @Override
    public PageResultVO<IdcBusinessVO> findListPage(IdcBusinessPageDTO idcBusinessPageDTO) {
        Map<String, Object> map;
        try {
            map = queryIdcIdcBusinessByParams(idcBusinessPageDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<IdcBusiness> list = (List<IdcBusiness>) map.get("list");
        List<IdcBusinessVO> idcBusinessVOList = IdcBusinessMapper.INSTANCE.userIdcBusinessListVO(list);
        return new PageResultVO<>(
                (long) map.get("total"),
                idcBusinessPageDTO.getPagination().getPage(),
                idcBusinessPageDTO.getPagination().getSize(),
                idcBusinessVOList);
    }

    private Map<String, Object> queryIdcIdcBusinessByParams(IdcBusinessPageDTO idcBusinessPageDTO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, idcBusinessPageDTO);
        Map<String, Object> result = new HashMap<>();
        long count =
                jpaQueryFactory
                        .select(qIdcBusiness.count())
                        .from(qIdcBusiness)
                        .where(booleanBuilder)
                        .fetchOne();

        List<IdcBusiness> idcBusinessList = jpaQueryFactory
                .select(qIdcBusiness)
                .from(qIdcBusiness)
                .where(booleanBuilder)
                .orderBy(qIdcBusiness.id.asc())
                .offset(
                        (long) (idcBusinessPageDTO.getPagination().getPage() - 1)
                                * idcBusinessPageDTO.getPagination().getSize())
                .limit(idcBusinessPageDTO.getPagination().getSize())
                .fetch();
        result.put("list", idcBusinessList);
        result.put("total", count);

        return result;
    }

    public void checkCondition(BooleanBuilder booleanBuilder, IdcBusinessPageDTO idcBusinessPageDTO) {
        if (!StringUtils.isEmpty(idcBusinessPageDTO.getBIndicatorName())) {
            booleanBuilder.and(qIdcBusiness.bIndicatorName.contains(idcBusinessPageDTO.getBIndicatorName()));
        }
        if (!StringUtils.isEmpty(idcBusinessPageDTO.getIndicatorPersonLiable())) {
            booleanBuilder.and(qIdcBusiness.indicatorPersonLiable.contains(idcBusinessPageDTO.getIndicatorPersonLiable()));
        }
        if(Objects.nonNull(idcBusinessPageDTO.getIndicatorStatus())){
            booleanBuilder.and(qIdcBusiness.indicatorStatus.eq(idcBusinessPageDTO.getIndicatorStatus()));
        }
        if (!StringUtils.isEmpty(idcBusinessPageDTO.getStartTime())
                && !StringUtils.isEmpty(idcBusinessPageDTO.getEndTime())) {
            StringTemplate dateExpr =
                    Expressions.stringTemplate(
                            "DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qIdcBusiness.gmtCreate);
            booleanBuilder.and(
                    dateExpr.between(idcBusinessPageDTO.getStartTime(), idcBusinessPageDTO.getEndTime()));
        }
        booleanBuilder.and(qIdcBusiness.delFlag.eq(0));
    }
}
