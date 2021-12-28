package com.qk.dm.dataquality.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataquality.entity.DqcRuleDir;
import com.qk.dm.dataquality.entity.DqcRuleTemplate;
import com.qk.dm.dataquality.entity.QDqcRuleDir;
import com.qk.dm.dataquality.entity.QDqcRuleTemplate;
import com.qk.dm.dataquality.mapstruct.mapper.DqcRuleDirTreeMapper;
import com.qk.dm.dataquality.repositories.DqcRuleDirRepository;
import com.qk.dm.dataquality.repositories.DqcRuleTemplateRepository;
import com.qk.dm.dataquality.service.DqcRuleDirService;
import com.qk.dm.dataquality.vo.DqcRuleDirTreeVO;
import com.qk.dm.dataquality.vo.DqcRuleDirVO;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据质量_规则分类目录
 *
 * @author wjq
 * @date 2021/11/08
 * @since 1.0.0
 */
@Service
public class DqcRuleDirServiceImpl implements DqcRuleDirService {
    private final QDqcRuleDir qDqcRuleDir = QDqcRuleDir.dqcRuleDir;

    private final DqcRuleDirRepository dqcRuleDirRepository;
    private final DqcRuleTemplateRepository dqcRuleTemplateRepository;

    @Autowired
    public DqcRuleDirServiceImpl(DqcRuleDirRepository dqcRuleDirRepository, DqcRuleTemplateRepository dqcRuleTemplateRepository) {
        this.dqcRuleDirRepository = dqcRuleDirRepository;
        this.dqcRuleTemplateRepository = dqcRuleTemplateRepository;
    }


    @Override
    public List<DqcRuleDirTreeVO> searchList() {
        Predicate predicate = QDqcRuleDir.dqcRuleDir.delFlag.eq(0);
        List<DqcRuleDir> dqcRuleDirList = (List<DqcRuleDir>) dqcRuleDirRepository.findAll(predicate);
        List<DqcRuleDirTreeVO> respList = new ArrayList<>();
        for (DqcRuleDir dqcRuleDir : dqcRuleDirList) {
            DqcRuleDirTreeVO dirTreeVO = DqcRuleDirTreeMapper.INSTANCE.useDqcRuleDirTreeVO(dqcRuleDir);
            respList.add(dirTreeVO);
        }
        return buildByRecursive(respList);
    }

