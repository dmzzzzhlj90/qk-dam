package com.qk.dm.datamodel.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datamodel.entity.ModelPhysicalRelation;
import com.qk.dm.datamodel.entity.ModelPhysicalTable;
import com.qk.dm.datamodel.mapstruct.mapper.ModelPhysicalRelationMapper;
import com.qk.dm.datamodel.params.dto.ModelPhysicalRelationDTO;
import com.qk.dm.datamodel.params.vo.ModelPhysicalRelationVO;
import com.qk.dm.datamodel.repositories.ModelPhysicalRelationRepository;
import com.qk.dm.datamodel.repositories.ModelPhysicalTableRepository;
import com.qk.dm.datamodel.service.ModelPhysicalRelationService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
/**
 * 数据模型表关系
 * @author wangzp
 * @date 2021/11/09 15:02
 * @since 1.0.0
 */
@Service
public class ModelPhysicalRelationServiceImpl implements ModelPhysicalRelationService {

    private final ModelPhysicalRelationRepository modelPhysicalRelationRepository;
    private final ModelPhysicalTableRepository modelPhysicalTableRepository;

    public ModelPhysicalRelationServiceImpl(ModelPhysicalRelationRepository modelPhysicalRelationRepository,ModelPhysicalTableRepository modelPhysicalTableRepository){
        this.modelPhysicalRelationRepository = modelPhysicalRelationRepository;
        this.modelPhysicalTableRepository = modelPhysicalTableRepository;
    }

    @Override
    public void insert(List<ModelPhysicalRelationDTO> modelPhysicalRelationDTOList) {
        List<ModelPhysicalRelation> list = ModelPhysicalRelationMapper.INSTANCE.use(modelPhysicalRelationDTOList);
        Date createTime = new Date();
        list.forEach(e->{
            e.setGmtCreate(createTime);
            e.setGmtModified(createTime);
        });
        modelPhysicalRelationRepository.saveAll(list);
    }

    @Override
    public void update(Long tableId,List<ModelPhysicalRelationDTO> modelPhysicalRelationDTOList) {
        Optional<ModelPhysicalTable> modelPhysicalTable = modelPhysicalTableRepository.findById(tableId);
        if(modelPhysicalTable.isEmpty()){
            throw new BizException("当前要查找的物理表数据不存在 id为："+tableId);
        }
        modelPhysicalRelationRepository.deleteByTableId(tableId);
        List<ModelPhysicalRelation> list = ModelPhysicalRelationMapper.INSTANCE.use(modelPhysicalRelationDTOList);
        Date createTime = new Date();
        list.forEach(e->{
            e.setGmtCreate(createTime);
            e.setGmtModified(createTime);
        });
        modelPhysicalRelationRepository.saveAllAndFlush(list);
    }


    @Override
    public void delete(String ids) {

    }

    @Override
    public List<ModelPhysicalRelationVO> list(Long tableId) {
        List<ModelPhysicalRelation> modelPhysicalRelationList = modelPhysicalRelationRepository.findAllByTableId(tableId);
        return ModelPhysicalRelationMapper.INSTANCE.of(modelPhysicalRelationList);
    }
}
