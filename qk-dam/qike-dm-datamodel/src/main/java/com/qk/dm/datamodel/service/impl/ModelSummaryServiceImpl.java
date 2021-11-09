package com.qk.dm.datamodel.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.entity.ModelSummary;
import com.qk.dm.datamodel.mapstruct.mapper.ModelSummaryMapper;
import com.qk.dm.datamodel.params.dto.ModelSummaryDTO;
import com.qk.dm.datamodel.params.vo.ModelSummaryVO;
import com.qk.dm.datamodel.repositories.ModelSummaryRepository;
import com.qk.dm.datamodel.service.ModelSummaryService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ModelSummaryServiceImpl implements ModelSummaryService {

    private final ModelSummaryRepository modelSummaryRepository;

    public ModelSummaryServiceImpl(ModelSummaryRepository modelSummaryRepository){
        this.modelSummaryRepository = modelSummaryRepository;
    }

    @Override
    public void insert(ModelSummaryDTO modelSummaryDTO) {
        ModelSummary modelSummary = ModelSummaryMapper.INSTANCE.of(modelSummaryDTO);
        modelSummary.setGmtCreate(new Date());
        modelSummary.setGmtModified(new Date());
        modelSummaryRepository.save(modelSummary);
    }

    @Override
    public ModelSummaryVO detail(Long id) {
        Optional<ModelSummary> modelSummary = modelSummaryRepository.findById(id);
        if(modelSummary.isEmpty()){
            throw new BizException("当前查询的汇总表 id为"+id+"的数据不存在");
        }
        return ModelSummaryMapper.INSTANCE.of(modelSummary.get());
    }

    @Override
    public void update(Long id, ModelSummaryDTO modelSummaryDTO) {
        modelSummaryRepository.findById(id);
    }

    @Override
    public void delete(String ids) {

    }

    @Override
    public PageResultVO<ModelSummaryVO> listPage(ModelSummaryDTO modelSummaryDTO) {
        return null;
    }
}
