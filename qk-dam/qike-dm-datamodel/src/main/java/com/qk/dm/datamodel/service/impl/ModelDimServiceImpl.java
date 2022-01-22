package com.qk.dm.datamodel.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.model.constant.ModelStatus;
import com.qk.dm.datamodel.entity.ModelDim;
import com.qk.dm.datamodel.entity.QModelDim;
import com.qk.dm.datamodel.mapstruct.mapper.ModelDimColumnMapper;
import com.qk.dm.datamodel.mapstruct.mapper.ModelDimMapper;
import com.qk.dm.datamodel.params.dto.ModelDimColumnDTO;
import com.qk.dm.datamodel.params.dto.ModelDimDTO;
import com.qk.dm.datamodel.params.dto.ModelDimQueryDTO;
import com.qk.dm.datamodel.params.dto.ModelDimTableDTO;
import com.qk.dm.datamodel.params.vo.ModelDimDetailVO;
import com.qk.dm.datamodel.params.vo.ModelDimVO;
import com.qk.dm.datamodel.repositories.ModelDimRepository;
import com.qk.dm.datamodel.service.ModelDimColumnService;
import com.qk.dm.datamodel.service.ModelDimService;
import com.qk.dm.datamodel.service.ModelDimTableService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 维度
 * @author wangzp
 * @date 2021/11/08 10:36
 * @since 1.0.0
 */
@Service
public class ModelDimServiceImpl implements ModelDimService {

    private JPAQueryFactory jpaQueryFactory;
    private final ModelDimRepository modelDimRepository;
    private final EntityManager entityManager;
    private final QModelDim qModelDim = QModelDim.modelDim;
    private final ModelDimColumnService modelDimColumnSerVice;
    private final ModelDimTableService modelDimTableService;


    public ModelDimServiceImpl(ModelDimRepository modelDimRepository,EntityManager entityManager,
                               ModelDimColumnService modelDimColumnSerVice,
                               ModelDimTableService modelDimTableService){
        this.modelDimRepository = modelDimRepository;
        this.entityManager = entityManager;
        this.modelDimColumnSerVice = modelDimColumnSerVice;
        this.modelDimTableService = modelDimTableService;
    }
    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(ModelDimDTO modelDimDTO) {
        ModelDim modelDim = ModelDimMapper.INSTANCE.of(modelDimDTO);
        List<ModelDimColumnDTO> modelDimColumnList = modelDimDTO.getColumnList();
        if(modelDimColumnList.isEmpty()){
            throw new BizException("维度字段不能为空！！");
        }
        if(checkRepeat(modelDimColumnList)){
            throw new BizException("存在重复的字段！！");
        }
        Predicate predicate = qModelDim.dimName.eq(modelDim.getDimName());
        if(modelDimRepository.exists(predicate)){
            throw new BizException("该维度已存在，不可重复添加！！");
        }
        //保存维度基本信息
        ModelDim dim = modelDimRepository.save(modelDim);
        //保存字段信息
        //modelDimColumnList.forEach(e->e.setDimId(dim.getId()));
        modelDimColumnSerVice.insert(modelDimColumnList,dim.getId());
        //如果是直接发布 需要保存维度表
        if(Objects.equals(ModelStatus.PUBLISH,modelDim.getStatus())){
            ModelDimTableDTO modelDimTableDTO= ModelDimMapper.INSTANCE.ofDimTable(modelDimDTO);
            modelDimTableDTO.setModelDimId(dim.getId());
            modelDimTableDTO.setColumnList(ModelDimColumnMapper.INSTANCE.ofDimTableColumn(modelDimColumnList));
            modelDimTableService.insert(modelDimTableDTO);
        }
    }

    @Override
    public ModelDimDetailVO detail(Long id) {
        Optional<ModelDim> modelDim = modelDimRepository.findById(id);
        if(modelDim.isEmpty()){
            throw new BizException("当前要查询的维度信息 id为"+id+"的不存在！！！");
        }
        ModelDimDetailVO modelDimDetailVO= ModelDimMapper.INSTANCE.ofDetail(modelDim.get());
        modelDimDetailVO.setColumnList(modelDimColumnSerVice.list(modelDimDetailVO.getId()));
        return modelDimDetailVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, ModelDimDTO modelDimDTO) {
        ModelDim modelDim = modelDimRepository.findById(id).orElse(null);
        if(Objects.isNull(modelDim)){
            throw new BizException("当前要修改的维度信息 id为"+id+"的数据不存在！！！");
        }
        if(Objects.equals(modelDim.getStatus(), ModelStatus.PUBLISH)){
            throw new BizException("当前维度已发布，不可修改！！！");
        }
        ModelDimMapper.INSTANCE.from(modelDimDTO,modelDim);
        modelDimRepository.saveAndFlush(modelDim);
        List<ModelDimColumnDTO> modelDimColumnDTOList = modelDimDTO.getColumnList();
        if(!modelDimColumnDTOList.isEmpty()){
            modelDimColumnSerVice.update(id,modelDimColumnDTOList);
        }
        //如果是直接发布 需要修改或添加维度表
        if(Objects.equals(ModelStatus.PUBLISH,modelDim.getStatus())){
            ModelDimTableDTO modelDimTableDTO= ModelDimMapper.INSTANCE.ofDimTable(modelDimDTO);
            modelDimTableDTO.setModelDimId(modelDim.getId());
            modelDimTableDTO.setColumnList(ModelDimColumnMapper.INSTANCE.ofDimTableColumn(modelDimColumnDTOList));
            modelDimTableService.updateDim(modelDim.getId(),modelDimTableDTO);
        }

    }

