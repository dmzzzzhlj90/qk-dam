package com.qk.dm.datamodel.service.impl;

import com.qk.dm.datamodel.entity.Model;
import com.qk.dm.datamodel.mapstruct.mapper.ModelMapper;
import com.qk.dm.datamodel.params.dto.ModelDTO;
import com.qk.dm.datamodel.params.vo.ModelVO;
import com.qk.dm.datamodel.repositories.ModelRepository;
import com.qk.dm.datamodel.service.ModelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {
    private final ModelRepository modelRepository;

    public ModelServiceImpl(ModelRepository modelRepository){
        this.modelRepository = modelRepository;
    }


    @Override
    public void insert(ModelDTO modelDTO) {
        Model model = ModelMapper.INSTANCE.useModel(modelDTO);
        modelRepository.save(model);
    }

    @Override
    public List<ModelVO> getList() {
       return ModelMapper.INSTANCE.userModelVO(modelRepository.findAll());
    }

}
