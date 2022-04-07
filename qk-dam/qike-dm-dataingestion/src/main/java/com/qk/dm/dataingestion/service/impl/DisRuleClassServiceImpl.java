package com.qk.dm.dataingestion.service.impl;

import com.qk.dm.dataingestion.entity.DisRuleClassification;
import com.qk.dm.dataingestion.entity.QDisRuleClassification;
import com.qk.dm.dataingestion.mapstruct.mapper.DisRuleClassMapper;
import com.qk.dm.dataingestion.repositories.DisRuleClassificationRepository;
import com.qk.dm.dataingestion.service.DisRuleClassService;
import com.qk.dm.dataingestion.vo.DisRuleClassVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DisRuleClassServiceImpl implements DisRuleClassService {

    private final QDisRuleClassification qDisRuleClassification = QDisRuleClassification.disRuleClassification;
    private final DisRuleClassificationRepository ddgRuleClassificationRepository;

    public DisRuleClassServiceImpl(DisRuleClassificationRepository ddgRuleClassificationRepository) {
        this.ddgRuleClassificationRepository = ddgRuleClassificationRepository;
    }

    @Override
    public List<DisRuleClassVO> list() {

        List<DisRuleClassVO> list = DisRuleClassMapper.INSTANCE.list((List<DisRuleClassification>) ddgRuleClassificationRepository.findAll(
                qDisRuleClassification.delFlag.eq(0)));

        return list.stream()
                .filter(e -> e.getParentId() == 0)
                .peek(e -> e.setChildren(getChildren(e, list)))
                .collect(Collectors.toList());
    }


    private List<DisRuleClassVO> getChildren(DisRuleClassVO vo, List<DisRuleClassVO> all){
        return all.stream()
                .filter(e -> Objects.equals(e.getParentId(), vo.getId()))
                .peek((e) -> e.setChildren(getChildren(e, all)))
                .collect(Collectors.toList());
    }
}
