package com.qk.dm.reptile.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.google.common.collect.Maps;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.reptile.client.ClientUserInfo;
import com.qk.dm.reptile.constant.RptConstant;
import com.qk.dm.reptile.constant.RptRolesConstant;
import com.qk.dm.reptile.constant.RptRunStatusConstant;
import com.qk.dm.reptile.entity.QRptBaseInfo;
import com.qk.dm.reptile.entity.RptBaseInfo;
import com.qk.dm.reptile.enums.TimeIntervalEnum;
import com.qk.dm.reptile.factory.ReptileServerFactory;
import com.qk.dm.reptile.mapstruct.mapper.RptBaseInfoMapper;
import com.qk.dm.reptile.params.dto.RptBaseInfoDTO;
import com.qk.dm.reptile.params.dto.TimeIntervalDTO;
import com.qk.dm.reptile.params.vo.RptBaseInfoVO;
import com.qk.dm.reptile.repositories.RptBaseInfoRepository;
import com.qk.dm.reptile.service.RptBaseInfoService;
import com.qk.dm.reptile.service.RptConfigInfoService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 爬虫数据采集基础信息
 * @author wangzp
 * @date 2021/12/8 14:32
 * @since 1.0.0
 */
@Service
public class RptBaseInfoServiceImpl implements RptBaseInfoService {

