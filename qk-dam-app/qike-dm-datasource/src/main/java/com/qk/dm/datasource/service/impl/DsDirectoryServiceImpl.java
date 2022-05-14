package com.qk.dm.datasource.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.datasource.entity.*;
import com.qk.dm.datasource.mapstruct.mapper.DsDirectoryMapper;
import com.qk.dm.datasource.repositories.DsDatasourceRepository;
import com.qk.dm.datasource.repositories.DsDirRepository;
import com.qk.dm.datasource.repositories.DsDirectoryRepository;
import com.qk.dm.datasource.service.DsDirectoryService;
import com.qk.dm.datasource.vo.DsDirectoryVO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据源管理应用系统录入接口实现
 *
 * @author zys
 * @date 20210729
 * @since 1.0.0 数据源管理应用系统录入接口实现
 */
@Service
@Transactional
public class DsDirectoryServiceImpl implements DsDirectoryService {
    private final QDsDirectory qDsDirectory = QDsDirectory.dsDirectory;
    private final QDsDir qDsDir = QDsDir.dsDir;
    private final DsDirectoryRepository dsDirectoryRepository;
    private final DsDirRepository dsDirRepository;
    private final EntityManager entityManager;
    private JPAQueryFactory jpaQueryFactory;
    private final DsDatasourceRepository dsDatasourceRepository;
    private final QDsDatasource qDsDatasource = QDsDatasource.dsDatasource;

    public DsDirectoryServiceImpl(DsDirectoryRepository dsDirectoryRepository,
                                  DsDirRepository dsDirRepository, EntityManager entityManager,
                                  DsDatasourceRepository dsDatasourceRepository) {
        this.dsDirectoryRepository = dsDirectoryRepository;
        this.dsDirRepository = dsDirRepository;
        this.entityManager = entityManager;
        this.dsDatasourceRepository = dsDatasourceRepository;
    }

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public PageResultVO<DsDirectoryVO> getSysDirectory(Pagination pagination) {
        Page<DsDirectory> pageList = dsDirectoryRepository.findAll(pagination.getPageable());
        List<DsDirectoryVO> dsDirectorielist =
                pageList.getContent()
                        .stream()
                        .map(DsDirectoryMapper.INSTANCE::useDsDirectoryVO)
                        .collect(Collectors.toList());
        // 获取总数量
        long count = jpaQueryFactory.select(qDsDirectory.count()).from(qDsDirectory).fetchOne();
        return new PageResultVO<>(count, pagination.getPage(), pagination.getSize(), dsDirectorielist);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSysDirectory(DsDirectoryVO dsDirectoryVO) {
        // 将vo转成数据库类
        DsDirectory dsDirectory = DsDirectoryMapper.INSTANCE.useDsDirectory(dsDirectoryVO);
        // 添加新增时间和修改时间
        dsDirectory.setGmtCreate(new Date());
        dsDirectory.setGmtModified(new Date());
        dsDirectory.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        // 根据应用系统名称判断数据库中是否存在，如果存在就抛出异常，否则新增
        boolean exists = dsDirectoryRepository.exists(qDsDirectory.sysName.eq(dsDirectory.getSysName()));
        if (exists) {
            throw new BizException("当前要新增的应用系统,名称为:" + dsDirectory.getSysName() + " 的数据，已存在！！！");
        }

        DsDirectory dsDirectorys = dsDirectoryRepository.save(dsDirectory);
        //根据应用系统名称判断是否存在目录
        boolean exists1 = dsDirRepository.exists(qDsDir.dicName.eq(dsDirectory.getSysName()));
        if (!exists1) {
            // 创建应用系统后创建对应的一级目录
            DsDir dsdir = getDsDir(dsDirectorys);
            dsDirRepository.save(dsdir);
        }
    }

    /**
     * 构建目录
     *
     * @param dsDirectory
     */
    private DsDir getDsDir(DsDirectory dsDirectory) {
        DsDir dsdir = new DsDir();
        //赋值目录id
        dsdir.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        //赋值应用id
        dsdir.setDsSystemId(dsDirectory.getId());
        // 赋值名称
        dsdir.setDicName(dsDirectory.getSysName());
        // 赋值父类编码
        dsdir.setParentId("0");
        // 创建时间
        dsdir.setGmtCreate(new Date());
        // 目录编码
        dsdir.setDsDirCode(UUID.randomUUID().toString().replaceAll("-", ""));
        // 逻辑删除(默认设置为0)
        dsdir.setDelFlag(0);
        return dsdir;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDsDirectory(String id) {
        DsDirectory dsDirectory = dsDirectoryRepository.findOne(qDsDirectory.id.eq(id)).orElse(null);
        if (Objects.isNull(dsDirectory)) {
            throw new BizException("当前要删除的应用系统,id为:" + id + " 的数据不存在");
        }
        chekDirDataSource(dsDirectory);
        dsDirectoryRepository.deleteById(id);
    }

    private void chekDirDataSource(DsDirectory dsDirectory) {
        BooleanExpression predicates = qDsDir.dsSystemId.eq(dsDirectory.getId());
        DsDir dsDir = dsDirRepository.findOne(predicates).orElse(null);
        if (!Objects.isNull(dsDir)) {
            BooleanExpression predicate = qDsDatasource.dicId.eq(String.valueOf(dsDir.getId()));
            List<DsDatasource> list = (List<DsDatasource>) dsDatasourceRepository.findAll(predicate);
            if (CollectionUtils.isNotEmpty(list)) {
                throw new BizException("当前删除的应用目录下存在数据源连接，请先删除连接后再操作");
            }
            dsDirRepository.deleteById(dsDir.getId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDsDirectory(DsDirectoryVO dsDirectoryVO) {
        // 将vo转成数据库类
        DsDirectory dsDirectory = DsDirectoryMapper.INSTANCE.useDsDirectory(dsDirectoryVO);
        // 添加新增时间和修改时间
        dsDirectory.setGmtModified(new Date());
        // 根据应用系统名称判断数据库中是否存在，如果存在就抛出异常，否则新增
        boolean exists = dsDirectoryRepository.exists(qDsDirectory.id.eq(dsDirectoryVO.getId()));
        if (!exists) {
            throw new BizException("当前要新增的应用系统,名称为:" + dsDirectory.getSysName() + " 的数据，不存在！！！");
        }
        chekDir(dsDirectory);
        dsDirectoryRepository.saveAndFlush(dsDirectory);
    }

    private void chekDir(DsDirectory dsDirectory) {
        DsDir dsDir = dsDirRepository.findOne(qDsDir.dsSystemId.eq(dsDirectory.getId())).orElse(null);
        if (!Objects.isNull(dsDir)) {
            getUpdateDsDir(dsDir, dsDirectory);
            dsDirRepository.saveAndFlush(dsDir);
        }
    }

    private void getUpdateDsDir(DsDir dsDir, DsDirectory dsDirectory) {
        // 赋值名称
        dsDir.setDicName(dsDirectory.getSysName());
        //修改人
        if (!Objects.isNull(dsDirectory.getUpdateUserid())) {
            dsDir.setUpdateUserid(String.valueOf(dsDirectory.getUpdateUserid()));
        }
        //修改时间
        if (!Objects.isNull(dsDirectory.getGmtModified())) {
            dsDir.setGmtModified(dsDirectory.getGmtModified());
        }
    }

    @Override
    public List<String> getSysName() {
        List<String> sysNameList = dsDirectoryRepository.getSysName();
        if(CollectionUtils.isEmpty(sysNameList)){
            throw new BizException("当前应用系统名称获取为空");
        }
        return sysNameList;
    }
}
