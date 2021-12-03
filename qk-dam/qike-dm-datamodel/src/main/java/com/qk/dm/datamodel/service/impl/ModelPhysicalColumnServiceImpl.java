package com.qk.dm.datamodel.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datamodel.entity.ModelPhysicalColumn;
import com.qk.dm.datamodel.entity.ModelPhysicalTable;
import com.qk.dm.datamodel.entity.QModelPhysicalColumn;
import com.qk.dm.datamodel.entity.QModelPhysicalTable;
import com.qk.dm.datamodel.mapstruct.mapper.ModelPhysicalColumnMapper;
import com.qk.dm.datamodel.params.dto.ModelPhysicalColumnDTO;
import com.qk.dm.datamodel.params.vo.ModelPhysicalColumnVO;
import com.qk.dm.datamodel.repositories.ModelPhysicalColumnRepository;
import com.qk.dm.datamodel.repositories.ModelPhysicalTableRepository;
import com.qk.dm.datamodel.service.ModelPhysicalColumnService;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
/**
 * 物理模型属性字段
 * @author wangzp
 * @date 2021/11/08 14:42
 * @since 1.0.0
 */
@Service
public class ModelPhysicalColumnServiceImpl implements ModelPhysicalColumnService {

    private final ModelPhysicalColumnRepository modelPhysicalColumnRepository;
    private final ModelPhysicalTableRepository modelPhysicalTableRepository;
    private final QModelPhysicalColumn qModelPhysicalColumn=QModelPhysicalColumn.modelPhysicalColumn;
    private final QModelPhysicalTable qModelPhysicalTable=QModelPhysicalTable.modelPhysicalTable;


    public ModelPhysicalColumnServiceImpl(ModelPhysicalColumnRepository modelPhysicalColumnRepository,
        ModelPhysicalTableRepository modelPhysicalTableRepository){
        this.modelPhysicalColumnRepository = modelPhysicalColumnRepository;
        this.modelPhysicalTableRepository = modelPhysicalTableRepository;
    }

    @Override
    public void insert(List<ModelPhysicalColumnDTO> modelPhysicalColumnDTOList) {
        List<ModelPhysicalColumn> modelPhysicalColumnList = ModelPhysicalColumnMapper.INSTANCE.use(modelPhysicalColumnDTOList);
        modelPhysicalColumnRepository.saveAll(modelPhysicalColumnList);
    }

    @Override
    public void update(Long tableId, List<ModelPhysicalColumnDTO> modelPhysicalColumnDTOList) {
        ModelPhysicalTable modelPhysicalTable = modelPhysicalTableRepository.findById(tableId).orElse(null);
        if(Objects.isNull(modelPhysicalTable)){
            throw new BizException("当前要修改的表id为："+tableId+"的数据不存在！！！");
        }
        modelPhysicalColumnRepository.deleteByTableId(tableId);
        List<ModelPhysicalColumn> modelPhysicalColumnList = ModelPhysicalColumnMapper.INSTANCE.use(modelPhysicalColumnDTOList);
        modelPhysicalColumnRepository.saveAllAndFlush(modelPhysicalColumnList);
    }

    @Override
    public void delete(String ids) {
        Iterable<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<ModelPhysicalColumn> modelPhysicalColumnList = modelPhysicalColumnRepository.findAllById(idSet);
        if(modelPhysicalColumnList.isEmpty()){
            throw new BizException("当前要删除的物理表id为："+ids+"的数据不存在！！！");
        }
        modelPhysicalColumnRepository.deleteAll(modelPhysicalColumnList);
    }

    @Override
    public List<ModelPhysicalColumnVO> list(Long tableId) {
        List<ModelPhysicalColumn> list = modelPhysicalColumnRepository.findAllByTableId(tableId);
        return ModelPhysicalColumnMapper.INSTANCE.of(list);
    }

    /**
     * 根据表名称查询表字段
     * @param tableName
     * @return
     */
    @Override
    public List<String> queryColumn(String tableName) {
        List<String> collect = new ArrayList<>();
        //判断当前表是否存在
        Predicate predicate=qModelPhysicalTable.tableName.eq(tableName);
        Optional<ModelPhysicalTable> table = modelPhysicalTableRepository.findOne(predicate);
        if (table.isEmpty()){
            throw  new BizException("当前表名为"+tableName+"的表不存在");
        }
        ModelPhysicalTable modelPhysicalTable = table.get();
        List<ModelPhysicalColumn> columnList = new ArrayList<>();
        if (modelPhysicalTable!=null && modelPhysicalTable.getId()!=null){
            columnList = modelPhysicalColumnRepository.findAllByTableId(modelPhysicalTable.getId());
        }
        if (CollectionUtils.isNotEmpty(columnList)){
             collect = columnList.stream().map(ModelPhysicalColumn::getColumnName).collect(Collectors.toList());
        }
        return collect;
    }

}
