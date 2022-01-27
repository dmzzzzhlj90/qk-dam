package com.qk.dm.datasource.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.*;
import com.qk.dam.datasource.enums.ConnTypeEnum;
import com.qk.dm.datasource.constant.DsConstant;
import com.qk.dm.datasource.entity.DsDatasource;
import com.qk.dm.datasource.entity.DsDir;
import com.qk.dm.datasource.entity.QDsDir;
import com.qk.dm.datasource.mapstruct.mapper.DSDatasourceMapper;
import com.qk.dm.datasource.mapstruct.mapper.DsDirMapper;
import com.qk.dm.datasource.repositories.DsDatasourceRepository;
import com.qk.dm.datasource.repositories.DsDirRepository;
import com.qk.dm.datasource.service.DsDirService;
import com.qk.dm.datasource.vo.DsDatasourceVO;
import com.qk.dm.datasource.vo.DsDirReturnVO;
import com.qk.dm.datasource.vo.DsDirVO;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 数据连接目录接口实现
 *
 * @author zys
 * @date 2021/7/30 15:24
 * @since 1.0.0
 */
@Service
@Transactional
public class DsDirServiceImpl implements DsDirService {
  private final QDsDir qDsDir = QDsDir.dsDir;
  private final DsDirRepository dsDirRepository;
  private final DsDatasourceRepository dsDatasourceRepository;

  public DsDirServiceImpl(
      DsDirRepository dsDirRepository, DsDatasourceRepository dsDatasourceRepository) {
    this.dsDirRepository = dsDirRepository;
    this.dsDatasourceRepository = dsDatasourceRepository;
  }

  @Override
  public void addDsDir(DsDirVO dsDirVO) {
    DsDir dsDir = DsDirMapper.INSTANCE.useDsDir(dsDirVO);
    dsDir.setGmtCreate(new Date());
    dsDir.setId(UUID.randomUUID().toString().replaceAll("-", ""));
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
  public void deleteDsDir(String id) {
    ArrayList<String> ids = new ArrayList<>();
    // 删除父级ID
    Optional<DsDir> dsdDirIsExist = dsDirRepository.findOne(qDsDir.id.eq(id));
    if (!dsdDirIsExist.isPresent()) {
      throw new BizException("参数有误,当前要删除的节点不存在！！！");
    }
    ids.add(id);
    getIds(ids, id);
    // 查询目录下是否存在数据源信息
    List<DsDatasourceVO> dsDatasourceVOList = new ArrayList<>();
    ids.forEach(
        dirid -> {
          List<DsDatasourceVO> list = getDataSourceList(id);
          dsDatasourceVOList.addAll(list);
        });
    if (!CollectionUtils.isEmpty(dsDatasourceVOList)) {
      throw new BizException("存在数据源连接信息，请先删除！！！");
    } else {
      // 批量删除
      Iterable<DsDir> dsDirList = dsDirRepository.findAll(qDsDir.id.in(ids));
      dsDirRepository.deleteAll(dsDirList);
    }
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
    }
    return buildByRecursive(dsDirVOList);
  }

  private List<DsDirReturnVO> buildByRecursive(List<DsDirReturnVO> dsDirVOList) {
    DsDirReturnVO dsDirReturnVO = DsDirReturnVO.builder().key("0").title("全部数据源").build();
    List<DsDirReturnVO> trees = new ArrayList<>();
    trees.add(findChildren(dsDirReturnVO, dsDirVOList));
    return trees;
  }