    @Override
    public void delete(String ids) {
        List<ModelDim> modelDimList = getModelDimList(ids);
        modelDimRepository.deleteAll(modelDimList);
    }

    @Override
    public PageResultVO<ModelDimVO> list(ModelDimQueryDTO modelDimQueryDTO) {
        Map<String, Object> map;
        try {
            map = queryByParams(modelDimQueryDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<ModelDim> list = (List<ModelDim>) map.get("list");
        List<ModelDimVO> voList = ModelDimMapper.INSTANCE.of(list);
        return new PageResultVO<>(
                (long) map.get("total"),
                modelDimQueryDTO.getPagination().getPage(),
                modelDimQueryDTO.getPagination().getSize(),
                voList);
    }

    @Override
    public void publish(String ids) {
        List<ModelDim> modelDimList = getModelDimList(ids);
        modelDimList = modelDimList.stream().peek(e -> {
            if (e.getStatus() == ModelStatus.PUBLISH) {
                throw new BizException(e.getDimName() + "维度已发布，不可重复发布！！！");
            }
            e.setStatus(ModelStatus.PUBLISH);
        }).collect(Collectors.toList());
        modelDimRepository.saveAllAndFlush(modelDimList);
    }

    @Override
    public void offline(String ids) {
        List<ModelDim> modelDimList = getModelDimList(ids);
        modelDimList.forEach(e->{
            if(e.getStatus()!=ModelStatus.PUBLISH){
                throw new BizException(e.getDimName() + "维度还未发布，不可下线！！！");
            }
            e.setStatus(ModelStatus.OFFLINE);
        });
        modelDimRepository.saveAllAndFlush(modelDimList);
    }

    private Boolean checkRepeat(List<ModelDimColumnDTO> modelDimColumnDTOList){
        Map<String, Long> collect = modelDimColumnDTOList.stream()
                .collect(Collectors.groupingBy(ModelDimColumnDTO::getColumnName, Collectors.counting()));
        List<String> list = collect.keySet().stream().
                filter(key -> collect.get(key) > 1).collect(Collectors.toList());
        return !CollectionUtils.isEmpty(list);
    }
    private List<ModelDim> getModelDimList(String ids){
        Iterable<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<ModelDim> modelDimList = modelDimRepository.findAllById(idSet);
        if(modelDimList.isEmpty()){
            throw new BizException("当前要操作维度id为："+ids+"的数据不存在！！！");
        }
        return modelDimList;
    }

    private Map<String, Object> queryByParams(ModelDimQueryDTO modelDimQueryDTO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, modelDimQueryDTO);
        Map<String, Object> result = new HashMap<>();
        long count =
                jpaQueryFactory.select(qModelDim.count()).from(qModelDim).where(booleanBuilder).fetchOne();
        List<ModelDim> modelDimList = jpaQueryFactory
                .select(qModelDim)
                .from(qModelDim)
                .where(booleanBuilder)
                .orderBy(qModelDim.id.asc())
                .offset(
                        (long) (modelDimQueryDTO.getPagination().getPage() - 1)
                                * modelDimQueryDTO.getPagination().getSize())
                .limit(modelDimQueryDTO.getPagination().getSize())
                .fetch();
        result.put("list", modelDimList);
        result.put("total", count);
        return result;
    }

    public void checkCondition(BooleanBuilder booleanBuilder, ModelDimQueryDTO modelDimQueryDTO) {
        if (!StringUtils.isEmpty(modelDimQueryDTO.getDimName())) {
            booleanBuilder.and(qModelDim.dimName.contains(modelDimQueryDTO.getDimName()));
        }
        if(Objects.nonNull(modelDimQueryDTO.getStatus())){
            booleanBuilder.and(qModelDim.status.eq(modelDimQueryDTO.getStatus()));
        }
    }

}
