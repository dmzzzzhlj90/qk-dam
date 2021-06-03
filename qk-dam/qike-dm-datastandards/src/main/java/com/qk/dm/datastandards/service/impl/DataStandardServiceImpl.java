package com.qk.dm.datastandards.service.impl;

import com.qk.commons.exception.BizException;
import com.qk.dm.datastandards.entity.DsdDir;
import com.qk.dm.datastandards.entity.QDsdDir;
import com.qk.dm.datastandards.repositories.DsdDirRepository;
import com.qk.dm.datastandards.service.DataStandardService;
import com.qk.dm.datastandards.vo.DataStandardTreeResp;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author wjq
 * @date 20210603
 * 数据标准接口实现类
 * @since 1.0.0
 */
@Service
public class DataStandardServiceImpl implements DataStandardService {
    private final DsdDirRepository dsdDirRepository;

    public DataStandardServiceImpl(DsdDirRepository dsdDirRepository) {
        this.dsdDirRepository = dsdDirRepository;
    }

    @Override
    public List<DataStandardTreeResp> getTree() {
        //查询条件
        Predicate predicate = QDsdDir.dsdDir.delFlag.eq(0);
        List<DsdDir> dsdDirList = (List<DsdDir>) dsdDirRepository.findAll(predicate);
        List<DataStandardTreeResp> respList = new ArrayList<>();
        for (DsdDir dsdDir : dsdDirList) {
            respList.add(getDataStandardTreeResp(dsdDir));
        }
        return buildByRecursive(respList);
    }

    @Override
    public void addDsdDir(DsdDir dsdDir) {
        Predicate predicate = QDsdDir.dsdDir.dirDsdid.eq(dsdDir.getDirDsdid());
        Optional<DsdDir> dsdDirIsExist = dsdDirRepository.findOne(predicate);
        if (dsdDirIsExist.isPresent()) {
            throw new BizException("当前要新增的数据分类ID为：" + dsdDirIsExist.get().getDirDsdid()
                    + "数据标准分类名称为:" + dsdDirIsExist.get().getDirDsdName() + "，已存在！！！");
        }
        dsdDirRepository.save(dsdDir);
    }


    @Override
    public void updateDsdDir(DsdDir dsdDir) {
        dsdDirRepository.saveAndFlush(dsdDir);
    }

    @Override
    public void deleteDsdDir(Integer id) {
        dsdDirRepository.deleteById(id);
    }


    /**
     * @param [respList]
     * @return java.util.List<com.qk.dm.datastandards.vo.DataStandardTreeResp>
     * 使用递归方法建树
     **/
    public static List<DataStandardTreeResp> buildByRecursive(List<DataStandardTreeResp> respList) {
        List<DataStandardTreeResp> trees = new ArrayList<DataStandardTreeResp>();
        for (DataStandardTreeResp treeNode : respList) {
            if (null == treeNode.getParentId() || 0 > treeNode.getParentId()) {
                trees.add(findChildren(treeNode, respList));
            }
        }
        return trees;
    }

    /**
     * @param [treeNode, respList]
     * @return com.qk.dm.datastandards.vo.DataStandardTreeResp
     * 递归查找子节点
     **/
    public static DataStandardTreeResp findChildren(DataStandardTreeResp treeNode, List<DataStandardTreeResp> respList) {
        treeNode.setChildren(new ArrayList<DataStandardTreeResp>());
        for (DataStandardTreeResp it : respList) {
            if (treeNode.getDirDsdid().equals(it.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<DataStandardTreeResp>());
                }
                treeNode.getChildren().add(findChildren(it, respList));
            }
        }
        return treeNode;
    }

    private DataStandardTreeResp getDataStandardTreeResp(DsdDir dsdDir) {
        return DataStandardTreeResp.builder()
                .id(dsdDir.getId())
                .dirDsdid(dsdDir.getDirDsdid())
                .dirDsdName(dsdDir.getDirDsdName())
                .parentId(dsdDir.getParentId())
                .build();
    }

}
