package com.qk.dm.datamodel.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datamodel.entity.ModelFactColumn;
import com.qk.dm.datamodel.entity.ModelFactTable;
import com.qk.dm.datamodel.mapstruct.mapper.ModelFactColumnMapper;
import com.qk.dm.datamodel.params.dto.ModelFactColumnDTO;
import com.qk.dm.datamodel.params.vo.ModelFactColumnVO;
import com.qk.dm.datamodel.repositories.ModelFactColumnRepository;
import com.qk.dm.datamodel.repositories.ModelFactTableRepository;
import com.qk.dm.datamodel.service.ModelFactColumnService;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ModelFactColumnServiceImpl implements ModelFactColumnService {

    private final ModelFactColumnRepository modelFactColumnRepository;
    private final ModelFactTableRepository modelFactTableRepository;

    public ModelFactColumnServiceImpl(ModelFactColumnRepository modelFactColumnRepository,
                                      ModelFactTableRepository modelFactTableRepository){
        this.modelFactColumnRepository = modelFactColumnRepository;
        this.modelFactTableRepository = modelFactTableRepository;
    }
    @Override
    public void insert(List<ModelFactColumnDTO> modelFactColumnDTOList) {
        List<ModelFactColumn> modelFactColumnList = ModelFactColumnMapper.INSTANCE.use(modelFactColumnDTOList);
        modelFactColumnRepository.saveAll(modelFactColumnList);
    }

    @Override
    public void update(Long factId, List<ModelFactColumnDTO> modelFactColumnDTOList) {
        ModelFactTable modelFactTable = modelFactTableRepository.findById(factId).orElse(null);
        if(Objects.isNull(modelFactTable)){
            throw new BizException("当前要修改的事实表id为："+factId+"的数据不存在！！！");
        }
        modelFactColumnRepository.deleteByFactId(factId);
        List<ModelFactColumn> modelFactColumnList = ModelFactColumnMapper.INSTANCE.use(modelFactColumnDTOList);
        modelFactColumnRepository.saveAllAndFlush(modelFactColumnList);
    }

    @Override
    public void delete(String ids) {

    }

    @Override
    public List<ModelFactColumnVO> list(Long factId) {
        List<ModelFactColumn> modelFactColumnList = modelFactColumnRepository.findAllByFactId(factId);
        return ModelFactColumnMapper.INSTANCE.of(modelFactColumnList);
    }
}
