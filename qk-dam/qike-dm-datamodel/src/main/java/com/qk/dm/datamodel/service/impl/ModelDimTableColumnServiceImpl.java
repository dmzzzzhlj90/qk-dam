package com.qk.dm.datamodel.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datamodel.entity.ModelDimTableColumn;
import com.qk.dm.datamodel.mapstruct.mapper.ModelDimTableColumnMapper;
import com.qk.dm.datamodel.params.dto.ModelDimTableColumnDTO;
import com.qk.dm.datamodel.params.vo.ModelDimTableColumnVO;
import com.qk.dm.datamodel.repositories.ModelDimTableColumnRepository;
import com.qk.dm.datamodel.service.ModelDimTableColumnService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 维度表字段
 * @author wangzp
 * @date 2021/11/16 19:29
 * @since 1.0.0
 */
@Service
public class ModelDimTableColumnServiceImpl implements ModelDimTableColumnService {
    private final ModelDimTableColumnRepository modelDimTableColumnRepository;

    public ModelDimTableColumnServiceImpl(ModelDimTableColumnRepository modelDimTableColumnRepository){
        this.modelDimTableColumnRepository = modelDimTableColumnRepository;
    }

    @Override
    public void insert(List<ModelDimTableColumnDTO> modelDimTableColumnDTOList) {
        List<ModelDimTableColumn> modelDimTableColumnList = ModelDimTableColumnMapper.INSTANCE.use(modelDimTableColumnDTOList);
        modelDimTableColumnRepository.saveAll(modelDimTableColumnList);
    }

    @Override
    public ModelDimTableColumnVO detail(Long id) {
        Optional<ModelDimTableColumn> modelDimTableColumn = modelDimTableColumnRepository.findById(id);
        if(modelDimTableColumn.isEmpty()){
            throw new BizException("当前要查找的维度表字段id为"+id+"的数据不存在");
        }
        return ModelDimTableColumnMapper.INSTANCE.of(modelDimTableColumn.get());
    }

    @Override
    public void update(Long dimTableId, List<ModelDimTableColumnDTO> modelDimTableColumnDTOList) {
        modelDimTableColumnRepository.deleteByDimTableId(dimTableId);
        List<ModelDimTableColumn> modelDimTableColumnList = ModelDimTableColumnMapper.INSTANCE.use(modelDimTableColumnDTOList);
        modelDimTableColumnList.forEach(e->e.setDimTableId(dimTableId));
        modelDimTableColumnRepository.saveAllAndFlush(modelDimTableColumnList);
    }

    @Override
    public void delete(String dimTableIds) {
        Arrays.stream(dimTableIds.split(",")).forEach(e->{modelDimTableColumnRepository.deleteByDimTableId(Long.valueOf(e));});
    }

    @Override
    public void delete(List<Long> dimTableIdList) {
        dimTableIdList.forEach(modelDimTableColumnRepository::deleteByDimTableId);
    }

    @Override
    public List<ModelDimTableColumnVO> list(Long dimTableId) {
        List<ModelDimTableColumn> modelDimTableColumnList = modelDimTableColumnRepository.findAllByDimTableId(dimTableId);
        return ModelDimTableColumnMapper.INSTANCE.of(modelDimTableColumnList);
    }

    @Override
    public void deleteByDimTableId(Long dimTableId) {
        modelDimTableColumnRepository.deleteByDimTableId(dimTableId);
    }
}
