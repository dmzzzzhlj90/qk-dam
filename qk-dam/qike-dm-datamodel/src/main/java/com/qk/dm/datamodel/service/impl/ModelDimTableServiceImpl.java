package com.qk.dm.datamodel.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.entity.ModelDimTable;
import com.qk.dm.datamodel.entity.QModelDimTable;
import com.qk.dm.datamodel.mapstruct.mapper.ModelDimTableMapper;
import com.qk.dm.datamodel.params.dto.ModelDimTableDTO;
import com.qk.dm.datamodel.params.vo.ModelDimTableVO;
import com.qk.dm.datamodel.repositories.ModelDimTableRepository;
import com.qk.dm.datamodel.service.ModelDimTableService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ModelDimTableServiceImpl implements ModelDimTableService {

    private JPAQueryFactory jpaQueryFactory;
    private final ModelDimTableRepository modelDimTableRepository;
    private final EntityManager entityManager;
    private final QModelDimTable qModelDimTable = QModelDimTable.modelDimTable;

    public ModelDimTableServiceImpl(ModelDimTableRepository modelDimTableRepository,
                                    EntityManager entityManager){
        this.modelDimTableRepository = modelDimTableRepository;
        this.entityManager = entityManager;
    }
    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public void insert(ModelDimTableDTO modelDimTableDTO) {
        ModelDimTable modelDimTable = ModelDimTableMapper.INSTANCE.of(modelDimTableDTO);
        modelDimTableRepository.save(modelDimTable);

    }

    @Override
    public ModelDimTableVO detail(Long id) {
        Optional<ModelDimTable> modelDimTable = modelDimTableRepository.findById(id);
        if(modelDimTable.isEmpty()){
            throw new BizException("当前查找的维度表id为"+id+"的数据不存在");
        }
       return ModelDimTableMapper.INSTANCE.of(modelDimTable.get());
    }

    @Override
    public void update(Long id, ModelDimTableDTO modelDimTableDTO) {
        ModelDimTable modelDimTable = modelDimTableRepository.findById(id).orElse(null);
        if(Objects.isNull(modelDimTable)){
            throw new BizException("当前查找的维度表id为"+id+"的数据不存在");
        }
        ModelDimTableMapper.INSTANCE.from(modelDimTableDTO,modelDimTable);
        modelDimTableRepository.saveAndFlush(modelDimTable);
    }

    @Override
    public void delete(String ids) {
        Iterable<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<ModelDimTable> modelDimTableList = modelDimTableRepository.findAllById(idSet);
        if(modelDimTableList.isEmpty()){
            throw new BizException("当前要删除的维度表id为："+ids+"的数据不存在！！！");
        }
        modelDimTableRepository.deleteAll(modelDimTableList);
    }

    @Override
    public PageResultVO<ModelDimTableVO> list(ModelDimTableDTO modelDimTableDTO) {
        Map<String, Object> map;
        try {
            map = queryByParams(modelDimTableDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<ModelDimTable> list = (List<ModelDimTable>) map.get("list");
        List<ModelDimTableVO> voList = ModelDimTableMapper.INSTANCE.of(list);
        return new PageResultVO<>(
                (long) map.get("total"),
                modelDimTableDTO.getPagination().getPage(),
                modelDimTableDTO.getPagination().getSize(),
                voList);
    }

    private Map<String, Object> queryByParams(ModelDimTableDTO modelDimTableDTO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, modelDimTableDTO);
        Map<String, Object> result = new HashMap<>();
        long count =
                jpaQueryFactory.select(qModelDimTable.count()).from(qModelDimTable).where(booleanBuilder).fetchOne();
        List<ModelDimTable> modelDimTableList = jpaQueryFactory
                .select(qModelDimTable)
                .from(qModelDimTable)
                .where(booleanBuilder)
                .orderBy(qModelDimTable.id.asc())
                .offset(
                        (long) (modelDimTableDTO.getPagination().getPage() - 1)
                                * modelDimTableDTO.getPagination().getSize())
                .limit(modelDimTableDTO.getPagination().getSize())
                .fetch();
        result.put("list", modelDimTableList);
        result.put("total", count);
        return result;
    }

    public void checkCondition(BooleanBuilder booleanBuilder, ModelDimTableDTO modelDimTableDTO) {
        if (!StringUtils.isEmpty(modelDimTableDTO.getDimName())) {
            booleanBuilder.and(
                    qModelDimTable
                            .dimName
                            .contains(modelDimTableDTO.getDimName()));
        }
    }
}
