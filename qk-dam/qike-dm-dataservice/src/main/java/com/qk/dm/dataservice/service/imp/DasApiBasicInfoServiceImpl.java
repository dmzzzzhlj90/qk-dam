package com.qk.dm.dataservice.service.imp;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.constant.DasConstant;
import com.qk.dm.dataservice.constant.RequestParamPositionEnum;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.QDasApiBasicInfo;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiBasicInfoMapper;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.repositories.DasApiCreateConfigRepository;
import com.qk.dm.dataservice.repositories.DasApiCreateSqlScriptRepository;
import com.qk.dm.dataservice.repositories.DasApiRegisterRepository;
import com.qk.dm.dataservice.service.DasApiBasicInfoService;
import com.qk.dm.dataservice.service.DasApiDirService;
import com.qk.dm.dataservice.vo.DasApiBasicInfoParamsVO;
import com.qk.dm.dataservice.vo.DasApiBasicInfoRequestParasVO;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;

/**
 * @author wjq
 * @date 20210816
 * @since 1.0.0
 */
@Service
public class DasApiBasicInfoServiceImpl implements DasApiBasicInfoService {
    private static final QDasApiBasicInfo qDasApiBasicInfo = QDasApiBasicInfo.dasApiBasicInfo;

    private final DasApiDirService dasApiDirService;
    private final DasApiBasicInfoRepository dasApiBasicinfoRepository;
    private final DasApiRegisterRepository dasApiRegisterRepository;
    private final DasApiCreateConfigRepository dasApiCreateConfigRepository;
    private final DasApiCreateSqlScriptRepository dasApiCreateSqlScriptRepository;

