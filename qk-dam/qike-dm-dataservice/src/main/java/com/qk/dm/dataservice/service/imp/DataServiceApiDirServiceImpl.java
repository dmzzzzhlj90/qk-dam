package com.qk.dm.dataservice.service.imp;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataservice.constant.DasConstant;
import com.qk.dm.dataservice.entity.DasApiDir;
import com.qk.dm.dataservice.entity.QDasApiDir;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiDirTreeMapper;
import com.qk.dm.dataservice.repositories.DasApiBasicinfoRepository;
import com.qk.dm.dataservice.repositories.DasApiDirRepository;
import com.qk.dm.dataservice.service.DataServiceApiDirService;
import com.qk.dm.dataservice.vo.DasApiDirTreeVO;
import com.qk.dm.dataservice.vo.DasApiDirVO;
import com.querydsl.core.types.Predicate;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wjq
 * @date 20210816
 * @since 1.0.0
 */
@Service
public class DataServiceApiDirServiceImpl implements DataServiceApiDirService {
  private static final QDasApiDir qDasApiDir = QDasApiDir.dasApiDir;

  private final DasApiDirRepository dasApiDirRepository;
  private final DasApiBasicinfoRepository dasApiBasicinfoRepository;

  @Autowired
  public DataServiceApiDirServiceImpl(
      DasApiDirRepository dasApiDirRepository,
      DasApiBasicinfoRepository dasApiBasicinfoRepository) {
    this.dasApiDirRepository = dasApiDirRepository;
    this.dasApiBasicinfoRepository = dasApiBasicinfoRepository;
  }

  @Override
  public List<DasApiDirTreeVO> getTree() {
    Predicate predicate = qDasApiDir.delFlag.eq(0);
    List<DasApiDir> dasApiDirList = (List<DasApiDir>) dasApiDirRepository.findAll(predicate);
    List<DasApiDirTreeVO> respList = new ArrayList<>();
    for (DasApiDir dasApiDir : dasApiDirList) {
      DasApiDirTreeVO dasApiTreeVO = DasApiDirTreeMapper.INSTANCE.useDasApiDirTreeVO(dasApiDir);
      respList.add(dasApiTreeVO);
    }
    return buildByRecursive(respList);
  }

  @Override
  public void addDasApiDir(DasApiDirVO dasApiDirVO) {
    DasApiDir dasApiDir = DasApiDirTreeMapper.INSTANCE.useDasApiDir(dasApiDirVO);
    dasApiDir.setGmtCreate(new Date());
    dasApiDir.setGmtModified(new Date());
    dasApiDir.setApiDirId(UUID.randomUUID().toString().replaceAll("-", ""));

    Predicate predicate = qDasApiDir.apiDirLevel.eq(dasApiDirVO.getApiDirLevel());
    boolean exists = dasApiDirRepository.exists(predicate);
    if (exists) {
      throw new BizException(
          "当前要新增的API目录分类名称为:"
              + dasApiDir.getApiDirName()
              + " 所属的节点层级目录为:"
              + dasApiDirVO.getApiDirLevel()
              + " 的数据，已存在！！！");
    }
    dasApiDirRepository.save(dasApiDir);
  }

  @Transactional
  @Override
  public void updateDasApiDir(DasApiDirVO dasApiDirVO) {
    DasApiDir dasApiDir = DasApiDirTreeMapper.INSTANCE.useDasApiDir(dasApiDirVO);
    dasApiDir.setGmtModified(new Date());
    Predicate predicate = qDasApiDir.apiDirId.eq(dasApiDir.getApiDirId());
    final Optional<DasApiDir> dasApiDirOptional = dasApiDirRepository.findOne(predicate);
    if (dasApiDirOptional.isPresent()) {
      String dasApiDirLevel = dasApiDirOptional.get().getApiDirLevel();
      if (dasApiDirLevel.equals(dasApiDirVO.getApiDirLevel())) {
        throw new BizException(
            "当前要编辑的API目录分类名称为:"
                + dasApiDir.getApiDirLevel()
                + " 所属的节点层级目录为:"
                + dasApiDirVO.getApiDirLevel()
                + ", 的数据，已存在！！！");
      }
      dasApiDirRepository.saveAndFlush(dasApiDir);
      //      dsdBasicinfoRepository.updateDirLevelByDirId(
      //          dasApiDirVO.getDasApiDirLevel(), dasApiDirVO.getDirDsdId());
    } else {
      throw new BizException("当前要编辑的API目录分类名称为:" + dasApiDir.getApiDirName() + " 的数据，不存在！！！");
    }
  }

