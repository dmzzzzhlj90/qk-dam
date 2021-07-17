package com.qk.dm.datastandards.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datastandards.constant.DsdConstant;
import com.qk.dm.datastandards.entity.DsdCodeDir;
import com.qk.dm.datastandards.entity.DsdCodeTerm;
import com.qk.dm.datastandards.entity.QDsdCodeDir;
import com.qk.dm.datastandards.mapstruct.mapper.DsdCodeTreeMapper;
import com.qk.dm.datastandards.mapstruct.mapper.DsdDirCodeDirTreeMapper;
import com.qk.dm.datastandards.repositories.DsdCodeDirRepository;
import com.qk.dm.datastandards.repositories.DsdCodeTermRepository;
import com.qk.dm.datastandards.service.DataStandardCodeDirService;
import com.qk.dm.datastandards.vo.DataStandardCodeTreeVO;
import com.qk.dm.datastandards.vo.DsdCodeDirVO;
import com.querydsl.core.types.Predicate;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static com.qk.dm.datastandards.entity.QDsdCodeDir.dsdCodeDir;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0 数据标准目录接口实现类
 */
@Service
public class DataStandardCodeDirServiceImpl implements DataStandardCodeDirService {
    private final DsdCodeDirRepository dsdCodeDirRepository;
    private final DsdCodeTermRepository dsdCodeTermRepository;

    @Autowired
    public DataStandardCodeDirServiceImpl(DsdCodeDirRepository dsdCodeDirRepository, DsdCodeTermRepository dsdCodeTermRepository) {
        this.dsdCodeDirRepository = dsdCodeDirRepository;
      this.dsdCodeTermRepository = dsdCodeTermRepository;
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
        dsdCodeDir.setGmtCreate(new Date());
        if (StringUtils.isEmpty(dsdCodeDir.getCodeDirId())) {
            dsdCodeDir.setCodeDirId(UUID.randomUUID().toString().replaceAll("-", ""));
        }
        Predicate predicate = QDsdCodeDir.dsdCodeDir.codeDirId.eq(dsdCodeDir.getCodeDirId());
        boolean exists = dsdCodeDirRepository.exists(predicate);
        if (exists) {
            throw new BizException(
                    "当前要新增的码表目录ID为："
                            + dsdCodeDir.getCodeDirId()
                            + "码表名称为:"
                            + dsdCodeDir.getCodeDirName()
                            + " 的数据，已存在！！！");
        }
        dsdCodeDirRepository.save(dsdCodeDir);
    }

    @Override
    public void updateDsdDir(DsdCodeDirVO dsdCodeDirVO) {
        DsdCodeDir dsdCodeDir = DsdDirCodeDirTreeMapper.INSTANCE.useDsdCodeDir(dsdCodeDirVO);
        dsdCodeDir.setGmtModified(new Date());
        Predicate predicate = QDsdCodeDir.dsdCodeDir.codeDirId.eq(dsdCodeDir.getCodeDirId());
        boolean exists = dsdCodeDirRepository.exists(predicate);
        if (exists) {
            dsdCodeDirRepository.saveAndFlush(dsdCodeDir);
        } else {
            throw new BizException(
                    "当前要编辑的数据分类ID为："
                            + dsdCodeDir.getCodeDirId()
                            + "数据标准分类名称为:"
                            + dsdCodeDir.getCodeDirName()
                            + " 的数据，不已存在！！！");
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
     * @param: respList
     * @return: 使用递归方法建树
     */
    public static List<DataStandardCodeTreeVO> buildByRecursive(
            List<DataStandardCodeTreeVO> respList) {
        List<DataStandardCodeTreeVO> trees = new ArrayList<DataStandardCodeTreeVO>();
        for (DataStandardCodeTreeVO treeNode : respList) {
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
    public static DataStandardCodeTreeVO findChildren(
            DataStandardCodeTreeVO treeNode, List<DataStandardCodeTreeVO> respList) {
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
        // 删除父级ID
        Optional<DsdCodeDir> dsdDirIsExist = dsdCodeDirRepository.findOne(dsdCodeDir.id.eq(delId));
        if (!dsdDirIsExist.isPresent()) {
            throw new BizException("参数有误,当前要删除的节点不存在！！！");
        }
        ids.add(delId);
        getIds(ids, delId);
        // 批量删除
        Iterable<DsdCodeDir> delDirList = dsdCodeDirRepository.findAll(dsdCodeDir.id.in(ids));
        dsdCodeDirRepository.deleteAll(delDirList);
    }

    /**
     * @param: ids, delId
     * @return: 获取删除叶子节点ID
     */
    private void getIds(ArrayList<Integer> ids, Integer delId) {
        Optional<DsdCodeDir> parentDir = dsdCodeDirRepository.findOne(dsdCodeDir.id.eq(delId));
        Iterable<DsdCodeDir> sonDirList =
                dsdCodeDirRepository.findAll(dsdCodeDir.parentId.eq(parentDir.get().getCodeDirId()));
        for (DsdCodeDir dsdCodeDir : sonDirList) {
            ids.add(dsdCodeDir.getId());
            this.getIds(ids, dsdCodeDir.getId());
        }
    }


    @Override
    /**
     * 根据传入的码表id判断码表目录中是否存在数据
     */
    public Boolean deleteJudgeDsdDir(Integer id) {
        Boolean resut = true;
        Optional<DsdCodeDir> dirOptional = dsdCodeDirRepository.findById(id);
        if (!dirOptional.isPresent()) {
            throw new BizException("参数有误,当前要删除的节点不存在！！！");
        }
        //获取目录节点id并且查询目录下是否存在有数据
        String codeDirId = dirOptional.get().getCodeDirId();
        if (StringUtils.isEmpty(codeDirId)) {
            throw new BizException("当前要删除目录的节点不存在！！！");
        }
        //获取传入id目录下所有的节点
        List<String> codeDirIdList = new ArrayList<>();
        codeDirIdList.add(codeDirId);
        getCodeDirID(codeDirIdList, id);
        if (!CollectionUtils.isEmpty(codeDirIdList)) {
            for (int i = 0; i < codeDirIdList.size(); i++) {
                String codeDirid = codeDirIdList.get(i);
                if (StringUtils.isNotBlank(codeDirid)) {
                    DsdCodeTerm dsdCodeTerm = new DsdCodeTerm();
                    dsdCodeTerm.setCodeDirId(codeDirid);
                    Example<DsdCodeTerm> example = Example.of(dsdCodeTerm);
                    List<DsdCodeTerm> dsdCodeTermList = dsdCodeTermRepository.findAll(example);
                    if (!CollectionUtils.isEmpty(dsdCodeTermList)) {
                        resut = false;
                        break;
                    }
                }
            }
        }
        return resut;
    }

    /**
     * 获取码表目录下所有目录的节点
     *
     * @param codeDirIdList
     * @param id
     */
    private void getCodeDirID(List<String> codeDirIdList, Integer id) {
        Optional<DsdCodeDir> parentDir = dsdCodeDirRepository.findOne(dsdCodeDir.id.eq(id));
        Iterable<DsdCodeDir> sonDirList =
                dsdCodeDirRepository.findAll(dsdCodeDir.parentId.eq(parentDir.get().getCodeDirId()));
        for (DsdCodeDir dsdCodeDir : sonDirList) {
            codeDirIdList.add(dsdCodeDir.getCodeDirId());
            this.getCodeDirID(codeDirIdList, dsdCodeDir.getId());
        }
    }
}