    private JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    private final QRptBaseInfo qRptBaseInfo = QRptBaseInfo.rptBaseInfo;
    private final RptBaseInfoRepository rptBaseInfoRepository;
    private final RptConfigInfoService rptConfigInfoService;
    private final JwtDecoder jwtDecoder;

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    public RptBaseInfoServiceImpl(RptBaseInfoRepository rptBaseInfoRepository,
                                  EntityManager entityManager,
                                  RptConfigInfoService rptConfigInfoService,
                                  JwtDecoder jwtDecoder){
        this.rptBaseInfoRepository = rptBaseInfoRepository;
        this.entityManager = entityManager;
        this.rptConfigInfoService = rptConfigInfoService;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public void insert(RptBaseInfoDTO rptBaseInfoDTO) {
        RptBaseInfo rptBaseInfo = RptBaseInfoMapper.INSTANCE.userRtpBaseInfo(rptBaseInfoDTO);
        rptBaseInfo.setCreateUsername(ClientUserInfo.getUserName());
        rptBaseInfoRepository.save(rptBaseInfo);
    }

    @Override
    public void update(Long id, RptBaseInfoDTO rptBaseInfoDTO) {
        RptBaseInfo rptBaseInfo = rptBaseInfoRepository.findById(id).orElse(null);
        if (Objects.isNull(rptBaseInfo)) {
            throw new BizException("当前要修改的基础信息id为：" + id + " 的数据不存在！！！");
        }
        RptBaseInfoMapper.INSTANCE.of(rptBaseInfoDTO, rptBaseInfo);
        rptBaseInfoRepository.saveAndFlush(rptBaseInfo);

    }

    @Override
    public void updateRunStatus(Long id, Integer runStatus) {
        RptBaseInfo rptBaseInfo = rptBaseInfoRepository.findById(id).orElse(null);
        if (Objects.isNull(rptBaseInfo)) {
            throw new BizException("当前要修改的基础信息id为：" + id + " 的数据不存在！！！");
        }
        rptBaseInfo.setRunStatus(runStatus);
        rptBaseInfoRepository.saveAndFlush(rptBaseInfo);
    }

    @Override
    public RptBaseInfoVO detail(Long id) {
        Optional<RptBaseInfo> rptBaseInfo = rptBaseInfoRepository.findById(id);
        if(rptBaseInfo.isEmpty()){
            throw new BizException("当前要查询的基础信息id为：" + id + " 的数据不存在！！！");
        }
        return RptBaseInfoMapper.INSTANCE.userRtpBaseInfoVO(rptBaseInfo.get());
    }

    @Override
    public void delete(String ids) {
        Iterable<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<RptBaseInfo> rptBaseInfoList = rptBaseInfoRepository.findAllById(idSet);
        if(rptBaseInfoList.isEmpty()){
            throw new BizException("当前要删除的基础信息id为：" + ids + " 的数据不存在！！！");
        }
        rptBaseInfoRepository.saveAllAndFlush(rptBaseInfoList.stream().peek(e->e.setStatus(RptConstant.HISTORY)).collect(Collectors.toList()));

    }

    @Override
    public PageResultVO<RptBaseInfoVO> listByPage(RptBaseInfoDTO rptBaseInfoDTO, OAuth2AuthorizedClient authorizedClient) {
        Map<String, Object> map;
        try {
            map = queryByParams(rptBaseInfoDTO,authorizedClient);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<RptBaseInfo> list = (List<RptBaseInfo>) map.get("list");
        List<RptBaseInfoVO> voList = RptBaseInfoMapper.INSTANCE.of(list);
        if(RptConstant.WAITING.equals(rptBaseInfoDTO.getStatus())){
            voList = checkConfig(voList);
        }
        return new PageResultVO<>(
                (long) map.get("total"),
                rptBaseInfoDTO.getPagination().getPage(),
                rptBaseInfoDTO.getPagination().getSize(),
                voList);
    }

    @Override
    public void updateStatus(Long id,Integer status) {
        RptBaseInfo rptBaseInfo = rptBaseInfoRepository.findById(id).orElse(null);
        if(Objects.isNull(rptBaseInfo)){
            throw new BizException("当前要修改的基础信息id为：" + id + " 的数据不存在！！！");
        }
        rptBaseInfo.setStatus(status);
        rptBaseInfoRepository.saveAndFlush(rptBaseInfo);
    }

    @Override
    public void timedExecution(String timeInterval) {
    List<RptBaseInfo> list = rptBaseInfoRepository.findAllByRunStatusAndStatusAndTimeInterval(RptRunStatusConstant.START,RptConstant.REPTILE,timeInterval);
        if(!CollectionUtils.isEmpty(list)){
            list.forEach(e -> {
                String result = ReptileServerFactory.requestServer(rptConfigInfoService.rptList(e.getId()));
                if (!StringUtils.isBlank(result)) {
                  updateBaseInfo(e, result);
                }
          });
        }
    }


    /**
     * 判斷角色
     * @param authorizedClient
     * @return
     */
    private Boolean judgeRoles( OAuth2AuthorizedClient authorizedClient){
        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        JSONObject resource_access = (JSONObject) jwtDecoder.decode(accessToken.getTokenValue()).getClaims().get("resource_access");
        JSONObject reptile = (JSONObject)resource_access.get(RptRolesConstant.REPTILE);
        if(Objects.isNull(reptile)||Objects.isNull(reptile.get(RptRolesConstant.ROLES))){
            return false;
        }
        JSONArray roles = (JSONArray)reptile.get(RptRolesConstant.ROLES);
        return roles.contains(RptRolesConstant.DAM_ADMIN);
    }
    /**
     * 将爬虫接口返回的jobid存入数据库中
     * @param rptBaseInfo
     * @param result
     */
    public void updateBaseInfo(RptBaseInfo rptBaseInfo,String result){
        Map<String,String> map = GsonUtil.fromJsonString(result, Map.class);
        if(Objects.nonNull(map.get(RptConstant.JOBID))){
            rptBaseInfo.setJobId(map.get(RptConstant.JOBID));
            rptBaseInfo.setGmtFunction(new Date());
            rptBaseInfoRepository.saveAndFlush(rptBaseInfo);
        }
    }

    @Override
    public void execution(Long id) {
        RptBaseInfo rptBaseInfo = rptBaseInfoRepository.findById(id).orElse(null);
        if(Objects.isNull(rptBaseInfo)){
            throw new BizException("当前要修改的基础信息id为：" + id + " 的数据不存在！！！");
        }
        String result = ReptileServerFactory.requestServer(rptConfigInfoService.rptList(rptBaseInfo.getId()));
        if (!StringUtils.isBlank(result)) {
             updateBaseInfo(rptBaseInfo, result);
        }
    }

    @Override
    public void copyConfig(Long sourceId, Long targetId) {
       rptConfigInfoService.copyConfig(sourceId,targetId);
    }

    @Override
    public void updateTimeInterval(TimeIntervalDTO timeIntervalDTO) {
        RptBaseInfo rptBaseInfo = rptBaseInfoRepository.findById(timeIntervalDTO.getId()).orElse(null);
        if(Objects.isNull(rptBaseInfo)){
            throw new BizException("当前要修改的基础信息id为：" + timeIntervalDTO.getId() + " 的数据不存在！！！");
        }
        rptBaseInfo.setTimeInterval(timeIntervalDTO.getTimeInterval());
        rptBaseInfo.setRunPeriod(TimeIntervalEnum.of(timeIntervalDTO.getTimeInterval()));
        rptBaseInfoRepository.saveAndFlush(rptBaseInfo);
    }

    private Map<String, Object> queryByParams(RptBaseInfoDTO rptBaseInfoDTO,OAuth2AuthorizedClient authorizedClient) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, rptBaseInfoDTO,authorizedClient);
        Map<String, Object> result = Maps.newHashMap();
        if(StringUtils.isBlank(ClientUserInfo.getUserName())){
            result.put("list", Collections.emptyList());
            result.put("total", (long)0);
            return result;
        }
        long count = jpaQueryFactory.select(qRptBaseInfo.count()).from(qRptBaseInfo).where(booleanBuilder).fetchOne();
        List<RptBaseInfo> rptBaseInfoList = jpaQueryFactory
                        .select(qRptBaseInfo)
                        .from(qRptBaseInfo)
                        .where(booleanBuilder)
                        .orderBy(qRptBaseInfo.id.desc())
                        .offset((long) (rptBaseInfoDTO.getPagination().getPage() - 1)
                                        * rptBaseInfoDTO.getPagination().getSize())
                        .limit(rptBaseInfoDTO.getPagination().getSize()).fetch();
        result.put("list", rptBaseInfoList);
        result.put("total", count);
        return result;
    }

