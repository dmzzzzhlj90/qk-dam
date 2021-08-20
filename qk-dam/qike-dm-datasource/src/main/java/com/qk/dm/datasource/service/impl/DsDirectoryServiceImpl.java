package com.qk.dm.datasource.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.datasource.entity.DsDir;
import com.qk.dm.datasource.entity.DsDirectory;
import com.qk.dm.datasource.entity.QDsDirectory;
import com.qk.dm.datasource.mapstruct.mapper.DsDirectoryMapper;
import com.qk.dm.datasource.repositories.DsDirRepository;
import com.qk.dm.datasource.repositories.DsDirectoryRepository;
import com.qk.dm.datasource.service.DsDirectoryService;
import com.qk.dm.datasource.vo.DsDirectoryVO;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * 数据源管理应用系统录入接口实现
 *
 * @author zys
 * @date 20210729
 * @since 1.0.0 数据源管理应用系统录入接口实现
 */
@Service
public class DsDirectoryServiceImpl implements DsDirectoryService {
  private final QDsDirectory qDsDirectory = QDsDirectory.dsDirectory;
  private final DsDirectoryRepository dsDirectoryRepository;
  private final DsDirRepository dsDirRepository;

  public DsDirectoryServiceImpl(
      DsDirectoryRepository dsDirectoryRepository, DsDirRepository dsDirRepository) {
    this.dsDirectoryRepository = dsDirectoryRepository;
    this.dsDirRepository = dsDirRepository;
  }

  @Override
  public List<DsDirectoryVO> getSysDirectory(Pagination pagination) {
    List<DsDirectoryVO> dsDirectorielist = new ArrayList<>();
    Page<DsDirectory> pageList = dsDirectoryRepository.findAll(pagination.getPageable());
    pageList
        .getContent()
        .forEach(
            dsDirectory -> {
              DsDirectoryVO dsDirectoryVO =
                  DsDirectoryMapper.INSTANCE.useDsDirectoryVO(dsDirectory);
              dsDirectorielist.add(dsDirectoryVO);
            });
    return dsDirectorielist;
  }

  @Override
  public void addSysDirectory(DsDirectoryVO dsDirectoryVO) {
    // 将vo转成数据库类
    DsDirectory dsDirectory = DsDirectoryMapper.INSTANCE.useDsDirectory(dsDirectoryVO);
    // 添加新增时间和修改时间
    dsDirectory.setGmtCreate(new Date());
    dsDirectory.setGmtModified(new Date());
    // 根据应用系统名称判断数据库中是否存在，如果存在就抛出异常，否则新增
    BooleanExpression predicate = qDsDirectory.sysName.eq(dsDirectory.getSysName());
    boolean exists = dsDirectoryRepository.exists(predicate);
    if (exists) {
      throw new BizException("当前要新增的应用系统,名称为:" + dsDirectory.getSysName() + " 的数据，已存在！！！");
    } else {
      dsDirectoryRepository.save(dsDirectory);
      // 创建应用系统后创建对应的一级目录
      DsDir dsdir = new DsDir();
      getDsDir(dsdir, dsDirectory);
      dsDirRepository.save(dsdir);
    }
  }

  /**
   * 构建目录
   *
   * @param dsdir
   * @param dsDirectory
   */
  private void getDsDir(DsDir dsdir, DsDirectory dsDirectory) {
    if (dsDirectory != null) {
      // 赋值名称
      dsdir.setDicName(dsDirectory.getSysName());
      // 赋值父类编码
      dsdir.setParentId(0);
      // 创建时间
      dsdir.setGmtCreate(new Date());
      // 目录编码
      dsdir.setDsDirCode(UUID.randomUUID().toString().replaceAll("-", ""));
      // 逻辑删除(默认设置为0)
      dsdir.setDelFlag(0);
    }
  }

  @Override
  public void deleteDsDirectory(Integer id) {
    boolean exists = dsDirectoryRepository.exists(qDsDirectory.id.eq(id));
    if (exists) {
      dsDirectoryRepository.deleteById(id);
    } else {
      throw new BizException("当前要删除的应用系统,id为:" + id + " 的数据不存在");
    }
  }
}
