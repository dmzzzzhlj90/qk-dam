package com.qk.dm.datamodel.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datamodel.entity.ModelDim;
import com.qk.dm.datamodel.entity.ModelDimColumn;
import com.qk.dm.datamodel.mapstruct.mapper.ModelDimColumnMapper;
import com.qk.dm.datamodel.params.dto.ModelDimColumnDTO;
import com.qk.dm.datamodel.params.vo.ModelDimColumnVO;
import com.qk.dm.datamodel.repositories.ModelDimColumnRepository;
import com.qk.dm.datamodel.repositories.ModelDimRepository;
import com.qk.dm.datamodel.service.ModelDimColumnService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 维度字段
 * @author wangzp
 * @date 2021/11/08 15:17
 * @since 1.0.0
 */
@Service
public class ModelDimColumnServiceImpl implements ModelDimColumnService {

    private final ModelDimColumnRepository modelDimColumnRepository;

    private final ModelDimRepository modelDimRepository;

    public ModelDimColumnServiceImpl(ModelDimColumnRepository modelDimColumnRepository,
                                     ModelDimRepository modelDimRepository){
        this.modelDimColumnRepository = modelDimColumnRepository;
        this.modelDimRepository = modelDimRepository;
    }

    @Override
    public void insert(List<ModelDimColumnDTO> modelDimColumnDTOList,Long dimId) {
        List<ModelDimColumn> modelDimColumnList = ModelDimColumnMapper.INSTANCE.use(modelDimColumnDTOList);
        modelDimColumnList.forEach(e->e.setDimId(dimId));
        modelDimColumnRepository.saveAll(modelDimColumnList);
    }

    @Override
    public void update(Long dimId, List<ModelDimColumnDTO> modelPhysicalColumnDTOList) {
        ModelDim modelDim = modelDimRepository.findById(dimId).orElse(null);
        if(Objects.isNull(modelDim)){
            throw new BizException("当前要修改的维度id为："+dimId+"的数据不存在！！！");
        }
        modelDimColumnRepository.deleteByDimId(dimId);
        List<ModelDimColumn> modelDimColumnList = ModelDimColumnMapper.INSTANCE.use(modelPhysicalColumnDTOList);
        modelDimColumnList.forEach(e->e.setDimId(dimId));
        modelDimColumnRepository.saveAllAndFlush(modelDimColumnList);
    }

    @Override
    public void delete(String dmIds) {
        Arrays.stream(dmIds.split(",")).forEach(e->{modelDimColumnRepository.deleteByDimId(Long.valueOf(e));});
    }

    @Override
    public List<ModelDimColumnVO> list(Long dimId) {
        List<ModelDimColumn> modelDimColumnList = modelDimColumnRepository.findAllByDimId(dimId);

       return ModelDimColumnMapper.INSTANCE.of(modelDimColumnList);
    }
}
