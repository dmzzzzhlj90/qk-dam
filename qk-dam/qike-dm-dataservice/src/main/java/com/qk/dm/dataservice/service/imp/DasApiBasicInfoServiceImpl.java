package com.qk.dm.dataservice.service.imp;

import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.enums.DataTypeEnum;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.constant.*;
import com.qk.dm.dataservice.entity.*;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiBasicInfoMapper;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.repositories.DasApiCreateConfigRepository;
import com.qk.dm.dataservice.repositories.DasApiCreateSqlScriptRepository;
import com.qk.dm.dataservice.repositories.DasApiRegisterRepository;
import com.qk.dm.dataservice.service.DasApiBasicInfoService;
import com.qk.dm.dataservice.service.DasApiDirService;
import com.qk.dm.dataservice.vo.BulkDeleteParamVO;
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

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wjq
 * @date 20210816
 * @since 1.0.0
 */
@Service
public class DasApiBasicInfoServiceImpl implements DasApiBasicInfoService {
    private static final QDasApiBasicInfo qDasApiBasicInfo = QDasApiBasicInfo.dasApiBasicInfo;
    private static final QDasApiRegister qDasApiRegister = QDasApiRegister.dasApiRegister;
    private static final QDasApiCreateConfig qDasApiCreateConfig = QDasApiCreateConfig.dasApiCreateConfig;
    private static final QDasApiCreateSqlScript qDasApiCreateSqlScript = QDasApiCreateSqlScript.dasApiCreateSqlScript;

