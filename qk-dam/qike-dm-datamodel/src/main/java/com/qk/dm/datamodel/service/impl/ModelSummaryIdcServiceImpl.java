package com.qk.dm.datamodel.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datamodel.entity.ModelSummary;
import com.qk.dm.datamodel.entity.ModelSummaryIdc;
import com.qk.dm.datamodel.mapstruct.mapper.ModelSummaryIdcMapper;
import com.qk.dm.datamodel.params.dto.ModelSummaryIdcDTO;
import com.qk.dm.datamodel.params.vo.ModelSummaryIdcVO;
import com.qk.dm.datamodel.repositories.ModelSummaryIdcRepository;
import com.qk.dm.datamodel.repositories.ModelSummaryRepository;
import com.qk.dm.datamodel.service.ModelSummaryIdcService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ModelSummaryIdcServiceImpl implements ModelSummaryIdcService {

    private final ModelSummaryIdcRepository modelSummaryIdcRepository;
    private final ModelSummaryRepository modelSummaryRepository;

    public ModelSummaryIdcServiceImpl(ModelSummaryIdcRepository modelSummaryIdcRepository,
                                      ModelSummaryRepository modelSummaryRepository){
        this.modelSummaryIdcRepository = modelSummaryIdcRepository;
        this.modelSummaryRepository = modelSummaryRepository;
    }

    @Override
    public void insert(List<ModelSummaryIdcDTO> modelSummaryIdcDTOList) {
        List<ModelSummaryIdc> modelSummaryIdcList = ModelSummaryIdcMapper.INSTANCE.use(modelSummaryIdcDTOList);
        modelSummaryIdcRepository.saveAll(modelSummaryIdcList);
    }

    @Override
    public ModelSummaryIdcVO detail(Long id) {
        Optional<ModelSummaryIdc> modelSummaryIdc = modelSummaryIdcRepository.findById(id);
        if(modelSummaryIdc.isEmpty()){
            throw new BizException("当前查询的汇总指标id"+id+"的信息不存在！！！");
        }
        return ModelSummaryIdcMapper.INSTANCE.of(modelSummaryIdc.get()) ;
    }

    @Override
    public void update(Long summaryId, List<ModelSummaryIdcDTO> modelSummaryIdcDTOList) {
        Optional<ModelSummary> modelSummary = modelSummaryRepository.findById(summaryId);
        if(modelSummary.isEmpty()){
            throw new BizException("当前查询的汇总信息id"+summaryId+"的信息不存在！！！");
        }
        modelSummaryIdcRepository.deleteBySummaryId(summaryId);
        List<ModelSummaryIdc> modelSummaryIdcList = ModelSummaryIdcMapper.INSTANCE.use(modelSummaryIdcDTOList);
        modelSummaryIdcRepository.saveAllAndFlush(modelSummaryIdcList);
    }

    @Override
    public void delete(String ids) {
        Iterable<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<ModelSummaryIdc> modelSummaryIdcList = modelSummaryIdcRepository.findAllById(idSet);
        if(modelSummaryIdcList.isEmpty()){
            throw new BizException("当前要操作的维度表指标id为："+ids+"的数据不存在！！！");
        }
        modelSummaryIdcRepository.deleteAll(modelSummaryIdcList);
    }

    @Override
    public List<ModelSummaryIdcVO> list(Long summaryId) {
        List<ModelSummaryIdc> modelSummaryIdcList = modelSummaryIdcRepository.findAllBySummaryId(summaryId);
        return ModelSummaryIdcMapper.INSTANCE.of(modelSummaryIdcList);
    }

}
