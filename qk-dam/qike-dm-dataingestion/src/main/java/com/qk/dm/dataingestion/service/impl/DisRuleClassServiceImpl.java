package com.qk.dm.dataingestion.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataingestion.entity.DisRuleClassification;
import com.qk.dm.dataingestion.entity.QDisRuleClassification;
import com.qk.dm.dataingestion.mapstruct.mapper.DisRuleClassMapper;
import com.qk.dm.dataingestion.repositories.DisRuleClassificationRepository;
import com.qk.dm.dataingestion.service.DisRuleClassService;
import com.qk.dm.dataingestion.vo.DisRuleClassVO;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DisRuleClassServiceImpl implements DisRuleClassService {

    private final QDisRuleClassification qDisRuleClassification = QDisRuleClassification.disRuleClassification;
    private final DisRuleClassificationRepository disRuleClassificationRepository;

    public DisRuleClassServiceImpl(DisRuleClassificationRepository disRuleClassificationRepository) {
        this.disRuleClassificationRepository = disRuleClassificationRepository;
    }

    @Override
    public List<DisRuleClassVO> list() {

        List<DisRuleClassVO> list = DisRuleClassMapper.INSTANCE.list((List<DisRuleClassification>) disRuleClassificationRepository.findAll(
                qDisRuleClassification.delFlag.eq(0)));
        List<DisRuleClassVO> childrenList = list.stream()
                .filter(e -> Objects.equals(e.getParentId(),  "-1"))
                .peek(e -> e.setChildren(getChildren(e, list)))
                .collect(Collectors.toList());
        return List.of(DisRuleClassVO.builder().dirId("-1").parentId("-1")
                .title("跟目录").value("根目录").children(childrenList).build());
    }

    @Override
    public void insert(DisRuleClassVO vo) {
        Predicate predicate = qDisRuleClassification.dirId.eq(vo.getTitle())
                .and(qDisRuleClassification.parentId.eq(vo.getParentId()));
        boolean exists = disRuleClassificationRepository.exists(predicate);
        if (exists) {
            throw new BizException("当前信添加的规则目录名称:" + vo.getTitle() + " 的数据，在本层级下已存在！！！");
        }
        DisRuleClassification disRuleClassification = DisRuleClassMapper.INSTANCE.of(vo);
        disRuleClassification.setDirId(UUID.randomUUID().toString().replaceAll("-", ""));
        disRuleClassificationRepository.save(disRuleClassification);
    }

    @Override
    public void update(DisRuleClassVO vo) {
        DisRuleClassification disRuleClass = disRuleClassificationRepository.findById(vo.getId()).orElse(null);
        if(Objects.isNull(disRuleClass)){
            throw new BizException("当前要修改的规则目录名称:" + vo.getTitle() + " 的数据，不存在！");
        }
        DisRuleClassMapper.INSTANCE.of(vo,disRuleClass);
        disRuleClassificationRepository.saveAndFlush(disRuleClass);
    }

    @Override
    public void delete(String id) {
       DisRuleClassification disRuleClassification = disRuleClassificationRepository.findById(Long.parseLong(id)).orElse(null);
       if(Objects.isNull(disRuleClassification)){
           throw new BizException("当前要删除的规则目录id:" + id + " 的数据，不存在！！！");
       }
        deleteChildren(disRuleClassification.getId(),disRuleClassification.getDirId());
    }

    private void deleteChildren(Long id,String parentId){
        disRuleClassificationRepository.deleteById(id);
       List<DisRuleClassification>  childrenList = disRuleClassificationRepository.findAllByParentId(parentId);
        if(CollectionUtils.isEmpty(childrenList)){
            return;
        }
        childrenList.forEach(e->deleteChildren(e.getId(),e.getDirId()));
    }


    private List<DisRuleClassVO> getChildren(DisRuleClassVO vo, List<DisRuleClassVO> all){
        return all.stream()
                .filter(e -> Objects.equals(e.getParentId(), vo.getDirId()))
                .peek((e) -> e.setChildren(getChildren(e, all)))
                .collect(Collectors.toList());
    }
}