    public static List<DqcRuleDirTreeVO> buildByRecursive(List<DqcRuleDirTreeVO> respList) {
        DqcRuleDirTreeVO topParent = DqcRuleDirTreeVO.builder().dirId("-1").title("全部规则").value("全部规则").parentId("-1").build();
        List<DqcRuleDirTreeVO> trees = new ArrayList<>();
        trees.add(findChildren(topParent, respList));

        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNode,respList
     * @return DqcRuleDirTreeVO
     */
    public static DqcRuleDirTreeVO findChildren(DqcRuleDirTreeVO treeNode, List<DqcRuleDirTreeVO> respList) {
        treeNode.setChildren(new ArrayList<>());
        for (DqcRuleDirTreeVO dqcRuleDirTreeVO : respList) {
            if (treeNode.getDirId().equals(dqcRuleDirTreeVO.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(dqcRuleDirTreeVO, respList));
            }
        }
        return treeNode;
    }

    @Override
    public void insert(DqcRuleDirVO dqcRuleDirVO) {
        DqcRuleDir dqcRuleDir = DqcRuleDirTreeMapper.INSTANCE.useDqcRuleDir(dqcRuleDirVO);
        dqcRuleDir.setGmtCreate(new Date());
        dqcRuleDir.setGmtModified(new Date());
        dqcRuleDir.setRuleDirId(UUID.randomUUID().toString().replaceAll("-", ""));
        dqcRuleDir.setDelFlag(0);

        Predicate predicate = qDqcRuleDir.ruleDirName.eq(dqcRuleDirVO.getTitle()).and(qDqcRuleDir.parentId.eq(dqcRuleDirVO.getParentId()));
        boolean exists = dqcRuleDirRepository.exists(predicate);
        if (exists) {
            throw new BizException("当前要新增的规则分类目录名称为:" + dqcRuleDirVO.getTitle() + " 的数据，在本层级下已存在！！！");
        }
        dqcRuleDirRepository.save(dqcRuleDir);
    }

    @Override
    public void update(DqcRuleDirVO dqcRuleDirVO) {
        //校验目录dirId是否与ParentId相等
        checkDirIdIsEqualParentId(dqcRuleDirVO);

        //校验目录父节点是否放到其子节点层级下
        checkParentNodeAndChildNode(dqcRuleDirVO);

        DqcRuleDir dqcRuleDir = DqcRuleDirTreeMapper.INSTANCE.useDqcRuleDir(dqcRuleDirVO);
        dqcRuleDir.setGmtModified(new Date());
        dqcRuleDir.setDelFlag(0);

        Optional<DqcRuleDir> dsdDirOptional = dqcRuleDirRepository.findById(dqcRuleDirVO.getId());
        if (dsdDirOptional.isPresent()) {
            dqcRuleDirRepository.saveAndFlush(dqcRuleDir);
            //TODO 修改目录后影响的列表信息
//            dsdBasicinfoRepository.updateDirLevelByDirId(dsdDirVO.getDsdDirLevel(), dsdDirVO.getDirDsdId());
        } else {
            throw new BizException("当前要编辑的规则分类目录名称为:" + dqcRuleDirVO.getTitle() + " 的数据，不存在！！！");
        }
    }

    private void checkDirIdIsEqualParentId(DqcRuleDirVO dqcRuleDirVO) {
        String dirId = dqcRuleDirVO.getDirId();
        String parentId = dqcRuleDirVO.getParentId();
        if (dirId.equals(parentId)) {
            throw new BizException("当前要编辑的规则分类目录,不能选择本节点作为父节点！！！");
        }
    }

    private void checkParentNodeAndChildNode(DqcRuleDirVO dqcRuleDirVO) {
        Predicate predicate = QDqcRuleDir.dqcRuleDir.delFlag.eq(0);
        List<DqcRuleDir> dqcRuleDirList = (List<DqcRuleDir>) dqcRuleDirRepository.findAll(predicate);
        List<DqcRuleDir> childDirList = new ArrayList<>();

        getDirIdsByParentId(dqcRuleDirList, dqcRuleDirVO.getDirId(), childDirList);

        List<String> childDirIds = childDirList.stream().map(DqcRuleDir::getRuleDirId).collect(Collectors.toList());
        if (childDirIds.contains(dqcRuleDirVO.getParentId())) {
            throw new BizException("当前要编辑的规则分类目录,不能选择自身子节点作为父节点！！！");
        }
    }

    private void getDirIdsByParentId(List<DqcRuleDir> dqcRuleDirList, String dirId, List<DqcRuleDir> childDirIds) {
        for (DqcRuleDir dqcRuleDir : dqcRuleDirList) {
            if (dqcRuleDir.getParentId() != null) {
                if (dqcRuleDir.getParentId().equals(dirId)) {
                    getDirIdsByParentId(dqcRuleDirList, dqcRuleDir.getRuleDirId(), childDirIds);
                    childDirIds.add(dqcRuleDir);
                }
            }
        }
    }

    @Override
    public void delete(String id) {
        ArrayList<Long> ids = new ArrayList<>();
        List<DqcRuleDir> childDirList = new ArrayList<>();
        List<DqcRuleDir> dirIsExistRulesList = new ArrayList<>();

        Long delId = Long.valueOf(id);
        // 删除父级ID
        Predicate predicate = QDqcRuleDir.dqcRuleDir.delFlag.eq(0);
        List<DqcRuleDir> dqcRuleDirList = (List<DqcRuleDir>) dqcRuleDirRepository.findAll(predicate);
        List<DqcRuleDir> dsdDirIsExist = dqcRuleDirList.stream().filter(dqcRuleDir -> dqcRuleDir.getId().equals(delId)).collect(Collectors.toList());
        if (dsdDirIsExist.size() < 1) {
            throw new BizException("参数有误,当前要删除的节点不存在！！！");
        }

        //级联所以子节点
        DqcRuleDir dqcRuleDir = dsdDirIsExist.get(0);
        ids.add(delId);
        getDirIdsByParentId(dqcRuleDirList, dqcRuleDir.getRuleDirId(), childDirList);
        childDirList.forEach((ruleDir) -> ids.add(ruleDir.getId()));

        //校验是否存在规则模板数据
        Iterable<DqcRuleDir> delDirList = dqcRuleDirRepository.findAll(qDqcRuleDir.id.in(ids));
        for (DqcRuleDir ruleDir : delDirList) {
            dirIsExistRulesList.add(ruleDir);
        }
        deleteCheckIsRules(dirIsExistRulesList);
        // 级联删除
        dqcRuleDirRepository.deleteAll(delDirList);
    }

    @Override
    public void deleteBulk(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        Set<Long> idSet = new HashSet<>();
        idList.forEach(id -> idSet.add(Long.valueOf(id)));
        List<DqcRuleDir> dqcRuleDirList = dqcRuleDirRepository.findAllById(idSet);

        //校验是否存在规则模板数据
        deleteCheckIsRules(dqcRuleDirList);
        // 批量删除
        dqcRuleDirRepository.deleteAll(dqcRuleDirList);

    }

    private void deleteCheckIsRules(List<DqcRuleDir> dirIsExistRulesList) {
        List<String> dirIdList = dirIsExistRulesList.stream().map(DqcRuleDir::getRuleDirId).collect(Collectors.toList());
        Iterable<DqcRuleTemplate> ruleTemplates = dqcRuleTemplateRepository.findAll(QDqcRuleTemplate.dqcRuleTemplate.dirId.in(dirIdList));

        if (ruleTemplates.iterator().hasNext()) {
            throw new BizException("当前要删除的规则分类目录,存在规则模板数据信息！！！");
        }
    }

    @Override
    public List<DqcRuleDir> getListByDirIds(Set<String> dirIds) {
        return (List<DqcRuleDir>) dqcRuleDirRepository.findAll(qDqcRuleDir.ruleDirId.in(dirIds));
    }
}
