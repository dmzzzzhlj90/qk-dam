package com.qk.dm.datamodel.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.model.constant.ModelStatus;
import com.qk.dm.datamodel.entity.ModelSummary;
import com.qk.dm.datamodel.entity.QModelSummary;
import com.qk.dm.datamodel.mapstruct.mapper.ModelSummaryMapper;
import com.qk.dm.datamodel.params.dto.ModelSummaryDTO;
import com.qk.dm.datamodel.params.dto.ModelSummaryIdcDTO;
import com.qk.dm.datamodel.params.dto.ModelSummaryInfoDTO;
import com.qk.dm.datamodel.params.vo.ModelSummaryVO;
import com.qk.dm.datamodel.repositories.ModelSummaryRepository;
import com.qk.dm.datamodel.service.ModelSummaryIdcService;
import com.qk.dm.datamodel.service.ModelSummaryService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ModelSummaryServiceImpl implements ModelSummaryService {

    private JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    private final ModelSummaryRepository modelSummaryRepository;
    private final QModelSummary qModelSummary = QModelSummary.modelSummary;
    private final ModelSummaryIdcService modelSummaryIdcService;

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    public ModelSummaryServiceImpl(EntityManager entityManager,ModelSummaryRepository modelSummaryRepository,
                                   ModelSummaryIdcService modelSummaryIdcService){
        this.entityManager = entityManager;
        this.modelSummaryRepository = modelSummaryRepository;
        this.modelSummaryIdcService = modelSummaryIdcService;
    }

    @Override
    public void insert(ModelSummaryInfoDTO modelSummaryInfoDTO) {
        ModelSummary modelSummary = ModelSummaryMapper.INSTANCE.of(modelSummaryInfoDTO.getModelSummaryBase());
        ModelSummary summary = modelSummaryRepository.save(modelSummary);
        List<ModelSummaryIdcDTO> modelSummaryIdcList = modelSummaryInfoDTO.getModelSummaryIdcList();
        if(!modelSummaryIdcList.isEmpty()){
            modelSummaryIdcList.forEach(e->e.setSummaryId(summary.getId()));
            modelSummaryIdcService.insert(modelSummaryIdcList);
        }
    }

    @Override
    public ModelSummaryVO detail(Long id) {
        Optional<ModelSummary> modelSummary = modelSummaryRepository.findById(id);
        if(modelSummary.isEmpty()){
            throw new BizException("当前查询的汇总表 id为"+id+"的数据不存在");
        }
        return ModelSummaryMapper.INSTANCE.of(modelSummary.get());
    }

    @Override
    public void update(Long id, ModelSummaryInfoDTO modelSummaryInfoDTO) {
        ModelSummary modelSummary = modelSummaryRepository.findById(id).orElse(null);
         if(Objects.isNull(modelSummary)){
             throw new BizException("当前查询的汇总表 id为"+id+"的数据不存在");
         }
         ModelSummaryMapper.INSTANCE.from(modelSummaryInfoDTO.getModelSummaryBase(),modelSummary);
        modelSummaryRepository.saveAndFlush(modelSummary);
        List<ModelSummaryIdcDTO> modelSummaryIdcList = modelSummaryInfoDTO.getModelSummaryIdcList();
        if(!modelSummaryIdcList.isEmpty()){
            modelSummaryIdcList.forEach(e->e.setSummaryId(id));
            modelSummaryIdcService.update(id,modelSummaryIdcList);
        }

    }

    @Override
    public void delete(String ids) {
        List<ModelSummary> modelSummaryList = getModelSummaryList(ids);
        modelSummaryRepository.deleteAll(modelSummaryList);
    }

    @Override
    public PageResultVO<ModelSummaryVO> list(ModelSummaryDTO modelSummaryDTO) {
        Map<String, Object> map;
        try {
            map = queryByParams(modelSummaryDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<ModelSummary> list = (List<ModelSummary>) map.get("list");
        List<ModelSummaryVO> voList = ModelSummaryMapper.INSTANCE.of(list);
        return new PageResultVO<>(
                (long) map.get("total"),
                modelSummaryDTO.getPagination().getPage(),
                modelSummaryDTO.getPagination().getSize(),
                voList);
    }

    @Override
    public void publish(String ids) {
        List<ModelSummary> modelSummaryList = getModelSummaryList(ids);
        modelSummaryList.forEach(e->e.setStatus(ModelStatus.PUBLISH));
        modelSummaryRepository.saveAllAndFlush(modelSummaryList);
    }

    @Override
    public void offline(String ids) {
        List<ModelSummary> modelSummaryList = getModelSummaryList(ids);
        modelSummaryList.forEach(e->e.setStatus(ModelStatus.OFFLINE));
        modelSummaryRepository.saveAllAndFlush(modelSummaryList);
    }

    private List<ModelSummary> getModelSummaryList(String ids){
        Iterable<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<ModelSummary> modelSummaryList = modelSummaryRepository.findAllById(idSet);
        if(modelSummaryList.isEmpty()){
            throw new BizException("当前要操作的汇总表id为："+ids+"的数据不存在！！！");
        }
        return modelSummaryList;
    }

    private Map<String, Object> queryByParams(ModelSummaryDTO modelSummaryDTO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, modelSummaryDTO);
        Map<String, Object> result = new HashMap<>();
        long count =
                jpaQueryFactory.select(qModelSummary.count()).from(qModelSummary).where(booleanBuilder).fetchOne();
        List<ModelSummary> modelPhysicalTableList =
                jpaQueryFactory
                        .select(qModelSummary)
                        .from(qModelSummary)
                        .where(booleanBuilder)
                        .orderBy(qModelSummary.id.asc())
                        .offset(
                                (long) (modelSummaryDTO.getPagination().getPage() - 1)
                                        * modelSummaryDTO.getPagination().getSize())
                        .limit(modelSummaryDTO.getPagination().getSize())
                        .fetch();
        result.put("list", modelPhysicalTableList);
        result.put("total", count);
        return result;
    }

    public void checkCondition(BooleanBuilder booleanBuilder, ModelSummaryDTO modelSummaryDTO) {
        if (!StringUtils.isEmpty(modelSummaryDTO.getTableName())) {
            booleanBuilder.and(
                    qModelSummary
                            .tableName
                            .contains(modelSummaryDTO.getTableName()));
        }
    }
}