    private final DasApiDirService dasApiDirService;
    private final DasApiBasicInfoRepository dasApiBasicinfoRepository;
    private final DasApiRegisterRepository dasApiRegisterRepository;
    private final DasApiCreateConfigRepository dasApiCreateConfigRepository;
    private final DasApiCreateSqlScriptRepository dasApiCreateSqlScriptRepository;

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public DasApiBasicInfoServiceImpl(
            DasApiDirService dasApiDirService,
            DasApiBasicInfoRepository dasApiBasicinfoRepository,
            DasApiRegisterRepository dasApiRegisterRepository,
            DasApiCreateConfigRepository dasApiCreateConfigRepository,
            DasApiCreateSqlScriptRepository dasApiCreateSqlScriptRepository,
            JPAQueryFactory jpaQueryFactory) {
        this.dasApiDirService = dasApiDirService;
        this.dasApiBasicinfoRepository = dasApiBasicinfoRepository;
        this.dasApiRegisterRepository = dasApiRegisterRepository;
        this.dasApiCreateConfigRepository = dasApiCreateConfigRepository;
        this.dasApiCreateSqlScriptRepository = dasApiCreateSqlScriptRepository;
        this.jpaQueryFactory = jpaQueryFactory;

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
    @Transactional(rollbackFor = Exception.class)
    public void insert(DasApiBasicInfoVO dasApiBasicInfoVO) {
        DasApiBasicInfo dasApiBasicInfo = buildSaveApiBasicInfo(dasApiBasicInfoVO);
        dasApiBasicinfoRepository.save(dasApiBasicInfo);
    }

    /**
     * 构建批量保存Api基础信息
     *
     * @param dasApiBasicInfoVO
     */
    @Override
    public DasApiBasicInfo buildBulkSaveApiBasicInfo(DasApiBasicInfoVO dasApiBasicInfoVO) {
        return buildSaveApiBasicInfo(dasApiBasicInfoVO);
    }

    /**
     * 构建保存Api基础信息
     *
     * @param dasApiBasicInfoVO
     * @return
     */
    private DasApiBasicInfo buildSaveApiBasicInfo(DasApiBasicInfoVO dasApiBasicInfoVO) {
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
        dasApiBasicInfo.setStatus(SyncStatusEnum.CREATE_NO_UPLOAD.getCode());
        dasApiBasicInfo.setGmtCreate(new Date());
        dasApiBasicInfo.setGmtModified(new Date());
        dasApiBasicInfo.setCreateUserid("admin");
        dasApiBasicInfo.setDelFlag(0);
        return dasApiBasicInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DasApiBasicInfoVO dasApiBasicInfoVO) {
        dasApiBasicinfoRepository.saveAndFlush(buildUpdateApiBasicInfo(dasApiBasicInfoVO));
    }

    /**
     * 构建更新Api基础信息
     *
     * @param dasApiBasicInfoVO
     */
    @Override
    public DasApiBasicInfo buildUpdateApiBasicInfo(DasApiBasicInfoVO dasApiBasicInfoVO) {
        checkUpdateParams(dasApiBasicInfoVO);
        Predicate predicate = qDasApiBasicInfo.apiId.eq(dasApiBasicInfoVO.getApiId());
        Optional<DasApiBasicInfo> optionalDasApiBasicInfo = dasApiBasicinfoRepository.findOne(predicate);
        if (optionalDasApiBasicInfo.isEmpty()) {
            throw new BizException("当前要编辑的apiId为:" + dasApiBasicInfoVO.getApiId() + " 的数据，不存在！！！");
        }

        DasApiBasicInfo dasApiBasicInfo = transformToEntity(dasApiBasicInfoVO);
        setDedInputParamJson(dasApiBasicInfoVO, dasApiBasicInfo);

        dasApiBasicInfo.setGmtModified(new Date());
        dasApiBasicInfo.setCreateUserid("admin");
        dasApiBasicInfo.setDelFlag(0);
        return dasApiBasicInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long delId) {
        Optional<DasApiBasicInfo> onDasApiBasicInfo =
                dasApiBasicinfoRepository.findOne(qDasApiBasicInfo.id.eq(delId));
        if (onDasApiBasicInfo.isPresent()) {
            // 根据API类型,对应清除配置信息
            DasApiBasicInfo dasApiBasicInfo = onDasApiBasicInfo.get();
            String apiType = dasApiBasicInfo.getApiType();
            String apiId = dasApiBasicInfo.getApiId();
            if (ApiTypeEnum.REGISTER_API.getCode().equalsIgnoreCase(apiType)) {
                dasApiRegisterRepository.deleteByApiId(apiId);
            }
            if (ApiTypeEnum.CREATE_API.getCode().equalsIgnoreCase(apiType)) {
                //TODO 新建API 删除需要测试
                dasApiCreateConfigRepository.deleteByApiId(apiId);
                dasApiCreateSqlScriptRepository.deleteByApiId(apiId);
            }
            // 删除AIP基本信息
            dasApiBasicinfoRepository.deleteById(delId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBulk(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        Set<Long> idSet = new HashSet<>();
        idList.forEach(id -> idSet.add(Long.valueOf(id)));
        // 批量删除API基础信息
        List<DasApiBasicInfo> apiBasicInfoList = dasApiBasicinfoRepository.findAllById(idSet);
        dasApiBasicinfoRepository.deleteAll(apiBasicInfoList);

        // 获取API标识ID集合
        List<String> registerApiIds = apiBasicInfoList.stream()
                .filter(o -> ApiTypeEnum.REGISTER_API.getCode().equalsIgnoreCase(o.getApiType()))
                .map(DasApiBasicInfo::getApiId).collect(Collectors.toList());

        List<String> createApiIds = apiBasicInfoList.stream()
                .filter(o -> ApiTypeEnum.CREATE_API.getCode().equalsIgnoreCase(o.getApiType()))
                .map(DasApiBasicInfo::getApiId).collect(Collectors.toList());

        // 批量删除注册API配置信息
        Iterable<DasApiRegister> apiRegisterRepositoryAll = dasApiRegisterRepository.findAll(qDasApiRegister.apiId.in(registerApiIds));
        dasApiRegisterRepository.deleteAll(apiRegisterRepositoryAll);

        // 批量删除新建API配置信息
        // 新建配置
        Iterable<DasApiCreateConfig> dasApiCreateConfigRepositoryAll = dasApiCreateConfigRepository.findAll(qDasApiCreateConfig.apiId.in(createApiIds));
        dasApiCreateConfigRepository.deleteAll(dasApiCreateConfigRepositoryAll);
        // 新建脚本
        Iterable<DasApiCreateSqlScript> dasApiCreateSqlScriptRepositoryAll = dasApiCreateSqlScriptRepository.findAll(qDasApiCreateSqlScript.apiId.in(createApiIds));
        dasApiCreateSqlScriptRepository.deleteAll(dasApiCreateSqlScriptRepositoryAll);
    }

    @Override
    public Map<String, String> getApiType() {
        return ApiTypeEnum.getAllValue();
    }

    @Override
    public Map<String, String> getDMSourceType() {
        return DMSourceTypeEnum.getAllValue();
    }

    @Override
    public LinkedList<Map<String, Object>> getRequestParasHeaderInfos() {
        return RequestParamHeaderInfoEnum.getAllValue();
    }

    @Override
    public Map<String, String> getRequestParamsPositions() {
        return RequestParamPositionEnum.getAllValue();
    }

    @Override
    public Optional<DasApiBasicInfo> checkExistApiBasicInfo(DasApiBasicInfoVO dasApiBasicInfoVO) {
        Predicate predicate =
                qDasApiBasicInfo
                        .apiName
                        .eq(dasApiBasicInfoVO.getApiName())
                        .and(qDasApiBasicInfo.apiPath.eq(dasApiBasicInfoVO.getApiPath()))
                        .and(qDasApiBasicInfo.apiType.eq(ApiTypeEnum.REGISTER_API.getCode()));
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

    @Override
    public Map<String, String> getStatusInfo() {
        return SyncStatusEnum.getAllValue();
    }

    @Override
    public Map<String, String> getDataType() {
        return DataTypeEnum.getAllValue();
    }

    @Override
    public Map<String, String> getSyncType() {
        return ApiSyncTypeEnum.getAllValue();
    }

    @Override
    public LinkedList<Map<String, Object>> getDebugParamHeaderInfo() {
        return DebugApiParamHeaderInfoEnum.getAllValue();
    }

    private DasApiBasicInfoVO transformToVO(DasApiBasicInfo dasApiBasicInfo) {
        return DasApiBasicInfoMapper.INSTANCE.useDasApiBasicInfoVO(dasApiBasicInfo);
    }

    private void setDelInputParamVO(DasApiBasicInfo dasApiBasicInfo, DasApiBasicInfoVO dasApiBasicinfoVO) {
        if (!ObjectUtils.isEmpty(dasApiBasicInfo.getDefInputParam())) {
            dasApiBasicinfoVO.setApiBasicInfoRequestParasVOS(
                    GsonUtil.fromJsonString(dasApiBasicInfo.getDefInputParam(),
                            new TypeToken<List<DasApiBasicInfoRequestParasVO>>() {
                            }.getType()));
        }
    }

    private DasApiBasicInfo transformToEntity(DasApiBasicInfoVO dasApiBasicInfoVO) {
        return DasApiBasicInfoMapper.INSTANCE.useDasApiBasicInfo(dasApiBasicInfoVO);
    }

    private void setDedInputParamJson(DasApiBasicInfoVO dasApiBasicInfoVO, DasApiBasicInfo dasApiBasicInfo) {
        if (!ObjectUtils.isEmpty(dasApiBasicInfoVO.getApiBasicInfoRequestParasVOS())) {
            dasApiBasicInfo.setDefInputParam(GsonUtil.toJsonString(dasApiBasicInfoVO.getApiBasicInfoRequestParasVOS()));
        }
    }

    private void checkUpdateParams(DasApiBasicInfoVO dasApiBasicInfoVO) {
        if (ObjectUtils.isEmpty(dasApiBasicInfoVO.getId())) {
            throw new BizException("API基础信息,ID参数为空,编辑失败!!!");
        }

        if (ObjectUtils.isEmpty(dasApiBasicInfoVO.getApiId())) {
            throw new BizException("API基础信息,apiId参数为空,编辑失败!!!");
        }

        if (ObjectUtils.isEmpty(dasApiBasicInfoVO.getStatus())) {
            throw new BizException("API基础信息,状态status参数为空,编辑失败!!!");
        }
    }

    public Map<String, Object> queryDasApiBasicInfoByParams(DasApiBasicInfoParamsVO dasApiBasicInfoParamsVO) {
        Map<String, Object> result = Maps.newHashMap();
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

        // API目录ID
        if (!ObjectUtils.isEmpty(dasApiBasicInfoParamsVO.getDirId())) {
            booleanBuilder.and(qDasApiBasicinfo.dirId.eq(dasApiBasicInfoParamsVO.getDirId()));
        }
        // API名称
        if (!ObjectUtils.isEmpty(dasApiBasicInfoParamsVO.getApiName())) {
            booleanBuilder.and(qDasApiBasicinfo.apiName.contains(dasApiBasicInfoParamsVO.getApiName()));
        }
        // API标识ID
        if (!ObjectUtils.isEmpty(dasApiBasicInfoParamsVO.getApiId())) {
            booleanBuilder.and(qDasApiBasicinfo.apiId.eq(dasApiBasicInfoParamsVO.getApiId()));
        }
        // API类型
        if (!ObjectUtils.isEmpty(dasApiBasicInfoParamsVO.getApiType())) {
            booleanBuilder.and(qDasApiBasicinfo.apiType.eq(dasApiBasicInfoParamsVO.getApiType()));
        }
        // 状态
        if (!ObjectUtils.isEmpty(dasApiBasicInfoParamsVO.getStatus())) {
            booleanBuilder.and(qDasApiBasicinfo.status.eq(dasApiBasicInfoParamsVO.getStatus()));
        }
        // 开始时间--结束时间
        if (!ObjectUtils.isEmpty(dasApiBasicInfoParamsVO.getBeginDay())
                && !ObjectUtils.isEmpty(dasApiBasicInfoParamsVO.getEndDay())) {
            StringTemplate dateExpr =
                    Expressions.stringTemplate(
                            "DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qDasApiBasicinfo.gmtModified);
            booleanBuilder.and(
                    dateExpr.between(
                            dasApiBasicInfoParamsVO.getBeginDay(), dasApiBasicInfoParamsVO.getEndDay()));
        }
    }
}