    private final EntityManager entityManager;
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    public DasApiBasicInfoServiceImpl(
            DasApiDirService dasApiDirService,
            DasApiBasicInfoRepository dasApiBasicinfoRepository,
            DasApiRegisterRepository dasApiRegisterRepository,
            DasApiCreateConfigRepository dasApiCreateConfigRepository,
            DasApiCreateSqlScriptRepository dasApiCreateSqlScriptRepository,
            EntityManager entityManager) {
        this.dasApiDirService = dasApiDirService;
        this.dasApiBasicinfoRepository = dasApiBasicinfoRepository;
        this.dasApiRegisterRepository = dasApiRegisterRepository;
        this.dasApiCreateConfigRepository = dasApiCreateConfigRepository;
        this.dasApiCreateSqlScriptRepository = dasApiCreateSqlScriptRepository;
        this.entityManager = entityManager;
    }

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public PageResultVO<DasApiBasicInfoVO> searchList(
            DasApiBasicInfoParamsVO dasApiBasicInfoParamsVO) {
        List<DasApiBasicInfoVO> dasApiBasicInfoVOList = new ArrayList<>();
        Map<String, Object> map = null;
        try {
            map = queryDasApiBasicInfoByParams(dasApiBasicInfoParamsVO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<DasApiBasicInfo> list = (List<DasApiBasicInfo>) map.get("list");
        long total = (long) map.get("total");

        list.forEach(
                dasApiBasicInfo -> {
                    DasApiBasicInfoVO dasApiBasicinfoVO = transformToVO(dasApiBasicInfo);
                    setDelInputParamVO(dasApiBasicInfo, dasApiBasicinfoVO);
                    dasApiBasicInfoVOList.add(dasApiBasicinfoVO);
                });
        return new PageResultVO<>(
                total,
                dasApiBasicInfoParamsVO.getPagination().getPage(),
                dasApiBasicInfoParamsVO.getPagination().getSize(),
                dasApiBasicInfoVOList);
    }

    @Override
    public void insert(DasApiBasicInfoVO dasApiBasicInfoVO) {
        Optional<DasApiBasicInfo> optionalDasApiBasicInfo = checkExistApiBasicInfo(dasApiBasicInfoVO);
        if (optionalDasApiBasicInfo.isPresent()) {
            DasApiBasicInfo dasApiBasicInfo = optionalDasApiBasicInfo.get();
            throw new BizException(
                    "当前要新增的API标准名称为:"
                            + dasApiBasicInfo.getApiName()
                            + ",API目录为:"
                            + dasApiBasicInfo.getApiPath()
                            + ",请求方式为:"
                            + dasApiBasicInfo.getRequestType()
                            + " 的数据，已存在！！！");
        }
        DasApiBasicInfo dasApiBasicInfo = transformToEntity(dasApiBasicInfoVO);
        setDedInputParamJson(dasApiBasicInfoVO, dasApiBasicInfo);
        dasApiBasicInfo.setGmtCreate(new Date());
        dasApiBasicInfo.setGmtModified(new Date());
        dasApiBasicInfo.setDelFlag(0);

        dasApiBasicinfoRepository.save(dasApiBasicInfo);
    }

    @Override
    public void update(DasApiBasicInfoVO dasApiBasicInfoVO) {
        Optional<DasApiBasicInfo> optionalDasApiBasicInfo = checkExistApiBasicInfo(dasApiBasicInfoVO);
        if (optionalDasApiBasicInfo.isEmpty()) {
            DasApiBasicInfo dasApiBasicInfo = optionalDasApiBasicInfo.get();
            throw new BizException(
                    "当前要新增的API标准名称为:"
                            + dasApiBasicInfo.getApiName()
                            + ",API目录为:"
                            + dasApiBasicInfo.getApiPath()
                            + ",请求方式为:"
                            + dasApiBasicInfo.getRequestType()
                            + " 的数据，不存在！！！");
        }

        DasApiBasicInfo dasApiBasicInfo = transformToEntity(dasApiBasicInfoVO);
        setDedInputParamJson(dasApiBasicInfoVO, dasApiBasicInfo);
        dasApiBasicInfo.setGmtModified(new Date());
        dasApiBasicInfo.setDelFlag(0);
        dasApiBasicinfoRepository.saveAndFlush(dasApiBasicInfo);
    }

    @Transactional
    @Override
    public void delete(Long delId) {
        Optional<DasApiBasicInfo> onDasApiBasicInfo =
                dasApiBasicinfoRepository.findOne(qDasApiBasicInfo.id.eq(delId));
        if (onDasApiBasicInfo.isPresent()) {
            // 根据API类型,对应清除配置信息
            DasApiBasicInfo dasApiBasicInfo = onDasApiBasicInfo.get();
            String apiType = dasApiBasicInfo.getApiType();
            String apiId = dasApiBasicInfo.getApiId();
            if (DasConstant.REGISTER_API_CODE.equalsIgnoreCase(apiType)) {
                dasApiRegisterRepository.deleteByApiId(apiId);
            }
            if (DasConstant.DM_SOURCE_API_CODE.equalsIgnoreCase(apiType)) {
                dasApiCreateConfigRepository.deleteByApiId(apiId);
                dasApiCreateSqlScriptRepository.deleteByApiId(apiId);
            }
            // 删除AIP基本信息
            dasApiBasicinfoRepository.deleteById(delId);
        }
    }

    @Transactional
    @Override
    public void deleteBulk(String ids) {
        //        List<String> idList = Arrays.asList(ids.split(","));
        //        Set<Long> idSet = new HashSet<>();
        //        idList.forEach(id -> idSet.add(Long.valueOf(id)));
        //        List<DasApiBasicInfo> apiBasicInfoList = dasApiBasicinfoRepository.findAllById(idSet);
        //        dasApiBasicinfoRepository.deleteInBatch(apiBasicInfoList);
    }

    @Override
    public List<Map<String, String>> getApiType() {
        return DasConstant.getApiType();
    }

    @Override
    public List<Map<String, String>> getDMSourceType() {
        return DasConstant.getDMSourceType();
    }

    @Override
    public Map<String, String> getRequestParasHeaderInfos() {
        return DasConstant.getRequestParasHeaderInfos();
    }

    @Override
    public Map<String, String> getRequestParamsPositions() {
        return RequestParamPositionEnum.getAllValue();
    }

    @Override
    public Optional<DasApiBasicInfo> searchApiBasicInfoByDelParamIsEmpty(
            DasApiBasicInfoVO dasApiBasicInfoVO) {
        Predicate predicate =
                qDasApiBasicInfo
                        .apiName
                        .eq(dasApiBasicInfoVO.getApiName())
                        .and(qDasApiBasicInfo.apiPath.eq(dasApiBasicInfoVO.getApiPath()))
                        .and(qDasApiBasicInfo.apiType.eq(dasApiBasicInfoVO.getApiType()))
                        .and(qDasApiBasicInfo.defInputParam.isNull());
        return dasApiBasicinfoRepository.findOne(predicate);
    }

    @Override
    public Optional<DasApiBasicInfo> checkExistApiBasicInfo(DasApiBasicInfoVO dasApiBasicInfoVO) {
        Predicate predicate =
                qDasApiBasicInfo
                        .apiName
                        .eq(dasApiBasicInfoVO.getApiName())
                        .and(qDasApiBasicInfo.apiPath.eq(dasApiBasicInfoVO.getApiPath()))
                        .and(qDasApiBasicInfo.apiType.eq(dasApiBasicInfoVO.getApiType()))
                        .and(qDasApiBasicInfo.apiType.eq(DasConstant.REGISTER_API_CODE));
        return dasApiBasicinfoRepository.findOne(predicate);
    }

    @Override
    public List<DasApiBasicInfoVO> findAllByApiType(String apiType) {
        List<DasApiBasicInfoVO> dasApiBasicInfoVOList = new ArrayList<>();
        Iterable<DasApiBasicInfo> apiBasicInfoIterable =
                dasApiBasicinfoRepository.findAll(qDasApiBasicInfo.apiType.eq(apiType));
        for (DasApiBasicInfo dasApiBasicInfo : apiBasicInfoIterable) {
            DasApiBasicInfoVO dasApiBasicInfoVO = transformToVO(dasApiBasicInfo);
            setDelInputParamVO(dasApiBasicInfo, dasApiBasicInfoVO);
            dasApiBasicInfoVOList.add(dasApiBasicInfoVO);
        }
        return dasApiBasicInfoVOList;
    }

    private DasApiBasicInfoVO transformToVO(DasApiBasicInfo dasApiBasicInfo) {
        return DasApiBasicInfoMapper.INSTANCE.useDasApiBasicInfoVO(dasApiBasicInfo);
    }

    private void setDelInputParamVO(
            DasApiBasicInfo dasApiBasicInfo, DasApiBasicInfoVO dasApiBasicinfoVO) {
        if (!ObjectUtils.isEmpty(dasApiBasicInfo.getDefInputParam())) {
            dasApiBasicinfoVO.setDasApiBasicInfoRequestParasVO(
                    GsonUtil.fromJsonString(
                            dasApiBasicInfo.getDefInputParam(),
                            new TypeToken<List<DasApiBasicInfoRequestParasVO>>() {
                            }.getType()));
        }
    }

    private DasApiBasicInfo transformToEntity(DasApiBasicInfoVO dasApiBasicInfoVO) {
        return DasApiBasicInfoMapper.INSTANCE.useDasApiBasicInfo(dasApiBasicInfoVO);
    }

    private void setDedInputParamJson(
            DasApiBasicInfoVO dasApiBasicInfoVO, DasApiBasicInfo dasApiBasicInfo) {
        if (!ObjectUtils.isEmpty(dasApiBasicInfoVO.getDasApiBasicInfoRequestParasVO())) {
            dasApiBasicInfo.setDefInputParam(
                    GsonUtil.toJsonString(dasApiBasicInfoVO.getDasApiBasicInfoRequestParasVO()));
        }
    }

    public Map<String, Object> queryDasApiBasicInfoByParams(
            DasApiBasicInfoParamsVO dasApiBasicInfoParamsVO) {
        Map<String, Object> result = new HashMap<>();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, qDasApiBasicInfo, dasApiBasicInfoParamsVO);
        long count =
                jpaQueryFactory
                        .select(qDasApiBasicInfo.count())
                        .from(qDasApiBasicInfo)
                        .where(booleanBuilder)
                        .fetchOne();
        List<DasApiBasicInfo> dasApiBasicInfoList =
                jpaQueryFactory
                        .select(qDasApiBasicInfo)
                        .from(qDasApiBasicInfo)
                        .where(booleanBuilder)
                        .orderBy(qDasApiBasicInfo.apiName.asc())
                        .offset(
                                (dasApiBasicInfoParamsVO.getPagination().getPage() - 1)
                                        * dasApiBasicInfoParamsVO.getPagination().getSize())
                        .limit(dasApiBasicInfoParamsVO.getPagination().getSize())
                        .fetch();
        result.put("list", dasApiBasicInfoList);
        result.put("total", count);
        return result;
    }

    public void checkCondition(
            BooleanBuilder booleanBuilder,
            QDasApiBasicInfo qDasApiBasicinfo,
            DasApiBasicInfoParamsVO dasApiBasicInfoParamsVO) {
        if (!ObjectUtils.isEmpty(dasApiBasicInfoParamsVO.getApiDirId())) {
            Set<String> apiDirIdSet = new HashSet<>();
            dasApiDirService.getApiDirId(apiDirIdSet, dasApiBasicInfoParamsVO.getApiDirId());
            booleanBuilder.and(qDasApiBasicinfo.apiId.in(apiDirIdSet));
        }
        if (!ObjectUtils.isEmpty(dasApiBasicInfoParamsVO.getApiName())) {
            booleanBuilder.and(qDasApiBasicinfo.apiName.contains(dasApiBasicInfoParamsVO.getApiName()));
        }
        if (!ObjectUtils.isEmpty(dasApiBasicInfoParamsVO.getApiId())) {
            booleanBuilder.and(qDasApiBasicinfo.apiId.eq(dasApiBasicInfoParamsVO.getApiId()));
        }
        if (!ObjectUtils.isEmpty(dasApiBasicInfoParamsVO.getBeginDay())
                && !StringUtils.isEmpty(dasApiBasicInfoParamsVO.getEndDay())) {
            StringTemplate dateExpr =
                    Expressions.stringTemplate(
                            "DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qDasApiBasicinfo.gmtModified);
            booleanBuilder.and(
                    dateExpr.between(
                            dasApiBasicInfoParamsVO.getBeginDay(), dasApiBasicInfoParamsVO.getEndDay()));
        }
    }
}
