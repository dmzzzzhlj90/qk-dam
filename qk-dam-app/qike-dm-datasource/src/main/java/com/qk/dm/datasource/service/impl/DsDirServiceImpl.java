package com.qk.dm.datasource.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.ConnectBasicInfo;
import com.qk.dam.datasource.entity.ElasticSearchVO;
import com.qk.dam.datasource.entity.HiveInfo;
import com.qk.dam.datasource.entity.MysqlInfo;
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
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

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
    private JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    public DsDirServiceImpl(DsDirRepository dsDirRepository,
                            DsDatasourceRepository dsDatasourceRepository,
                            EntityManager entityManager) {
        this.dsDirRepository = dsDirRepository;
        this.dsDatasourceRepository = dsDatasourceRepository;
        this.entityManager = entityManager;
    }

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public void addDsDir(DsDirVO dsDirVO) {
        DsDir dsDir = DsDirMapper.INSTANCE.useDsDir(dsDirVO);
        dsDir.setGmtCreate(new Date());
        dsDir.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        boolean exists = dsDirRepository.exists(qDsDir.dsDirCode.eq(dsDir.getDsDirCode()));
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
        // 删除父级ID
        Optional<DsDir> dsdDirIsExist = dsDirRepository.findOne(qDsDir.id.eq(id));
        if (!dsdDirIsExist.isPresent()) {
            throw new BizException("参数有误,当前要删除的节点不存在！！！");
        }
        List<String> ids = List.of(id);
        getIds(ids, id);
        // 查询目录下是否存在数据源信息
        List<DsDatasourceVO> dsDatasourceVOList =
                ids.stream()
                        .map(dirid -> getDataSourceList(id))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());
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
        List<DsDirReturnVO> dsDirVOList = queryDir().stream().map(dsDir -> {
            DsDirReturnVO dsDirReturnVO = DsDirMapper.INSTANCE.useDsDirVO(dsDir);
            dsDirReturnVO.setKey(dsDir.getId());
            dsDirReturnVO.setTitle(dsDir.getDicName());
            return dsDirReturnVO;
        }).collect(Collectors.toList());
        return buildByRecursive(dsDirVOList);
    }

    private List<DsDir> queryDir() {
        return jpaQueryFactory.select(qDsDir).from(qDsDir).orderBy(qDsDir.gmtCreate.desc()).fetch();
    }

    private List<DsDirReturnVO> buildByRecursive(List<DsDirReturnVO> dsDirVOList) {
        DsDirReturnVO dsDirReturnVO = DsDirReturnVO.builder().key("0").title("全部数据源").build();
        List<DsDirReturnVO> trees = new ArrayList<>();
        trees.add(findChildren(dsDirReturnVO, dsDirVOList));
        return trees;
    }

    private DsDirReturnVO findChildren(DsDirReturnVO dsDirReturnVO, List<DsDirReturnVO> dsDirVOList) {
        dsDirVOList
                .stream()
                .filter(dsdtv -> dsDirReturnVO.getKey().equals(dsdtv.getParentId()))
                .peek(dsdtv -> {
                    if (dsDirReturnVO.getChildren() == null) {
                        dsDirReturnVO.setChildren(new ArrayList<>());
                    }
                    dsDirReturnVO.getChildren().add(findChildren(dsdtv, dsDirVOList));
                }).collect(Collectors.toList());
        return dsDirReturnVO;
    }

    /**
     * 递归查找子类并查询目录下对应的数据源信息
     *
     * @param dsDirReturnVO
     * @param dsDirVOList
     * @return
     */
    private DsDirReturnVO findDirChildren(DsDirReturnVO dsDirReturnVO, List<DsDirReturnVO> dsDirVOList) {
        dsDirReturnVO.setChildren(new ArrayList<>());
        // 将数据源转成目录
        List<DsDatasourceVO> datasourceList = getDataSourceList(dsDirReturnVO.getKey());
        List<DsDirReturnVO> dsDirReturnVOList = getDsDirReturnVoList(datasourceList);
        dsDirReturnVO.getChildren().addAll(dsDirReturnVOList);
        dsDirVOList
                .stream()
                .filter(dsdtv -> dsDirReturnVO.getKey().equals(dsdtv.getParentId()))
                .peek(dsdtv -> {
                    dsdtv.setDataType(DsConstant.DIR_TYPE);
                    if (dsDirReturnVO.getChildren() == null) {
                        dsDirReturnVO.setChildren(new ArrayList<>());
                    }
                    dsDirReturnVO.getChildren().add(findDirChildren(dsdtv, dsDirVOList));
                }).collect(Collectors.toList());
        return dsDirReturnVO;
    }

    private List<DsDatasourceVO> getDataSourceList(String dicid) {
        if (!StringUtils.isEmpty(dicid)) {
            return dsDatasourceRepository.getByDicId(dicid).stream().map(dsDatasource -> {
                DsDatasourceVO dsDatasourceVO = DSDatasourceMapper.INSTANCE.useDsDatasourceVO(dsDatasource);
                ConnectBasicInfo dsConnectBasicInfo = getConnectInfo(dsDatasource.getLinkType(), dsDatasource);
                dsDatasourceVO.setConnectBasicInfo(dsConnectBasicInfo);
                return dsDatasourceVO;
            }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * 将数据源信息转换成目录
     *
     * @param datasourceList
     * @return
     */
    private List<DsDirReturnVO> getDsDirReturnVoList(List<DsDatasourceVO> datasourceList) {
        return datasourceList.stream().map(dsDatasourceVO -> {
            DsDirReturnVO dsDirReturnVO = new DsDirReturnVO();
            // 赋值目录id
            dsDirReturnVO.setKey(String.valueOf(dsDatasourceVO.getId()));
            // 赋值目录名称
            dsDirReturnVO.setTitle(dsDatasourceVO.getDataSourceName());
            // 赋值父类名称
            dsDirReturnVO.setParentId(dsDatasourceVO.getDicId());
            // 赋值类型
            dsDirReturnVO.setDataType(DsConstant.DATASOURCE_TYPE);
            // 赋值数据源连接类型
            dsDirReturnVO.setConType(dsDatasourceVO.getLinkType());
            return dsDirReturnVO;
        }).collect(Collectors.toList());
    }

private ConnectBasicInfo getConnectInfo(String type, DsDatasource dsDatasource) {
    String dataSourceValues = dsDatasource.getDataSourceValues();
    Type typeToken;
    switch (ConnTypeEnum.locateEnum(type)) {
        case MYSQL:
            typeToken = new TypeToken<MysqlInfo>() {}.getType();
            break;
        case HIVE:
            typeToken = new TypeToken<HiveInfo>() {}.getType();
            break;
        case ELASTICSEARCH:
            typeToken = new TypeToken<ElasticSearchVO>() {}.getType();
            break;
        default:
            typeToken = new TypeToken<MysqlInfo>() {}.getType();
    }
    return GsonUtil.fromJsonString(dataSourceValues, typeToken);
}

    /**
     * 根据目录id获取目录下所有节点的目录id集合
     *
     * @param dsDicIdSet
     * @param dicId
     */
    @Override
    public void getDsdId(Set<String> dsDicIdSet, String dicId) {
        Iterable<DsDir> sonDirList = dsDirRepository.findAll(qDsDir.parentId.eq(dicId));
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
        List<DsDirReturnVO> dsDirVOList = queryDir().stream().map(dsDir -> {
            DsDirReturnVO dsDirReturnVO = DsDirMapper.INSTANCE.useDsDirVO(dsDir);
            dsDirReturnVO.setKey(dsDir.getId());
            dsDirReturnVO.setTitle(dsDir.getDicName());
            return dsDirReturnVO;
        }).collect(Collectors.toList());
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
        boolean exists = dsDirRepository.exists(qDsDir.id.eq(dsDir.getId()));
        if (exists) {
            dsDirRepository.saveAndFlush(dsDir);
        } else {
            throw new BizException(
                    "当前要更新的目录ID为：" + dsDir.getId() + "目录名称为:" + dsDir.getDicName() + " 的数据，不存在！！！");
        }
    }

    private List<DsDirReturnVO> buildByRecursives(List<DsDirReturnVO> dsDirVOList) {
        DsDirReturnVO dsDirReturnVO = DsDirReturnVO.builder().key("0").title("全部数据源").dataType(DsConstant.DIR_TYPE).build();
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
    private void getIds(List<String> ids, String id) {
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