  @Override
  public void deleteDasApiDir(Long delId) {
    Optional<DasApiDir> dirOptional = dasApiDirRepository.findById(delId);
    if (!dirOptional.isPresent()) {
      throw new BizException("参数有误,当前要删除的节点不存在！！！");
    }

    Predicate predicate = QDasApiDir.dasApiDir.parentId.eq(dirOptional.get().getApiDirId());
    long count = dasApiDirRepository.count(predicate);
    if (count > 0) {
      throw new BizException("当前要删除的数据下存在子节点信息，请勿删除！！！");
    } else {
      dasApiDirRepository.deleteById(delId);
    }
  }

  public static List<DasApiDirTreeVO> buildByRecursive(List<DasApiDirTreeVO> respList) {
    DasApiDirTreeVO topParent =
        DasApiDirTreeVO.builder().apiDirId("-1").apiDirName("全部API").build();
    List<DasApiDirTreeVO> trees = new ArrayList<>();
    trees.add(findChildren(topParent, respList));

    return trees;
  }

  /**
   * 递归查找子节点
   *
   * @param treeNode,respList
   * @return DataStandardTreeVO
   */
  public static DasApiDirTreeVO findChildren(
      DasApiDirTreeVO treeNode, List<DasApiDirTreeVO> respList) {
    treeNode.setChildren(new ArrayList<>());
    for (DasApiDirTreeVO dasApiDirTreeVO : respList) {
      if (treeNode.getApiDirId().equals(dasApiDirTreeVO.getParentId())) {
        if (treeNode.getChildren() == null) {
          treeNode.setChildren(new ArrayList<>());
        }
        if (!DasConstant.TREE_DIR_TOP_PARENT_ID.equals(treeNode.getApiDirId())) {
          dasApiDirTreeVO.setApiDirLevel(
              treeNode.getApiDirLevel() + "/" + dasApiDirTreeVO.getApiDirName());
        }
        treeNode.getChildren().add(findChildren(dasApiDirTreeVO, respList));
      }
    }
    return treeNode;
  }

  @Override
  public void deleteDasApiDirRoot(Long delId) {
    ArrayList<Long> ids = new ArrayList<>();
    // 删除父级ID
    Optional<DasApiDir> dasApiDirIsExist =
        dasApiDirRepository.findOne(QDasApiDir.dasApiDir.id.eq(delId));
    if (!dasApiDirIsExist.isPresent()) {
      throw new BizException("参数有误,当前要删除的节点不存在！！！");
    }
    ids.add(delId);
    getIds(ids, delId);
    // 批量删除
    Iterable<DasApiDir> delDirList = dasApiDirRepository.findAll(QDasApiDir.dasApiDir.id.in(ids));
    dasApiDirRepository.deleteAll(delDirList);
  }

  /** 根据API目录id获取目录下的所有节点id */
  @Override
  public void getApiDirId(Set<String> apiDirIdSet, String apiDirId) {
    Optional<DasApiDir> parentDir = dasApiDirRepository.findOne(qDasApiDir.apiDirId.eq(apiDirId));
    if (parentDir.isPresent()) {
      apiDirIdSet.add(parentDir.get().getApiDirId());
      Iterable<DasApiDir> sonDirList =
          dasApiDirRepository.findAll(qDasApiDir.parentId.eq(parentDir.get().getApiDirId()));
      for (DasApiDir dasApiDir : sonDirList) {
        apiDirIdSet.add(dasApiDir.getApiDirId());
        this.getApiDirId(apiDirIdSet, dasApiDir.getApiDirId());
      }
    }
  }

  /**
   * 获取删除叶子节点ID
   *
   * @param ids,delId
   */
  private void getIds(ArrayList<Long> ids, Long delId) {
    Optional<DasApiDir> parentDir = dasApiDirRepository.findOne(QDasApiDir.dasApiDir.id.eq(delId));
    Iterable<DasApiDir> sonDirList =
        dasApiDirRepository.findAll(
            QDasApiDir.dasApiDir.parentId.eq(parentDir.get().getApiDirId()));
    for (DasApiDir dasApiDir : sonDirList) {
      ids.add(dasApiDir.getId());
      this.getIds(ids, dasApiDir.getId());
    }
  }
}
