package com.qk.dm.datastandards.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datastandards.constant.DsdConstant;
import com.qk.dm.datastandards.entity.DsdBasicinfo;
import com.qk.dm.datastandards.entity.DsdDir;
import com.qk.dm.datastandards.entity.QDsdDir;
import com.qk.dm.datastandards.mapstruct.mapper.DsdDirTreeMapper;
import com.qk.dm.datastandards.repositories.DsdBasicinfoRepository;
import com.qk.dm.datastandards.repositories.DsdDirRepository;
import com.qk.dm.datastandards.service.DataStandardDirService;
import com.qk.dm.datastandards.vo.DataStandardTreeVO;
import com.qk.dm.datastandards.vo.DsdDirVO;
import com.querydsl.core.types.Predicate;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author wjq
 * @date 20210603
 * @since 1.0.0 数据标准目录接口实现类
 */
@Service
public class DataStandardDirServiceImpl implements DataStandardDirService {
    private final DsdDirRepository dsdDirRepository;
    private final DsdBasicinfoRepository dsdBasicinfoRepository;

    @Autowired
    public DataStandardDirServiceImpl(DsdDirRepository dsdDirRepository, DsdBasicinfoRepository dsdBasicinfoRepository) {
        this.dsdDirRepository = dsdDirRepository;
        this.dsdBasicinfoRepository = dsdBasicinfoRepository;
    }

    @Override
    public List<DataStandardTreeVO> getTree() {
        Predicate predicate = QDsdDir.dsdDir.delFlag.eq(0);
        List<DsdDir> dsdDirList = (List<DsdDir>) dsdDirRepository.findAll(predicate);
        List<DataStandardTreeVO> respList = new ArrayList<>();
        for (DsdDir dsdDir : dsdDirList) {
            DataStandardTreeVO dirTreeVO = DsdDirTreeMapper.INSTANCE.useDirTreeVO(dsdDir);
            respList.add(dirTreeVO);
        }
        return buildByRecursive(respList);
    }

    @Override
    public void addDsdDir(DsdDirVO dsdDirVO) {
        dsdDirVO.setDirDsdId(UUID.randomUUID().toString().replaceAll("-", ""));
        DsdDir dsdDir = DsdDirTreeMapper.INSTANCE.useDsdDir(dsdDirVO);
        dsdDir.setGmtCreate(new Date());
        Predicate predicate = QDsdDir.dsdDir.dirDsdId.eq(dsdDir.getDirDsdId());
        boolean exists = dsdDirRepository.exists(predicate);
        if (exists) {
            throw new BizException(
                    "当前要新增的数据分类ID为："
                            + dsdDir.getDirDsdId()
                            + "数据标准分类名称为:"
                            + dsdDir.getDirDsdName()
                            + " 的数据，已存在！！！");
        }
        dsdDirRepository.save(dsdDir);
    }

    @Override
    public void updateDsdDir(DsdDirVO dsdDirVO) {
        DsdDir dsdDir = DsdDirTreeMapper.INSTANCE.useDsdDir(dsdDirVO);
        dsdDir.setGmtModified(new Date());
        Predicate predicate = QDsdDir.dsdDir.dirDsdId.eq(dsdDir.getDirDsdId());
        boolean exists = dsdDirRepository.exists(predicate);
        if (exists) {
            dsdDirRepository.saveAndFlush(dsdDir);
        } else {
            throw new BizException(
                    "当前要编辑的数据分类ID为："
                            + dsdDir.getDirDsdId()
                            + "数据标准分类名称为:"
                            + dsdDir.getDirDsdName()
                            + " 的数据，不已存在！！！");
        }
    }

    @Override
    public void deleteDsdDir(Integer delId) {
        Optional<DsdDir> dirOptional = dsdDirRepository.findById(delId);
        if (!dirOptional.isPresent()) {
            throw new BizException("参数有误,当前要删除的节点不存在！！！");
        }

        Predicate predicate = QDsdDir.dsdDir.parentId.eq(dirOptional.get().getDirDsdId());
        long count = dsdDirRepository.count(predicate);
        if (count > 0) {
            throw new BizException("当前要删除的数据下存在子节点信息，请勿删除！！！");
        } else {
            dsdDirRepository.deleteById(delId);
        }
    }

    /**
     * @param: respList
     * @return: 使用递归方法建树
     */
    public static List<DataStandardTreeVO> buildByRecursive(List<DataStandardTreeVO> respList) {
        List<DataStandardTreeVO> trees = new ArrayList<DataStandardTreeVO>();
        for (DataStandardTreeVO treeNode : respList) {
            if (null == treeNode.getParentId()
                    || DsdConstant.TREE_DIR_TOP_PARENT_ID.equals(treeNode.getParentId())) {
                trees.add(findChildren(treeNode, respList));
            }
        }
        return trees;
    }

    /**
     * @param: treeNode, respList
     * @return: 递归查找子节点
     */
    public static DataStandardTreeVO findChildren(
            DataStandardTreeVO treeNode, List<DataStandardTreeVO> respList) {
        treeNode.setChildren(new ArrayList<DataStandardTreeVO>());
        for (DataStandardTreeVO it : respList) {
            if (treeNode.getDirDsdId().equals(it.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<DataStandardTreeVO>());
                }
                treeNode.getChildren().add(findChildren(it, respList));
            }
        }
        return treeNode;
    }

    @Override
    public void deleteDsdDirRoot(Integer delId) {
        ArrayList<Integer> ids = new ArrayList<>();
        // 删除父级ID
        Optional<DsdDir> dsdDirIsExist = dsdDirRepository.findOne(QDsdDir.dsdDir.id.eq(delId));
        if (!dsdDirIsExist.isPresent()) {
            throw new BizException("参数有误,当前要删除的节点不存在！！！");
        }
        ids.add(delId);
        getIds(ids, delId);
        // 批量删除
        Iterable<DsdDir> delDirList = dsdDirRepository.findAll(QDsdDir.dsdDir.id.in(ids));
        dsdDirRepository.deleteAll(delDirList);
    }

    /**
     * @param: ids, delId
     * @return: 获取删除叶子节点ID
     */
    private void getIds(ArrayList<Integer> ids, Integer delId) {
        Optional<DsdDir> parentDir = dsdDirRepository.findOne(QDsdDir.dsdDir.id.eq(delId));
        Iterable<DsdDir> sonDirList =
                dsdDirRepository.findAll(QDsdDir.dsdDir.parentId.eq(parentDir.get().getDirDsdId()));
        for (DsdDir dsdDir : sonDirList) {
            ids.add(dsdDir.getId());
            this.getIds(ids, dsdDir.getId());
        }
    }


    /**
     * 根据目录id获取目录下的所有节点id
     *
     * @param dirDsdIdSet
     * @param dirDsdId
     */
    @Override
    public void getDsdId(Set<String> dirDsdIdSet, String dirDsdId) {
        Optional<DsdDir> parentDir = dsdDirRepository.findOne(QDsdDir.dsdDir.dirDsdId.eq(dirDsdId));
        if (parentDir.isPresent()) {
            dirDsdIdSet.add(parentDir.get().getDirDsdId());
            Iterable<DsdDir> sonDirList =
                    dsdDirRepository.findAll(QDsdDir.dsdDir.parentId.eq(parentDir.get().getDirDsdId()));
            for (DsdDir dsdDir : sonDirList) {
                dirDsdIdSet.add(dsdDir.getDirDsdId());
                this.getDsdId(dirDsdIdSet, dsdDir.getDirDsdId());
            }
        }
    }

}
