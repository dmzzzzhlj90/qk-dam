package com.qk.dm.datastandards.service.impl;


import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datastandards.entity.DsdCodeDir;
import com.qk.dm.datastandards.entity.QDsdCodeDir;
import com.qk.dm.datastandards.mapstruct.mapper.DsdCodeTreeMapper;
import com.qk.dm.datastandards.mapstruct.mapper.DsdDirCodeDirTreeMapper;
import com.qk.dm.datastandards.repositories.DsdCodeDirRepository;
import com.qk.dm.datastandards.service.DataStandardCodeDirService;
import com.qk.dm.datastandards.vo.DataStandardCodeTreeVO;
import com.qk.dm.datastandards.vo.DsdCodeDirVO;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.qk.dm.datastandards.entity.QDsdCodeDir.dsdCodeDir;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 * 数据标准目录接口实现类
 */
@Service
public class DataStandardCodeDirServiceImpl implements DataStandardCodeDirService {
    private final DsdCodeDirRepository dsdCodeDirRepository;

    public DataStandardCodeDirServiceImpl(DsdCodeDirRepository dsdCodeDirRepository) {
        this.dsdCodeDirRepository = dsdCodeDirRepository;
    }


    @Override
    public List<DataStandardCodeTreeVO> getTree() {
        Predicate predicate = dsdCodeDir.delFlag.eq(0);
        List<DsdCodeDir> dsdDirList = (List<DsdCodeDir>) dsdCodeDirRepository.findAll(predicate);
        List<DataStandardCodeTreeVO> respList = new ArrayList<>();
        for (DsdCodeDir dsdCodeDir : dsdDirList) {
            DataStandardCodeTreeVO codeTreeVO = DsdCodeTreeMapper.INSTANCE.useCodeTreeVO(dsdCodeDir);
            respList.add(codeTreeVO);
        }
        return buildByRecursive(respList);
    }

    @Override
    public void addDsdDir(DsdCodeDirVO dsdCodeDirVO) {
        DsdCodeDir dsdCodeDir = DsdDirCodeDirTreeMapper.INSTANCE.useDsdCodeDir(dsdCodeDirVO);
        Predicate predicate = QDsdCodeDir.dsdCodeDir.codeDirId.eq(dsdCodeDir.getCodeDirId());
        boolean exists = dsdCodeDirRepository.exists(predicate);
        if (exists) {
            throw new BizException("当前要新增的码表目录ID为：" + dsdCodeDir.getCodeDirId()
                    + "码表名称为:" + dsdCodeDir.getCodeDirName() + " 的数据，已存在！！！");
        }
        dsdCodeDirRepository.save(dsdCodeDir);
    }

    @Override
    public void updateDsdDir(DsdCodeDirVO dsdCodeDirVO) {
        DsdCodeDir dsdCodeDir = DsdDirCodeDirTreeMapper.INSTANCE.useDsdCodeDir(dsdCodeDirVO);
        Predicate predicate = QDsdCodeDir.dsdCodeDir.codeDirId.eq(dsdCodeDir.getCodeDirId());
        boolean exists = dsdCodeDirRepository.exists(predicate);
        if (exists) {
            dsdCodeDirRepository.saveAndFlush(dsdCodeDir);
        } else {
            throw new BizException("当前要编辑的数据分类ID为：" + dsdCodeDir.getCodeDirId()
                    + "数据标准分类名称为:" + dsdCodeDir.getCodeDirName() + " 的数据，不已存在！！！");
        }

    }

    @Override
    public void deleteDsdDir(Integer delId) {
        Optional<DsdCodeDir> dirOptional = dsdCodeDirRepository.findById(delId);
        if (!dirOptional.isPresent()) {
            throw new BizException("参数有误,当前要删除的节点不存在！！！");
        }

        Predicate predicate = dsdCodeDir.parentId.eq(dirOptional.get().getCodeDirId());
        long count = dsdCodeDirRepository.count(predicate);
        if (count > 0) {
            throw new BizException("当前要删除的数据下存在子节点信息，请勿删除！！！");
        } else {
            dsdCodeDirRepository.deleteById(delId);
        }
    }


    /**
     * @Param: respList
     * @return: java.util.List<com.qk.dm.datastandards.vo.DataStandardTreeResp>
     * 使用递归方法建树
     **/
    public static List<DataStandardCodeTreeVO> buildByRecursive(List<DataStandardCodeTreeVO> respList) {
        List<DataStandardCodeTreeVO> trees = new ArrayList<DataStandardCodeTreeVO>();
        for (DataStandardCodeTreeVO treeNode : respList) {
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
    public static DataStandardCodeTreeVO findChildren(DataStandardCodeTreeVO treeNode, List<DataStandardCodeTreeVO> respList) {
        treeNode.setChildren(new ArrayList<DataStandardCodeTreeVO>());
        for (DataStandardCodeTreeVO it : respList) {
            if (treeNode.getCodeDirId().equals(it.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<DataStandardCodeTreeVO>());
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
        Optional<DsdCodeDir> dsdDirIsExist = dsdCodeDirRepository.findOne(dsdCodeDir.id.eq(delId));
        if (!dsdDirIsExist.isPresent()) {
            throw new BizException("参数有误,当前要删除的节点不存在！！！");
        }
        ids.add(delId);
        getIds(ids, delId);
        //批量删除
        Iterable<DsdCodeDir> delDirList = dsdCodeDirRepository.findAll(dsdCodeDir.id.in(ids));
        dsdCodeDirRepository.deleteAll(delDirList);
    }

    /**
     * @Param: ids, delId
     * @return: void
     * 获取删除叶子节点ID
     **/
    private void getIds(ArrayList<Integer> ids, Integer delId) {
        Optional<DsdCodeDir> parentDir = dsdCodeDirRepository.findOne(dsdCodeDir.id.eq(delId));
        Iterable<DsdCodeDir> sonDirList = dsdCodeDirRepository.findAll(dsdCodeDir.parentId.eq(parentDir.get().getCodeDirId()));
        for (DsdCodeDir dsdCodeDir : sonDirList) {
            ids.add(dsdCodeDir.getId());
            this.getIds(ids, dsdCodeDir.getId());
        }
    }

}
