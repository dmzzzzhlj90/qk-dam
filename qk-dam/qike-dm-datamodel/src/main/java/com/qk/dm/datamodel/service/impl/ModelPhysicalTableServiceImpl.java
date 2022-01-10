package com.qk.dm.datamodel.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.model.constant.ModelStatus;
import com.qk.dm.datamodel.entity.ModelPhysicalTable;
import com.qk.dm.datamodel.entity.QModelPhysicalTable;
import com.qk.dm.datamodel.mapstruct.mapper.ModelPhysicalTableMapper;
import com.qk.dm.datamodel.params.dto.ModelPhysicalTableDTO;
import com.qk.dm.datamodel.params.vo.ModelPhysicalTableVO;
import com.qk.dm.datamodel.repositories.ModelPhysicalTableRepository;
import com.qk.dm.datamodel.service.ModelPhysicalTableService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;
/**
 * 物理模型表的基础信息
 * @author wangzp
 * @date 2021/11/09 10:02
 * @since 1.0.0
 */
@Service
public class ModelPhysicalTableServiceImpl implements ModelPhysicalTableService {

    private JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    private final QModelPhysicalTable qModelPhysicalTable = QModelPhysicalTable.modelPhysicalTable;
    private final ModelPhysicalTableRepository modelPhysicalTableRepository;

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    public ModelPhysicalTableServiceImpl(EntityManager entityManager,ModelPhysicalTableRepository modelPhysicalTableRepository){
        this.entityManager = entityManager;
        this.modelPhysicalTableRepository = modelPhysicalTableRepository;
    }


    @Override
    public void insert(ModelPhysicalTableDTO modelPhysicalTableDTO) {
        ModelPhysicalTable modelPhysicalTable = ModelPhysicalTableMapper.INSTANCE.of(modelPhysicalTableDTO);
        modelPhysicalTableRepository.save(modelPhysicalTable);
    }

    @Override
    public ModelPhysicalTableVO detail(Long id) {
        Optional<ModelPhysicalTable> modelPhysicalTable = modelPhysicalTableRepository.findById(id);
        if(modelPhysicalTable.isEmpty()){
            throw new BizException("当前要查询的物理表信息id为："+id+" 的数据不存在！！");
        }
        return ModelPhysicalTableMapper.INSTANCE.of(modelPhysicalTable.get());
    }

    @Override
    public void update(Long id, ModelPhysicalTableDTO modelPhysicalTableDTO) {
        ModelPhysicalTable modelPhysicalTable = modelPhysicalTableRepository.findById(id).orElse(null);
        if(Objects.isNull(modelPhysicalTable)){
            throw new BizException("当前要修改的表信息id为："+id+"的数据不存在！！！");
        }
        ModelPhysicalTableMapper.INSTANCE.from(modelPhysicalTableDTO,modelPhysicalTable);
        modelPhysicalTableRepository.saveAndFlush(modelPhysicalTable);
    }

    @Override
    public void delete(String ids) {
        Iterable<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<ModelPhysicalTable> modelPhysicalTableList = modelPhysicalTableRepository.findAllById(idSet);
        if(modelPhysicalTableList.isEmpty()){
            throw new BizException("当前要删除的表id为："+ids+"的数据不存在！！！");
        }

        modelPhysicalTableRepository.deleteAll(modelPhysicalTableList);
    }

    @Override
    public PageResultVO<ModelPhysicalTableVO> listPage(ModelPhysicalTableDTO modelPhysicalTableDTO) {
        Map<String, Object> map;
        try {
            map = queryByParams(modelPhysicalTableDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<ModelPhysicalTable> list = (List<ModelPhysicalTable>) map.get("list");
        List<ModelPhysicalTableVO> voList = ModelPhysicalTableMapper.INSTANCE.of(list);
        return new PageResultVO<ModelPhysicalTableVO>(
                (long) map.get("total"),
                1,
               10,
                voList);
    }

    /**
     * 关系建模获取表名称
     * @return
     */
    @Override
    public List<String> queryTables() {
        List<String> list = new ArrayList<>();
        Predicate predicate = qModelPhysicalTable.status.eq(ModelStatus.PUBLISH);
        Iterable<ModelPhysicalTable> modelTalbe = modelPhysicalTableRepository.findAll(predicate);
       if (modelTalbe==null){
           throw new BizException("获取表信息失败");
       }
       for (ModelPhysicalTable modelPhysicalTable : modelTalbe){
           list.add(modelPhysicalTable.getTableName());
       }
       return list;
    }

    private Map<String, Object> queryByParams(ModelPhysicalTableDTO modelPhysicalTableDTO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, modelPhysicalTableDTO);
        Map<String, Object> result = new HashMap<>();
        long count =
                jpaQueryFactory.select(qModelPhysicalTable.count()).from(qModelPhysicalTable).where(booleanBuilder).fetchOne();
        List<ModelPhysicalTable> modelPhysicalTableList =
                jpaQueryFactory
                        .select(qModelPhysicalTable)
                        .from(qModelPhysicalTable)
                        .where(booleanBuilder)
                        .orderBy(qModelPhysicalTable.id.asc())
                        .fetch();
        result.put("list", modelPhysicalTableList);
        result.put("total", count);
        return result;
    }

    public void checkCondition(BooleanBuilder booleanBuilder, ModelPhysicalTableDTO modelPhysicalTableDTO) {
        if (!StringUtils.isEmpty(modelPhysicalTableDTO.getTableName())) {
            booleanBuilder.and(
                    qModelPhysicalTable
                            .tableName
                            .contains(modelPhysicalTableDTO.getTableName()));
        }
    }

}
