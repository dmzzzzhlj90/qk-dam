package com.qk.dm.datasource.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datasource.entity.DsDir;
import com.qk.dm.datasource.entity.QDsDir;
import com.qk.dm.datasource.mapstruct.mapper.DsDirMapper;
import com.qk.dm.datasource.repositories.DsDirRepository;
import com.qk.dm.datasource.service.DsDirService;
import com.qk.dm.datasource.vo.DsDirReturnVO;
import com.qk.dm.datasource.vo.DsDirVO;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 数据连接目录接口实现
 *
 * @author zys
 * @date 2021/7/30 15:24
 * @since 1.0.0
 */
@Service
public class DsDirServiceImpl implements DsDirService {
  private final QDsDir qDsDir = QDsDir.dsDir;
  private final DsDirRepository dsDirRepository;

  public DsDirServiceImpl(DsDirRepository dsDirRepository) {
    this.dsDirRepository = dsDirRepository;
  }

  @Override
  public void addDsDir(DsDirVO dsDirVO) {
    DsDir dsDir = DsDirMapper.INSTANCE.useDsDir(dsDirVO);
    dsDir.setGmtCreate(new Date());
    BooleanExpression predicate = qDsDir.dsDirCode.eq(dsDir.getDsDirCode());
    boolean exists = dsDirRepository.exists(predicate);
    if (exists) {
      throw new BizException(
          "当前要新增的数据连接分类名称为:"
              + dsDir.getDicName()
              + " 所属的节点层级目录code为:"
              + dsDir.getDicName()
              + " 的数据，已存在！！！");
    }
    dsDirRepository.save(dsDir);
  }

  @Override
  public void deleteDsDir(Integer id) {
    ArrayList<Integer> ids = new ArrayList<>();
    // 删除父级ID
    Optional<DsDir> dsdDirIsExist = dsDirRepository.findOne(qDsDir.id.eq(id));
    if (!dsdDirIsExist.isPresent()) {
      throw new BizException("参数有误,当前要删除的节点不存在！！！");
    }
    ids.add(id);
    getIds(ids, id);
    // 批量删除
    Iterable<DsDir> dsDirList = dsDirRepository.findAll(qDsDir.id.in(ids));
    dsDirRepository.deleteAll(dsDirList);
  }

  @Override
  public List<DsDirReturnVO> getDsDir() {
    List<DsDir> dsDirList = dsDirRepository.findAll();
    List<DsDirReturnVO> dsDirVOList = new ArrayList<>();
    if (!CollectionUtils.isEmpty(dsDirList)) {
      dsDirList.forEach(
          dsDir -> {
            DsDirReturnVO dsDirReturnVO = DsDirMapper.INSTANCE.useDsDirVO(dsDir);
            dsDirReturnVO.setKey(dsDir.getId());
            dsDirReturnVO.setTitle(dsDir.getDicName());
            dsDirVOList.add(dsDirReturnVO);
          });
    } else {
      throw new BizException("获取目录为空");
    }
    return buildByRecursive(dsDirVOList);
  }

  private List<DsDirReturnVO> buildByRecursive(List<DsDirReturnVO> dsDirVOList) {
    DsDirReturnVO dsDirReturnVO=DsDirReturnVO.builder().key(0).title("全部数据源").build();
    List<DsDirReturnVO> trees = new ArrayList<>();
    trees.add(findChildren(dsDirReturnVO, dsDirVOList));
    return trees;
  }

  /**
   * 递归查找子类
   * @param dsDirReturnVO
   * @param dsDirVOList
   * @return
   */
  private DsDirReturnVO findChildren(DsDirReturnVO dsDirReturnVO, List<DsDirReturnVO> dsDirVOList) {
    dsDirReturnVO.setChildren(new ArrayList<>());
    for (DsDirReturnVO DSDTV : dsDirVOList) {
      if (dsDirReturnVO.getKey().equals(DSDTV.getParentId())) {
        if (dsDirReturnVO.getChildren() == null) {
          dsDirReturnVO.setChildren(new ArrayList<>());
        }
        dsDirReturnVO.getChildren().add(findChildren(DSDTV, dsDirVOList));
      }
    }
    return dsDirReturnVO;
  }

  /**
   * 根据目录id获取目录下所有节点的目录id集合
   *
   * @param dsDicIdSet
   * @param dicId
   */
  @Override
  public void getDsdId(Set<String> dsDicIdSet, String dicId) {
    Iterable<DsDir> sonDirList =
        dsDirRepository.findAll(qDsDir.parentId.eq(Integer.valueOf(dicId)));
    if (sonDirList != null) {
      sonDirList.forEach(
          dsDir -> {
            dsDicIdSet.add(Integer.toString(dsDir.getId()));
            this.getDsdId(dsDicIdSet, Integer.toString(dsDir.getId()));
          });
    }
  }

  /**
   * 根据需要删除的id查询目录下所有的子目录
   *
   * @param ids
   * @param id
   */
  private void getIds(ArrayList<Integer> ids, Integer id) {
    Iterable<DsDir> sonDirList = dsDirRepository.findAll(qDsDir.parentId.eq(id));
    if (sonDirList != null) {
      sonDirList.forEach(
          dsDir -> {
            ids.add(dsDir.getId());
            this.getIds(ids, dsDir.getId());
          });
    }
  }
}