    public void checkCondition(BooleanBuilder booleanBuilder, RptBaseInfoDTO rptBaseInfoDTO,OAuth2AuthorizedClient authorizedClient) {
        if(!judgeRoles(authorizedClient)){
           booleanBuilder.and(qRptBaseInfo.createUsername.contains(ClientUserInfo.getUserName()));
        }
        if (!StringUtils.isEmpty(rptBaseInfoDTO.getWebsiteName())) {
            booleanBuilder.and(qRptBaseInfo.websiteName.contains(rptBaseInfoDTO.getWebsiteName()));
        }
        if (!StringUtils.isEmpty(rptBaseInfoDTO.getConfigName())) {
            booleanBuilder.and(qRptBaseInfo.configName.contains(rptBaseInfoDTO.getConfigName()));
        }
        if (!StringUtils.isEmpty(rptBaseInfoDTO.getWebsiteUrl())) {
           booleanBuilder.and(qRptBaseInfo.websiteUrl.contains(rptBaseInfoDTO.getWebsiteUrl()));
        }
        if (!StringUtils.isEmpty(rptBaseInfoDTO.getCreateUsername())) {
            booleanBuilder.and(qRptBaseInfo.createUsername.contains(rptBaseInfoDTO.getCreateUsername()));
        }
        if(Objects.nonNull(rptBaseInfoDTO.getStatus())){
            booleanBuilder.and(qRptBaseInfo.status.eq(rptBaseInfoDTO.getStatus()));
        }
        if(Objects.nonNull(rptBaseInfoDTO.getConfigName())){
            booleanBuilder.and(qRptBaseInfo.configName.contains(rptBaseInfoDTO.getConfigName()));
        }
        if(Objects.nonNull(rptBaseInfoDTO.getStartDate())){
            booleanBuilder.and(qRptBaseInfo.configDate.goe(rptBaseInfoDTO.getStartDate()));
        }
        if(Objects.nonNull(rptBaseInfoDTO.getEndDate())){
            booleanBuilder.and(qRptBaseInfo.configDate.loe(rptBaseInfoDTO.getEndDate()));
        }

    }

    /**
     * 是否添加了配置信息
     * @param rptBaseInfoList
     */
    private List<RptBaseInfoVO> checkConfig(List<RptBaseInfoVO> rptBaseInfoList){
        if(CollectionUtils.isEmpty(rptBaseInfoList)){return Collections.emptyList();}
        rptBaseInfoList.forEach(e->{
            e.setConfigStatus(!CollectionUtils.isEmpty(rptConfigInfoService.rptList(e.getId())));
        });
        return rptBaseInfoList;
    }
}
