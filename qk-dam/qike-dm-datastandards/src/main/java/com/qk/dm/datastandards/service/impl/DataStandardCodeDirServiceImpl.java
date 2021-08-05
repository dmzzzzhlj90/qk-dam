package com.qk.dm.datastandards.service.impl;

import static com.qk.dm.datastandards.entity.QDsdCodeDir.dsdCodeDir;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datastandards.constant.DsdConstant;
import com.qk.dm.datastandards.entity.DsdCodeDir;
import com.qk.dm.datastandards.entity.QDsdCodeDir;
import com.qk.dm.datastandards.mapstruct.mapper.DsdCodeTreeMapper;
import com.qk.dm.datastandards.mapstruct.mapper.DsdDirCodeDirTreeMapper;
import com.qk.dm.datastandards.repositories.DsdCodeDirRepository;
import com.qk.dm.datastandards.repositories.DsdCodeInfoRepository;
import com.qk.dm.datastandards.service.DataStandardCodeDirService;
import com.qk.dm.datastandards.vo.DataStandardCodeTreeVO;
import com.qk.dm.datastandards.vo.DsdCodeDirVO;
import com.querydsl.core.types.Predicate;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0 数据标准目录接口实现类
 */
@Service
public class DataStandardCodeDirServiceImpl implements DataStandardCodeDirService {
  private final DsdCodeDirRepository dsdCodeDirRepository;
  private final DsdCodeInfoRepository dsdCodeInfoRepository;

  @Autowired
  public DataStandardCodeDirServiceImpl(
      DsdCodeDirRepository dsdCodeDirRepository, DsdCodeInfoRepository dsdCodeInfoRepository) {
    this.dsdCodeDirRepository = dsdCodeDirRepository;
    this.dsdCodeInfoRepository = dsdCodeInfoRepository;
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
    dsdCodeDir.setCodeDirId(UUID.randomUUID().toString().replaceAll("-", ""));

    Predicate predicate = QDsdCodeDir.dsdCodeDir.codeDirLevel.eq(dsdCodeDir.getCodeDirLevel());
    boolean exists = dsdCodeDirRepository.exists(predicate);
    if (exists) {
      throw new BizException(
          "当前要新增的码表目录名称为:"
              + dsdCodeDir.getCodeDirName()
              + " 所属的节点层级目录为:"
              + dsdCodeDir.getCodeDirLevel()
              + " 的数据，已存在！！！");
    }
    dsdCodeDirRepository.save(dsdCodeDir);
  }

  @Transactional
  @Override
  public void updateDsdDir(DsdCodeDirVO dsdCodeDirVO) {
    DsdCodeDir dsdCodeDir = DsdDirCodeDirTreeMapper.INSTANCE.useDsdCodeDir(dsdCodeDirVO);
    dsdCodeDir.setGmtModified(new Date());
    Predicate predicate = QDsdCodeDir.dsdCodeDir.codeDirId.eq(dsdCodeDir.getCodeDirId());
    final Optional<DsdCodeDir> dsdCodeDirOptional = dsdCodeDirRepository.findOne(predicate);
    if (dsdCodeDirOptional.isPresent()) {
      String codeDirLevel = dsdCodeDirOptional.get().getCodeDirLevel();
      if (codeDirLevel.equals(dsdCodeDirVO.getCodeDirLevel())) {
        throw new BizException(
            "当前要编辑的数据标准分类名称为:"
                + dsdCodeDir.getCodeDirName()
                + " 所属的节点层级目录为:"
                + dsdCodeDir.getCodeDirLevel()
                + ", 的数据，已存在！！！");
      }
      dsdCodeDirRepository.saveAndFlush(dsdCodeDir);
      dsdCodeInfoRepository.updateCodeDirLevelByCodeDirId(
          dsdCodeDirVO.getCodeDirLevel(), dsdCodeDirVO.getCodeDirId());

    } else {
      throw new BizException("当前要编辑的数据标准分类名称为:" + dsdCodeDir.getCodeDirName() + " 的数据，不存在！！！");
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
    DataStandardCodeTreeVO topParent =
        DataStandardCodeTreeVO.builder().codeDirId("-1").codeDirName("全部码表").build();
    List<DataStandardCodeTreeVO> trees = new ArrayList<>();
    trees.add(findChildren(topParent, respList));
    return trees;
  }

  /**
   * @param: treeNode, respList
   * @return: 递归查找子节点
   */
  public static DataStandardCodeTreeVO findChildren(
      DataStandardCodeTreeVO treeNode, List<DataStandardCodeTreeVO> respList) {
    treeNode.setChildren(new ArrayList<>());
    for (DataStandardCodeTreeVO DSCTV : respList) {
      if (treeNode.getCodeDirId().equals(DSCTV.getParentId())) {
        if (treeNode.getChildren() == null) {
          treeNode.setChildren(new ArrayList<>());
        }
        if (!DsdConstant.TREE_DIR_TOP_PARENT_ID.equals(treeNode.getCodeDirId())) {
          DSCTV.setCodeDirLevel(treeNode.getCodeDirLevel() + "/" + DSCTV.getCodeDirName());
        }
        treeNode.getChildren().add(findChildren(DSCTV, respList));
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

  /**
   * 获取码表目录下所有目录的节点
   *
   * @param codeDirIdSet
   * @param codeDirId
   */
  @Override
  public void getCodeDirId(Set<String> codeDirIdSet, String codeDirId) {
    Optional<DsdCodeDir> parentDir =
        dsdCodeDirRepository.findOne(QDsdCodeDir.dsdCodeDir.codeDirId.eq(codeDirId));
    if (parentDir.isPresent()) {
      codeDirIdSet.add(parentDir.get().getCodeDirId());
      Iterable<DsdCodeDir> sonDirList =
          dsdCodeDirRepository.findAll(
              QDsdCodeDir.dsdCodeDir.parentId.eq(parentDir.get().getCodeDirId()));
      for (DsdCodeDir dsdCodeDir : sonDirList) {
        codeDirIdSet.add(dsdCodeDir.getCodeDirId());
        this.getCodeDirId(codeDirIdSet, dsdCodeDir.getCodeDirId());
      }
    }
  }

  @Override
  public List<String> findAllDsdCodeDirLevel() {
    return dsdCodeDirRepository.findAll().stream()
        .map(dsdCodeDir -> dsdCodeDir.getCodeDirLevel())
        .collect(Collectors.toList());
  }
}
