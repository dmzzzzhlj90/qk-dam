package com.qk.dm.datamodel.service.impl;


import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.entity.ModelFactTable;
import com.qk.dm.datamodel.entity.QModelFactTable;
import com.qk.dm.datamodel.mapstruct.mapper.ModelFactTableMapper;
import com.qk.dm.datamodel.params.dto.ModelFactColumnDTO;
import com.qk.dm.datamodel.params.dto.ModelFactInfoDTO;
import com.qk.dm.datamodel.params.dto.ModelFactTableDTO;
import com.qk.dm.datamodel.params.vo.ModelFactTableVO;
import com.qk.dm.datamodel.repositories.ModelFactTableRepository;
import com.qk.dm.datamodel.service.ModelFactColumnService;
import com.qk.dm.datamodel.service.ModelFactTableService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ModelFactTableServiceImpl implements ModelFactTableService {

    private JPAQueryFactory jpaQueryFactory;
    private final ModelFactTableRepository modelFactTableRepository;
    private final EntityManager entityManager;
    private final QModelFactTable qModelFactTable = QModelFactTable.modelFactTable;
    private final ModelFactColumnService modelFactColumnService;

    public ModelFactTableServiceImpl(ModelFactTableRepository modelFactTableRepository,
                                     EntityManager entityManager,ModelFactColumnService modelFactColumnService){
        this.modelFactTableRepository = modelFactTableRepository;
        this.entityManager = entityManager;
        this.modelFactColumnService = modelFactColumnService;
    }

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public void insert(ModelFactInfoDTO modelFactInfoDTO) {
        ModelFactTable modelFactTable = ModelFactTableMapper.INSTANCE.of(modelFactInfoDTO.getModelFactTableBase());
        ModelFactTable modelFact = modelFactTableRepository.save(modelFactTable);
        List<ModelFactColumnDTO> modelFactColumnList = modelFactInfoDTO.getModelFactColumnList();
        modelFactColumnList.forEach(e->e.setFactId(modelFact.getId()));
        modelFactColumnService.insert(modelFactColumnList);
    }

    @Override
    public ModelFactTableVO detail(Long id) {
        Optional<ModelFactTable> modelFactTable = modelFactTableRepository.findById(id);
        if(modelFactTable.isEmpty()){
            throw new BizException("当前要查找的事实表id为"+id+"的数据不存在");
        }
        return ModelFactTableMapper.INSTANCE.of(modelFactTable.get());
    }

    @Override
    public void update(Long id, ModelFactTableDTO modelFactTableDTO) {
        ModelFactTable modelFactTable = modelFactTableRepository.findById(id).orElse(null);
        if(Objects.isNull(modelFactTable)){
            throw new BizException("当前要查找的事实表id为"+id+"的数据不存在");
        }
        ModelFactTableMapper.INSTANCE.from(modelFactTableDTO,modelFactTable);
        modelFactTableRepository.saveAndFlush(modelFactTable);
    }

    @Override
    public void delete(String ids) {
        Iterable<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<ModelFactTable> modelFactTableList = modelFactTableRepository.findAllById(idSet);
        if(modelFactTableList.isEmpty()){
            throw new BizException("当前要删除的事实表id为："+ids+"的数据不存在！！！");
        }
        modelFactTableRepository.deleteAll(modelFactTableList);
    }

    @Override
    public PageResultVO<ModelFactTableVO> listPage(ModelFactTableDTO modelFactTableDTO) {
        Map<String, Object> map;
        try {
            map = queryByParams(modelFactTableDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<ModelFactTable> list = (List<ModelFactTable>) map.get("list");
        List<ModelFactTableVO> voList = ModelFactTableMapper.INSTANCE.of(list);
        return new PageResultVO<>(
                (long) map.get("total"),
                modelFactTableDTO.getPagination().getPage(),
                modelFactTableDTO.getPagination().getSize(),
                voList);
    }

    private Map<String, Object> queryByParams(ModelFactTableDTO modelFactTableDTO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, modelFactTableDTO);
        Map<String, Object> result = new HashMap<>();
        long count =
                jpaQueryFactory.select(qModelFactTable.count()).from(qModelFactTable).where(booleanBuilder).fetchOne();
        List<ModelFactTable> modelFactTableList = jpaQueryFactory
                .select(qModelFactTable)
                .from(qModelFactTable)
                .where(booleanBuilder)
                .orderBy(qModelFactTable.id.asc())
                .offset(
                        (long) (modelFactTableDTO.getPagination().getPage() - 1)
                                * modelFactTableDTO.getPagination().getSize())
                .limit(modelFactTableDTO.getPagination().getSize())
                .fetch();
        result.put("list", modelFactTableList);
        result.put("total", count);
        return result;
    }

    public void checkCondition(BooleanBuilder booleanBuilder, ModelFactTableDTO modelFactTableDTO) {
        if (!StringUtils.isEmpty(modelFactTableDTO.getFactName())) {
            booleanBuilder.and(
                    qModelFactTable
                            .factName
                            .contains(modelFactTableDTO.getFactName()));
        }
    }
}
