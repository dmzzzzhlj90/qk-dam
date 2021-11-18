package com.qk.dm.dataquality.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataquality.entity.DqcRuleDir;
import com.qk.dm.dataquality.entity.QDqcRuleDir;
import com.qk.dm.dataquality.mapstruct.mapper.DqcRuleDirTreeMapper;
import com.qk.dm.dataquality.repositories.DqcRuleDirRepository;
import com.qk.dm.dataquality.service.DqcRuleDirService;
import com.qk.dm.dataquality.vo.DqcRuleDirTreeVO;
import com.qk.dm.dataquality.vo.DqcRuleDirVO;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    public DqcRuleDirServiceImpl(DqcRuleDirRepository dqcRuleDirRepository) {
        this.dqcRuleDirRepository = dqcRuleDirRepository;
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
        DqcRuleDirTreeVO topParent = DqcRuleDirTreeVO.builder().id(-1).ruleDirId("-1").ruleDirName("全部规则").build();
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
            if (treeNode.getRuleDirId().equals(dqcRuleDirTreeVO.getParentId())) {
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

        Predicate predicate = qDqcRuleDir.ruleDirName.eq(dqcRuleDir.getRuleDirName());
        boolean exists = dqcRuleDirRepository.exists(predicate);
        if (exists) {
            throw new BizException("当前要新增的规则分类目录名称为:" + dqcRuleDirVO.getRuleDirName() + " 的数据，已存在！！！");
        }
        dqcRuleDirRepository.save(dqcRuleDir);
    }

    @Override
    public void update(DqcRuleDirVO dqcRuleDirVO) {
        DqcRuleDir dqcRuleDir = DqcRuleDirTreeMapper.INSTANCE.useDqcRuleDir(dqcRuleDirVO);
        dqcRuleDir.setGmtModified(new Date());
        Predicate predicate = qDqcRuleDir.ruleDirId.eq(dqcRuleDirVO.getRuleDirId());
        final Optional<DqcRuleDir> dsdDirOptional = dqcRuleDirRepository.findOne(predicate);
        if (dsdDirOptional.isPresent()) {
            String ruleDirId = dsdDirOptional.get().getRuleDirId();
            if (ruleDirId.equals(dqcRuleDirVO.getRuleDirId())) {
                throw new BizException("当前要编辑的规则分类目录名称为:" + dqcRuleDir.getRuleDirName() + ", 的数据，已存在！！！");
            }
            dqcRuleDirRepository.saveAndFlush(dqcRuleDir);
            //TODO 修改目录后影响的列表信息
//            dsdBasicinfoRepository.updateDirLevelByDirId(dsdDirVO.getDsdDirLevel(), dsdDirVO.getDirDsdId());
        } else {
            throw new BizException("当前要编辑的规则分类目录名称为:" + dqcRuleDir.getRuleDirName() + " 的数据，不存在！！！");
        }
    }

    @Override
    public void deleteOne(Long delId) {
        Optional<DqcRuleDir> dirOptional = dqcRuleDirRepository.findById(delId);
        if (!dirOptional.isPresent()) {
            throw new BizException("参数有误,当前要删除的节点不存在！！！");
        }

        Predicate predicate = qDqcRuleDir.parentId.eq(dirOptional.get().getRuleDirId());
        long count = dqcRuleDirRepository.count(predicate);
        if (count > 0) {
            throw new BizException("当前要删除的数据下存在子节点信息，请勿删除！！！");
        } else {
            dqcRuleDirRepository.deleteById(delId);
        }
    }

    @Override
    public void deleteBulk(Long delId) {
        ArrayList<Long> ids = new ArrayList<>();
        // 删除父级ID
        Optional<DqcRuleDir> dsdDirIsExist = dqcRuleDirRepository.findOne(qDqcRuleDir.id.eq(delId));
        if (!dsdDirIsExist.isPresent()) {
            throw new BizException("参数有误,当前要删除的节点不存在！！！");
        }
        ids.add(delId);
        getIds(ids, delId);
        // 批量删除
        Iterable<DqcRuleDir> delDirList = dqcRuleDirRepository.findAll(qDqcRuleDir.id.in(ids));
        dqcRuleDirRepository.deleteAll(delDirList);
    }

    /**
     * 获取删除叶子节点ID
     *
     * @param ids,delId
     */
    private void getIds(ArrayList<Long> ids, Long delId) {
        Optional<DqcRuleDir> parentDir = dqcRuleDirRepository.findOne(qDqcRuleDir.id.eq(delId));
        Iterable<DqcRuleDir> sonDirList = dqcRuleDirRepository.findAll(qDqcRuleDir.parentId.eq(parentDir.get().getRuleDirId()));
        for (DqcRuleDir dqcRuleDir : sonDirList) {
            ids.add(dqcRuleDir.getId());
            this.getIds(ids, dqcRuleDir.getId());
        }
    }
}
