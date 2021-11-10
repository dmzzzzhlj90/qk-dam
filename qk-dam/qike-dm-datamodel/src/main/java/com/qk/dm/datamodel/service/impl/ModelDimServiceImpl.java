package com.qk.dm.datamodel.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.entity.ModelDim;
import com.qk.dm.datamodel.entity.QModelDim;
import com.qk.dm.datamodel.mapstruct.mapper.ModelDimMapper;
import com.qk.dm.datamodel.params.dto.ModelDimColumnDTO;
import com.qk.dm.datamodel.params.dto.ModelDimDTO;
import com.qk.dm.datamodel.params.dto.ModelDimInfoDTO;
import com.qk.dm.datamodel.params.vo.ModelDimVO;
import com.qk.dm.datamodel.repositories.ModelDimRepository;
import com.qk.dm.datamodel.service.ModelDimColumnSerVice;
import com.qk.dm.datamodel.service.ModelDimService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private final ModelDimColumnSerVice modelDimColumnSerVice;

    private static final int PUBLISH = 1; //已发布
    private static final int OFFLINE = 2;//已下线

    public ModelDimServiceImpl(ModelDimRepository modelDimRepository,EntityManager entityManager,
                               ModelDimColumnSerVice modelDimColumnSerVice){
        this.modelDimRepository = modelDimRepository;
        this.entityManager = entityManager;
        this.modelDimColumnSerVice = modelDimColumnSerVice;
    }
    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public void insert(ModelDimInfoDTO modelDimInfoDTO) {
        ModelDim modelDim = ModelDimMapper.INSTANCE.of(modelDimInfoDTO.getModelDimBase());
        //保存维度基本信息
        ModelDim dim = modelDimRepository.save(modelDim);
        //保存字段信息
        List<ModelDimColumnDTO> modelDimColumnList = modelDimInfoDTO.getModelDimColumnList();
        modelDimColumnList.forEach(e->e.setDimId(dim.getId()));;
        modelDimColumnSerVice.insert(modelDimColumnList);
    }

    @Override
    public ModelDimVO detail(Long id) {
        Optional<ModelDim> modelDim = modelDimRepository.findById(id);
        if(modelDim.isEmpty()){
            throw new BizException("当前要查询的维度信息 id为"+id+"的不存在！！！");
        }
       return ModelDimMapper.INSTANCE.of(modelDim.get());
    }

    @Override
    public void update(Long id, ModelDimInfoDTO modelDimInfoDTO) {
        ModelDim modelDim = modelDimRepository.findById(id).orElse(null);
        if(Objects.isNull(modelDim)){
            throw new BizException("当前要修改的维度信息 id为"+id+"的数据不存在！！！");
        }
        ModelDimMapper.INSTANCE.from(modelDimInfoDTO.getModelDimBase(),modelDim);
        modelDimRepository.saveAndFlush(modelDim);
        modelDimColumnSerVice.update(id,modelDimInfoDTO.getModelDimColumnList());
    }

    @Override
    public void delete(String ids) {
        List<ModelDim> modelDimList = getModelDimList(ids);
        modelDimRepository.deleteAll(modelDimList);
    }

    @Override
    public PageResultVO<ModelDimVO> list(ModelDimDTO modelDimDTO) {
        Map<String, Object> map;
        try {
            map = queryByParams(modelDimDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<ModelDim> list = (List<ModelDim>) map.get("list");
        List<ModelDimVO> voList = ModelDimMapper.INSTANCE.of(list);
        return new PageResultVO<>(
                (long) map.get("total"),
                modelDimDTO.getPagination().getPage(),
                modelDimDTO.getPagination().getSize(),
                voList);
    }

    @Override
    public void publish(String ids) {
        List<ModelDim> modelDimList = getModelDimList(ids);
        modelDimList.forEach(e->e.setStatus(PUBLISH));
        modelDimRepository.saveAllAndFlush(modelDimList);
    }

    @Override
    public void offline(String ids) {
        List<ModelDim> modelDimList = getModelDimList(ids);
        modelDimList.forEach(e->e.setStatus(OFFLINE));
        modelDimRepository.saveAllAndFlush(modelDimList);
    }
    private List<ModelDim> getModelDimList(String ids){
        Iterable<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<ModelDim> modelDimList = modelDimRepository.findAllById(idSet);
        if(modelDimList.isEmpty()){
            throw new BizException("当前要操作维度id为："+ids+"的数据不存在！！！");
        }
        return modelDimList;
    }

    private Map<String, Object> queryByParams(ModelDimDTO modelDimDTO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, modelDimDTO);
        Map<String, Object> result = new HashMap<>();
        long count =
                jpaQueryFactory.select(qModelDim.count()).from(qModelDim).where(booleanBuilder).fetchOne();
        List<ModelDim> modelDimList = jpaQueryFactory
                .select(qModelDim)
                .from(qModelDim)
                .where(booleanBuilder)
                .orderBy(qModelDim.id.asc())
                .offset(
                        (long) (modelDimDTO.getPagination().getPage() - 1)
                                * modelDimDTO.getPagination().getSize())
                .limit(modelDimDTO.getPagination().getSize())
                .fetch();
        result.put("list", modelDimList);
        result.put("total", count);
        return result;
    }

    public void checkCondition(BooleanBuilder booleanBuilder, ModelDimDTO modelDimDTO) {
        if (!StringUtils.isEmpty(modelDimDTO.getDimName())) {
            booleanBuilder.and(
                    qModelDim
                            .dimName
                            .contains(modelDimDTO.getDimName()));
        }
    }
}
