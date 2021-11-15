package com.qk.dm.datamodel.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datamodel.entity.ModelSql;
import com.qk.dm.datamodel.mapstruct.mapper.ModelSqlMapper;
import com.qk.dm.datamodel.params.dto.ModelSqlDTO;
import com.qk.dm.datamodel.params.vo.ModelSqlVO;
import com.qk.dm.datamodel.repositories.ModelSqlRepository;
import com.qk.dm.datamodel.service.ModelSqlService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 维度
 * @author wangzp
 * @date 2021/11/15 15:48
 * @since 1.0.0
 */
@Service
public class ModelSqlServiceImpl implements ModelSqlService {
    private final ModelSqlRepository modelSqlRepository;

    public ModelSqlServiceImpl(ModelSqlRepository modelSqlRepository){
        this.modelSqlRepository = modelSqlRepository;
    }

    @Override
    public void insert(ModelSqlDTO modelDTO) {
        ModelSql modelSql = ModelSqlMapper.INSTANCE.of(modelDTO);
        modelSqlRepository.save(modelSql);
    }

    @Override
    public ModelSqlVO detail(Long tableId) {

        ModelSql modelSql = modelSqlRepository.findByTableId(tableId);
        if(Objects.isNull(modelSql)){
            throw new BizException("当前要查询的表 id为"+tableId+"的SQL不存在！！！");
        }
        return ModelSqlMapper.INSTANCE.of(modelSql);
    }

    @Override
    public void update(Long tableId, ModelSqlDTO modelDTO) {
        ModelSql modelSql = modelSqlRepository.findByTableId(tableId);
        if(Objects.isNull(modelSql)){
            throw new BizException("当前要查询的表 id为"+tableId+"的SQL不存在！！！");
        }
        ModelSqlMapper.INSTANCE.from(modelDTO,modelSql);
        modelSqlRepository.saveAndFlush(modelSql);
    }

    @Override
    public void delete(Long tableId) {
        modelSqlRepository.deleteByTableId(tableId);
    }
}
