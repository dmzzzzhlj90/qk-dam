package com.qk.dm.dataservice.service.imp;

import com.google.common.collect.Maps;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.dataservice.spi.pojo.RouteData;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.biz.ApiSixProcessService;
import com.qk.dm.dataservice.entity.DasApiAppManageInfo;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.QDasApiAppManageInfo;
import com.qk.dm.dataservice.entity.QDasApiBasicInfo;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiAppManageInfoMapper;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiBasicInfoMapper;
import com.qk.dm.dataservice.repositories.DasApiAppManageInfoRepository;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.service.DasApiAppManageInfoService;
import com.qk.dm.dataservice.vo.DasApiAppManageInfoParamsVO;
import com.qk.dm.dataservice.vo.DasApiAppManageInfoVO;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * 数据服务_应用管理
 *
 * @author wjq
 * @date 2022/03/18
 * @since 1.0.0
 */
@Service
public class DasApiAppManageInfoServiceImpl implements DasApiAppManageInfoService {

    private static final QDasApiAppManageInfo qDasApiAppManageInfo = QDasApiAppManageInfo.dasApiAppManageInfo;
    private static final QDasApiBasicInfo qDasApiBasicInfo = QDasApiBasicInfo.dasApiBasicInfo;

    private final ApiSixProcessService apiSixProcessService;
    private final DasApiAppManageInfoRepository dasApiAppManageInfoRepository;
    private final DasApiBasicInfoRepository dasApiBasicInfoRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public DasApiAppManageInfoServiceImpl(ApiSixProcessService apiSixProcessService,
                                          DasApiAppManageInfoRepository dasApiAppManageInfoRepository,
                                          DasApiBasicInfoRepository dasApiBasicInfoRepository,
                                          JPAQueryFactory jpaQueryFactory) {
        this.apiSixProcessService = apiSixProcessService;
        this.dasApiAppManageInfoRepository = dasApiAppManageInfoRepository;
        this.dasApiBasicInfoRepository = dasApiBasicInfoRepository;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public PageResultVO<DasApiAppManageInfoVO> searchList(DasApiAppManageInfoParamsVO dasApiAppManageParamsVO) {
        List<DasApiAppManageInfoVO> dasApiAppManageInfoVOList = new ArrayList<>();
        Map<String, Object> map = null;
        try {
            map = queryDasApiAppManageInfoVOByParams(dasApiAppManageParamsVO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<DasApiAppManageInfo> list = (List<DasApiAppManageInfo>) map.get("list");
        long total = (long) map.get("total");

        list.forEach(
                dasApiAppManageInfo -> {
                    DasApiAppManageInfoVO dasApiAppManageInfoVO = transformToVO(dasApiAppManageInfo);
                    dasApiAppManageInfoVOList.add(dasApiAppManageInfoVO);
                });
        return new PageResultVO<>(
                total,
                dasApiAppManageParamsVO.getPagination().getPage(),
                dasApiAppManageParamsVO.getPagination().getSize(),
                dasApiAppManageInfoVOList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(DasApiAppManageInfoVO dasApiAppManageInfoVO) {
        Optional<DasApiAppManageInfo> optionalDasApiAppManageInfo = checkExistApiAppManageInfo(dasApiAppManageInfoVO);
        if (optionalDasApiAppManageInfo.isPresent()) {
            DasApiAppManageInfo dasApiAppManageInfo = optionalDasApiAppManageInfo.get();
            throw new BizException("当前要新增的应用管理名称为:" + dasApiAppManageInfo.getAppName() + " 的数据，已存在！！！");
        }
        DasApiAppManageInfo dasApiAppManageInfo = transformToEntity(dasApiAppManageInfoVO);
        dasApiAppManageInfo.setGmtCreate(new Date());
        dasApiAppManageInfo.setGmtModified(new Date());
        dasApiAppManageInfo.setCreateUserid("admin");
        dasApiAppManageInfo.setUpdateUserid("admin");
        dasApiAppManageInfo.setDelFlag(0);
        dasApiAppManageInfoRepository.save(dasApiAppManageInfo);
    }

    public Optional<DasApiAppManageInfo> checkExistApiAppManageInfo(DasApiAppManageInfoVO dasApiAppManageInfoVO) {
        Predicate predicate = qDasApiAppManageInfo.appName.eq(dasApiAppManageInfoVO.getAppName());
        return dasApiAppManageInfoRepository.findOne(predicate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DasApiAppManageInfoVO dasApiAppManageInfoVO) {
        checkUpdateParams(dasApiAppManageInfoVO);
        Predicate predicate = qDasApiAppManageInfo.id.eq(dasApiAppManageInfoVO.getId());
        Optional<DasApiAppManageInfo> optionalDasApiAppManageInfo = dasApiAppManageInfoRepository.findOne(predicate);
        if (optionalDasApiAppManageInfo.isEmpty()) {
            throw new BizException("当前要编辑的应用管理ID为:" + dasApiAppManageInfoVO.getId() + " 的数据，不存在！！！");
        }

        DasApiAppManageInfo dasApiAppManageInfo = transformToEntity(dasApiAppManageInfoVO);
        dasApiAppManageInfo.setGmtModified(new Date());
        dasApiAppManageInfo.setCreateUserid("admin");
        dasApiAppManageInfo.setUpdateUserid("admin");
        dasApiAppManageInfo.setDelFlag(0);
        dasApiAppManageInfoRepository.saveAndFlush(dasApiAppManageInfo);
    }

    private void checkUpdateParams(DasApiAppManageInfoVO dasApiAppManageInfoVO) {
        if (ObjectUtils.isEmpty(dasApiAppManageInfoVO.getId())) {
            throw new BizException("应用管理信息,ID参数为空,编辑失败!!!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBulk(List<Long> ids) {
        List<DasApiAppManageInfo> apiAppManageInfoList = dasApiAppManageInfoRepository.findAllById(ids);
        // 批量删除应用管理信息
        dasApiAppManageInfoRepository.deleteAllInBatch(apiAppManageInfoList);
    }

    @Override
    public List<RouteData> searchApiSixRouteInfo() {
        return apiSixProcessService.searchApiSixRouteInfo();
    }
    @Override
    public List<Map<String,String>> searchApiSixUpstreamInfo() {
        return apiSixProcessService.searchApiSixUpstreamInfo();
    }

    @Override
    public List<Map<String,String>> searchApiSixServiceInfo() {
        return apiSixProcessService.searchApiSixServiceInfo();
    }

    @Override
    public List<DasApiBasicInfoVO> relationApiList(String apiGroupRoutePath) {
        List<DasApiBasicInfoVO> dasApiBasicInfoVOList = Lists.newArrayList();
        // 根据路由组路径匹配API集合
        String apiPath = apiGroupRoutePath.split("\\*")[0];
        Iterable<DasApiBasicInfo> basicInfos = dasApiBasicInfoRepository.findAll(qDasApiBasicInfo.apiPath.like(apiPath + "%"));
        for (DasApiBasicInfo basicInfo : basicInfos) {
            DasApiBasicInfoVO dasApiBasicInfoVO = DasApiBasicInfoMapper.INSTANCE.useDasApiBasicInfoVO(basicInfo);
            dasApiBasicInfoVOList.add(dasApiBasicInfoVO);
        }
        return dasApiBasicInfoVOList;
    }

    private DasApiAppManageInfoVO transformToVO(DasApiAppManageInfo dasApiAppManageInfo) {
        return DasApiAppManageInfoMapper.INSTANCE.useDasApiAppManageInfoVO(dasApiAppManageInfo);
    }

    private DasApiAppManageInfo transformToEntity(DasApiAppManageInfoVO dasApiAppManageInfoVO) {
        return DasApiAppManageInfoMapper.INSTANCE.useDasApiAppManageInfo(dasApiAppManageInfoVO);
    }

    /**
     * 多条件查询应用管理信息列表
     *
     * @param dasApiAppManageParamsVO
     * @return
     */
    public Map<String, Object> queryDasApiAppManageInfoVOByParams(DasApiAppManageInfoParamsVO dasApiAppManageParamsVO) {
        Map<String, Object> result = Maps.newHashMap();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, qDasApiAppManageInfo, dasApiAppManageParamsVO);
        long count =
                jpaQueryFactory
                        .select(qDasApiAppManageInfo.count())
                        .from(qDasApiAppManageInfo)
                        .where(booleanBuilder)
                        .fetchOne();
        List<DasApiAppManageInfo> dasApiAppManageInfoList =
                jpaQueryFactory
                        .select(qDasApiAppManageInfo)
                        .from(qDasApiAppManageInfo)
                        .where(booleanBuilder)
                        .orderBy(qDasApiAppManageInfo.appName.asc())
                        .offset(
                                (dasApiAppManageParamsVO.getPagination().getPage() - 1)
                                        * dasApiAppManageParamsVO.getPagination().getSize())
                        .limit(dasApiAppManageParamsVO.getPagination().getSize())
                        .fetch();
        result.put("list", dasApiAppManageInfoList);
        result.put("total", count);
        return result;
    }

    /**
     * 条件信息
     *
     * @param dasApiAppManageParamsVO
     * @return
     */
    public void checkCondition(
            BooleanBuilder booleanBuilder,
            QDasApiAppManageInfo qDasApiAppManageInfo,
            DasApiAppManageInfoParamsVO dasApiAppManageParamsVO) {

        // 应用名称
        if (!ObjectUtils.isEmpty(dasApiAppManageParamsVO.getAppName())) {
            booleanBuilder.and(qDasApiAppManageInfo.appName.contains(dasApiAppManageParamsVO.getAppName()));
        }
        // API类型
        if (!ObjectUtils.isEmpty(dasApiAppManageParamsVO.getApiType())) {
            booleanBuilder.and(qDasApiAppManageInfo.apiType.eq(dasApiAppManageParamsVO.getApiType()));
        }
        // 开始时间--结束时间
        if (!ObjectUtils.isEmpty(dasApiAppManageParamsVO.getBeginDay())
                && !ObjectUtils.isEmpty(dasApiAppManageParamsVO.getEndDay())) {
            StringTemplate dateExpr =
                    Expressions.stringTemplate(
                            "DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qDasApiAppManageInfo.gmtModified);
            booleanBuilder.and(
                    dateExpr.between(
                            dasApiAppManageParamsVO.getBeginDay(), dasApiAppManageParamsVO.getEndDay()));
        }
    }
}