  private DsDirReturnVO findChildren(DsDirReturnVO dsDirReturnVO, List<DsDirReturnVO> dsDirVOList) {
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
   * 递归查找子类并查询目录下对应的数据源信息
   *
   * @param dsDirReturnVO
   * @param dsDirVOList
   * @return
   */
  private DsDirReturnVO findDirChildren(
      DsDirReturnVO dsDirReturnVO, List<DsDirReturnVO> dsDirVOList) {
    dsDirReturnVO.setChildren(new ArrayList<>());
    // 将数据源转成目录
    List<DsDatasourceVO> datasourceList = getDataSourceList(dsDirReturnVO.getKey());
    List<DsDirReturnVO> dsDirReturnVOList = getDsDirReturnVoList(datasourceList);
    dsDirReturnVO.getChildren().addAll(dsDirReturnVOList);
    dsDirVOList.forEach(
        DSDTV -> {
          if (dsDirReturnVO.getKey().equals(DSDTV.getParentId())) {
            DSDTV.setDataType(DsConstant.DIR_TYPE);
            if (dsDirReturnVO.getChildren() == null) {
              dsDirReturnVO.setChildren(new ArrayList<>());
            }
            dsDirReturnVO.getChildren().add(findDirChildren(DSDTV, dsDirVOList));
          }
        });
    return dsDirReturnVO;
  }

  /**
   * 将数据源信息转换成目录
   *
   * @param datasourceList
   * @return
   */
  private List<DsDirReturnVO> getDsDirReturnVoList(List<DsDatasourceVO> datasourceList) {
    List<DsDirReturnVO> returnVOList = new ArrayList<>();
    if (!CollectionUtils.isEmpty(datasourceList)) {
      datasourceList.forEach(
          dsDatasourceVO -> {
            DsDirReturnVO dsDirReturnVO = new DsDirReturnVO();
            // 赋值目录id
            dsDirReturnVO.setKey(dsDatasourceVO.getId());
            // 赋值目录名称
            dsDirReturnVO.setTitle(dsDatasourceVO.getDataSourceName());
            // 赋值父类名称
            dsDirReturnVO.setParentId(dsDatasourceVO.getDicId());
            // 赋值类型
            dsDirReturnVO.setDataType(DsConstant.DATASOURCE_TYPE);
            // 赋值数据源连接类型
            dsDirReturnVO.setConType(dsDatasourceVO.getLinkType());
            returnVOList.add(dsDirReturnVO);
          });
    }
    return returnVOList;
  }

  private List<DsDatasourceVO> getDataSourceList(String dicid) {
    List<DsDatasourceVO> dsDatasourceVOList = new ArrayList<>();
    if (!StringUtils.isEmpty(dicid)) {
      List<DsDatasource> byDicIdList = dsDatasourceRepository.getByDicId(dicid);
      if (byDicIdList != null) {
        byDicIdList.forEach(
            dsDatasource -> {
              DsDatasourceVO dsDatasourceVO =
                  DSDatasourceMapper.INSTANCE.useDsDatasourceVO(dsDatasource);
              ConnectBasicInfo dsConnectBasicInfo =
                  getConnectInfo(dsDatasource.getLinkType(), dsDatasource);
              dsDatasourceVO.setConnectBasicInfo(dsConnectBasicInfo);
              dsDatasourceVOList.add(dsDatasourceVO);
            });
      }
    }
    return dsDatasourceVOList;
  }

  private ConnectBasicInfo getConnectInfo(String type, DsDatasource dsDatasource) {
    ConnectBasicInfo connectBasicInfo = null;
    if (type.equalsIgnoreCase(ConnTypeEnum.MYSQL.getName())) {
      String dataSourceValues = dsDatasource.getDataSourceValues();
      connectBasicInfo =
          GsonUtil.fromJsonString(dataSourceValues, new TypeToken<MysqlInfo>() {}.getType());
    }
    if (type.equalsIgnoreCase(ConnTypeEnum.HIVE.getName())) {
      String dataSourceValues = dsDatasource.getDataSourceValues();
      connectBasicInfo =
          GsonUtil.fromJsonString(dataSourceValues, new TypeToken<HiveInfo>() {}.getType());
    }
//    if (type.equalsIgnoreCase(ConnTypeEnum.ORACLE.getName())) {
//      String dataSourceValues = dsDatasource.getDataSourceValues();
//      connectBasicInfo =
//          GsonUtil.fromJsonString(dataSourceValues, new TypeToken<OracleInfo>() {}.getType());
//    }
//    if (type.equalsIgnoreCase(ConnTypeEnum.POSTGRESQL.getName())) {
//      String dataSourceValues = dsDatasource.getDataSourceValues();
//      connectBasicInfo =
//          GsonUtil.fromJsonString(dataSourceValues, new TypeToken<PostgresqlInfo>() {}.getType());
//    }
    return connectBasicInfo;
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
        dsDirRepository.findAll(qDsDir.parentId.eq(dicId));
    if (sonDirList != null) {
      sonDirList.forEach(
          dsDir -> {
            dsDicIdSet.add(dsDir.getId());
            this.getDsdId(dsDicIdSet, dsDir.getId());
          });
    }
  }

  @Override
  public List<DsDirReturnVO> getDsDirDataSource() {
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
    }
    return buildByRecursives(dsDirVOList);
  }

  /**
   * 修改目录名称
   *
   * @param dsDirVO
   */
  @Override
  public void updateDsDir(DsDirVO dsDirVO) {
    DsDir dsDir = DsDirMapper.INSTANCE.useDsDir(dsDirVO);
    dsDir.setGmtModified(new Date());
    BooleanExpression predicate = qDsDir.id.eq(dsDir.getId());
    boolean exists = dsDirRepository.exists(predicate);
    if (exists) {
      dsDirRepository.saveAndFlush(dsDir);
    } else {
      throw new BizException(
          "当前要更新的目录ID为：" + dsDir.getId() + "目录名称为:" + dsDir.getDicName() + " 的数据，不存在！！！");
    }
  }

  private List<DsDirReturnVO> buildByRecursives(List<DsDirReturnVO> dsDirVOList) {
    DsDirReturnVO dsDirReturnVO =
        DsDirReturnVO.builder().key("0").title("全部数据源").dataType(DsConstant.DIR_TYPE).build();
    List<DsDirReturnVO> trees = new ArrayList<>();
    trees.add(findDirChildren(dsDirReturnVO, dsDirVOList));
    return trees;
  }

  /**
   * 根据需要删除的id查询目录下所有的子目录
   *
   * @param ids
   * @param id
   */
  private void getIds(ArrayList<String> ids, String id) {
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
