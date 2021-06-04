package com.qk.dm.datastandards.service.impl;

import com.qk.commons.exception.BizException;
import com.qk.dm.datastandards.entity.DsdDir;
import com.qk.dm.datastandards.entity.QDsdDir;
import com.qk.dm.datastandards.mapstruct.mapper.DsdDirTreeMapper;
import com.qk.dm.datastandards.repositories.DsdDirRepository;
import com.qk.dm.datastandards.service.DataStandardDirService;
import com.qk.dm.datastandards.vo.DataStandardTreeVO;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author wjq
 * @date 20210603
 * @since 1.0.0
 * 数据标准__目录接口实现类
 */
@Service
public class DataStandardDirServiceImpl implements DataStandardDirService {
    private final DsdDirRepository dsdDirRepository;

    public DataStandardDirServiceImpl(DsdDirRepository dsdDirRepository) {
        this.dsdDirRepository = dsdDirRepository;
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
    public void addDsdDir(DsdDir dsdDir) {
        Predicate predicate = QDsdDir.dsdDir.dirDsdId.eq(dsdDir.getDirDsdId());
        Optional<DsdDir> dsdDirIsExist = dsdDirRepository.findOne(predicate);
        if (dsdDirIsExist.isPresent()) {
            throw new BizException("当前要新增的数据分类ID为：" + dsdDirIsExist.get().getDirDsdId()
                    + "数据标准分类名称为:" + dsdDirIsExist.get().getDirDsdName() + " 的数据，已存在！！！");
        }
        dsdDirRepository.save(dsdDir);
    }


    @Override
    public void updateDsdDir(DsdDir dsdDir) {
        dsdDirRepository.saveAndFlush(dsdDir);
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
     * @Param: respList
     * @return: java.util.List<com.qk.dm.datastandards.vo.DataStandardTreeResp>
     * 使用递归方法建树
     **/
    public static List<DataStandardTreeVO> buildByRecursive(List<DataStandardTreeVO> respList) {
        List<DataStandardTreeVO> trees = new ArrayList<DataStandardTreeVO>();
        for (DataStandardTreeVO treeNode : respList) {
            if (null == treeNode.getParentId() || 0 > treeNode.getParentId()) {
                trees.add(findChildren(treeNode, respList));
            }
        }
        return trees;
    }

    /**
     * @Param: treeNode, respList
     * @return: com.qk.dm.datastandards.vo.DataStandardTreeResp
     * 递归查找子节点
     **/
    public static DataStandardTreeVO findChildren(DataStandardTreeVO treeNode, List<DataStandardTreeVO> respList) {
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
        //删除父级ID
        Optional<DsdDir> dsdDirIsExist = dsdDirRepository.findOne(QDsdDir.dsdDir.id.eq(delId));
        if (!dsdDirIsExist.isPresent()) {
            throw new BizException("参数有误,当前要删除的节点不存在！！！");
        }
        ids.add(delId);
        getIds(ids, delId);
        //批量删除
        Iterable<DsdDir> delDirList = dsdDirRepository.findAll(QDsdDir.dsdDir.id.in(ids));
        dsdDirRepository.deleteAll(delDirList);
    }

    /**
     * @Param: ids, delId
     * @return: void
     * 获取删除叶子节点ID
     **/
    private void getIds(ArrayList<Integer> ids, Integer delId) {
        Optional<DsdDir> parentDir = dsdDirRepository.findOne(QDsdDir.dsdDir.id.eq(delId));
        Iterable<DsdDir> sonDirList = dsdDirRepository.findAll(QDsdDir.dsdDir.parentId.eq(parentDir.get().getDirDsdId()));
        for (DsdDir dsdDir : sonDirList) {
            ids.add(dsdDir.getId());
            this.getIds(ids, dsdDir.getId());
        }
    }

}
